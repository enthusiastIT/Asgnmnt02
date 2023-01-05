package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ActitimeLoginPage {
	
	private WebDriver driver = null;
	
	private By textbox_username = By.id("username");
	private By textbox_password = By.name("pwd");
	private By button_login = By.id("loginButton");

	public ActitimeLoginPage(WebDriver webdrvr) {
	
		driver = webdrvr;
	}
	
	public void setTextInUsernameTxtBox(String userName) {
		driver.findElement(textbox_username).sendKeys(userName);
	}
	
	public void setTextInPasswordTxtBox(String pWrd) {
		driver.findElement(textbox_password).sendKeys(pWrd);
	}
	
	public void clickLoginButton() {
		driver.findElement(button_login).sendKeys(Keys.RETURN);
	}

}
