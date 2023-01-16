package io.github.parthappm.ultimatix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

class UserProperties
{
	private static UserProperties currentProperties;
	private final String browser;
	private final String driverPath;
	private String username;
	private String password;

	private UserProperties()
	{
		Properties properties = new Properties();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			properties.load(new FileInputStream("resources/application.properties"));
			// setting the username
			String username = properties.getProperty("username", "").trim();
			if (username.equals(""))
			{
				System.out.print("Username: ");
				this.username = br.readLine().trim();
			}
			else
			{
				this.username = username;
			}
			// setting the password
			String password = properties.getProperty("password", "").trim();
			if (password.equals(""))
			{
				System.out.print("Password for " + this.username + ": ");
				// this.password = br.readLine().trim(); // use this statement instead of the below statement if running through an IDE
				this.password = new String(System.console().readPassword()).trim();
			}
			else
			{
				this.password = password;
			}
		}
		catch (IOException ignored) {}
		this.browser = properties.getProperty("browser", "").trim();
		this.driverPath = properties.getProperty("driverPath", "").trim();
	}

	static UserProperties getInstance()
	{
		if (currentProperties == null)
		{
			currentProperties = new UserProperties();
		}
		return currentProperties;
	}

	String getBrowserName()
	{
		return this.browser;
	}

	String getDriverPropertyName()
	{
		String propertyName;
		switch (this.browser)
		{
			case "chrome":
				propertyName = "webdriver.chrome.driver";
				break;
			case "firefox":
				propertyName = "webdriver.gecko.driver";
				break;
			default:
				propertyName = "webdriver.edge.driver";
		}
		return propertyName;
	}

	String getDriverPath()
	{
		return this.driverPath.equals("") ? "msedgedriver.exe" : this.driverPath;
	}

	String getUsername()
	{
		return this.username == null ? "" : this.username;
	}

	String getPassword()
	{
		return this.password == null ? "" : this.password;
	}
}
