package QATestLab.test_selenium.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtils {

	private static WebDriver driver;

	public static WebDriver getDriver() {
		if (driver == null) {
			ChromeOptions options = getOptions("chrome");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return driver;
	}

	public static ChromeOptions getOptions(String browser) {
		// creating desired capabilities for chrome
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		ChromeOptions optns = new ChromeOptions();

		capability.setBrowserName(browser);
		capability.setJavascriptEnabled(true);
		capability.setPlatform(Platform.ANY);

		optns = new ChromeOptions();
		optns.addArguments("test-type");
		optns.addArguments("--start-maximized");
		optns.addArguments("--disable-extensions");
		optns.merge(capability);

		return optns;
	}
}