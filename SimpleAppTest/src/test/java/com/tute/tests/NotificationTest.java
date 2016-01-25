package com.tute.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tute.pages.NativeNotificationPage;

public class NotificationTest extends TestBase {
	
	private NativeNotificationPage notifPage;

	@Override
	public String getName() {
		return "Notification test";
	}
	
	protected boolean searchNotification(String notifText) {
		int itemListSize = notifPage.getLastItemsContentSize();

		String title;
		boolean notifFound = false;
		for (int i = 0; i < itemListSize; i++) {
			title = notifPage.getItemTitle(i);
			if (title.contains(notifText)) {
				notifFound = true;
				break;
			}
		}
		return notifFound;
		
	}

	@Test
	public void testNotification() throws InterruptedException {
		driver.findElementById("push").click();
		Thread.sleep(8000);
		driver.openNotifications();
		Thread.sleep(1500);
				
		//notifPage = new NativeNotificationPage(driver);		
		
		//Assert.assertTrue(searchNotification("proximiti")); 
	}

	@Override
	public void setUpPage() {
		// TODO Auto-generated method stub		
	}

}


