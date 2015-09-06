package games;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SizeOfIcons {
	private WebDriver driver;
    private String baseUrl;
    private JavascriptExecutor jse;
    
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        jse = (JavascriptExecutor)driver;
        baseUrl = "http://www.y8.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
	@Test
	public void testSize() throws Exception {
		driver.get(baseUrl);
		WebElement first = driver.findElement(By.xpath("//*[@id='items_container']/div[1]"
				+ "/descendant::img[@class='playable']"));
		int width = first.getSize().getWidth();
		int height = first.getSize().getHeight();
		WebElement lastOnPage;
		int lastOnPageHashCode;
		WebElement counter = first;
		WebElement newLastOnPage;
		
		int count = 1;
		int number = 1;
		
		while(true){
			lastOnPage = driver.findElement(By.xpath("//*[@id='items_container']/div[last()]"
					+ "/descendant::img[@class='playable']"));
			lastOnPageHashCode = lastOnPage.hashCode();
			
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			while(true){
			count++;
			counter = driver.findElement(By.xpath("//*[@id='items_container']/div["+ (count) + "]"
					+ "/descendant::img[@class='playable']"));
			
			if(counter.isDisplayed()){
				number++;
				if((counter.getSize().getWidth()!=width)
						||(counter.getSize().getHeight()!=height)){
						String title = (driver.findElement(By.xpath("//*[@id='items_container']/div["+ (count) + "]"
							+ "/descendant::div[@class='game-title']")).getText());
					System.out.println(title + ": number " + number
							+ "has incorrect logo size!");
					}
				}
			if(lastOnPageHashCode == (counter.hashCode())){
				if(lastOnPage.equals(counter)){
					break;
				}
			}
			}
			newLastOnPage = driver.findElement(By.xpath("//*[@id='items_container']/div[last()]"
					+ "/descendant::img[@class='playable']"));
			if (newLastOnPage.hashCode() == lastOnPageHashCode){
				if (newLastOnPage.equals(lastOnPage)){
				break;
				}
			}
		}
		System.out.println("Number of items on the Page = " + number);
	}

	@After
    public void tearDown() throws Exception {
        driver.quit();
        }
}
