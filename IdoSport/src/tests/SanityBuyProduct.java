package tests;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.Main;
import pages.Product;
import utilites.GetDriver;
import utilites.Utilities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;


public class SanityBuyProduct {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\IdoSport.html";

	private WebDriver driver;
	private String baseUrl;
	
	
	//pages
	private Main main;
	private Product product;
	
	

	@BeforeClass
	public void beforeClass() {
		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\IdoSport-extent-config.xml"));
		baseUrl = "https://www.idosport.co.il/";
		driver = GetDriver.getDriver("chrome", baseUrl);
		
		main = new Main(driver);
		product = new Product(driver);

	}

	
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
	

	
	/*  Prerequisite: getting into https://www.idosport.co.il//
	 * 		Given: Client is in site 
	 * 		When: perform search
	 *  	Then: Getting results
	 */
	
	@Test(priority = 1, enabled = true, description = "verify date")
	public void searchProduct() throws InterruptedException, IOException {	
		Assert.assertTrue(main.search("משקולות יד דאמבלס משושה עם אחיזה נוחה לידיים"));
	}
	
	
	/*  Prerequisite: Do search
	 * 		Given: Client is in search results 
	 * 		When: click on result
	 *  	Then: Getting into product
	 */
	@Test(priority = 2, enabled = true, description = "verify date")
	public void getIntoProduct() throws InterruptedException, IOException {	
		Assert.assertTrue(main.getIntoProduct("משקולות יד דאמבלס משושה עם אחיזה נוחה לידיים"));
	}
	
	/*  Prerequisite: choose a product
	 * 		Given: Client is in product page
	 * 		When: click on weight of product
	 *  	Then: Getting product into cart
	 */
	@Test(priority = 3, enabled = true, description = "choose products")
	public void EditProduct() throws InterruptedException, IOException {	
		Assert.assertTrue(product.chooseProduct());
	}
	
	
	/*  Prerequisite: Do add product to cart
	 * 		Given: Client is in product page 
	 * 		When: click on add to cart
	 *  	Then: Getting product into cart
	 */
	@Test(priority = 4, enabled = true, description = "add product to Cart")
	public void AddtoCart() throws InterruptedException, IOException {	
		Assert.assertTrue(product.addToCart());
	}
	

	@Test(priority = 5, enabled = true, description = "go to Cart")
	public void goToCart() throws InterruptedException, IOException {	
		Assert.assertTrue(product.goToCart());
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " + result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));
		}
		else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);
	
		//return to base URL 
		//driver.get(baseUrl);
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}

	
}
