package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ApproveTimeTrackPage {

		WebDriver driver = null;
		
		private By dropdown_statusFilter = By.xpath("//*[@id=\"statusFilterButton\"]/div");
		private By dropdownChk_ReadyForApproval = By.id("readyForApprovalCbx");
		private By dropdownChk_NotReadyForApproval = By.id("notReadyForApprovalCbx");
		private By dropdownChk_Rejected = By.id("rejectedCbx");
		private By button_Apply = By.xpath("/html/body/div[12]/div[3]/div/button[1]/span");
		private By button_Cancel = By.xpath("/html/body/div[12]/div[3]/div/button[2]/span");
		private By button_Approve = By.id("approveButton");
		private By reject_Button = By.id("rejectButton");
		private WebElement htmlTable;
		private List<WebElement> rows;
		private String timeTrackStatus;
		private List<WebElement> columns;
		private String revokeLinkExp;
		private String recordStatus;
		private boolean statusApplybtn;
		private By rejectionComment = By.id("rejectWeekCommentTextArea");
		private By rejectButtonCommentBox = By.xpath("/html/body/div[13]/div[3]/div/button[1]/span");
		
		public ApproveTimeTrackPage(WebDriver drvr)
		{
			this.driver = drvr;
		}
		
		public void selectStatusFilterDropDown()
		{
			driver.findElement(dropdown_statusFilter).click();
		}
		
		public void selectReadyForApprovalChkBox()
		{
			driver.findElement(dropdownChk_ReadyForApproval).click();
		}
		
		public void selectNotReadyForApprovalChkBox()
		{
			driver.findElement(dropdownChk_NotReadyForApproval).click();
		}
		
		public void selectRejectedChkBox()
		{
			driver.findElement(dropdownChk_Rejected).click();
		}
		
		public void clickApplyButton()
		{
			driver.findElement(button_Apply).click();
		}
		
		public void clickCancelButton()
		{
			driver.findElement(button_Cancel).click();
		}
			
		public String checkTimeTrackRecordStatus(int grpNum, int rowNum)
		{
			recordStatus = driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+(grpNum)+"]/tr["+(rowNum+1)+"]/td[2]")).getText();
			return recordStatus;
		}
		
		public List<WebElement> getRowCountOfUserGroup(int grpNum)
		{
			htmlTable=driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+grpNum+"]"));
			rows=htmlTable.findElements(By.tagName("tr"));
			return rows;
		}
		
		public String getTimeTrackStatus(int grpNum, int rowNum)
		{
			timeTrackStatus = driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+(grpNum)+"]/tr["+(rowNum)+"]/td[2]")).getText();
			return timeTrackStatus;
		}
		
		public List<WebElement> getColumnCount(int rNum)
		{
			columns = rows.get(rNum).findElements(By.tagName("td"));
			return columns;
		}
		
		public void selectRandomRowCheckbox(int arrayGroupIndexValue, int arrayRowIndexValue)
		{
			driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+arrayGroupIndexValue+"]/tr["+arrayRowIndexValue+"]/td[7]/input")).click();
		}
		
		public void clickApproveButton() 
		{
			driver.findElement(button_Approve).click();
		}
		
		public void clickRejectButton()
		{
			driver.findElement(reject_Button).click();
		}
		
		public void commentForRejection(String comment)
		{
			driver.findElement(rejectionComment).sendKeys(comment);
		}
		public String verifyExpectedRevokeLink(int arrayGroupIndexValue, int arrayRowIndexValue)
		{
			revokeLinkExp = driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+arrayGroupIndexValue+"]/tr["+arrayRowIndexValue+"]/td[6]/input")).getText();
			return revokeLinkExp;
		}
		
		public void clickCommentBoxRejectButton()
		{
			driver.findElement(rejectButtonCommentBox).click();
		}
		public boolean isApplyButtonEnabled()
		{
			statusApplybtn= driver.findElement(button_Apply).isEnabled();
			return statusApplybtn;
			
		}
		
		public boolean isDropDownStatusSelected_1()
		{
			return driver.findElement(dropdownChk_ReadyForApproval).isSelected();
		}
		
		public boolean isDropDownStatusSelected_2()
		{
			return driver.findElement(dropdownChk_NotReadyForApproval).isSelected();
		}
		
		public boolean isDropDownStatusSelected_3()
		{
			return driver.findElement(dropdownChk_Rejected).isSelected();
		}
		
		public void revokeButtonClick(int grpNum, int rowNum)
		{
			driver.findElement(By.xpath("//*[@id=\"approveTTTable\"]/tbody["+grpNum+"]/tr["+rowNum+"]/td[6]/a")).click();
		}

}
