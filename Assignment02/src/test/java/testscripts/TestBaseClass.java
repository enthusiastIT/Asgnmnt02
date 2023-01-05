package testscripts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.ApproveTimeTrackPage;

public class TestBaseClass {

	static WebDriver webDriver;
	public static String projectPathTest;
	static ApproveTimeTrackPage objTimetrack;
	
	public static void initialize() {
		projectPathTest = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",
				projectPathTest + "/drivers/chromedriver/chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		
	}
	
	public static void setStatusValues() {

		objTimetrack = new ApproveTimeTrackPage(webDriver);
		
		if(objTimetrack.isDropDownStatusSelected_1() && objTimetrack.isDropDownStatusSelected_2() && objTimetrack.isDropDownStatusSelected_3())
		{
			objTimetrack.selectReadyForApprovalChkBox();
			objTimetrack.selectNotReadyForApprovalChkBox();
			objTimetrack.selectRejectedChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(objTimetrack.isDropDownStatusSelected_1() && !(objTimetrack.isDropDownStatusSelected_2() || objTimetrack.isDropDownStatusSelected_3()))
		{
			objTimetrack.selectReadyForApprovalChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(objTimetrack.isDropDownStatusSelected_2() && !(objTimetrack.isDropDownStatusSelected_1() || objTimetrack.isDropDownStatusSelected_3()))
		{
			objTimetrack.selectNotReadyForApprovalChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(objTimetrack.isDropDownStatusSelected_3() && !(objTimetrack.isDropDownStatusSelected_1() || objTimetrack.isDropDownStatusSelected_2()))
		{
			objTimetrack.selectRejectedChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(objTimetrack.isDropDownStatusSelected_1() && objTimetrack.isDropDownStatusSelected_2())
		{
			objTimetrack.selectReadyForApprovalChkBox();
			objTimetrack.selectNotReadyForApprovalChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(objTimetrack.isDropDownStatusSelected_2() && objTimetrack.isDropDownStatusSelected_3())
		{
			objTimetrack.selectNotReadyForApprovalChkBox();
			objTimetrack.selectRejectedChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(objTimetrack.isDropDownStatusSelected_1() && objTimetrack.isDropDownStatusSelected_3())
		{
			objTimetrack.selectReadyForApprovalChkBox();
			objTimetrack.selectRejectedChkBox();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		String destination =  "FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		String absDestination = finalDestination.getAbsolutePath();
		
		return absDestination;
	}
}
