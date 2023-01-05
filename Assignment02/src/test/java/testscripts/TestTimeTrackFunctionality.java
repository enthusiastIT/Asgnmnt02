package testscripts;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import data.DataObject;
import pages.ActitimeLoginPage;
import pages.ApproveTimeTrackPage;

public class TestTimeTrackFunctionality extends TestBaseClass {

	ApproveTimeTrackPage timeTrackObj;
	List<WebElement> dataGroup;
	String assertApprovalAll;
	String statusLabel = "Ready for Approval";
	String statusLabelReject = "Rejected";
	String statusNotReady = "Not Ready";
	ArrayList<Integer> tBodyVal1;
	ArrayList<Integer> tRowVal1;
	Random randVal;
	int randIntVal;
	String statusActual;
	ExtentReports extReport;
	ExtentSparkReporter extSpark;
	ExtentTest exTest;
	
	@BeforeClass
	public void beforeClass()
	{
		extReport = new ExtentReports();
		extSpark = new ExtentSparkReporter("ExtentReportOutput/ExtentReportTimeTrack.html");
		extReport.attachReporter(extSpark);
		extSpark.config().setReportName("Time Track Functionality testing");
		
		webDriver.get("https://demo.actitime.com/");
		ActitimeLoginPage loginPg = new ActitimeLoginPage(webDriver); 
		loginPg.setTextInUsernameTxtBox("admin");
		loginPg.setTextInPasswordTxtBox("manager");
		loginPg.clickLoginButton();
		
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		webDriver.navigate().to("https://demo.actitime.com/user/submit_tt.do");
		webDriver.findElement(By.xpath("//*[@id=\"topnav\"]/tbody/tr[2]/td[2]/div[4]/a")).click();
		webDriver.manage().timeouts().getImplicitWaitTimeout();
		timeTrackObj = new ApproveTimeTrackPage(webDriver);
	}
	
	@DataProvider(name= "testStatusFilterData")	
	public Object[][] getData() {
		String excelPath = System.getProperty("user.dir")+"/ExcelData/TestData_TimeTrackStatusSelect.xlsx";
		Object data[][] = DataObject.testStatusData(excelPath, "Sheet1");
		return data;
	}
	
	@Test(dataProvider = "testStatusFilterData")
	public void selectStatusDropdown(int statusTag) throws InterruptedException
	{
		exTest = extReport.createTest("Time Track details", "This test is created to verify the records are displayed for the selected status");
		
		switch (statusTag)
		{
			case 1:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectReadyForApprovalChkBox();
				exTest.info("Select only Ready For Approval checkbox");
				Thread.sleep(500);
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected status");
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						
						assertEquals(assertApprovalAll, statusLabel);
				
					}
				}
				
				Thread.sleep(500);
				break;
			
