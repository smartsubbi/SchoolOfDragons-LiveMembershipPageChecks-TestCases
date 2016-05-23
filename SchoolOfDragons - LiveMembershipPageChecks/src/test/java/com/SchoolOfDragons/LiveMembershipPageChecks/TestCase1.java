package com.SchoolOfDragons.LiveMembershipPageChecks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import Factory.BrowserFactory;
import Pages.AfterLoggedInPage;
import Pages.CommonHeader;
import Pages.LoginPage;
import Pages.MembershipPage;
import ReUse.SendMailSSL;
import Utility.CaptureScreenshot;

public class TestCase1
{
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;		
	String Category;
	static String testCase1Result = "No result";	
	
	@BeforeClass
	public void setUp() throws Throwable
	{		
		report=ExtentManager.Instance();
		driver = BrowserFactory.getBrowser("firefox");
	}
	
	@Parameters(value="Category")
	@Test()
	public void ValidAuthorisedPlayerLogin(String catg) throws Throwable
	{
		logger = report.startTest("Test Case 1 : Live - Verify Membership page - Verify Membership","This will verify membership page contents, Select a paymethod DB and payment method forms");		
		logger.log(LogStatus.INFO, "Browser is up and running");
		String browserOpenedScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "browserOpenedScreenshot"));
		logger.log(LogStatus.INFO, browserOpenedScreenshot);		
		driver.get("http://www.schoolofdragons.com");
		logger.log(LogStatus.INFO, "Url is Loading");		
		Thread.sleep(5000);		
		CommonHeader header = PageFactory.initElements(driver, CommonHeader.class);		
		String homePageScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "homePageScreenshot"));
		logger.log(LogStatus.INFO, homePageScreenshot);		
		header.clickHeaderLoginLink();
		logger.log(LogStatus.INFO, "Clicked the Login Link on the Homepage header");		
		Thread.sleep(5000);		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);	
		String loginPageScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "loginPageScreenshot"));
		logger.log(LogStatus.INFO, loginPageScreenshot);		
		Thread.sleep(5000);		
		loginPage.userNameType("subbuPlayer");
		logger.log(LogStatus.INFO, "Entered username : subbuPlayer");
		loginPage.passwordType("123456");
		logger.log(LogStatus.INFO, "Entered password : 123456");
		String afterEnteringUsernameAndPassword=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "afterEnteringUsernameAndPassword"));
		logger.log(LogStatus.INFO, afterEnteringUsernameAndPassword);
		loginPage.playNowButtonClick();
		logger.log(LogStatus.INFO, "Clicked on the Play Now button after entering Username and Password");		
		Thread.sleep(5000);		
		AfterLoggedInPage afterLoggedInPage = PageFactory.initElements(driver, AfterLoggedInPage.class);    					
		afterLoggedInPage.currentlyLoggedInText("subbuPlayer").isDisplayed();
		afterLoggedInPage.afterLoggedInSuccessfully();
		logger.log(LogStatus.INFO, "After Logged in Page is verified successfully");
		String afterLoggedinPageScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "afterLoggedinPageScreenshot"));
		logger.log(LogStatus.INFO, afterLoggedinPageScreenshot);		
		Thread.sleep(5000);		
		header.clickMembershipTab();
		logger.log(LogStatus.INFO, "Clicked the Membership tab Link on the Homepage");		
		Thread.sleep(5000);					
		String membershipPageScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver, "membershipPageScreenshot"));
		logger.log(LogStatus.INFO, membershipPageScreenshot);		
		Thread.sleep(5000);		
		MembershipPage membershipPage = PageFactory.initElements(driver, MembershipPage.class);	
		membershipPage.verifyAllMembersipOptionsAndFeatures(logger);			
		BrowserFactory.closeBrowser();
		logger.log(LogStatus.INFO, "Quitting the Browser Opened");		
	}
	
	@AfterMethod
	public void afterTest(ITestResult result) throws Throwable
	{
		if(result.getStatus()==ITestResult.FAILURE)	
		{		
			logger.log(LogStatus.FAIL, "<pre>" + result.getThrowable().getMessage() + "</pre>");
			String failureScreenshot=logger.addScreenCapture(CaptureScreenshot.takeScreenshot(driver,result.getName()));			  
			logger.log(LogStatus.FAIL, failureScreenshot);	
			testCase1Result = "Fail";
		}	
		testCase1Result = "Pass";
		System.out.println("Monthly Price : "+MembershipPage.oneMonthExpectedPrice);
		System.out.println("Three Month Price : "+MembershipPage.threeMonthsExpectedPrice);                        
        System.out.println("Six Month Price : "+MembershipPage.sixMonthsExpectedPrice);                        
        System.out.println("Twelve Month Price : "+MembershipPage.twelveMonthsExpectedPrice); 
	}
	
	@AfterClass
	public void tearDown()
	{
		report.endTest(logger);
		report.flush();	
		BrowserFactory.closeBrowser();
		report.close();				
	}		
	
	@AfterTest
	public void printReportPath() throws Throwable
	{	
		System.out.println("Monthly Price : "+MembershipPage.oneMonthExpectedPrice);
		System.out.println("Three Month Price : "+MembershipPage.threeMonthsExpectedPrice);                        
        System.out.println("Six Month Price : "+MembershipPage.sixMonthsExpectedPrice);                        
        System.out.println("Twelve Month Price : "+MembershipPage.twelveMonthsExpectedPrice); 
		String emailReportPathToSend = ExtentManager.finalPath1;
		String mailContent ="You can refer to the below report path for the run result : \n"+emailReportPathToSend+
				"\n\n Refer to the file with date as : "+ExtentManager.fileDate+
				"\n\n ========================================================================================================"+
				"\n                            Below are the Membership Details Verified                                      "+
				"\n =========================================================================================================="+
				"\n\n Monthly Price : "+MembershipPage.oneMonthExpectedPrice+		                
	            "\n\n Three Month Price : "+MembershipPage.threeMonthsExpectedPrice+                        
                "\n\n Six Month Price : "+MembershipPage.sixMonthsExpectedPrice+                        
                "\n\n Twelve Month Price : "+MembershipPage.twelveMonthsExpectedPrice;                        	     
		String mailSubject = "School Of Dragons - Live - Verify Membership Page - Membership Options Contents and Membership Features Section";
		SendMailSSL.sendMail(mailContent, mailSubject);	
	}

}
