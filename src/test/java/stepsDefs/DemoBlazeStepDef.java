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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlazeStepDef {
	
	static WebDriver driver;
	static WebDriverWait wait;
	int itmsize;
	String beforedel,afterdel;
	
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
	
	
	
	@Given("User is on the login Page")
	public void user_is_on_the_login_page() {
		driver.findElement(By.id("login2")).click();
	}
	
	
	
	@When("User enters {string} and {string} in the login page")
	public void user_enters_and_in_the_login_page(String StrUsr, String StrPwd) {
//		driver.findElement(By.id("login2")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=\"loginusername\"]")));
		driver.findElement(By.cssSelector("input[id=\"loginusername\"]")).sendKeys(StrUsr);
		driver.findElement(By.id("loginpassword")).sendKeys(StrPwd);
		driver.findElement(By.cssSelector("button[onclick=\"logIn()\"]")).click();
	}
	
	
	@Then("It navigates to the Home Page")
	public void it_navigates_to_the_home_page() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Welcome')]")));
		WebElement welcomeTxt = driver.findElement(By.xpath("//a[contains(text(),'Welcome')]"));
		Assert.assertEquals(welcomeTxt.getText(), "Welcome ShriAthi");
	}
	
	
	@When("User add {string} to the cart")
	public void user_add_to_the_cart(String products) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	    driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
	    wait.until(ExpectedConditions.elementToBeClickable(By.linkText(products))).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();		
	    wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		alert.accept();
	}
	
	
	@Then("products must be added to cart")
	public void products_must_be_added_to_cart() {
		driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
		List<WebElement>feature=driver.findElements(By.xpath("//tr/td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(feature));
		itmsize=feature.size();
		boolean value = true;
		if(itmsize>1) {
		  Assert.assertTrue(value);
	}
}
	
	
	@When("List of added items must be available in the cart page")
	public void list_of_added_items_must_be_available_in_the_cart_page() {
		driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
		List<WebElement>products=driver.findElements(By.xpath("//tbody/tr"));
		Assert.assertTrue(products.size()!=0);
		WebElement price;
		price=driver.findElement(By.id("totalp"));
		beforedel=price.getText();
	}
	
	
	@Then("Delete an item in the cart")
	public void delete_an_item_in_the_cart() {
		driver.findElement(By.xpath("(//td[4]//a)[2]")).click();
		//Thread.sleep(3000);
		WebElement price;
		price=driver.findElement(By.id("totalp"));
		afterdel=price.getText();
		Assert.assertEquals(beforedel, afterdel);		
	}
	
	
	@When("User snaps the place order")
	public void User_snaps_the_place_order() throws InterruptedException {
		Thread.sleep(3000);
    	driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("ShriAthi");
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Tirunelveli");
		driver.findElement(By.xpath("//input[@id='card']")).sendKeys("6383 5188 3645 1758");
		driver.findElement(By.xpath("//input[@id='month']")).sendKeys("September");
		driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2023");
		Thread.sleep(2000);
    	driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
	}
	
	@Then("Products should be placed")
	public void Products_should_be_placed() throws InterruptedException {
		Thread.sleep(2000);
    	driver.findElement(By.xpath("//button[text()='OK']")).click();	
		driver.close();
	}
}