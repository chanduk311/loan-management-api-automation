package com.banking.api.automation.mockApi;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockManager {

    private static WireMockServer wireMockServer;

    public static void startServer() {

        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        System.out.println("WireMock Started");
    }

    public static void stopServer() {

        if (wireMockServer != null) {
            wireMockServer.stop();
            System.out.println("WireMock Stopped");
        }
    }

    public static WireMockServer getServer() {
        return wireMockServer;
    }
}