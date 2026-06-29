package com.banking.api.automation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockSetup {

    private static WireMockServer wireMockServer;

    /**
     * Start WireMock server on port 8989
     */
    public static void startWireMock() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8989));
        wireMockServer.start();
        System.out.println("✓ WireMock server started on http://localhost:8989");
    }

    /**
     * Stop WireMock server
     */
    public static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            System.out.println("✓ WireMock server stopped");
        }
    }

    /**
     * Get WireMock server instance
     */
    public static WireMockServer getWireMockServer() {
        return wireMockServer;
    }

    /**
     * Reset all mocks
     */
    public static void resetMocks() {
        if (wireMockServer != null) {
            wireMockServer.resetAll();
            System.out.println("✓ All WireMock mocks reset");
        }
    }

}