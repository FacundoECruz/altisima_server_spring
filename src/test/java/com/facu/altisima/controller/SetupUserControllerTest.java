package com.facu.altisima.controller;

import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;

public class SetupUserControllerTest {
    public String urlTemplate;
    public String userJson;
    public String expectedErrMsg;
    public String loginRequestJson;

    public SetupUserControllerTest(String urlTemplate, String userJson, String expectedErrMsg, String loginRequestJson) {
        this.urlTemplate = urlTemplate;
        this.userJson = userJson;
        this.expectedErrMsg = expectedErrMsg;
        this.loginRequestJson = loginRequestJson;
    }

    public SetupUserControllerTest() {

    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public String getUserJson() {
        return userJson;
    }

    public void setUserJson(String userJson) {
        this.userJson = userJson;
    }

    public String getExpectedErrMsg() {
        return expectedErrMsg;
    }

    public void setExpectedErrMsg(String expectedErrMsg) {
        this.expectedErrMsg = expectedErrMsg;
    }

    public String getLoginRequestJson() {
        return loginRequestJson;
    }

    public void setLoginRequestJson(String loginRequestJson) {
        this.loginRequestJson = loginRequestJson;
    }
}

