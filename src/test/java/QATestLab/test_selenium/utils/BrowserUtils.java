package QATestLab.test_selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BrowserUtils {

	protected static final Logger logger = LoggerFactory.getLogger(BrowserUtils.class);

	private WebDriverWait wait;
	public WebDriver driver;

	@BeforeClass
	public void setUp() {
		logger.info("INFO -------------> Setting up the driver ");
		driver = WebDriverUtils.getDriver();
		String MainPageURL = "http://prestashop-automation.qatestlab.com.ua/ru/";
		driver.get(MainPageURL);
	}

	@AfterClass
	public void tearDown() {
		logger.info("INFO -------------> Tearing down the driver ");
		driver.quit();
	}

	public void newWait(int sec) {
		wait = new WebDriverWait(this.driver, sec);
	}

	public void waitForClickability(WebElement element) {
		newWait(10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForVisibility(WebElement element) {
		newWait(10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}