package com.tommyjohn.automation.Components;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class CollectionPageComponents {
	public CollectionPageComponents(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driver;
	WebElement element;
	SoftAssert softAssert = new SoftAssert();
	String text;
	List<String> url_list = new ArrayList<String>();
	public void validateallurls()

	{
		try {
			File src = new File("src/main/resources/AllCollectionPages.xlsx");

			// load file
			FileInputStream fis = new FileInputStream(src);

			// Load workbook
			XSSFWorkbook wb = new XSSFWorkbook(fis);

			// Load sheet- Here we are loading first sheet only
			XSSFSheet sh1 = wb.getSheetAt(0);
			System.out.println(sh1.getRow(0).getCell(0).getStringCellValue());

			int rowCount = sh1.getLastRowNum() - sh1.getFirstRowNum();

			for (int i = 1; i < rowCount + 1; i++)
			{
				String url = sh1.getRow(i).getCell(0).getStringCellValue();
				text = Check_Undefined_Title(url);
				if(text != null)
				{ 
					url_list.add(text);
				}
			}

		} 
		catch (Exception e) {
			Reporter.log("File not found");
		}

		if(url_list.size() > 0)
		{
			sendmail(url_list);
		}

	}

	private String Check_Undefined_Title(String url) throws InterruptedException 
	{ 
		driver.get(url);
		Thread.sleep(10000);
		List<WebElement> Titles = driver.findElements(By.cssSelector("div.mega-collection-grid__title"));
		Reporter.log("No. of Titles present in " + url + " is " + Titles.size());

		for(int i =0; i<Titles.size(); i++)
		{
			element = Titles.get(i);

			if(element.getText().equalsIgnoreCase("undefined")) 
			{				
				text= url;	
			}
		}	
		return text;
	}

	private void sendmail(List<String> url) {

		// Recipient's email ID needs to be mentioned.
		String to1 = "vijeta@tommyjohnwear.com";
		/*
		 * String to2 = "saurabh.agarwal@tommyjohnwear.com"; 
		 * String to3 ="manoj.konale@tommyjohnwear.com"; 
		 * String to4 = "vipul@tommyjohnwear.com";
		 * String to5 = "anjali.pathak@tommyjohnwear.com"; 
		 * String to6 ="jubin@tommyjohnwear.com"; String to7 = "akshata@tommyjohnwear.com";
		 */
		// String to8 = "anil@tommyjohnwear.com";
		String to9 = "shweta.garhewal@tommyjohnwear.com";
		// Sender's email ID needs to be mentioned
		String from = "noreplymw@tommyjohnwear.com";
		// Assuming you are sending email from localhost
		String host = "secure.emailsrvr.com";
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("edw_job_alerts@aretove.com", "mA9$hjq@jpL");
			}
		};

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, auth);
		System.out.println("Session Created");

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to1));

			/*
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to2));
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to3));
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to4));
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to5));
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to6));
			 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to7));
			 */

			// message.addRecipient(Message.RecipientType.TO,new InternetAddress(to8));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to9));
			// Set Subject: header field
			message.setSubject("Collection Page has undefined title! ");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			//messageBodyPart.setText("Please check for collection as it may have 'undefined' title : " + url);

			messageBodyPart.setText("Hello! \n \n Please check for following collection pages as it may have 'undefined' title: \n" +url+ "\n \n Thanks!");

			// Create a multiple message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = "<Enter File Path of Emailable Report>";
			// DataSource source = new FileDataSource(filename);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			// multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
			System.out.println(message.getSubject());
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
	}

}
