package fer.progi.illidimusdigitus.trueblood;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTests {
	
	private static void waitMiliseconds(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) {

		  System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome Driver\\chromedriver.exe");
		  WebDriver driver = new ChromeDriver(); 
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    

	        boolean result;
	       
	        result = loginTest(driver, "admin", "admin", "admin");
	        System.out.println("Test pass: " + result);
	        
	        result = loginTest(driver, "djelatnik", "djelatnik", "djelatnik");
	        System.out.println("Test pass: " + result);
	        
	        result = loginTest(driver, "donor", "donor", "donor");
	        System.out.println("Test pass: " + result);

	        result = sendBlood(driver, 2);
	        System.out.println("Test pass: " + result);
	        
	        result = changeBloodLimit(driver, 2, "350", "150");
	        System.out.println("Test pass: " + result);
	        
	        result = changeBloodLimit(driver, 2, "55", "-5");
	        System.out.println("Test pass: " + result);
	        
	        result = makeDonation(driver);
	        System.out.println("Test pass: " + result);
	        driver.quit();

	}


	
	
	
	//ispitivanje slanja krvi
	
	private static boolean sendBlood(WebDriver driver, int bloodIndex) {
		
		driver.get("http://localhost:3000/login");
		WebElement element;
	    element = driver.findElement(By.name("username")); 
        element.sendKeys("djelatnik");
       
        //Find password element and enter password
        element = driver.findElement(By.name("password"));
        element.sendKeys("djelatnik");
        
      //submit (login)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitMiliseconds(2000);
        driver.findElement(By.cssSelector("#root > div:nth-child(1) > nav > div.navbar-nav.justify-content-end.ms-auto > div:nth-child(1) > a")).click();  
        driver.findElement(By.cssSelector("#root > div.App > div > div > div.col-md-2.mb-1.col > div > div:nth-child(3)")).click();
        //ovo je zahjevalo dodatni import gore
        Select dropdown = new Select(
        		driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.container.col-md-3.border.border-danger.rounded > form > div:nth-child(1) > div > select")));
        waitMiliseconds(1000);
        dropdown.selectByIndex(bloodIndex);
        
        element = driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.container.col-md-3.border.border-danger.rounded > form > div:nth-child(2) > div:nth-child(1) > input"));
        element.sendKeys("1");
        element = driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.container.col-md-3.border.border-danger.rounded > form > div:nth-child(2) > div:nth-child(2) > input"));
        element.sendKeys("TESTNA_INSTITUCIJA");
        
        String text = driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.col > p:nth-child(5)")).getText();
        String nakonSlanja = text.substring(text.indexOf(":")+2);
        
        driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.container.col-md-3.border.border-danger.rounded > form > div.mb-3.row > div > button")).click();
        waitMiliseconds(1000);
        driver.switchTo().alert().accept();
        
        dropdown = new Select(driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.container.col-md-3.border.border-danger.rounded > form > div:nth-child(1) > div > select")));
        waitMiliseconds(1000);
        dropdown.selectByIndex(bloodIndex);
        
        text = driver.findElement(By.cssSelector("#left-tabs-example-tabpane-third > div > div > div.col > p:nth-child(5)")).getText();
        String trenutnaZaliha = text.substring(text.indexOf(":")+2);    
        return nakonSlanja.equals(trenutnaZaliha);
	}


	//ispitivanje logina
	public static boolean loginTest(WebDriver driver, String username, String password, String role) {
		
		driver.get("http://localhost:3000/login");
		// Find the username input element by its name
		WebElement element;
	    element = driver.findElement(By.name("username"));        
        // Enter username
        element.sendKeys(username);
       
        //Find password element and enter password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
       
        //submit (login)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        waitMiliseconds(2000);
        element = driver.findElement((By.cssSelector("#root > div:nth-child(1) > nav > div.navbar-nav.justify-content-end.ms-auto > div:nth-child(4) > a")));
        
        if(element.getText().equals(role))
        	return true;
        else 
        	return false;
	
	}
	
	
	
	public static boolean changeBloodLimit(WebDriver driver, int bloodIndex, String upperbound, String lowerbound) {
		
		driver.get("http://localhost:3000/login");
		WebElement element;
	    element = driver.findElement(By.name("username")); 
        element.sendKeys("admin");
        element = driver.findElement(By.name("password"));
        element.sendKeys("admin");
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitMiliseconds(2000);
        driver.findElement(By.cssSelector("#root > div:nth-child(1) > nav > div.navbar-nav.justify-content-end.ms-auto > div:nth-child(1) > a")).click();
        driver.findElement(By.cssSelector("#root > div.App > div > div > div.col-md-2.mb-1.col > div > div:nth-child(3)")).click();
        
        Select dropdown = new Select(driver.findElement(By.cssSelector("#parent > form > div:nth-child(1) > div.col-md-6 > select")));
        waitMiliseconds(1000);
        dropdown.selectByIndex(bloodIndex);
        
        element = driver.findElement(By.name("upperbound"));
        element.sendKeys(upperbound);
        element = driver.findElement(By.name("lowerbound"));
        element.sendKeys(lowerbound);
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitMiliseconds(1000);
        if (Integer.parseInt(upperbound) >= 0 && Integer.parseInt(lowerbound) >= 0) {
        	 driver.switchTo().alert().accept();
             dropdown = new Select(driver.findElement(By.cssSelector("#parent > form > div:nth-child(1) > div.col-md-6 > select")));
             waitMiliseconds(1000);
             dropdown.selectByIndex(bloodIndex);
             
             String text = driver.findElement(By.cssSelector("#parent > form > div:nth-child(1) > div.col > p:nth-child(2)")).getText();
             String gornjaGranica = text.substring(text.indexOf(":")+2);
             text = driver.findElement(By.cssSelector("#parent > form > div:nth-child(1) > div.col > p:nth-child(3)")).getText();
             String donjaGranica = text.substring(text.indexOf(":")+2);
            
             return gornjaGranica.equals(upperbound) && donjaGranica.equals(lowerbound);
        } else {
        	String textAlert = driver.findElement(By.cssSelector("#parent > form > div.mb-3.mt-3.row > div > div")).getText();
        	return textAlert.equals("Granice moraju biti pozitivne!");
        }
	}
	
	
	
	
	
	public static boolean makeDonation(WebDriver driver) {
		
		driver.get("http://localhost:3000/login");
		WebElement element;
	    element = driver.findElement(By.name("username")); 
        element.sendKeys("djelatnik");
       
        //Find password element and enter password
        element = driver.findElement(By.name("password"));
        element.sendKeys("djelatnik");
        
      //submit (login)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        waitMiliseconds(2000);
        
        driver.findElement(By.cssSelector("#root > div:nth-child(1) > nav > div.navbar-nav.justify-content-end.ms-auto > div:nth-child(1) > a")).click();
        
       
        
        driver.findElement(By.cssSelector("#root > div.App > div > div > div.col-md-2.mb-1.col > div > div:nth-child(2)")).click();
		
        element = driver.findElement(By.name("filter")); 
        element.sendKeys("TEST_DONOR_NAME");
		
        
        driver.findElement(By.cssSelector("#left-tabs-example-tabpane-second > div > div > div:nth-child(2) > table > tbody > tr:nth-child(3) > td:nth-child(6) > button")).click();
		
        element = driver.findElement(By.name("mjestoDarivanja")); 
        element.sendKeys("TEST_LOKACIJA");
        
        
        driver.findElement(By.cssSelector("#left-tabs-example-tabpane-second > div > div > div > div > form > div:nth-child(23) > div.col-md-6 > button")).click();
        
        waitMiliseconds(1000);
        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        
        return alertText.equals("Donacija evidentirana") || alertText.equals("Nije moguće donirati! Nije prošlo dovoljno vremena od zadnje donacije!");
        
        
	}

	
}

