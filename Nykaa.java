package week4.day2.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		ChromeDriver driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// 1) Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");

		Actions builder = new Actions(driver);

		// 2) Mouseover on Brands and Search L'Oreal Paris
		WebElement brands = driver.findElement(By.xpath("//a[text()='brands']"));
		builder.moveToElement(brands).perform();
		driver.findElement(By.id("brandSearchBox")).sendKeys("L'Oreal Paris");

		// 3) Click L'Oreal Paris
		driver.findElement(By.xpath("//div[@class='css-ov2o3v']//a")).click();

		// 4) Check the title contains L'Oreal Paris(Hint-GetTitle)
		String brandTitle = driver.getTitle();
		if (brandTitle.contains("L'Oreal Paris"))
			System.out.println("Brand is L'Oreal Paris");
		else
			System.out.println("Brand is not L'Oreal Paris");

		// 5) Click sort By and select customer top rated
		driver.findElement(By.className("sort-name")).click();
		driver.findElement(By.xpath("(//div[@class='control-indicator radio '])[3]")).click();

		// 6) Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.xpath("//span[text()='Category']")).click();
		driver.findElement(By.xpath("//span[text()='Hair']")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		driver.findElement(By.xpath("//div[@class='control-indicator checkbox ']")).click();

		// 7) Click->Concern->Color Protection
		driver.findElement(By.xpath("//div[@id='filters-strip']/div[1]/div[1]/div[6]/div[1]")).click();
		driver.findElement(By.xpath("(//label[@for='checkbox_Color Protection_10764']//div)[2]")).click();

		// 8)check whether the Filter is applied with Shampoo
		String filter = driver.findElement(By.xpath("//span[text()='Shampoo']")).getText();
		if (filter.contains("Shampoo"))
			System.out.println("Shampoo filter is selected");
		else
			System.out.println("Filter is not selected");

		// 9) Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElement(By.xpath("//div[text()=\"L'Oreal Paris Colour Protect Shampoo\"][1]")).click();

		// 10) GO to the new window and select size as 175ml
		Set<String> handles = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(handles);
		driver.switchTo().window(winList.get(1));

		// 11) Print the MRP of the product
		String price = driver.findElement(By.xpath("//span[text()='₹150']")).getText();
		System.out.println("Price of product is: " + price);

		// 12) Click on ADD to BAG
		driver.findElement(By.xpath("//span[text()=\"ADD TO BAG\"][1]")).click();

		// 13) Go to Shopping Bag
		driver.findElement(By.className("cart-count")).click();

		// 14) Print the Grand Total amount
		driver.switchTo().frame(0);
		String grandTotal = driver.findElement(By.xpath("//div[@class='value medium-strong']")).getText();
		System.out.println("Grand total is: " + grandTotal);

		// 15) Click Proceed
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();

		// 16) Click on Continue as Guest
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();

		// 17) Check if this grand total is the same in step 14
		String grandTotal2 = driver.findElement(By.xpath("(//div[@class='value']//span)[2]")).getText();
		// Adding ₹ symbol to grandtotal in address page
		grandTotal2 = "₹" + grandTotal2;
		if (grandTotal.equals(grandTotal2))
			System.out.println("Grand total is same");
		else
			System.out.println("Grand total is different");

		// 18) Close all windows
		driver.quit();
	}

}
