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

package cn.lhfei.idcode.constant;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 31, 2021
 */
public class Recognition {

  public double[] getCrop() {
    return crop;
  }

  public void setCrop(double[] crop) {
    this.crop = crop;
  }

  public double[] getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(double[] coordinate) {
    this.coordinate = coordinate;
  }

  public String getClasses() {
    return classes;
  }

  public void setClasses(String classes) {
    this.classes = classes;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private double[] crop;
  private double[] coordinate;
  private String classes;
  private String content;

}
