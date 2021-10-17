package com.nanshufang.win.client.demo.testcase;

import org.assertj.core.api.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

/**
 * @Author WangGang6@100tal.com
 * @create 2019/9/12 22:49
 */

import com.nanshufang.win.client.demo.common.driver.Driver;
import com.nanshufang.win.client.demo.common.utils.ProcessUtils;
import com.nanshufang.win.client.demo.func.NotepadMainFunc;

public class ChangeFontTest {

    @BeforeClass
    public void beforeClass() {
        ProcessUtils.killAllNotepadProcess();
        Driver.initDriver();
    }

    @Test()
    public void loginTest()  {
        NotepadMainFunc notepadMainFunc=new NotepadMainFunc();
        notepadMainFunc.inputContentToEditArea("这是测试文本，This is some test Text!");
        notepadMainFunc.clickFormatMenu();
        notepadMainFunc.clickFontMenu();
        notepadMainFunc.inputFontSize(30);
        notepadMainFunc.clickConfirmButton();
    }

}
