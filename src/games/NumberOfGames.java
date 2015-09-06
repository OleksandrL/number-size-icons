package games;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.junit.Test;

public class NumberOfGames {
	private WebDriver driver;
    private String baseUrl;
    private String numberOfGamesBefore;
    private String numberOfGamesAfter;
    private String numberOfGamesCount;
    private JavascriptExecutor jse;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        jse = (JavascriptExecutor)driver;
        baseUrl = "http://ru.y8.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
	@Test
	public void testNumber() throws Exception {
		driver.get(baseUrl + "tags/Zombies");
		WebElement numb;
		
		// Warning: waitForTextPresent may require manual changes 
        for (int second = 0;; second++) {
         	if (second >= 60) fail("timeout");
         	try { 
         		numb = driver.findElement(By.id("quantity"));
         	if (numb.isDisplayed()){
         		numberOfGamesBefore = numb.getText();
         		break;
         		} 
         	} catch (Exception e) {}
         	Thread.sleep(1000);
         }
		WebElement oldOne;
		WebElement newOne;
		WebElement counter;
		
		counter = driver.findElement(By.xpath("//*[@id='items_container']/div[1]"));
		int count = 1;
		int number = 1;
		String oldOneID;
		while(true){
			oldOne = driver.findElement(By.xpath("//*[@id='items_container']/div[last()]"));
			oldOneID = oldOne.getAttribute("id");
			
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			/*boolean pageDown = false;
			while(!pageDown){
				pageDown = ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
				Thread.sleep(1000);
			}*/
			
			while(!(oldOneID.equals(counter.getAttribute("id")))){
				count++;
				counter = driver.findElement(By.xpath("//*[@id='items_container']/div["+ (count) + "]"));
				if(counter.isDisplayed()){
					number++;
				}
			}
			
			newOne = driver.findElement(By.xpath("//*[@id='items_container']/div[last()]"));
			if (newOne.getAttribute("id").equals(oldOneID)){
				break;
			}
		}
		
		numberOfGamesCount = "" + number;
		
		numberOfGamesAfter = driver.findElement(By.id("quantity")).getText();
		
		System.out.println ("numberOfGamesBefore = " + numberOfGamesBefore);
		System.out.println ("numberOfGamesAfter = " + numberOfGamesAfter);
		System.out.println ("numberOfGamesCount = " + numberOfGamesCount);
	}
	
	@After
    public void tearDown() throws Exception {
        driver.quit();
        }
    }

