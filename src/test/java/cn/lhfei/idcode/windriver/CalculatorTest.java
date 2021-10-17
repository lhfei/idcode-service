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

import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import io.appium.java_client.windows.WindowsDriver;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Sep 13, 2021
 */

public class CalculatorTest {
  private static WindowsDriver CalculatorSession = null;
  private static WebElement CalculatorResult = null;

  @BeforeClass
  public static void setup() {
    try {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
      CalculatorSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
      CalculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

      CalculatorResult = CalculatorSession.findElementByAccessibilityId("CalculatorResults");
      Assert.assertNotNull(CalculatorResult);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }

  @Before
  public void Clear() {
    CalculatorSession.findElementByName("Clear").click();
    Assert.assertEquals("0", _GetCalculatorResultText());
  }

  @AfterClass
  public static void TearDown() {
    CalculatorResult = null;
    if (CalculatorSession != null) {
      CalculatorSession.quit();
    }
    CalculatorSession = null;
  }

  @Test
  public void Addition() {
    CalculatorSession.findElementByName("One").click();
    CalculatorSession.findElementByName("Plus").click();
    CalculatorSession.findElementByName("Seven").click();
    CalculatorSession.findElementByName("Equals").click();
    Assert.assertEquals("8", _GetCalculatorResultText());
  }

  @Test
  public void Combination() {
    CalculatorSession.findElementByName("Seven").click();
    CalculatorSession.findElementByName("Multiply by").click();
    CalculatorSession.findElementByName("Nine").click();
    CalculatorSession.findElementByName("Plus").click();
    CalculatorSession.findElementByName("One").click();
    CalculatorSession.findElementByName("Equals").click();
    CalculatorSession.findElementByName("Divide by").click();
    CalculatorSession.findElementByName("Eight").click();
    CalculatorSession.findElementByName("Equals").click();
    Assert.assertEquals("8", _GetCalculatorResultText());
  }

  @Test
  public void Division() {
    CalculatorSession.findElementByName("Eight").click();
    CalculatorSession.findElementByName("Eight").click();
    CalculatorSession.findElementByName("Divide by").click();
    CalculatorSession.findElementByName("One").click();
    CalculatorSession.findElementByName("One").click();
    CalculatorSession.findElementByName("Equals").click();
    Assert.assertEquals("8", _GetCalculatorResultText());
  }

  @Test
  public void Multiplication() {
    CalculatorSession.findElementByName("Nine").click();
    CalculatorSession.findElementByName("Multiply by").click();
    CalculatorSession.findElementByName("Nine").click();
    CalculatorSession.findElementByName("Equals").click();
    Assert.assertEquals("81", _GetCalculatorResultText());
  }

  @Test
  public void Subtraction() {
    CalculatorSession.findElementByName("Nine").click();
    CalculatorSession.findElementByName("Minus").click();
    CalculatorSession.findElementByName("One").click();
    CalculatorSession.findElementByName("Equals").click();
    Assert.assertEquals("8", _GetCalculatorResultText());
  }

  protected String _GetCalculatorResultText() {
    // trim extra text and whitespace off of the display value
    return CalculatorResult.getText().replace("Display is", "").trim();
  }
}
