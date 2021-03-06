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

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

public class TencentMeetingDriver {
  private static WindowsDriver driver = null;
  private static Logger LOG = LoggerFactory.getLogger(AbstractDriver.class);

  @BeforeAll
  public static void setup() {
      try {
        DesiredCapabilities appCapabilities = new DesiredCapabilities();
        appCapabilities.setCapability("app", "Root");
        // set the path of the application exe file location
        appCapabilities.setCapability("app",
            "D:\\Program Files (x86)\\Tencent\\WeMeet\\wemeetapp.exe");
        driver = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), appCapabilities);
        
      } catch (MalformedURLException e) {
        LOG.error(e.getMessage(), e);
      }

  }
  
  @Test
  public void login() {
    LOG.info("==");
    driver.findElementByXPath("//Tencent Meeting(LoadingWnd)");
    LOG.info("==");
    
  }
}
