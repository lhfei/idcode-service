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

import cn.lhfei.idcode.constant.IdCode;
import cn.lhfei.idcode.constant.IdCodeResult;
import cn.lhfei.idcode.constant.JiangsuScript;
import cn.lhfei.idcode.constant.Recognition;
import com.google.gson.Gson;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 24, 2021
 */

public class JiangsuRobot2 {
  private static final Logger LOG = LoggerFactory.getLogger(JiangsuRobot2.class);
  
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
    //String nevalidate = verify(js);
    String nevalidate = "CN31__ysCizy0d090Yp5iZDD9dxG0qb8v52GEYfjiKPOUaoyjbNB1kfC.77dYNCkO.buFCPhjja7g6YXN8UVb5yV1v1-0Qv.siCiFifAlB8ir4TcdBaNgFPIAL_PfB5xxALp1OSTNCSv7WENELYBV6bfuuftV4TlyqIxDkvjfccdJ8JMUhCyX2EpSQZqG_JDlS_MXfbJ5Ze24DyKZildC2IFdX4XDRigiwOIC6uLW7EpYyyplQK.E2eEtvi45lha8iIqJB0SihQeg6I6dLWBvsvcpC6nM-_e2TV5FLUuYvk-UgBP11XE5G8.-RkK0afJX5jl4s9s4KzexWfy1UEcsWnjG1z.r2zEQBiN-7p9FeXBbd7.Lid89j5kVb-aCOZONYnbisxT4gmkCLIcJBPxG-kooL1kXg4H.D4X.9eG4jo6NwWU_YUwrAWOErmgxuydr9WFHFG-4ydulx0hn8V9NuCkb0DxkGBT9YJeKUKAsRu0A2_XlSABeJqFHIjwvRcN3";
    js.executeScript("$('#captcha_div').append('<div class=\"yidun yidun--light yidun--embed yidun--point yidun--success\" style=\"width: 320px; min-width: 220px\">   <div class=\"yidun_panel\" style=\"padding-bottom: 15px\"> <div class=\"yidun_panel-placeholder\"> <div class=\"yidun_bgimg\" style=\"border-radius: 2px\"> <img class=\"yidun_bg-img\" alt=\"验证码背景\" style=\"border-radius: 2px\" src=\"http://necaptcha.nosdn.127.net/e6bce573de8345859298613c3db4c763.jpg\">         <img class=\"yidun_jigsaw\" alt=\"验证码滑块\"> <div class=\"yidun_inference yidun_inference--0\" draggable=\"true\">             <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span> </div> <div class=\"yidun_inference yidun_inference--1\" draggable=\"true\">             <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span> </div> <div class=\"yidun_inference yidun_inference--2\" draggable=\"true\">             <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span>           </div>                                          <div class=\"yidun_inference yidun_inference--3\" draggable=\"true\"> <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span>           </div>                                          <div class=\"yidun_inference yidun_inference--4\" draggable=\"true\"> <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span>           </div>                                          <div class=\"yidun_inference yidun_inference--5\" draggable=\"true\"> <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span>           </div>                                          <div class=\"yidun_inference yidun_inference--6\" draggable=\"true\"> <img class=\"yidun_inference__img\" draggable=\"false\"> <span class=\"yidun_inference__border\"></span>           </div>                                          <div class=\"yidun_inference yidun_inference--7\" draggable=\"true\"> <img class=\"yidun_inference__img\" draggable=\"false\">             <span class=\"yidun_inference__border\"></span> </div> <div class=\"yidun_voice\"> <div class=\"yidun_voice__inner\"> <audio class=\"yidun_voice__source\" src=\"\"></audio>             <button type=\"button\" class=\"yidun_voice__play\"><span class=\"yidun_voice__txt\">播放语音验证码</span></button> <input class=\"yidun_voice__input\" aria-label=\"输入听到的验证码\" placeholder=\"输入听到的验证码\" maxlength=\"10\">             <div class=\"yidun_voice_btns\">               <button type=\"button\" class=\"yidun_voice__refresh\"><span class=\"yidun_voice__txt\">刷新验证码</span></button>               <button type=\"button\" class=\"yidun_voice__back\"><span class=\"yidun_voice__txt\">返回</span></button>             </div>           </div>         </div>       <div class=\"yidun_icon-point yidun_point-1\" style=\"left: 167.5px; top: 85px;\"></div><div class=\"yidun_icon-point yidun_point-2\" style=\"left: 9.5px; top: 78px;\"></div><div class=\"yidun_icon-point yidun_point-3\" style=\"left: 105.5px; top: 99px;\"></div></div> <div class=\"yidun_loadbox\" style=\"border-radius: 2px\"> <div class=\"yidun_loadbox__inner\">           <div class=\"yidun_loadicon\"></div>           <span class=\"yidun_loadtext\">加载中...</span>         </div>       </div>       <div class=\"yidun_top\">                  <a class=\"yidun_feedback\" href=\"http://support.dun.163.com/feedback/captcha?captchaId=1a623022803d4cbc86fa157ec267bb36&amp;token=3f63b404b3cf4073923087cf8995ba65\" target=\"_blank\" tabindex=\"0\"><span class=\"yidun_feedback_txt\">提交使用问题反馈</span></a>                  <div class=\"yidun_top__right\"> <button type=\"button\" class=\"yidun_refresh\">刷新验证码</button> </div>  </div> </div>   </div>   <div class=\"yidun_control\" style=\"height: 40px; border-radius: 2px\" tabindex=\"0\" role=\"button\">     <div class=\"yidun_slide_indicator\" style=\"height: 40px; border-radius: 2px\"></div>     <div class=\"yidun_slider\" style=\"width: 40px; border-radius: 2px\"> <span class=\"yidun_slider__icon\"></span> </div> <div class=\"yidun_tips\" style=\"line-height: 40px\" aria-live=\"polite\">  <span class=\"yidun_tips__icon\"></span>  <span class=\"yidun_tips__text yidun-fallback__tip\">验证成功</span>       <div class=\"yidun_tips__answer hide\">         <span class=\"yidun_tips__point\"> \"栏\" \"鞋\" \"朱\"</span> <img class=\"yidun_tips__img\">       </div>     </div>   </div> </div> <input type=\"hidden\" name=\"NECaptchaValidate\" value=\""+nevalidate+"\" class=\"yidun_input\">')");
    js.executeScript("nevalidate='"+nevalidate+"'");
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
    // inject crawler script
    js.executeScript(JiangsuScript.FUN_preLogin);
    js.executeScript(JiangsuScript.FUN_addCursor);
    js.executeScript(JiangsuScript.FUN_move);
    js.executeScript(JiangsuScript.FUN_idcodeRecog);
    js.executeScript(JiangsuScript.FUN_getIdCode);
//    js.executeScript(JiangsuScript.FUN_sleep);
    js.executeScript(JiangsuScript.FUN_createMouseMoveEvent);
    js.executeScript(JiangsuScript.FUN_mouseHanlder);
    js.executeScript(JiangsuScript.FUN_imitateClick);
  }
  
  private static WebDriver launch(String url) {
    // System Property for Chrome Driver
//    System.setProperty("webdriver.chrome.driver",
//        "D:\\DevProfile\\ChromeDriver\\chromedriver_91.exe");

    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Desktop\\Drivers\\chromedriver(93.0.4577.15).exe");

    // Instantiate a ChromeDriver class.
    WebDriver driver = new ChromeDriver();

    // Launch Website
    driver.navigate().to(url);

    // Maximize the browser
    driver.manage().window().maximize();
    
    
    return driver;
  }
  
  private static void recognise(JavascriptExecutor js, String url) throws InterruptedException {
    @SuppressWarnings("unchecked")
    ArrayList<String> codeString = (ArrayList<String>) js.executeScript("return window.getIdCode()");
    LOG.info("ID Code: {}", codeString);
   
    // checkt argumets is vaild: [0]: imgUrl, [1]: content
    synchronized (JiangsuRobot2.class) {
      if (codeString == null | codeString.get(0) == null || codeString.get(1) == null) {
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
          
          // js.executeScript("window.move(arguments[0], arguments[1])", recog.getCoordinate()[0] -
          // 5, recog.getCoordinate()[1] - 5);
          
          js.executeScript("window.mouseHanlder(arguments[0], arguments[1])",
              recog.getCoordinate()[0] - 5, recog.getCoordinate()[1] - 5);
          
          
//          js.executeScript("window.imitateClick(arguments[0], arguments[1], arguments[2])",
//              "div.yidun_bgimg", recog.getCoordinate()[0] - 5, recog.getCoordinate()[1] - 5);
          try {
            Thread.sleep(1 * 1000);
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
  
  private static void
  formatResult(String jsonResult) {
    try {
      IDCODE_RESULT = GSON.fromJson(jsonResult, IdCodeResult.class);
    } catch (Exception e) {
      IDCODE_RESULT.setResult(false);
    }
  }

}
