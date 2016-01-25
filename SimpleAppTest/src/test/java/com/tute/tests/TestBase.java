package com.tute.tests;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * An abstract base for all of the Android tests within this package
 * 
 * Responsible for setting up the Appium driver
 * 
 * @author skh
 *
 */
public abstract class TestBase {

	/**
	 * Driver used across the all of the test cases.
	 */
	public static AndroidDriver<WebElement> driver;

	/**
	 * The category name
	 * 
	 * @return The name of the Android category
	 */
	public abstract String getName();
	
	/**
     * Method to initialize the test's page
     */
    @BeforeTest
    public abstract void setUpPage();

	@BeforeSuite
	public void setUpAppium() throws MalformedURLException {

		// Use it as default URL
		final String URL_STR = "http://127.0.0.1:4723/wd/hub";

		URL url = new URL(URL_STR);

		// Using empty DesiredCapabilities object
		driver = new AndroidDriver<WebElement>(url, new DesiredCapabilities());

		// Use a higher value if your mobile elements take time to show up
		driver.manage().timeouts().implicitlyWait(85, TimeUnit.SECONDS);

	}

	/**
	 * Quit the driver after test
	 */
	@AfterSuite
	public void tearDownAppium() {
		driver.quit();
	}

	@AfterClass
    public void restartApp() {
        driver.resetApp();
    }

}
