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

package cn.lhfei.idcode.windriver;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.windows.WindowsDriver;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 13, 2021
 */

public class MyAppTest {

  private static WindowsDriver WEB_DRIVER = null;
  private static WebElement WEB_PAGE = null;

  public static void setup() {
    try {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      
      // set the path of the application exe file location
      capabilities.setCapability("app", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
      
      WEB_DRIVER = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
      
      WEB_PAGE = WEB_DRIVER.findElement(By.name("New Tab - Google Chrome"));

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }
  
  public static void main(String[] args) {
    setup();
    
//    WEB_PAGE.sendKeys("https://etax.jiangsu.chinatax.gov.cn/sso/login", Keys.RETURN, Keys.RETURN);
    
//    WEB_PAGE.findElement(By.xpath("//span[@class='xubox_setwin']/a")).click();
    
//    WEB_DRIVER.findElementByXPath("//span[@class='xubox_setwin']/a");
    WEB_DRIVER.findElement(By.name("Customize and control Google Chrome")).click();
//    WEB_DRIVER.findElement(By.name("Help")).click();
//    
//    WEB_DRIVER.findElement(By.name("About Google Chrome")).click();
    
  }

}
