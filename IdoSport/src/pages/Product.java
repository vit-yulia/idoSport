package pages;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Product extends Base {

	Actions action;

	public Product(WebDriver driver) {
		super(driver);
	}
	
	
	public boolean chooseProduct() {
		if (isExist(By.cssSelector("Button.single_add_to_cart_button.button.alt.disabled"))) {
			Select select = new Select(driver.findElement(By.xpath("//table//select")));
			select.selectByIndex(1);

			return true;
		} else
			return false;
	}
	
	public boolean addToCart() throws InterruptedException {
		click(By.cssSelector(".single_add_to_cart_button.button"));
		Thread.sleep(2000);
		
		String items = driver.findElement(By.xpath("((//a[@id=\"elementor-menu-cart__toggle_button\"])[1]/span)[2]")).getAttribute("data-counter");		
		
		return items.equals("1");

	}
	


	public void selectDate() throws InterruptedException {

		click(By.id("shipping_delivery_date"));
		Thread.sleep(2000);
		List<WebElement> days = driver.findElements(By.xpath("//table[@id='grid-shipping_delivery_date']//td"));

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day   = localDate.getDayOfMonth();
		day = day + 3;

		for (WebElement e : days) {
			// System.out.println(e.getText());
			if (e.getText().equals(String.valueOf(day))) {
				e.click();
				break;
				// }

			}
		}
	}
	
	public boolean goToCart() throws InterruptedException {
		click(By.cssSelector("#elementor-menu-cart__toggle_button"));
		Thread.sleep(3000);
		String item = driver.findElement(By.xpath("/html/body/div[2]/div/section[1]/div/div/div/div/div/section[2]/div/div/div[3]/div/div/div/div/div/div[1]/div")).getAttribute("aria-expanded");
		
		return item.equals("true");
		
	}
	
//	public boolean deleteFromCart() {
//		click(By.xpath("(//a[@data-product_id='2929'])[1]"));
//
//
//		
//	}
//	


}
