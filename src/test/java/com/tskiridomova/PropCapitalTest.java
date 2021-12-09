package com.tskiridomova;

import static org.junit.Assert.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PropCapitalTest {
  private ChromeDriver driver;
  private final String baseUrl = "https://prop.capital";

  @Before
  public void login() {
    WebDriverManager.chromedriver().setup();
    this.driver = new ChromeDriver();
    this.driver.get(this.baseUrl + "/login");
    WebElement email = this.driver.findElement(By.id("email"));
    email.sendKeys("demo@prop.capital");
    WebElement password = this.driver.findElement(By.id("password"));
    password.sendKeys("demo1234");
    WebElement submit = this.driver.findElement(By.xpath("//button[@type='submit']"));
    submit.click();
    new WebDriverWait(driver, Duration.ofSeconds(10))
      .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div/div/div/div[1]/div[2]")));
  }

  @Test
  public void shouldReturnHeaderWhenUserLoggedIn() {
    assertEquals("Welcome to your Jetstream application!",
      this.driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div/div/div/div[1]/div[2]")).getText());
  }

  @Test
  public void shouldReturnNewProfileNameWhenChanged() {
    this.driver.get(this.baseUrl + "/user/profile");
    WebElement name = this.driver.findElement(By.id("name"));
    name.clear();
    name.sendKeys("New Name");
    this.driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div/div[1]/div[1]/div[2]/form/div[2]/button")).click();

    this.driver.get(this.baseUrl + "/user/profile");
    WebElement changeResult = new WebDriverWait(this.driver, Duration.ofSeconds(10))
      .until(ExpectedConditions.elementToBeClickable(By.id("name")));

    assertEquals("New Name", changeResult.getAttribute("value"));
  }

  @After
  public void tearDown() {
    this.driver.quit();
  }


}
