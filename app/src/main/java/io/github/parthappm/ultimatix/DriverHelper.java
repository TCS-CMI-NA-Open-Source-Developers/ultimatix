package io.github.parthappm.ultimatix;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class DriverHelper
{
	private final WebDriver driver;

	DriverHelper()
	{
		UserProperties properties = UserProperties.getInstance();

		// setting the browser
		System.setProperty(properties.getDriverPropertyName(), properties.getDriverPath());
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.edge.silentOutput", "true");
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		switch (properties.getBrowserName())
		{
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	void get(String url)
	{
		driver.get(url);
	}

	WebElement findElement(By by)
	{
		return driver.findElement(by);
	}

	WebDriver.TargetLocator switchTo()
	{
		return driver.switchTo();
	}

	void executeScript(String script)
	{
		((JavascriptExecutor) driver).executeScript(script);
	}

	void waitInfinite()
	{
		do
		{
			List<LogEntry> logEntries = driver.manage().logs().get(LogType.DRIVER).getAll();
			int noOfLogs = logEntries.size();
			if (noOfLogs > 0)
			{
				String message = logEntries.get(noOfLogs - 1).getMessage();
				if (message.contains("Timed out connection") || message.contains("disconnected") || message.contains("no such window"))
				{
					break;
				}
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException ignore)
			{
				break;
			}
		} while (true);
	}

	void close()
	{
		if (driver != null)
		{
			driver.quit();
		}
	}
}
