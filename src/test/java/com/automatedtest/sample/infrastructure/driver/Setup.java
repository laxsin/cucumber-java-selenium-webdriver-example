package com.automatedtest.sample.infrastructure.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.cucumber.java.Before;

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
			// System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			System.setProperty("webdriver.chrome.driver", "src/main/java/drivers/chromedriver_v84_linux");
	//		System.setProperty("webdriver.chrome.driver", "src/main/java/drivers/chromedriver_v76_linux");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setJavascriptEnabled(true);

			ChromeOptions options = new ChromeOptions();
			options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
		
			options.merge(cap);
			driver = new ChromeDriver(options);
			driver.get("http://www.gmail.com");
		//	driver.get("https://userapi.dit03-insight-dev.com/oauth2/207931/SSO/login/insight?RelayState=/keep");
		//	driver.get(url);
			Thread.sleep(15000);
			System.out.println(driver.getTitle());
			if(driver.getTitle().equals("Gmail")) {
				System.out.println("On Gmail login screen");
				System.out.println("***********************************************");
				
				System.out.println(driver.getPageSource());
				
				System.out.println("***********************************************");

			}
			System.out.println("New Chrome driver Initiated successfully in linux");
			verifyLogin();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		default:
			throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
		}
	}

	public boolean verifyLogin() {
		try {
		//	Thread.sleep(20000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("entering email");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).sendKeys("devopstestingpractice@gmail.com");
		 	System.out.println("clicking Next");
			driver.findElement(By.id("next")).click();
			System.out.println("clicked on Next");
			Thread.sleep(20000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("Entering password");
			WebElement ele = driver.findElement(By.id("Passwd-hidden"));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", ele);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			executor.executeScript("document.getElementById('Passwd-hidden').value='Learn&Master@123';");
			System.out.println("entered password");
			System.out.println("clicking next again");
			driver.findElement(By.id("next")).click();
			System.out.println("clicked next again");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(10000);
			if (driver.getTitle().equals("Iron Mountain InSight")) {
				System.out.println(driver.getTitle());
				return true;
			}
		} catch (Exception e) {
			System.out.println(driver.getTitle());
			e.printStackTrace();
		}
		System.out.println("title not matched");
		System.out.println(driver.getTitle());
		return false;
	}

	public void ExecuteShellCommandRuntimeExec(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success");
				System.out.println(output);
				System.exit(0);
			} else {
				System.out.println("Something abnormal has haapened :( ");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
