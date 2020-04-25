package QATestLab.test_selenium.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import QATestLab.test_selenium.utils.BrowserUtils;
import QATestLab.test_selenium.utils.WebDriverUtils;

public class SearchPage extends BrowserUtils {

	protected static final Logger logger = LoggerFactory.getLogger(SearchPage.class);

	public SearchPage() {
		this.driver = WebDriverUtils.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h2[@class='h2']")
	public WebElement searchResult;

	@FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']//p")
	public WebElement numberOfGoods;

	@FindBy(xpath = "//article[@data-id-product='1']//span[@class='price']")
	public WebElement price;

	@FindBy(xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
	public WebElement currency;

	@FindBy(xpath = "//a[@class='select-title']")
	public WebElement sortBy_dropdown;

	@FindBy(xpath = "//div[@class='dropdown-menu']//a[5]")
	public WebElement sortByPriceHighToLow;

	@FindBy(xpath = "//article[contains(@class, 'product-miniature')]")
	public List<WebElement> listOfItems;

	@FindBy(xpath = "//article[contains(@class, 'product-miniature')]//span[@itemprop='price']")
	public List<WebElement> listOfItemsPrices;

	@FindBy(xpath = "//span[@class='discount-percentage']")
	public List<WebElement> listOfItemsDiscountPercentage;

	@FindBy(xpath = "//span[@class='regular-price']")
	public List<WebElement> listOfPriceBeforeDiscount;

	@FindBy(xpath = "//article[contains(@class, 'product-miniature')]//span[@class='regular-price']//following-sibling::span[@itemprop='price']")
	public List<WebElement> listOfPriceAfterDiscount;

	public void comparingNumberOfItems() {
		// waiting for the searchResult to load
		waitForVisibility(searchResult);

		// capturing number of items
		String actualNumberOfItems = "" + listOfItems.size();
		logger.info("INFO -------------> Capturing the number of items from the page: " + actualNumberOfItems);
		String expectedNumberOfItems = numberOfGoods.getText().substring(9).replace(".", "");

		// verifying number of items
		assertEquals(actualNumberOfItems, expectedNumberOfItems);
		logger.info("INFO -------------> Compared with our expected number of items: " + expectedNumberOfItems);
	}

	public void setSortByPriceHighToLow() {
		// clicking on dropDown, then on sortBy_List
		logger.info("INFO -------------> Clicking on dropDown");
		sortBy_dropdown.click();
		
		logger.info("INFO -------------> Clicking on sortByPriceHighToLow");
		sortByPriceHighToLow.click();
		
		logger.info("INFO -------------> List of goods is sorted by price high to low");
	}

	public void checkBounderyPriceSorting(String currency) throws InterruptedException {
		// waiting for updated sorted list
		Thread.sleep(500);
		
		logger.info("INFO -------------> Verifying that list is sorted in the right way ");
		
		// parsing price tags
		double firstItem = Double
				.parseDouble(listOfItemsPrices.get(0).getText().replace(currency, "").replace(",", "."));
		logger.info("INFO -------------> Getting first item: " + firstItem);
		double middleItem = Double.parseDouble(
				listOfItemsPrices.get(listOfItemsPrices.size() / 2).getText().replace(currency, "").replace(",", "."));
		logger.info("INFO -------------> Getting middle item: " + middleItem);
		double lastItem = Double.parseDouble(
				listOfItemsPrices.get(listOfItemsPrices.size() - 1).getText().replace(currency, "").replace(",", "."));
		logger.info("INFO -------------> Getting last item: " + lastItem);

		// verifying that list is sorted in the right way
		assertTrue(firstItem >= middleItem);
		assertTrue(middleItem >= lastItem);
		logger.info("INFO -------------> List is sorted in the right way");

	}

	public void comparingPriceDiscount() {
		
		logger.info("INFO -------------> Comparing price discount ");

		// here we run through two elements and comparing price with the indicated discount size
		for (int index = 0; index < listOfPriceBeforeDiscount.size(); index++) {

			// parsing price and percentage tags
			double percentage = Double
					.valueOf(listOfItemsDiscountPercentage.get(index).getText().replace("-", "").replace("%", ""));
			double priceBeforeDiscount = Double
					.valueOf(listOfPriceBeforeDiscount.get(index).getText().replace(",", ".").replace("$", ""));
			double priceAfterDiscount = Double
					.valueOf(listOfPriceAfterDiscount.get(index).getText().replace(",", ".").replace("$", ""));

			// calculating amount of discount
			double discount = priceBeforeDiscount * (percentage / 100);

			// calculating price with discount
			double discountedPrice = priceBeforeDiscount - discount;
			discountedPrice = Double.valueOf(new DecimalFormat("##.##").format(discountedPrice).replace(",", "."));
			logger.info("INFO -------------> Price with discount after calculating: " + discountedPrice);
			logger.info("INFO -------------> Actual price we have: " + priceAfterDiscount);
			// verifying that the price before and after the discount matches the indicated discount size
			assertTrue(priceAfterDiscount == discountedPrice);
		}
		logger.info("INFO -------------> Prices match the indicated discount size");
	}
}
