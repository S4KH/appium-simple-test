package com.tute.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class NativeNotificationPage extends BasePage {

    @FindBy(id = "com.android.systemui:id/notification_panel")
    private List<WebElement> notificationPanel;
    //settings data
    @FindBy(id = "com.android.systemui:id/clear_all_button")
    private List<WebElement> clearAllBtn;
    //events data
//    @FindBy(id = "android:id/status_bar_latest_event_content")
    @AndroidFindAll(
            @AndroidFindBy(id = "android:id/status_bar_latest_event_content")
    )
    private List<WebElement> lastItemsContent;
    @FindBy(id = "android:id/title")
    private List<WebElement> itemTitle;
    String itemTitle_Locator_Text = "android:id/title";
    @FindBys({
            @FindBy (id = "android:id/big_text"),
            @FindBy (id = "android:id/text")
    })
    private List<WebElement> itemText;
    String itemText_Phone_Locator_Text = "android:id/text";
    String itemText_Tablet_Locator_Text = "android:id/big_text";

    public NativeNotificationPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isNativeNotificationPage() throws Exception {
        boolean bool;
//        setFastLookTiming();
        Thread.sleep(1000);
        bool = !notificationPanel.isEmpty();
        Thread.sleep(3000);
        return bool;
    }

    public boolean isClearAllBtnLoaded() throws InterruptedException{
        boolean bool;
        Thread.sleep(3000);
        bool = !clearAllBtn.isEmpty();
        Thread.sleep(3000);
        return bool;
    }

    public int getLastItemsContentSize() {return lastItemsContent.size();}

    public String getItemTitle(int num) {return lastItemsContent.get(num).findElement(By.id(itemTitle_Locator_Text)).getText();}

//    public String getItemText(int num) {
//        //System.out.println(lastItemsContent.get(num).findElements(MobileBy.className("android.widget.TextView")).size());
//        if (isPhone())
//            return lastItemsContent.get(num).findElements(MobileBy.className("android.widget.TextView")).get(2).getText();
//        else {
//            setLookTiming(3);
//            if (lastItemsContent.get(num).findElements(MobileBy.id(itemText_Tablet_Locator_Text)).isEmpty()) {
//                setDefaultTiming();
//                return lastItemsContent.get(num).findElement(MobileBy.id(itemText_Phone_Locator_Text)).getText();
//            } else {
//                setDefaultTiming();
//                return lastItemsContent.get(num).findElement(MobileBy.id(itemText_Tablet_Locator_Text)).getText();
//            }
//        }
//    }
//
//    public void tapClearAllBtn() {tapElement(clearAllBtn.get(0));}
//
//    public MessagesPage tapLastItemsContent(int num) {
//        tapElement(lastItemsContainer.get(num));
//        return new MessagesPage(driver);
//    }
//
//    public MessagesPage tapItemTitle(int num) {
//        tapElement(lastItemsContent.get(num));
//        return new MessagesPage(driver);
//    }
}