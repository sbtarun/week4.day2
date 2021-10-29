package week4.day2.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Snapdeal {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		ChromeDriver driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// 1. Launch https://www.snapdeal.com/
		driver.get("https://www.snapdeal.com/");

		Actions builder = new Actions(driver);

		// 2. Go to Mens Fashion
		WebElement mens = driver.findElement(By.xpath("(//span[@class='catText'])[6]"));
		builder.moveToElement(mens).perform();

		// 3. Go to Sports Shoes
		driver.findElement(By.xpath("(//span[text()='Sports Shoes'])[1]")).click();

		// 4. Get the count of the sports shoes
		String count = driver.findElement(By.className("category-count")).getText();
		System.out.println("Count of shoes is: " + count);

		// 5. Click Training shoes
		driver.findElement(By.xpath("(//div[@class='child-cat-name '])[2]")).click();

		// 6. Sort by Low to High
		driver.findElement(By.className("sort-selected")).click();
		driver.findElement(By.xpath("(//li[@class='search-li'])[1]")).click();

		// 7. Check if the items displayed are sorted correctly
		String sort = driver.findElement(By.className("sort-selected")).getText();
		if (sort.equals("Price Low To High"))
			System.out.println("Items are sorted by price low to high");
		else
			System.out.println("Items are not sorted by price low to high");

		// 8.Select the price range (900-1200)
		WebElement fromValue = driver.findElement(By.name("fromVal"));
		fromValue.clear();
		fromValue.sendKeys("900");

		WebElement toValue = driver.findElement(By.name("toVal"));
		toValue.clear();
		toValue.sendKeys("1200");

		driver.findElement(By.xpath("//div[contains(text(),'GO')]")).click();
		System.out.println("Step 8 completed");

		Thread.sleep(2000);
		// 9.Filter with color Navy (Using black color because Navy was not available
		// for the selected price range)
		driver.findElement(By.xpath("//label[@for='Color_s-Black']")).click();

		// 10 verify the all applied filters

		String price = driver.findElement(By.xpath("//a[@data-key='Price|Price'][1]")).getText();

		if (price.contains("900") && price.contains("1200"))
			System.out.println("Price range verified");
		else
			System.out.println("Price range is wrong");

		String color = driver.findElement(By.xpath("//a[@data-key='Color_s|Color'][1]")).getText();

		if (color.contains("Black"))
			System.out.println("Color is verified");
		else
			System.out.println("Color does not match");

		// 11. Mouse Hover on first resulting Training shoes
		builder.moveToElement(driver.findElement(By.xpath("//img[@class='product-image wooble']"))).perform();

		// 12. click QuickView button
		driver.findElement(By.xpath("//div[contains(@class,'center quick-view-bar')]")).click();

		Thread.sleep(2000);
		// 13. Print the cost and the discount percentage
		String discountedPrice = driver.findElement(By.className("payBlkBig")).getText();
		System.out.println("Price is: " + discountedPrice);

		String discountedPercentage = driver.findElement(By.className("percent-desc")).getText();
		System.out.println("Discount percentage is: " + discountedPercentage);

		// 14. Take the snapshot of the shoes
		WebElement shoeImage = driver.findElement(By.xpath("//img[@class='cloudzoom']"));
		File src = shoeImage.getScreenshotAs(OutputType.FILE);
		File dst = new File("screenshots/shoes.png");
		FileUtils.copyFile(src, dst);
		
		//15. Close the current window
		driver.findElement(By.xpath("//div[contains(@class,'close close1')]")).click();
		
		//16. Close the main window
		driver.close();
		

	}

}
