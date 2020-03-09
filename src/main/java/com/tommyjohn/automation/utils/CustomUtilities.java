package com.tommyjohn.automation.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class CustomUtilities {

	public WebDriver driver;
	public static String baseUrl = "https://tommyjohn.com/";
	public static BufferedReader reader;
	public static Properties properties;
	public static final String propertiesFilePath = "src/main/resources/configuration.properties";

	/**
	 * Method to set up the drivers.
	 * 
	 * @throws Exception
	 */
	//	@BeforeSuite
	@SuppressWarnings("finally")
	public WebDriver launchtj() throws Exception 
	{
		// load properties file
		loadPropertiesFile();

		//	System.setProperty("webdriver.chrome.driver" ,"src\\main\\resources\\chromedriver.exe");
		//	System.setProperty("webdriver.chrome.driver" ,"/tmp/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver" ,properties.getProperty("chromeDriverPath"));
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("window-size=1366,768");
		driver= new ChromeDriver(chromeOptions);
		driver.get(baseUrl);

		//driver.navigate().refresh();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("bcx_local_storage_frame")));
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bx-close-xsvg")));
			driver.findElement(By.cssSelector(".bx-close-xsvg")).click();
			driver.switchTo().defaultContent();
		}
		catch (ElementNotVisibleException e)
		{
			System.out.println("Overlay not displayed");
		}
		finally {

			driver.get(baseUrl);
			return driver;
		}

	}
	//	@AfterSuite
	public void teardown()
	{
		if (driver != null)
			driver.quit();
	}

	//method to load properties file
	public static void loadPropertiesFile()throws Exception 
	{
		reader = new BufferedReader(new FileReader(propertiesFilePath));
		properties = new Properties();
		properties.load(reader);
		reader.close();
		if(properties.isEmpty()) {
			Reporter.log("Properties file not loaded or empty..!");
			System.exit(0);
		}

	}
	
	

}
