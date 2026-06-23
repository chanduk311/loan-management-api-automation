package com.banking.api.automation.mockbase;

import com.banking.api.automation.mockApi.WireMockManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setupSuite() {

        WireMockManager.startServer();
    }

    @AfterSuite
    public void tearDownSuite() {

        WireMockManager.stopServer();
    }
}
