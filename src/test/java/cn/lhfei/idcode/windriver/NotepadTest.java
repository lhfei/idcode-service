/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.lhfei.idcode.windriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 14, 2021
 */
public class NotepadTest {

  private static WindowsDriver driver = null;
  private static Logger LOG = LoggerFactory.getLogger(AbstractDriver.class);

  @BeforeAll
  public static void setup() {
      try {
        DesiredCapabilities appCapabilities = new DesiredCapabilities();
        appCapabilities.setCapability("app", "Root");
        // set the path of the application exe file location
        appCapabilities.setCapability("app",
            "D:\\Program Files (x86)\\Notepad++\\notepad++.exe");
        driver = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), appCapabilities);
        
      } catch (MalformedURLException e) {
        LOG.error(e.getMessage(), e);
      }

  }
  
  @Test
  public void checkHelp() {
    driver.findElementByName("?").click();
    driver.findElement(By.name("About Notepad++")).click();
    driver.findElementByName("OK").click();
  }
  
  
  @Test
  public void newTab() throws InterruptedException {
    LOG.info("===============");
    
    // add new tab
    driver.findElementByName("File").click();
//    driver.findElement(By.name("New")).click();

    List<WebElement> list = driver.findElementsByClassName("Scintilla");
    
    list.get(0).sendKeys("Hello World!");
    
    LOG.info("{}", list.size());
    
    LOG.info("===============");
    
    // save file
    
    driver.findElementByName("File").click();
    
    // click 'Save As' button
    driver.findElementByAccessibilityId("41008").click();
//    driver.findElement(By.name("Save As...")).click();
    
    //
//    driver.findElementByName("桌面").click();
    
    // focus 'File name' input
    driver.findElementByName("File name:").sendKeys("test1.txt");
    
    driver.findElement(By.name("Save")).click();
    
  }

}
