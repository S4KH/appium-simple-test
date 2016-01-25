package com.tute.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tute.pages.HomePage;
import com.tute.utils.SettingsUtils;

import io.appium.java_client.NetworkConnectionSetting;

/**
 * Device network test, includes Wifi, Data
 * 
 * @author skh
 *
 */
// TODO: GPS and network off checks
public class NetworkTest extends TestBase {

	private final String TOGGLE_ACTIVATED_TEXT = "ON";
	private final String TOGGLE_INACTIVATED_TEXT = "OFF";
	private static NetworkConnectionSetting ncs;
	private static SettingsUtils sUtil;
	private HomePage homePage;

	@BeforeClass
	public void setUp() {
		ncs = driver.getNetworkConnection();
		sUtil = new SettingsUtils();

		// ba = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public String getName() {
		return "Bluetooth Wifi test";
	}

	@Test
	public void testWifiOn() {
		ncs.setWifi(true);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(ncs.wifiEnabled());
	}

	@Test(dependsOnMethods = { "testWifiOn" })
	public void testWifiOff() {
		ncs.setWifi(false);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertFalse(ncs.wifiEnabled());
	}

	@Test
	public void testLocationServiceOn() throws InterruptedException {
		// driver.toggleLocationServices();
		WebElement l = sUtil.findSingleElInPage(driver, "Location",
				"android.widget.Switch");
		// Turn on location if it is off
		if (!l.getText().equalsIgnoreCase(TOGGLE_ACTIVATED_TEXT)) {
			l.click();
		}
		Thread.sleep(5000);
		Assert.assertEquals(l.getText(), TOGGLE_ACTIVATED_TEXT);
	}

	@Test(dependsOnMethods = { "testLocationServiceOn" })
	public void testLocationServiceOff() throws InterruptedException {
		System.out.println(driver.getSettings().toString());
		// driver.toggleLocationServices();
		WebElement l = sUtil.findSingleElInPage(driver, "Location",
				"android.widget.Switch");
		// Turn on location if it is off
		if (l.getText().equalsIgnoreCase(TOGGLE_ACTIVATED_TEXT)) {
			l.click();
		}
		Thread.sleep(5000);
		Assert.assertEquals(l.getText(), TOGGLE_INACTIVATED_TEXT);
	}

	@Test
	public void testBluetoothConnectionOn() throws InterruptedException {
		WebElement bt = sUtil.findSingleElInPage(driver, "Bluetooth",
				"android.widget.Switch");
		// Turn on bluetooth if it is off
		if (!bt.getText().equalsIgnoreCase(TOGGLE_ACTIVATED_TEXT)) {
			bt.click();
		}
		Thread.sleep(5000);
		Assert.assertEquals(bt.getText(), TOGGLE_ACTIVATED_TEXT);

	}

	@Test(dependsOnMethods = { "testBluetoothConnectionOn" })
	public void testBluetoothConnectionOff() throws InterruptedException {
		WebElement bt = sUtil.findSingleElInPage(driver, "Bluetooth",
				"android.widget.Switch");
		// Turn on bluetooth if it is off
		if (bt.getText().equalsIgnoreCase(TOGGLE_ACTIVATED_TEXT)) {
			bt.click();
		}
		Thread.sleep(5000);
		Assert.assertEquals(bt.getText(), TOGGLE_INACTIVATED_TEXT);
	}

	@Test(dependsOnMethods = { "testWifiOn" }, dataProvider = "strProvider")
	public void testJsonCall(String txt) throws InterruptedException {
		homePage.sendJsonRequest(txt);
		Thread.sleep(5000);
		Assert.assertTrue(homePage.getJsonParsedText().contains(txt));
	}

	@DataProvider(name = "strProvider")
	private Object[][] strProvider() {
		return new Object[][] { { "hello" }, { "yes" }, { "ball" } };
	}

	@BeforeTest
	@Override
	public void setUpPage() {
		homePage = new HomePage(driver);
	}

}
