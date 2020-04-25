package QATestLab.test_selenium.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import QATestLab.test_selenium.utils.BrowserUtils;
import QATestLab.test_selenium.utils.WebDriverUtils;

public class MainPage extends BrowserUtils {

	protected static final Logger logger = LoggerFactory.getLogger(BrowserUtils.class);

	public MainPage() {
		this.driver = WebDriverUtils.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='_desktop_logo']")
	public WebElement logo;

	@FindBy(xpath = "//article[@data-id-product='1']//span[@class='price']")
	public WebElement price;

	@FindBy(xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
	public WebElement currency;

	@FindBy(xpath = "//input[@class='ui-autocomplete-input']")
	public WebElement searchField;

	@FindBy(xpath = "//button[@type='submit']")
	public WebElement submitButton;

	public void setCurrency(String option) {
		waitForVisibility(currency);
		logger.info("INFO -------------> Clicking on currency");
		currency.click();
		String currencyXpath = String.format("//a[contains(text(), '%s')]", option);
		driver.findElement(By.xpath(currencyXpath)).click();
		logger.info("INFO -------------> Currency is set");
	}

	public void comparingCurrency() {
		waitForVisibility(price);

		String endOfPriceString = returnCurrency(price);
		logger.info("INFO -------------> Capturing the currency from the price_field: " + endOfPriceString);
		String endOfCurrencyString = returnCurrency(currency);
		logger.info("INFO -------------> Capturing the currency from the currency_field: " + endOfCurrencyString);

		// comparing currency in the price_field and currency_field
		assertTrue(endOfPriceString.equals(endOfCurrencyString));
		logger.info("INFO -------------> The currency is: " + endOfCurrencyString);
	}

	public String returnCurrency(WebElement element) {
		String currencySymbol = element.getText().substring(element.getText().length() - 1, element.getText().length());
		return currencySymbol;
	}

	public SearchPage searchGoods(String goods) {
		// type "dress" to the searchField
		searchField.sendKeys(goods);

		logger.info("INFO -------------> Waiting for button to become clickable");
		waitForClickability(submitButton);

		// clicking on submit button
		logger.info("INFO -------------> Clicking on submit button");
		submitButton.click();

		return new SearchPage();
	}
}
