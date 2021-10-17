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

package cn.lhfei.idcode.jiangsu;

import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import cn.hutool.core.util.RandomUtil;
import cn.lhfei.idcode.constant.JiangsuScript;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 09, 2021
 */
public class TestSelenium {

  public static void main(String[] args) throws Exception {
    String userName = "91320105MA1XQ8KN8G";
    String password = "Xiaomi123456";
    
    String nav = "https://etax.jiangsu.chinatax.gov.cn/sso/login";
    
    WebDriver driver = launch(nav);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    injectCrawler(js);
    
    js.executeScript("$('div.yidun_panel').show()");
    js.executeScript("$('div.yidun_tips__answer').removeClass('hide');");
    
    // auto fill username and password
    js.executeScript("window.preLogin(arguments[0], arguments[1])", userName, password);
    
    
    final AtomicReference<WebElement> webElementRef = new AtomicReference<>();
    
    webElementRef.set(driver.findElement(By.xpath("//img[@class='yidun_bg-img']")));
    WebElement yzElement = webElementRef.get();
    
    Actions actions = new Actions(driver);
    actions.moveToElement(yzElement);
    int coordinatex = 216;
    int coordinatey = 82;

    int xyz_x = 190;
    int xyz_y = 187;
    int sb_x = xyz_x -coordinatex;
    int sb_y = xyz_y -coordinatey;
    coordinatex = xyz_x;
    coordinatey = xyz_y;
    
    actions.moveToElement(driver.findElement(By.xpath("//img[@class='yidun_bg-img']")), coordinatex, coordinatey).click();

  }
  
  
  private static WebDriver launch(String url) {
    // System Property for Chrome Driver
    System.setProperty("webdriver.chrome.driver",
        "D:\\DevProfile\\ChromeDriver\\chromedriver_91.exe");

    // Instantiate a ChromeDriver class.
    WebDriver driver = new ChromeDriver();

    // Launch Website
    driver.navigate().to(url);

    // Maximize the browser
    driver.manage().window().maximize();


    return driver;
  }
  
  private static void injectCrawler(JavascriptExecutor js) {
    // inject crawler script
    js.executeScript(JiangsuScript.FUN_preLogin);
    js.executeScript(JiangsuScript.FUN_addCursor);
    js.executeScript(JiangsuScript.FUN_move);
    js.executeScript(JiangsuScript.FUN_idcodeRecog);
    js.executeScript(JiangsuScript.FUN_getIdCode);
    // js.executeScript(JiangsuScript.FUN_sleep);
    js.executeScript(JiangsuScript.FUN_createMouseMoveEvent);
    js.executeScript(JiangsuScript.FUN_mouseHanlder);
    js.executeScript(JiangsuScript.FUN_imitateClick);
  }

  public static void algorithmVoid02(Actions actions, int left, int top) throws Exception {
    
    actions.moveToElement(null, left, top).click();
  }
}
