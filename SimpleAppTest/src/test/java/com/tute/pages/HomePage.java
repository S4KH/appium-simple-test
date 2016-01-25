package com.tute.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends BasePage {

	@AndroidFindBy(id = "editText1")
	private WebElement input1;
	
	@AndroidFindBy(id = "editText2")
	private WebElement input2;
	
	@AndroidFindBy(id = "sumBtn")
	private WebElement addBtn;
	
	@AndroidFindBy(id = "result")
	private WebElement res;
	
	@AndroidFindBy(id = "compBtn")
	private WebElement cmpBtn;
	
	@AndroidFindBy(id="GetServerData")
	private WebElement jsonReqBtn;
	
	@AndroidFindBy(id="serverText")
	private WebElement reqData;
	
	@AndroidFindBy(id="jsonParsed")
	private WebElement jsonParsed;

	public HomePage(AppiumDriver driver) {
		super(driver);
	}
	
	protected void populateFields(String t1, String t2){

		// Clear fields before setting text
		input1.clear();
		input2.clear();

		input1.sendKeys(t1);
		input2.sendKeys(t2);
	}

	/**
	 * Add 2 numbers in edittexts 
	 * @param n1
	 *            - Number 1
	 * @param n2
	 *            - Number 2
	 */
	public void addNumbers(String n1, String n2) {
		populateFields(n1, n2);
		addBtn.click();
	}

	/**
	 * Compare 2 texts
	 * @param txt1
	 * 			- Text 1
	 * @param txt2
	 * 			- Text 2
	 */
	public void compareTxts(String txt1, String txt2) {		
		populateFields(txt1, txt2);
		cmpBtn.click();
	}
	
	public String getResultText(){
		return res.getText();
	}
	
	public String getJsonParsedText(){
		return jsonParsed.getText();
	}
	
	/**
	 * Send JSON request to server defined in the app 
	 * @param data
	 * 			- Data
	 */
	public void sendJsonRequest(String data){
		reqData.clear();
		reqData.sendKeys(data);
		
		jsonReqBtn.click();
	}

}
