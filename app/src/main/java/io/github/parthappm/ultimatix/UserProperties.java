package io.github.parthappm.ultimatix;

import java.io.*;
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
		try (InputStream is = new FileInputStream("resources/application.properties"))
		{
			properties.load(is);
		}
		catch (IOException | NullPointerException ignored) {}

		this.username = properties.getProperty("username", "").trim();
		this.password = properties.getProperty("password", "").trim();
		this.browser = properties.getProperty("browser", "").trim();
		this.driverPath = properties.getProperty("driverPath", "").trim();

		// reading the username and password from console
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			// setting the username
			if (this.username.equals(""))
			{
				System.out.print("Username: ");
				this.username = br.readLine().trim();
			}
			// setting the password
			if (this.password.equals(""))
			{
				System.out.print("Password for " + this.username + ": ");
				Console console = System.console();
				this.password = (console == null ? br.readLine() : new String(System.console().readPassword())).trim();
			}
		}
		catch (IOException | NullPointerException ignored) {}
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
		return this.driverPath;
	}

	String getUsername()
	{
		return this.username;
	}

	String getPassword()
	{
		return this.password;
	}
}
