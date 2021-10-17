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
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.windows.WindowsDriver;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 14, 2021
 */
public abstract class AbstractDriver {

  private WindowsDriver driver = null;
  private static Logger LOG = LoggerFactory.getLogger(AbstractDriver.class);
  
  @BeforeClass
  public void setup() {
    try {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      
      // set the path of the application exe file location
      capabilities.setCapability("app", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
      
      driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }
}
