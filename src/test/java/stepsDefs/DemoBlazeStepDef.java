package stepsDefs;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlazeStepDef {
	static WebDriver driver;
	static WebDriverWait wait;
	String search,beforePrice,afterPrice;
	WebElement rate;
	List<WebElement> items;
	
	@BeforeAll
	public static void setup() {
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
		wait=new WebDriverWait(driver,Duration.ofMinutes(1));
		driver.get("https://www.demoblaze.com/");
	}
	@Given("User into the login page")
	public void user_into_the_login_page() {
		driver.findElement(By.xpath("//a[contains(text(),'Log in')]")).click();
	}
	@When("User enters login credientials")
	public void user_enters_login_credientials() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.findElement(By.xpath("//input[@id='loginusername']")).sendKeys("ShriAthi");
		driver.findElement(By.xpath("//input[@id='loginpassword']")).sendKeys("Shri.01");
		driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();
	}
	@Then("Should Display Home Page")
	public void should_display_home_page() {
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(text(),'Welcome ShriAthi')]")).getText(), "Welcome ShriAthi");
	}
	@When("Add an Item {string} to {string} Cart")
	public void add_an_item_to_cart(String catagory, String item) {
		driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
		String catagoryPath="//a[text()='"+catagory+"']";
		driver.findElement(By.xpath(catagoryPath)).click();
		String searchPath="//a[text()='"+item+"']";
		driver.findElement(By.xpath(searchPath)).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		alert.accept();
		search=item;

	}
	@Then("Items must be added to cart")
	public void items_must_be_added_to_cart() {
		driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
		List<WebElement> items=driver.findElements(By.xpath("//td[2]"));
		boolean flag=false;
		for(WebElement test:items) {
			if(test.getText().equalsIgnoreCase(search)) {
				Assert.assertEquals(test.getText(),search);
				flag=true;
			}
		}
		Assert.assertTrue(flag);
	}
	@When("List of items should be available in Cart")
	public void list_of_items_should_be_available_in_cart() {
		driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
		List<WebElement> items=driver.findElements(By.xpath("//tbody/tr"));
		Assert.assertTrue(items.size()!=0);
		rate=driver.findElement(By.id("totalp"));
		beforePrice=rate.getText();
		System.out.println(beforePrice);
	}
	@Then("Delete an item from cart")
	public void delete_an_item_from_cart() throws InterruptedException {
		driver.findElement(By.xpath("(//td[4]//a)[1]")).click();
		Thread.sleep(3000);
		rate=driver.findElement(By.id("totalp"));
		afterPrice=rate.getText();
		System.out.println(afterPrice);
		Assert.assertNotEquals(beforePrice, afterPrice);
	}
	@When("Items Should be available in Cart")
	public void items_should_be_available_in_cart() {
		driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
		items=driver.findElements(By.xpath("//tbody/tr"));
		Assert.assertTrue(items.size()!=0);
	}
	@Then("Place order")
	public void place_order() {
		if(items.size()!=0) {
			driver.findElement(By.xpath("//button[contains(text(),'Place Order' )]")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Purchase')]")));
			wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			driver.findElement(By.xpath("//input[@id='name']")).sendKeys("ShriAthi");
			driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
			driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Tirunelveli");
			driver.findElement(By.xpath("//input[@id='card']")).sendKeys("1234 4567 7890 0123");
			driver.findElement(By.xpath("//input[@id='month']")).sendKeys("September");
			driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2023");
		}
	}
	@Then("Purchase Items")
	public void purchase_items() throws InterruptedException {
		driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("(//h2)[3]")).getText(), "Thank you for your purchase!");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
	}
	
	@After
	public void attachImgToReport(Scenario scenario) {
		TakesScreenshot scr=(TakesScreenshot)driver;
		byte[] img=scr.getScreenshotAs(OutputType.BYTES);
		scenario.attach(img, "image/png", "imageOne");
		
	}
	@AfterAll
	public static void finish() {
		driver.close();
	}


}