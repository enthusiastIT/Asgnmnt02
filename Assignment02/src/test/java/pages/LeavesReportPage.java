package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeavesReportPage {
	
		WebDriver driver = null;
		
		By reportsPageButton = By.id("container_reports");
		By reportCloseButton = By.id("closeCreateChartLightboxButton");
		
		public LeavesReportPage(WebDriver drvr)
		{
			this.driver = drvr;
		}
		
		public void selectLeavesReport()
		{
			driver.findElement(By.xpath("//*[@id=\"reportConfig_119\"]/tbody/tr[2]/td/div/div[1]")).click();
		}
		
		public void closeReportWindow()
		{
			driver.findElement(reportCloseButton).click();
		}
}
