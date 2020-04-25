package QATestLab.test_selenium.tests;

import org.testng.annotations.Test;

import QATestLab.test_selenium.pages.MainPage;
import QATestLab.test_selenium.pages.SearchPage;
import QATestLab.test_selenium.utils.BrowserUtils;

public class PrestashopTasks extends BrowserUtils {

	@Test
	public void justTest() throws InterruptedException {
		MainPage mainPage = new MainPage();				
		mainPage.setCurrency("USD");			// setting new currency USD
		mainPage.comparingCurrency();			// comparing currency
		mainPage.searchGoods("dress");
		SearchPage searchPage = new SearchPage();
		searchPage.comparingNumberOfItems();
		mainPage.comparingCurrency();
		searchPage.setSortByPriceHighToLow();
		searchPage.checkBounderyPriceSorting("$");
		searchPage.comparingPriceDiscount();
	}
}
