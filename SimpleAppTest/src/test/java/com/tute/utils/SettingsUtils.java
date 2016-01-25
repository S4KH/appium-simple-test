package com.tute.utils;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

/**
 * Finding elements from settings page used of GPS, Bluetooth
 * 
 * @author skh
 *
 */

public class SettingsUtils {

	public SettingsUtils() {

	}

	/**
	 * Find an unique element from a page
	 * 
	 * @param driver
	 *            Your device
	 * @param page
	 *            Page
	 * @param className
	 *            Has to unique
	 * @return WebElement
	 */
	public WebElement findSingleElInPage(AndroidDriver<WebElement> driver, String el, 
			String className) {
		driver.startActivity("com.android.settings", ".Settings");
		//driver.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity);
		driver.scrollTo(el);
		driver.findElementByName(el).click();
		return driver.findElementByClassName(className);
	}

}
