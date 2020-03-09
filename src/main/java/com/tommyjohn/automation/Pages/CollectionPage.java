package com.tommyjohn.automation.Pages;

import org.openqa.selenium.WebDriver;

import com.tommyjohn.automation.Components.CollectionPageComponents;

public class CollectionPage 
{
	WebDriver driver;
	public CollectionPage(WebDriver driver)
	{

		this.driver=driver;
	}



	// All collection URLs
	public void validateallcollectionurls() throws Exception
	{
	new CollectionPageComponents(driver).validateallurls();
	}

}



