package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;  //This package is responsible for generating multiple data at run time.
import org.apache.logging.log4j.LogManager;   //Log4j
import org.apache.logging.log4j.Logger;   //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {

	public WebDriver driver;
	public Logger logger;  //Log4j
	public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		
		//Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		
		logger = LogManager.getLogger(this.getClass());   //Log4j2
		
		switch(br.toLowerCase())
		{
		case "chrome" : driver =  new ChromeDriver(); break;
		case "edge" : driver =  new EdgeDriver(); break;
		case "firefox" : driver =  new FirefoxDriver(); break;
		default : System.out.println("Invalid Browser Name..."); return;   //here, return means it will directly go out from the execution.
		}	
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL1"));   //reading url from properties file.		
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void teardown()
	{
		driver.close();
	}
	
	
	
	public String randomString()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(5);  //This pre-define method will create random string of 5 characters.
		return generatedstring;
	}
	
	public String randomNumber()
	{
		String generatednumber = RandomStringUtils.randomNumeric(10);  //This pre-define method will create random Numeric of 10 Numbers.
		return generatednumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(4);
		String generatednumber = RandomStringUtils.randomNumeric(4);
		return (generatedstring + "@" + generatednumber);
	}
	
	public String captureScreen(String tname) throws IOException {
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
	
}
