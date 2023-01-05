package pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActitimeUsersPage {

	WebDriver driver = null;
	
	private By userDetailsPanel = By.cssSelector(".edit_user_sliding_panel.sliding_panel.components_panelContainer");
	private By pLink = By.xpath("//*[@id=\"editUserPanel\"]/div[1]/div/div[3]/div[2]/span");
	private By panelHideBtn = By.xpath("//*[@id=\"container\"]/div[12]/div[1]/div[3]");
	
	public ActitimeUsersPage(WebDriver drvr)
	{
		this.driver = drvr;
	}
	
	public void selectUser()
	{
		List<WebElement> usersElmnt = driver.findElements(By.cssSelector(".userNameContent.clickable"));
		Random rIndex = new Random();
		int randIndex = rIndex.nextInt(usersElmnt.size());
		usersElmnt.get(randIndex).click();
		
	}
	
	public boolean userDetailsWindow()
	{
		return driver.findElement(userDetailsPanel).isDisplayed();
	}
	
	public void viewPermission()
	{
		driver.findElement(pLink).click();
	}
	
	public void closeUserInfoPanel()
	{
		driver.findElement(panelHideBtn).click();
	}
}
