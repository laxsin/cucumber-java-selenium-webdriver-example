package com.automatedtest.sample.infrastructure.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setJavascriptEnabled(true);

			ChromeOptions options = new ChromeOptions();
			options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			/*options.addArguments("--screenshot"); 		// takes screenshot of the cuurent page and put it in root dir
			options.addArguments("--dump-dom"); 		// prints the DOM
			options.addArguments("--remote-debugging-port=9222"); // navigate to http://localhost:9222 in another browser.
*/			System.out.println("Options are set");

			options.merge(cap);
			System.out.println("caps set");
			// driver.manage().deleteAllCookies();
			driver = new ChromeDriver(options);
			// driver.get("http://www.google.com");
			driver.get("https://userapi.dit03-insight-dev.com/oauth2/207931/SSO/login/insight?RelayState=/keep");
			Thread.sleep(5000);
			System.out.println("New Chrome driver Initiated successfully in linux");
			System.out.println(driver.getTitle());
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
			System.out.println("entering email");
			driver.findElement(By.id("identifierId")).sendKeys("perftest01@ironmountain-insight.com");
			System.out.println("clicking Next");
			driver.findElement(By.id("identifierNext")).click();
			Thread.sleep(10000);
			System.out.println("Entering password");
			driver.findElement(By.xpath("//input[@type='password'")).sendKeys("PerfLo@dTest2020");
			System.out.println("clicking next again");
			driver.findElement(By.id("passwordNext")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if (driver.getTitle().equals("Iron Mountain InSight"))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
