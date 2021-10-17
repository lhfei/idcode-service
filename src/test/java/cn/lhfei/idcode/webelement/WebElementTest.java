/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.lhfei.idcode.webelement;

import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 09, 2021
 */

public class WebElementTest {
private static final Logger LOG = LoggerFactory.getLogger(WebElementTest.class);

  public static void main(String[] args) {
    String nav = "http://localhost:12000/index.html";
    
    WebDriver driver = launch(nav);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    js.executeScript("$('#left').val(90)");
    js.executeScript("$('#top').val(45)");
    
    int left = Integer.parseInt(driver.findElement(By.xpath("//body/input[@id='left']")).getAttribute("value"));
    int top = Integer.parseInt(driver.findElement(By.xpath("//body/input[@id='top']")).getAttribute("value"));
    
    String style = driver.findElement(By.xpath("//img[@class='yidun_bg-img']")).getAttribute("style");
    LOG.info("Style: {}", style);
    
    driver.findElement(By.id("btn")).click();
    
    final AtomicReference<WebElement> webElementRef = new AtomicReference<>();
    
    webElementRef.set(driver.findElement(By.xpath("//img[@class='yidun_bg-img']")));
    
    Actions actions = new Actions(driver);    
    actions.moveToElement(webElementRef.get()).moveByOffset(left, top).click().build().perform();


    actions.moveByOffset(-20, 25).click().build().perform();
  }
  
  
  private static WebDriver launch(String url) {
    // System Property for Chrome Driver
    System.setProperty("webdriver.chrome.driver",
        "D:\\DevProfile\\ChromeDriver\\chromedriver_93.exe");

    // Instantiate a ChromeDriver class.
    WebDriver driver = new ChromeDriver();

    // Launch Website
    driver.navigate().to(url);

    // Maximize the browser
    driver.manage().window().maximize();


    return driver;
  }

}
