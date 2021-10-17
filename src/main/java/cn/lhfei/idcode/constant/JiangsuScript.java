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

package cn.lhfei.idcode.constant;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 24, 2021
 */

public class JiangsuScript {
  public static final String FUN_initNECaptcha = ""
      +" window.initNECaptcha({                                            "
      +"     captchaId: '1a623022803d4cbc86fa157ec267bb36',                "
      +"     element: '#captcha_div',                                      "
      +"     mode: 'embed',                                                "
      +"     width: '310px',                                               "
      +"     onVerify: function(err, ret) {                                "
      +"         if (!err) {                                               "
      +"             nevalidate = ret['validate'];                         "
      +"         }                                                         "
      +"     }                                                             "
      +" }, function(instance) {                                           "
      +"     neinstance = instance;                                        "
      +" }, function(err) {                                                "
      +"     layerHandler.alert('验证码服务加载失败，请刷新页面后重试。');             "
      +" })                                                                ";

  public static final String FUN_preLogin = ""
      +" window.preLogin = function(username, password) {"
      +"     /*close message modal*/                     "
      +"     $('.xubox_setwin>a').click();               "
      +"                                                 "
      +"     /*show login modal*/                        "
      +"     $('.go_login>img').click();                 "
      +"                                                 "
      +"     $('#username_msg').val(username);           "
      +"     $('#password_msg').val(password);           "
      +" }                                               ";
  
  public static final String FUN_idcodeRecog = ""
      +" window.idcodeRecog = async function(codeString, username, password) {      "
      +"     var codeObj = $.parseJSON(codeString);                           "
      +"                                                                      "
      +"     if (codeObj.result) {                                            "
      +"         var target = $('div.yidun_bgimg');                           "
      +"         var recognition = codeObj.recognition;                       "
      +"         for (var i = 0; i < 3; i++) {                                "
      +"             var coordinate = recognition[i].coordinate;              "
      +"             imitateClick($(target)[0], coordinate[0], coordinate[1]);"
      +"             await sleep(800)                                             ;"
      +"         }                                                            "
      +"     } else {                                                         "
      +"         alert('识别错误')                                               "
      +"     }                                                                "
      +" }                                                                    ";
  
  
  public static final String FUN_getIdCode = ""
      +" window.getIdCode = function() {                           "
      +"     return [                                              "
      +"         $($('img.yidun_bg-img')[0]).attr('src'),  "
      +"         $($('span.yidun_tips__point')[0]).text() "
      +"     ]                                                     "
      +" }                                                         ";
  
  public static final String FUN_sleep = ""
      +" window.sleep = function(ms) {                           "
      +"   return new Promise(resolve => setTimeout(resolve, ms))"
      +" }                                                       ";
  
  public static final String FUN_imitateClick = ""
      +" window.imitateClick = function(ele, oX, oY) {         "
      +"     var oEvent, oEle;                                       "
      +"     oEle = $(ele)[0];                                       "
      +"     window.move(oX, oY);                                    "
      +"     if (document.createEventObject) {                       "
      +"         /*For IE*/                                          "
      +"         oEvent = document.createEventObject();              "
      +"         oEvent.clientX = oX;                                "
      +"         oEvent.clientY = oY;                                "
      +"         oEle.fireEvent('onclick', oEvent);                  "
      +"     } else {                                                "
      +"         var imgClient = $(oEle)[0].getBoundingClientRect(); "
      +"         var imgX = imgClient.left;                          "
      +"         var imgY = imgClient.top;                           "
      +"         oEvent = document.createEvent('MouseEvents');       "
      +"         oEvent.initMouseEvent(                              "
      +"             'click',                                        "
      +"             true,                                           "
      +"             true,                                           "
      +"             document.defaultView,                           "
      +"             0,                                              "
      +"             0,                                              "
      +"             0,                                              "
      +"             parseInt(oX) + imgX,                            "
      +"             parseInt(oY) + imgY                             "
      +"         );                                                  "
      +"         oEle.dispatchEvent(oEvent);                         "
      +"     }                                                       "
      +"                                                             "
      +" }                                                           ";
  
  public static final String FUN_addCursor = ""
      +" window.addCursor = function() { "
      +"     $('#pointer').remove();     "
      +"     var pointerHtml = '<img id=\"pointer\" src=\"http://cdn1.iconfinder.com/data/icons/CrystalClear/22x22/actions/14_select.png\" />';"
      +"     var target = $('div.yidun_bgimg'); "
      +"     $(target).append(pointerHtml);     "
      +" }                                      ";
  
  public static final String FUN_move = ""
      +" window.move = function(left, top) { "
      +"     var fakeMouse = $('#pointer');  "
      +"     fakeMouse.animate({             "
      +"         top: top,                   "
      +"         left: left                  "
      +"     }, 1200, 'swing', function() {  "
      +"                                     "
      +"     });                             "
      +" }                                   ";
  
  
  public static final String FUN_createMouseMoveEvent = ""
      +" window.createMouseMoveEvent = function(left, top) {   "
      +"     var oEvent = document.createEvent('MouseEvents'); "
      +"     oEvent.initMouseEvent(                            "
      +"         'mousemove',                                  "
      +"         true,                                         "
      +"         false,                                        "
      +"         null,                                         "
      +"         0,                                            "
      +"         0,                                            "
      +"         0,                                            "
      +"         left,                                         "
      +"         top,                                          "
      +"         false,                                        "
      +"         false,                                        "
      +"         false,                                        "
      +"         false,                                        "
      +"         0,                                            "
      +"         null                                          "
      +"     );                                                "
      +"                                                       "
      +"     return oEvent;                                    "
      +" }                                                     ";
  
  public static final String FUN_mouseHanlder = ""
      +" window.mouseHanlder = function(left, top) {                      "
      +"     var _x    = 0,                                               "
      +"     _y        = 0,                                               "
      +"     _imgIdx   = 0;                                               "
      +"                                                                  "
      +"     var steps = 0,                                               "
      +"     index     = 0,                                               "
      +"     _left     = 0,                                               "
      +"     _right    = 0,                                               "
      +"     incr      = 5;                                               "
      +"                                                                  "
      +"     _x = parseInt(left / 80);                                    "
      +"     _y = parseInt(top / 80);                                     "
      +"     _imgIdx = _x + (_y * 4);                                     "
      +"                                                                  "
      +"     var containerNode = $('div.yidun_inference--' + _imgIdx)[0]; "
      +"                                                                  "
      +"                                                                  "
      +"     incr = (left > 149) ? 20 : 15;                               "
      +"     steps = parseInt(left / incr);                               "
      +"                                                                  "
      +"     var timeHanler = setInterval(() => {                         "
      +"         if (index > steps) {                                     "
      +"             clearInterval(timeHanler);                           "
      +"             var target = $('div.yidun_bgimg');                   "
      +"             imitateClick($(target)[0], left, top);               "
      +"             return;                                              "
      +"         }                                                        "
      +"         if (index == steps) {                                    "
      +"             _left = left;                                        "
      +"             _top = top;                                          "
      +"         } else {                                                 "
      +"             _left = incr * index;                                "
      +"             _top = parseInt(top / steps) * index;                "
      +"         }                                                        "
      +"         console.log('_left: {}, _top: {}', _left, _top);         "
      +"         containerNode.dispatchEvent(                             "
      +"             createMouseMoveEvent(_left, _top)                    "
      +"         );                                                       "
      +"         index++;                                                 "
      +"     }, 1);                                                     "
      +" }                                                                ";
}

