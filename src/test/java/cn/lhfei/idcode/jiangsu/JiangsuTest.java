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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import cn.lhfei.idcode.constant.IdCode;
import cn.lhfei.idcode.constant.IdCodeResult;
import cn.lhfei.idcode.constant.JiangsuScript;
import cn.lhfei.idcode.constant.Recognition;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 24, 2021
 */

public class JiangsuTest {
  private static final Logger LOG = LoggerFactory.getLogger(JiangsuTest.class);
  
  private static final Gson GSON = new Gson();

  private static volatile String REG_RESULT = null;
  private static volatile IdCodeResult IDCODE_RESULT = new IdCodeResult();

  private static final String ORC_URL = "http://116.196.89.155/api/v1/idcodes/";
  
  public static void main(String[] args) throws InterruptedException {
    String userName = "91320105MA1XQ8KN8G";
    String password = "Xiaomi123456";
    
    String nav = "https://etax.jiangsu.chinatax.gov.cn/sso/login";
    
    WebDriver driver = launch(nav);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    injectCrawler(js);
    
    // auto fill username and password
    js.executeScript("window.preLogin(arguments[0], arguments[1])", userName, password);
    
    js.executeScript(JiangsuScript.FUN_initNECaptcha);
    
    robotLogin(js, driver);
  }
  
  private static void robotLogin(JavascriptExecutor js, WebDriver driver)
      throws InterruptedException {
    recognise(js, ORC_URL);

    String nevalidate = verify(js);
    if (nevalidate == null) {
      robotLogin(js, driver);
    } else {
      Select select2 = new Select(driver.findElement(By.cssSelector("#sflx_sel")));
      select2.selectByValue("30");

      Thread.sleep(200);
      Select select0 = new Select(driver.findElement(By.cssSelector("#ry_sel")));
      Thread.sleep(200);
      select0.selectByValue("杨要芳");
    }
  }
  
  
  private static void injectCrawler(JavascriptExecutor js) {
    try {
      // inject crawler script
      js.executeScript(JiangsuScript.FUN_preLogin);
      js.executeScript(JiangsuScript.FUN_addCursor);
      js.executeScript(JiangsuScript.FUN_move);
      js.executeScript(JiangsuScript.FUN_idcodeRecog);
      js.executeScript(JiangsuScript.FUN_getIdCode);
//    js.executeScript(JiangsuScript.FUN_sleep);
      js.executeScript(JiangsuScript.FUN_imitateClick);
    } catch (Exception e) {
      try {
        Thread.sleep(500);
        injectCrawler(js);
      } catch (InterruptedException e1) {
        LOG.info("{}", e.getMessage(), e);
      }
    }
  }
  
  private static WebDriver launch(String url) {
    // System Property for Chrome Driver
    System.setProperty("webdriver.chrome.driver",
        "D:\\DevProfile\\ChromeDriver\\chromedriver_91.exe");

    /*
     * System.setProperty("webdriver.chrome.driver",
     * "C:\\Users\\Administrator\\Desktop\\Drivers\\chromedriver(93.0.4577.15).exe");
     */
    
    // Instantiate a ChromeDriver class.
    WebDriver driver = new ChromeDriver();

    try {
      // Launch Website
      driver.navigate().to(url);

      // Maximize the browser
      driver.manage().window().maximize();
    } catch (Exception e) {
      LOG.info("{}", e.getMessage(), e);
      
      driver.close();
      
      try {
        Thread.sleep(500);
        launch(url);
      } catch (InterruptedException e1) {
        LOG.info("{}", e.getMessage(), e);
      }
    }
    
    return driver;
  }
  
  private static void recognise(JavascriptExecutor js, String url) throws InterruptedException {
    @SuppressWarnings("unchecked")
    ArrayList<String> codeString = (ArrayList<String>) js.executeScript("return window.getIdCode()");
    LOG.info("ID Code: {}", codeString);
   
    // checkt argumets is vaild: [0]: imgUrl, [1]: content
    synchronized(JiangsuTest.class) {
      if(codeString == null || codeString.get(0) == null || codeString.get(1) == null) {
        Thread.sleep(500);
        recognise(js, url);
      }
      
      REG_RESULT = sendPOST(url, "", codeString.get(0), codeString.get(1));
      LOG.info("========{}", REG_RESULT);
      formatResult(REG_RESULT);
      if (!IDCODE_RESULT.isResult()) {
        js.executeScript("neinstance.refresh()");
        Thread.sleep(1000);
        recognise(js, url);
      }
    }

  }
  
  public static synchronized String verify(JavascriptExecutor js) {
    String nevalidate = null;
    if (IDCODE_RESULT.isResult()) {
      IDCODE_RESULT.getRecognition().forEach(new Consumer<Recognition>() {
        @Override
        public void accept(Recognition recog) {
          LOG.info("Content: {}, window.imitateClick('div.yidun_bgimg', {}, {})",
              recog.getContent(), recog.getCoordinate()[0], recog.getCoordinate()[1]);
          
          js.executeScript("window.move(arguments[0], arguments[1])", recog.getCoordinate()[0] - 5, recog.getCoordinate()[1] - 5);
          
          js.executeScript("window.imitateClick(arguments[0], arguments[1], arguments[2])",
              "div.yidun_bgimg", recog.getCoordinate()[0] - 5, recog.getCoordinate()[1] - 5);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            LOG.info("{}", e.getMessage(), e);
          }
        }

      });

      nevalidate = (String) js.executeScript("return window.nevalidate");
      LOG.info("Verify Code: {}", nevalidate);
    }
    return nevalidate;
  }
  
  private static synchronized String sendPOST(String url, String base64, String imgUrl,
      String content) {

    String result = "";
    HttpPost post = new HttpPost(url);

    IdCode idCode = new IdCode(base64, imgUrl, content);

    String postData = GSON.toJson(idCode);

    LOG.info("Post Data: {}", postData);

    // send a JSON data

    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;

    LOG.info("Post Data: {}", postData);
    try {
      httpClient = HttpClients.createDefault();
      post.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

      response = httpClient.execute(post);
      result = EntityUtils.toString(response.getEntity());

    } catch (UnsupportedEncodingException e) {
      LOG.info("{}", e.getMessage(), e);
    } catch (ClientProtocolException e) {
      LOG.info("{}", e.getMessage(), e);
    } catch (ParseException e) {
      LOG.info("{}", e.getMessage(), e);
    } catch (IOException e) {
      LOG.info("{}", e.getMessage(), e);
    } finally {
      if (response != null)
        try {
          response.close();
        } catch (IOException e) {
          LOG.info("{}", e.getMessage(), e);
        }
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          LOG.info("{}", e.getMessage(), e);
        }
      }
    }

    return result;
  }
  
  private static void formatResult(String jsonResult) {
    try {
      IDCODE_RESULT = GSON.fromJson(jsonResult, IdCodeResult.class);
    }catch(Exception e) {
      IDCODE_RESULT.setResult(false);
    }
  }

}
