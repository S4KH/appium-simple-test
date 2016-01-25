package com.tute.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tute.pages.HomePage;

public class FuncTest extends TestBase {
	
	private HomePage homePage;

	public FuncTest() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Sets up homepage
     */
    @BeforeTest
    @Override
    public void setUpPage(){
        homePage = new HomePage(driver);
    }

	@Override
	public String getName() {
		return "Validations";
	}

	@Test(dataProvider = "numberProvider")
	public void testAddition(String t1, String t2, String exp) throws Exception {
		homePage.addNumbers(t1, t2);
		Assert.assertEquals(homePage.getResultText(), exp);
	}

	@DataProvider(name = "numberProvider")
	private Object[][] numberProvider() {
		return new Object[][] { { "1", "2", "3" }, { "4", "66", "70" }, { "34", "34", "68" } };
	}

	@Test(dataProvider = "strProvider")
	public void testComparison(String t1, String t2, boolean exp) throws Exception {
		homePage.compareTxts(t1, t2);
		Assert.assertTrue(Boolean.valueOf(homePage.getResultText()) == exp);
	}

	@DataProvider(name = "strProvider")
	private Object[][] strProvider() {
		return new Object[][] { { "hello", "peace", false }, { "yes", "yes", true }, { "ball", "bill", false } };
	}

}
