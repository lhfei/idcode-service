#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Author: wangkang
# @Time: 2021-04-13 15:55

import time

import win32api  # pip install pywin32 可以包含win32api和win32con
import win32con
from appium import webdriver
from selenium.webdriver import ActionChains


def find_element_by_name_exist(driver, name):
    '''
    查找元素，失败返回None，成功返回元素对象
    :param driver:
    :param name:
    :return:
    '''
    try:
        element = driver.find_element_by_name(name)
    except Exception as e:
        return None
    return element


class WeiXin(object):
    def __init__(self, weixin_path=r"E:\Programs\WeChat\WeChat.exe", already_login=False, login_time_out=10):
        '''
        打开微信
        :param weixin_path: Windows微信的安装地址
        :param already_login: 微信是否登录，如果微信已经登录，则为True，否则为False，当为False时会先打开微信，再登录微信
        :param login_time_out: 登录超时实践
        '''
        self.login_time_out = login_time_out
        self.weixin_path = weixin_path
        # 获取整个桌面的driver，然后利用桌面的driver获取微信的driver，因为微信具有启动界面，无法直接获取微信的dirver
        desired_caps = {"platformName": "Windows", "deviceName": "WindowsPC", "app": "Root"}
        self.driver = webdriver.Remote(command_executor='http://127.0.0.1:4723', desired_capabilities=desired_caps)
        self.weixin_driver = None
        if self.open_weixin(already_login):
            if not already_login:
                self.login()
            print("已经进入主界面！")

    def open_weixin(self, already_login):
        '''
        打开微信
        :param already_login: 如果已经登录则表示已经打开，此时无需再打开微信
        :return:
        '''
        while True:
            if not already_login:
                # 没有登录微信，则先打开微信，打开微信无法直接获取微信的driver，此时self.weixin_driver为False
                self.weixin_driver = self.click_weixin(self.weixin_path)
            if not self.weixin_driver:
                # 当打开微信之后，通过获取微信句柄的方式来获取微信的driver
                weixin = find_element_by_name_exist(self.driver, "微信")
                weixin_handle = weixin.get_attribute("NativeWindowHandle")
                if weixin_handle != 0:
                    weixin_caps = {"appTopLevelWindow": str(hex(int(weixin_handle)))}
                    self.weixin_driver = webdriver.Remote(command_executor='http://127.0.0.1:4723',
                                                          desired_capabilities=weixin_caps)
            if self.weixin_driver:
                break
        if self.weixin_driver:
            return True
        return False

    def login(self):
        '''
        登录微信，微信登录前后的窗口的句柄会发生改变，此时需要通过微信的类名获取新的句柄，来对微信进行重新绑定（获取微信driver）
        :return:
        '''
        self.weixin_driver.find_element_by_name("登录").click()
        login_time = 0
        check_time = 0.5
        while True:
            time.sleep(check_time)
            weixin = find_element_by_name_exist(self.driver, "微信")
            if weixin:
                class_name = weixin.get_attribute("ClassName")
                if class_name == "WeChatMainWndForPC":
                    weixin_handle = weixin.get_attribute("NativeWindowHandle")
                    if weixin_handle != 0:
                        print("登录后句柄：", weixin_handle)
                        weixin_caps = {"appTopLevelWindow": str(hex(int(weixin_handle)))}
                        self.weixin_driver = webdriver.Remote(command_executor='http://127.0.0.1:4723',
                                                              desired_capabilities=weixin_caps)
                    print("登录成功！")
                    break
            login_time += check_time
            if login_time > self.login_time_out:
                print("登录超时！")
                return False
        time.sleep(0.5)
        return True

    def click_weixin(self, weixin_path):
        '''
        相当于点击微信桌面图标，即打开微信
        :param weixin_path:
        :return:
        '''
        weixin_caps = {"platformName": "Windows", "deviceName": "WindowsPC", "app": weixin_path}
        try:
            weixin_driver = webdriver.Remote(command_executor='http://127.0.0.1:4723', desired_capabilities=weixin_caps)
        except Exception as e:
            return False
        return weixin_driver

    def send_message(self, user, msg=""):
        '''
        初级版本：
        给微信好友发送消息，此函数还需要完善，目前当好友位于历史消息列表中，且能够看到的时候才会成功
        :param user:
        :param msg:
        :return:
        '''
        user = self.weixin_driver.find_element_by_name(user)
        print(user)
        print(self.weixin_driver.find_element_by_name("会话").get_attribute("Next"))
        user.click()
        self.weixin_driver.find_element_by_name("输入").send_keys(msg)
        self.weixin_driver.find_element_by_name("发送(S)").click()

    def get_all_user(self):
        '''
        初级版本：
        获取所有的好友，需要先用鼠标点击通讯录按钮，然后这个接口会模拟鼠标滚动的方式滑动获取所有的好友列表。
        :return:
        '''
        user_list = []
        pre_user_list = []
        ActionChains(self.weixin_driver).move_to_element(self.weixin_driver.find_element_by_name("联系人")).perform()
        while True:
            cur_user_list = []
            list_elements = self.weixin_driver.find_elements_by_tag_name("ListItem")
            for element in list_elements:
                cur_user_list.append(element.text)
                user_list.extend(cur_user_list)
            win32api.mouse_event(win32con.MOUSEEVENTF_WHEEL, 0, 0, -600)
            if pre_user_list == cur_user_list:
                break
            pre_user_list = cur_user_list
            print(pre_user_list)
        user_list = list(set(user_list))
        print(user_list)
        print(len(user_list))


if __name__ == '__main__':
    wx = WeiXin(already_login=True)
    # wx = WeiXin()
    # wx.send_message("文件传输助手", "你好呀")
    wx.get_all_user()