package testscripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import data.DataObject;
import pages.ActitimeLoginPage;

public class TestLoginFunctionality extends TestBaseClass {

	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest testLogin;
	
	@BeforeTest
	public void setup()
	{
		extent = new ExtentReports();
		
		spark = new ExtentSparkReporter("ExtentReportOutput/ExtentReportLogin.html");
		extent.attachReporter(spark);
		spark.config().setReportName("Extent Report for Login Functionality");
		initialize();
	}
	
	@BeforeMethod
	public void beforeMethod() 
	{
		webDriver.get("https://demo.actitime.com/");
	}
	
	@DataProvider(name= "testLoginData")	
	public Object[][] getData() {
		String excelPath = System.getProperty("user.dir")+"/ExcelData/TestData_Login.xlsx";
		Object data[][] = DataObject.testData(excelPath, "Sheet1");
		return data;
	}
	
	@Test(dataProvider = "testLoginData")
	public void enterLoginDetails(String username, String password) throws IOException 
	{
		testLogin = extent.createTest("Enter Login Details", "This test is created to validate Actitime Login functionality");
		
		ActitimeLoginPage loginPg = new ActitimeLoginPage(webDriver);
		loginPg.setTextInUsernameTxtBox(username);
		testLogin.info("Username is entered");
		
		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loginPg.setTextInPasswordTxtBox(password);
		testLogin.info("Password is entered");
		try 
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e) 
		{
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loginPg.clickLoginButton();
		testLogin.log(Status.INFO, "The Login button is clicked");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if((webDriver.getCurrentUrl()).equalsIgnoreCase("https://demo.actitime.com/user/submit_tt.do"))
		{
			testLogin.log(Status.PASS, "Test Case passed. Input values: Username - "+username+" , Password - "+password);	
		}
		else
		{
			testLogin.log(Status.FAIL, "Test Case failed. Input values: Username - "+username+" , Password - "+password);
		}

		try {
				webDriver.findElement(By.id("closeProjectLightBoxBtn")).click();
			
				
		} catch(NoSuchElementException e) {

	
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(webDriver.findElement(By.id("logoutLink")).isDisplayed())
		{
			webDriver.findElement(By.id("logoutLink")).click();
		}
		
	}
	
	@AfterMethod
	
	public void getResult(ITestResult result) throws Exception{
		if(result.getStatus() == ITestResult.FAILURE){
			testLogin.log(Status.FAIL, "Test Case Failed is "+result.getName());
			testLogin.log(Status.FAIL, "Test Case Failed is "+result.getThrowable());
		
			String screenshotPath = TestBaseClass.getScreenshot(webDriver, result.getName());

			testLogin.addScreenCaptureFromPath(screenshotPath);
		}else if(result.getStatus() == ITestResult.SKIP){
			testLogin.log(Status.SKIP, "Test Case Skipped is "+result.getName());
		}
		extent.flush();
	}
	
}
