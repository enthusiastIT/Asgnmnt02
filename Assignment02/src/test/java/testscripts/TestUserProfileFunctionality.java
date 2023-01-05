package testscripts;

import static org.testng.Assert.assertEquals;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.ActitimeUsersPage;

public class TestUserProfileFunctionality extends TestBaseClass {

	ActitimeUsersPage userObj;
	ExtentReports extReport;
	ExtentSparkReporter extSpark;
	ExtentTest exTest;
	
	@BeforeClass
	public void setup()
	{
		userObj = new ActitimeUsersPage(webDriver);
		extReport = new ExtentReports();
		extSpark = new ExtentSparkReporter("ExtentReportOutput/ExtentReportUserProfiles.html");
		extReport.attachReporter(extSpark);
		extSpark.config().setReportName("User Profile Functionality test report");
	}
	@BeforeMethod
	public void beforeMethod()
	{
		webDriver.navigate().to("https://demo.actitime.com/administration/userlist.do");
		
	}
	@Test (priority=1)
	public void viewUserProfile()
	{	
		exTest = extReport.createTest("view user profile", "This test report contains viewing of User Profiles functionality ");
		exTest.info("Select a user record randomly to view the profile");
		userObj.selectUser();
	}
	
	@Test (priority=2)
	public void verifyUserDetailsDisplay()
	{
		exTest = extReport.createTest("Verify user's basic details", "This test report contains viewing of User Profiles functionality ");
		userObj.selectUser();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exTest.info("View user's basic details");
;		assertEquals(userObj.userDetailsWindow(), true);
	}
	
	@Test (priority=3)
	public void viewPermissionInfo()
	{
		exTest = extReport.createTest("View user permission details", "This test report contains viewing of User Profiles functionality ");
		userObj.selectUser();
		try {
			exTest.info("View permission of the user");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userObj.viewPermission();
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
	
	@AfterClass
	public void afterClass()
	{
		userObj.closeUserInfoPanel();
		webDriver.navigate().to("https://demo.actitime.com/user/submit_tt.do");
		
	}
	
}