			case 2:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectNotReadyForApprovalChkBox();
				exTest.info("Select only Not Ready For Approval checkbox");
				Thread.sleep(500);
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(1000);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected status");
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						assertEquals(assertApprovalAll, statusNotReady);
					}
				}
				
				Thread.sleep(500);
				break;
			case 3:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectRejectedChkBox();
				exTest.info("Select only Rejected checkbox");
				Thread.sleep(500);
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected status");
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						assertEquals(assertApprovalAll, statusLabelReject);
					}
				}
				
				Thread.sleep(500);
				break;		
			case 4:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectReadyForApprovalChkBox();
				Thread.sleep(300);
				exTest.info("Select Ready for approval checkbox");
				timeTrackObj.selectNotReadyForApprovalChkBox();
				Thread.sleep(500);
				exTest.info("Select Not ready for approval checkbox");
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected statuses");
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						
						if(statusLabel.equals(assertApprovalAll)) 
						{
							assertEquals(assertApprovalAll, statusLabel);
						}
						else
						{ 
							assertEquals(assertApprovalAll, statusNotReady, "Expected status not found");
						}
					
					}
				}
				
				Thread.sleep(500);
				break;
			case 5:
				
				timeTrackObj.selectStatusFilterDropDown();;
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectReadyForApprovalChkBox();
				Thread.sleep(300);
				exTest.info("Select Ready for approval checkbox");
				timeTrackObj.selectRejectedChkBox();
				Thread.sleep(500);
				exTest.info("Select Rejected checkbox");
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected status");
				
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{		
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						//statusLabel.equals(columns.get(2).getText());
						
						if(statusLabel.equals(assertApprovalAll))
						{
							assertEquals(assertApprovalAll, statusLabel);
						}
						else
						{ 
							assertEquals(assertApprovalAll, statusLabelReject, "Expected status not found");
						}
					
					}
				}
				
				Thread.sleep(500);
				break;
			case 6:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectNotReadyForApprovalChkBox();
				Thread.sleep(300);
				exTest.info("Select Not ready for approval checkbox");
				timeTrackObj.selectRejectedChkBox();
				Thread.sleep(500);
				exTest.info("Select Rejected checkbox");
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected statuses");
				
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
						
						if(statusNotReady.equals(assertApprovalAll)) 
						{
							assertEquals(assertApprovalAll, statusNotReady);
						}
						else
						{ 
							assertEquals(assertApprovalAll, statusLabelReject, "Expected status not found");
						}
					
					}
				}
				
				break;
			case 7:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all checkboxes in Status filter dropdown");
				timeTrackObj.selectReadyForApprovalChkBox();
				Thread.sleep(200);
				exTest.info("Select Ready for approval checkbox");
				timeTrackObj.selectNotReadyForApprovalChkBox();
				Thread.sleep(200);
				exTest.info("Select Not ready for approval checkbox");
				timeTrackObj.selectRejectedChkBox();
				exTest.info("Select Rejected checkbox");
				Thread.sleep(200);
				timeTrackObj.clickApplyButton();
				exTest.info("Click Apply button in Status Filter dropdown");
				Thread.sleep(500);
				
				dataGroup = webDriver.findElements(By.className("data"));
				
				exTest.info("Verify the records are displayed according to the selected statuses");
				for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
				{
					
					timeTrackObj.getRowCountOfUserGroup(grpNo);
					
					for(int rnum=1;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
					{
						assertApprovalAll = timeTrackObj.checkTimeTrackRecordStatus(grpNo, rnum);
		
						if(statusLabel.equals(assertApprovalAll)) 
						{
							assertEquals(assertApprovalAll, statusLabel);
						}
						else if(statusNotReady.equals(assertApprovalAll))
						{ 
							assertEquals(assertApprovalAll, statusNotReady, "Expected status not found");
						}
						else 
						{
							assertEquals(assertApprovalAll, statusLabelReject, "Expected status not found");
						}
					
					}
				}
				
				break;
			case 8:
				
				timeTrackObj.selectStatusFilterDropDown();
				setStatusValues();
				exTest.info("Deselect all three status checkboxes");
				exTest.info("Apply button should be disabled");
	
				assertEquals(timeTrackObj.isApplyButtonEnabled(), false);
			
				break;
		}
	}
	
 @Test
	public void readyForApprovalstatus_approval()
	{
	 	exTest = extReport.createTest("Time Track details- Approve", "This test is created to check approval of readyForApproval status");
	 	
		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Select Not ready for approval checkbox from dropdown, if unticked");
		if(!(timeTrackObj.isDropDownStatusSelected_1()))
		{
			timeTrackObj.selectReadyForApprovalChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement> dataGroup = webDriver.findElements(By.className("data"));

		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
	
		
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			timeTrackObj.getRowCountOfUserGroup(grpNo);
			
			for(int rnum=0;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
			{
			
				for(int cnum=0;cnum<timeTrackObj.getColumnCount(rnum).size();cnum++)
				{
					if(statusLabel.equals(timeTrackObj.getColumnCount(rnum).get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);
						
					}
				}
			}
		} 
		// To select all the check boxes belonging to pending approval records (refer the above displayed programming code) 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a Ready for Approval record randomly to approve");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timeTrackObj.clickApproveButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusActual = timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		assertEquals(statusActual, "Approved");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	
		
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		randIntVal = 0;
	}
 
 	@Test
 	public void revokeApproval()
 	{
 		exTest = extReport.createTest("Time Track details- Revoke", "This test is created to revoke the apporval/ rejection status");
 		
 		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!(timeTrackObj.isDropDownStatusSelected_1()))
		{
			timeTrackObj.selectReadyForApprovalChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement> dataGroup = webDriver.findElements(By.className("data"));

		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
	
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			timeTrackObj.getRowCountOfUserGroup(grpNo);
			
			for(int rnum=0;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
			{
			
				for(int cnum=0;cnum<timeTrackObj.getColumnCount(rnum).size();cnum++)
				{
					if(statusLabel.equals(timeTrackObj.getColumnCount(rnum).get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);
						
					}
				}
			}
		} 
		// To select all the check boxes belonging to pending approval records (refer the above displayed programming code) 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a record randomly");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click Approve button");
		timeTrackObj.clickApproveButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click revoke link");
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusActual = timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		assertEquals(statusActual, "Ready for Approval");
		
		randIntVal = 0;
		
 	}
 	
 	@Test
 	public void notReadyStatus_approval() 
	{
 		exTest = extReport.createTest("Time Track details- Approve", "This test is created to check approval of notReady status");
 		
		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Select Not ready for approval checkbox from dropdown, if unticked");
		if(!timeTrackObj.isDropDownStatusSelected_2())
		{
			timeTrackObj.selectNotReadyForApprovalChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataGroup = webDriver.findElements(By.className("data"));

		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
		
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			timeTrackObj.getRowCountOfUserGroup(grpNo);
				
			for(int rnum=0;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
			{
				timeTrackObj.getColumnCount(rnum);
				
				for(int cnum=0;cnum<timeTrackObj.getColumnCount(rnum).size();cnum++)
				{
					if(statusNotReady.equals(timeTrackObj.getColumnCount(rnum).get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);
					}
				}
			}
		} 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a Not Ready for Approval record randomly to approve");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click Approve button");
		timeTrackObj.clickApproveButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusActual= timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
	
		assertEquals(statusActual,"Approved", "assertion for the Approve link button click");
		
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		randIntVal = 0;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	
 	@Test
 	public void rejectStatus_approval() 
	{
 		exTest = extReport.createTest("Time Track details- Approve", "This test is created to check approval of reject status");
 		
		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Select Rejected for approval checkbox from dropdown, if unticked");
		if(!(timeTrackObj.isDropDownStatusSelected_3()))
		{
			timeTrackObj.selectRejectedChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataGroup = webDriver.findElements(By.className("data"));
		

		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
		
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			timeTrackObj.getRowCountOfUserGroup(grpNo);
				
			for(int rnum=0;rnum<timeTrackObj.getRowCountOfUserGroup(grpNo).size();rnum++)
			{
				timeTrackObj.getColumnCount(rnum);
				
				for(int cnum=0;cnum<timeTrackObj.getColumnCount(rnum).size();cnum++)
				{
					if(statusLabelReject.equals(timeTrackObj.getColumnCount(rnum).get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);
					}
					
				}
			}
		} 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a Rejected record randomly to approve");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click Approve button");
		timeTrackObj.clickApproveButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusActual = timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		assertEquals(statusActual,"Approved", "assertion for the Approve link button click");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		randIntVal = 0;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	
 	@Test
 	public void readyForApproveStatus_rejection()
	{	
 		exTest = extReport.createTest("Time Track details- Rejection", "This test is created to check Rejection of readyForApproval status");
 		
		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!(timeTrackObj.isDropDownStatusSelected_1()))
		{
			timeTrackObj.selectReadyForApprovalChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataGroup = webDriver.findElements(By.className("data"));
		
		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
		
	    // Identify all the check boxes belonging to ready for approval records 
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			List<WebElement> rows = timeTrackObj.getRowCountOfUserGroup(grpNo);
				
			for(int rnum=0;rnum<rows.size();rnum++)
			{
				List<WebElement> columns= timeTrackObj.getColumnCount(rnum);
			
				for(int cnum=0;cnum<columns.size();cnum++)
				{
					if(statusLabel.equals(columns.get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);			
					}
				}
			}
		} 
		// Identify all the check boxes belonging to ready for approval records (refer the above displayed programming code) 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a Ready for Approval record randomly to reject");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click Reject button with a comment");
		timeTrackObj.clickRejectButton();
		timeTrackObj.commentForRejection("Need to improve");
		timeTrackObj.clickCommentBoxRejectButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String assertReject = timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		assertEquals(assertReject,"Rejected", "assertion on revoke link");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		randIntVal = 0;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	
 	@Test
 	public void notReadyStatus_rejection()
	{
 		exTest = extReport.createTest("Time Track details- Reject", "This test is created to check rejection of notReady status");
 		
		timeTrackObj.selectStatusFilterDropDown();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!(timeTrackObj.isDropDownStatusSelected_2()))
		{
			timeTrackObj.selectNotReadyForApprovalChkBox();
		}
		
		timeTrackObj.clickApplyButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataGroup = webDriver.findElements(By.className("data"));
		
		tBodyVal1 = new ArrayList<Integer>();
		tRowVal1 = new ArrayList<Integer>();
		
	    // Identify all the check boxes belonging to ready for approval records 
		for(int grpNo=2; grpNo<(dataGroup.size()+2); grpNo++)
		{
			
			List<WebElement> rows = timeTrackObj.getRowCountOfUserGroup(grpNo);
				
			for(int rnum=0;rnum<rows.size();rnum++)
			{
				List<WebElement> columns= timeTrackObj.getColumnCount(rnum);
			
				for(int cnum=0;cnum<columns.size();cnum++)
				{
					if(statusNotReady.equals(columns.get(cnum).getText()))
					{
						tBodyVal1.add(grpNo);
						tRowVal1.add(rnum+1);			
					}
				}
			}
		} 
		// Identify all the check boxes belonging to ready for approval records (refer the above displayed programming code) 
		
		randVal = new Random();
		randIntVal = randVal.nextInt((tBodyVal1.size()));
		
		exTest.info("Select a Not Ready for Approval record randomly to approve");
		timeTrackObj.selectRandomRowCheckbox(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exTest.info("Click Reject button with a comment");
		timeTrackObj.clickRejectButton();
		timeTrackObj.commentForRejection("Need to improve");
		timeTrackObj.clickCommentBoxRejectButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String assertReject = timeTrackObj.getTimeTrackStatus(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		assertEquals(assertReject,"Rejected", "assertion on revoke link");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timeTrackObj.revokeButtonClick(tBodyVal1.get(randIntVal), tRowVal1.get(randIntVal));
		randIntVal = 0;
		
		try {
			Thread.sleep(500);
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
	
}
