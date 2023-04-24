package com.qa.demo.selenium;

import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Button;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "classpath:cat-schema.sql",
		"classpath:cat-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class SpringSeleniumTest {

	private WebDriver driver;

	@LocalServerPort
	private int port;

	private WebDriverWait wait;

	@BeforeEach
	void init() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		this.driver = new ChromeDriver(options);
		this.driver.manage().window().maximize();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	}

	@Test
	void testTitle() {
		this.driver.get("http://localhost:" + port + "/");

		WebElement title = this.driver.findElement(By.cssSelector("body > header > h1"));

		assertEquals("CATS", title.getText());
	}

	@Test
	void testGetAll() throws Exception {
		this.driver.get("http://localhost:" + port + "/");

		WebElement card = this.wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#output > div > div")));

		assertTrue(card.getText().contains("Mr Bigglesworth"));
	}
	
	@Test
	void testDelete() throws Exception {
		this.driver.get("http://localhost:" + port + "/");
		


		WebElement card = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#output > div > div > div > button:nth-child(6)")));
	    card.click();
		
		List<WebElement> cards = driver.findElements(By.className("card")); 
		assertTrue(cards.isEmpty());
	}
	
	@Test
	void testCreate() throws Exception {
		this.driver.get("http://localhost:" + port + "/");
		

		WebElement inputName = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catName")));
		inputName.sendKeys("Private Meow");
				
		WebElement inputLength =  this.wait 
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catLength")));
		inputLength.sendKeys("28");
		
		WebElement hasWhiskersCheckBox = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catWhiskers")));
		hasWhiskersCheckBox.click();
		
		WebElement evilCheckBox = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catEvil")));
		evilCheckBox.click();
		
		WebElement submit = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catForm > div.mt-3 > button.btn.btn-success")));
		submit.click();
		
		
		
		assertTrue(inputName.getAttribute("value").contains("Private Meow"));
	
//	card.click();
		
	}
	
	@Test
	void testUpdate() throws Exception {
		this.driver.get("http://localhost:" + port + "/");
		
		WebElement card = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#output > div > div > div > button:nth-child(5)")));
		card.click();
		
		WebElement reset = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#updateForm > div.mt-3 > button.btn.btn-primary")));
		reset.click();
		

//		WebElement element = driver.findElement(By.cssSelector("#catName"));
//		if(element.isDisplayed()) {
//		    System.out.println("Element is visible");
//		} else {
//		    System.out.println("Element is not visible");
//		}
			    
	
		WebElement updateName = this.wait
	    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catName")));
		updateName.sendKeys("Lola");
		
				
		WebElement updateLength =  this.wait 
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catLength")));
		updateLength.clear();
		updateLength.sendKeys("90");
		
		WebElement hasWhiskersCheckBox = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catWhiskers")));
		hasWhiskersCheckBox.click();
		
		WebElement evilCheckBox = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#catEvil")));
		evilCheckBox.click();
		
		WebElement submitBtn = this.wait
		.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#updateForm > div.mt-3 > button.btn.btn-success")));
		 submitBtn.click();
		 
		

	}

	@AfterEach
	void tearDown() {
//		this.driver.close();
	}

}