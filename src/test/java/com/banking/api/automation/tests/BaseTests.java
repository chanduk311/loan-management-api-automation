package com.banking.api.automation.tests;

import com.banking.api.automation.config.MockApiSetup;
import com.banking.api.automation.config.WireMockSetup;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class BaseTests {

    protected static WireMockServer wireMockServer;

    /**
     * Start WireMock before all tests
     */
    @BeforeSuite
    public void setupWireMock() {
        WireMockSetup.startWireMock();
        wireMockServer = WireMockSetup.getWireMockServer();
        MockApiSetup.setupAllMocks(wireMockServer);
        System.out.println("✓ Test setup complete - WireMock ready");
    }

    /**
     * Stop WireMock after all tests
     */
    @AfterSuite
    public void teardownWireMock() {
        WireMockSetup.stopWireMock();
        System.out.println("✓ Test teardown complete");
    }

}