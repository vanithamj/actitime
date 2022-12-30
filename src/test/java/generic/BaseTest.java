package generic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	public static final String XLPATH="./data/input.xlsx";
//	public String CONFIGPATH ="./config.properties";
	
	@Parameters("property")
	@BeforeMethod
	public void openApp(@Optional("config.properties") String propertyFile) throws Exception 
	{
		
		String useGrid=Utility.getPropertyValue(propertyFile,"USEGRID");
		String gridURL=Utility.getPropertyValue(propertyFile,"GRIDURL");
		
	String browser=Utility.getPropertyValue(propertyFile,"BROWSER");
	String appURL=Utility.getPropertyValue(propertyFile,"APP_URL");
	String strITO=Utility.getPropertyValue(propertyFile,"ITO");
	long lITO=Long.parseLong(strITO);
	String strETO=Utility.getPropertyValue(propertyFile,"ETO");
	long lETO=Long.parseLong(strETO);
	
	
	if(useGrid.equalsIgnoreCase("Yes"))
	{
		URL remoteURL=new URL(gridURL);
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setBrowserName(browser);
		driver=new RemoteWebDriver(remoteURL,capabilities);
	}
	else
	{			
		switch (browser.toLowerCase()) 
		{
			case "chrome":
							/*WebDriverManager.firefoxdriver().setup();
							driver=new FirefoxDriver();
							break;*/
							WebDriverManager.chromedriver().setup();
							driver=new ChromeDriver();
							break;
	
			default:
							WebDriverManager.firefoxdriver().setup();
							driver=new FirefoxDriver();
							break;
							/*WebDriverManager.chromedriver().setup();
							driver=new ChromeDriver();
							break;*/
		}
	}
		
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait( Duration.ofSeconds( lITO));
		wait=new WebDriverWait(driver, Duration.ofSeconds(lETO));
		
	}
	
	@AfterMethod
	public void closeApp(ITestResult result) throws Exception 
	{
		String testName = result.getName();
		int status = result.getStatus();
		if(status==2)
		{
			//test.fail("Test:"+testName+" is failed");
			TakesScreenshot tDriver=(TakesScreenshot)driver;
			File scrFile = tDriver.getScreenshotAs(OutputType.FILE);
			File dstFile = new File("./screenshots/"+testName+".png");
			FileUtils.copyFile(scrFile, dstFile);
			Reporter.log("Test:"+testName +" Failed and ScreenShot has been taken:"+dstFile, true);
		//	test.info("Test:"+testName +" Failed and ScreenShot has been taken:"+dstFile);
		//	test.addScreenCaptureFromPath("./../screenshots/"+testName+".png");
		}
	//	test.info("Closing the browser");
	//	driver.quit();
		driver.quit();
	}
	

}
