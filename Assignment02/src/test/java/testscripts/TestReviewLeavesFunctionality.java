package testscripts;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.LeavesReportPage;

public class TestReviewLeavesFunctionality extends TestBaseClass {
	
	ExtentReports extReport;
	ExtentSparkReporter extSpark;
	ExtentTest exTest;
	
	LeavesReportPage reportObj;
	
	@BeforeTest
	public void setup()
	{
		reportObj = new LeavesReportPage(webDriver);
		extReport = new ExtentReports();
		extSpark = new ExtentSparkReporter("ExtentReportOutput/ExtentReportLeaves.html");
		extReport.attachReporter(extSpark);
		extSpark.config().setReportName("Review Leaves Functionality test report");
		exTest = extReport.createTest("Leaves reports viewing functionality", "This test report contains viewing of leaves report functionality ");
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		webDriver.navigate().to("https://demo.actitime.com/reports/reports.do");
	}
	
	@Test
	public void viewLeavesReport()
	{
		exTest.info("Select Past Months Leaves pane");
		reportObj.selectLeavesReport();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception{
 		if(result.getStatus() == ITestResult.FAILURE){
 			exTest.log(Status.FAIL, "Test Case Failed is "+result.getName());
 			exTest.log(Status.FAIL, "Test Case Failed is "+result.getThrowable());
 			
 			String screenshotPath = TestBaseClass.getScreenshot(webDriver, result.getName());
 			
 			exTest.addScreenCaptureFromPath(screenshotPath);
 		}else if(result.getStatus() == ITestResult.SKIP){
 			exTest.log(Status.SKIP, "Test Case Skipped is "+result.getName());
 		}
 		
 		extReport.flush();
 	}
	@AfterTest
	public void finish()
	{
		reportObj.closeReportWindow();
		webDriver.findElement(By.id("logoutLink")).click();
		webDriver.close();
		webDriver.quit();
	}
}
