package com.automatedtest.sample.infrastructure.driver;

import io.cucumber.java.Before;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Setup {

	public static WebDriver driver;

	/*
	 * @Before public void setWebDriver() throws Exception {
	 * 
	 * // for windows String browser = System.getProperty("browser"); if (browser ==
	 * null) { browser = "chrome"; } switch (browser) { case "chrome": ChromeOptions
	 * chromeOptions = new ChromeOptions();
	 * chromeOptions.addArguments("['start-maximized']");
	 * System.setProperty("webdriver.chrome.driver",
	 * "src/main/java/drivers/chromedriver_v140_win.exe"); driver = new
	 * ChromeDriver(chromeOptions); break; case "firefox": driver = new
	 * FirefoxDriver(); driver.manage().window().maximize(); break; default: throw
	 * new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
	 * } }
	 */

	@SuppressWarnings("deprecation")
	@Before
	public void setWebDriver() throws Exception {
		// for linux
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = "chrome";
		}
		switch (browser) {
		case "chrome":
			System.out.println("inside linux system");
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			
			/* chromedriver --user-data-dir=$HOME/.config/google-chrome
			 --app=https://userapi.dit03-insight-dev.com/oauth2/207931/SSO/login/insight?RelayState=/keep
			 */
			
			 DesiredCapabilities cap = new DesiredCapabilities();
			 cap.setJavascriptEnabled(true);
		    
			ChromeOptions options = new ChromeOptions();
			options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
// 			echo $HOME
//			/home/practiceframework/
//			/home/practiceframework/.config/google-chrome
			options.addArguments("--user-data-dir='$HOME/.config/google-chrome'");

//			options.addArguments("--disable-setuid-sandbox");
// 			options.addArguments("disable-gpu");
//			options.addArguments("--test-type");
// 			options.addArguments("--window-size=1920,1080");
// 			options.addArguments("window-size=1920,1080");
// 			options.addArguments("start-maximized");
// 			options.addArguments("--start-maximized");
// 			options.addArguments("--disable-dev-shm-usage");
// 			options.addArguments("--acceptSslCerts=true");
// 			options.addArguments("--ignore-certificate-errors");
// 			options.addArguments("--remote-debugging-port=9515");
// 			options.addArguments("--no-first-run");
// 			options.addArguments("--disable-extensions");
// 			options.addArguments("--proxy-server='direct://'");
// 			options.addArguments("--proxy-bypass-list=*");
// 			options.addArguments("--disable-dev-shm-usage");
			
			System.out.println("Options are set");

			options.merge(cap);
		        
		        
			/* DesiredCapabilities capabilities = DesiredCapabilities.chrome(); //
			 capabilities.setCapability("browser_version", "76");
			 capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			 capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			 capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
			 UnexpectedAlertBehaviour.IGNORE);
			*/  
			 System.out.println("caps set"); driver = new ChromeDriver(cap);
			 
		/*	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(capabilities);*/
			driver = new ChromeDriver(options);
	//		driver.manage().deleteAllCookies();
			System.out.println("driver = " + driver);
			driver = new ChromeDriver(options);
			System.out.println("New Chrome driver Initiated successfully in linux");
			break;
		case "firefox":
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		default:
			throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
		}
	}
}
