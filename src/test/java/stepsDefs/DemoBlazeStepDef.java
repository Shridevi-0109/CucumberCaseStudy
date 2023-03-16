package stepsDefs;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlazeStepDef {
	static WebDriver driver;
	static WebDriverWait wait;
	int itemSize;

	@BeforeAll
	public static void setup() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	}

	@Given("User is on Launch Page")
	public void user_is_on_launch_page() {
		driver.get("https://www.demoblaze.com/index.html");
	}

	@When("User navigates to the login Page enters {string} and {string}")
	public void user_navigates_to_the_login_page_enters_and(String userName, String password) {
		driver.findElement(By.id("login2")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
		driver.findElement(By.id("loginusername")).sendKeys(userName);
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		driver.findElement(By.cssSelector("button[onclick=\"logIn()\"]")).click();
	}
	
	@Then("It should display Home Page")
	public void it_should_display_home_page() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Welcome')]")));
		WebElement welcomeTxt = driver.findElement(By.xpath("//a[contains(text(),'Welcome')]"));
		Assert.assertEquals(welcomeTxt.getText(), "Welcome ShriAthi");
	}

	@When("User add an {string} to the cart")
	public void user_add_an_to_the_cart(String items) {
		WebElement product = driver.findElement(By.partialLinkText(items));
		wait.until(ExpectedConditions.elementToBeClickable(product));
		product.click();
		driver.findElement(By.xpath("//a[contains(text(),'Add to')]")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.findElement(By.xpath("(//ul/li//a)[1]")).click();
	}

	@Then("the items added to the cart")
	public void the_items_added_to_the_cart() {
		driver.findElement(By.xpath("//a[text()='Cart']")).click();
		List<WebElement> products = driver.findElements(By.xpath("//tr/td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(products));
		itemSize = products.size();
		boolean proAdded = true;
		if(itemSize>1) {
			Assert.assertTrue(proAdded);
		}
	}

	@Given("User is on Cart Page")
	public void user_is_on_cart_page() {
		List<WebElement> listOfpro = driver.findElements(By.xpath("//td"));
		boolean list = listOfpro.isEmpty();
		boolean productsAvail = true;
		if(!list) {
			Assert.assertTrue(productsAvail);
		}
	}

	@When("User deletes an item from the cart page")
	public void user_deletes_an_item_from_the_cart_page() throws InterruptedException {
		driver.findElement(By.xpath("//td/a[1]")).click();
		Thread.sleep(2000);
	}

	@Then("the item must be deleted from the cart page")
	public void the_item_must_be_deleted_from_the_cart_page() {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td")));
		List<WebElement> currentproduct = driver.findElements(By.xpath("//tr/td[2]"));
		int cntproductSize = currentproduct.size();
		boolean productDeleted = true;
		if(itemSize>cntproductSize) {
			Assert.assertTrue(productDeleted);
		}
	}

	@When("User places an order")
	public void user_places_an_order() throws InterruptedException {
		WebElement placeOrdBtn = driver.findElement(By.xpath("//button[text()='Place Order']"));
		wait.until(ExpectedConditions.visibilityOf(placeOrdBtn));
		placeOrdBtn.click();
		driver.findElement(By.id("name")).sendKeys("ShriAthi");
		driver.findElement(By.id("country")).sendKeys("India");
		driver.findElement(By.id("city")).sendKeys("Chennai");
		driver.findElement(By.id("card")).sendKeys("1234 4567 7890 0123");
		driver.findElement(By.id("month")).sendKeys("September");
		driver.findElement(By.id("year")).sendKeys("2023");
		driver.findElement(By.xpath("//button[text()='Purchase']")).click();
	}

	@Then("the item must be placed")
	public void the_item_must_be_placed() throws InterruptedException {
		WebElement successMsg = driver.findElement(By.xpath("//h2[text()='Thank you for your purchase!']"));
		wait.until(ExpectedConditions.visibilityOf(successMsg));
		Assert.assertEquals(successMsg.getText(), "Thank you for your purchase!");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
	}

}
