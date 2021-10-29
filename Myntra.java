package week4.day2.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Myntra {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		ChromeDriver driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// 1) Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");

		Actions builder = new Actions(driver);

		// 2) Mouse hover on Men
		builder.moveToElement(driver.findElement(By.xpath("//a[@class='desktop-main']"))).perform();

		// 3) Click Jackets
		driver.findElement(By.xpath("//a[@href='/men-jackets']")).click();

		// 4) Find the total count of item
		String totalCount = driver.findElement(By.className("title-count")).getText();
		String totalCountnumberOnly = totalCount.replaceAll("[^\\d]", "");
		int countTotal = Integer.parseInt(totalCountnumberOnly);
		System.out.println("Total count is: " + countTotal);

		// 5) Validate the sum of categories count matches
		String jackets = driver.findElement(By.xpath("//label[text()='Jackets']")).getText();
		String rainJacket = driver.findElement(By.xpath("//label[text()='Rain Jacket']")).getText();
		String jacketsnumberOnly = jackets.replaceAll("[^\\d]", "");
		String rainJacketsnumberOnly = rainJacket.replaceAll("[^\\d]", "");
		int result = Integer.parseInt(jacketsnumberOnly) + Integer.parseInt(rainJacketsnumberOnly);
		System.out.println("Sum of Jackets and Rain Jacket: " + result);
		if (result == countTotal)
			System.out.println("Total count matches");
		else
			System.out.println("Total count does not match");

		// 6) Check jackets
		driver.findElement(By.xpath("//div[@class='common-checkboxIndicator'][1]")).click();

		// 7) Click + More option under BRAND
		driver.findElement(By.xpath("//div[@class='brand-more']")).click();

		// 8) Type Duke and click checkbox
		driver.findElement(By.xpath("//input[@placeholder='Search brand']")).sendKeys("Duke");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[@class=' common-customCheckbox']")).click();

		// 9) Close the pop-up x
		driver.findElement(By.xpath("//span[contains(@class,'myntraweb-sprite FilterDirectory-close')]")).click();

		// 10) Confirm all the Coats are of brand Duke
		Thread.sleep(2000);
		List<WebElement> brandName = driver.findElements(By.xpath("//h3[@class='product-brand']"));
		int size = brandName.size();
		int count = 0;
		System.out.println("Size of list: " + size);
		for (int i = 0; i < brandName.size(); i++) {
			if (brandName.get(i).getText().equals("Duke"))
				count++;
		}
		if (count == size)
			System.out.println("All coats are of brand duke");
		else
			System.out.println("All coats are not of brand duke");

		// 11) Sort by Better Discount
		builder.moveToElement(driver.findElement(By.className("sort-sortBy"))).perform();
		driver.findElement(By.xpath("//label[text()='Better Discount']")).click();
		Thread.sleep(2000);

		// 12) Find the price of first displayed item and click on first product
		String firstPrice = driver.findElement(By.xpath("//span[@class='product-discountedPrice'][1]")).getText();
		System.out.println("Price of first displayed item is: " + firstPrice);
		driver.findElement(By.xpath("//span[@class='product-discountedPrice'][1]")).click();

		Set<String> handles = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(handles);
		driver.switchTo().window(winList.get(1));

		// 13) Take a screen shot
		File src = driver.getScreenshotAs(OutputType.FILE);
		File dst = new File("screenshots/myntra.png");
		FileUtils.copyFile(src, dst);

		// 14) Click on WishList Now
		driver.findElement(By.xpath("//div[contains(@class,'pdp-add-to-wishlist pdp-button')]")).click();

		// 15) Close Browser
		driver.quit();

	}

}
