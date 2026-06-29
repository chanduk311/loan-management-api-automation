package com.banking.api.automation.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BaseTests {

    /**
     * Use port 3000 (Less likely to be occupied)
     */
    protected static final int WIREMOCK_PORT = 3000;
    protected static final String WIREMOCK_BASE_URL = "http://localhost:" + WIREMOCK_PORT;

    protected static WireMockServer wireMockServer;

    @BeforeClass(alwaysRun = true)
    public void setupWireMock() {
        try {
            // Start WireMock server
            wireMockServer = new WireMockServer(WIREMOCK_PORT);
            wireMockServer.start();

            // Configure WireMock
            WireMock.configureFor("localhost", WIREMOCK_PORT);

            System.out.println("✓ WireMock Server Started on " + WIREMOCK_BASE_URL);
            System.out.println("✓ Admin URL: " + WIREMOCK_BASE_URL + "/__admin/");

        } catch (Exception e) {
            System.err.println("✗ Error starting WireMock on port " + WIREMOCK_PORT);
            System.err.println("✗ Port " + WIREMOCK_PORT + " may be occupied");
            System.err.println("✗ Error: " + e.getMessage());
            throw new RuntimeException("Failed to start WireMock on port " + WIREMOCK_PORT, e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void stopWireMock() {
        try {
            if (wireMockServer != null && wireMockServer.isRunning()) {
                wireMockServer.stop();
                System.out.println("✓ WireMock Server Stopped");
            }
        } catch (Exception e) {
            System.err.println("✗ Error stopping WireMock: " + e.getMessage());
        }
    }

    // ============================================================================
    // MOCK ENDPOINTS - Same as before
    // ============================================================================

    protected void mockCreateLoanSuccess() {
        stubFor(post(urlEqualTo("/api/v1/loans/create"))
                .withHeader("Content-Type", containing("application/json"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"loanId\": \"LN20240101000001\",\n" +
                                "  \"status\": \"CREATED\",\n" +
                                "  \"message\": \"Loan application created successfully\",\n" +
                                "  \"approvedAmount\": 500000.0,\n" +
                                "  \"interestRate\": 8.5\n" +
                                "}")
                        .withFixedDelay(500)));
    }

    protected void mockCreateLoanInvalidIncome() {
        stubFor(post(urlEqualTo("/api/v1/loans/create"))
                .withHeader("Content-Type", containing("application/json"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"status\": \"ERROR\",\n" +
                                "  \"message\": \"Income is insufficient for this loan amount\"\n" +
                                "}")));
    }

    protected void mockCreateLoanExceedsLimit() {
        stubFor(post(urlEqualTo("/api/v1/loans/create"))
                .withHeader("Content-Type", containing("application/json"))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"status\": \"ERROR\",\n" +
                                "  \"message\": \"Loan amount exceeds maximum limit\"\n" +
                                "}")));
    }

    protected void mockGetLoanById() {
        stubFor(get(urlMatching("/api/v1/loans/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"loanId\": \"LN20240101000001\",\n" +
                                "  \"customerId\": \"CUST001\",\n" +
                                "  \"loanAmount\": 500000.0,\n" +
                                "  \"tenureMonths\": 60,\n" +
                                "  \"interestRate\": 8.5,\n" +
                                "  \"loanStatus\": \"PENDING\"\n" +
                                "}")));
    }

    protected void mockApproveLoan() {
        stubFor(put(urlMatching("/api/v1/loans/.*/approve"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"status\": \"APPROVED\",\n" +
                                "  \"message\": \"Loan approved successfully\"\n" +
                                "}")));
    }

    protected void mockApproveLoanAlreadyApproved() {
        stubFor(put(urlMatching("/api/v1/loans/.*/approve"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"status\": \"ERROR\",\n" +
                                "  \"message\": \"Loan is already approved\"\n" +
                                "}")));
    }

    protected void mockRejectLoan() {
        stubFor(put(urlMatching("/api/v1/loans/.*/approve"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"status\": \"REJECTED\",\n" +
                                "  \"message\": \"Loan rejected due to low credit score\"\n" +
                                "}")));
    }

    protected void mockCalculateEMI() {
        stubFor(post(urlEqualTo("/api/v1/loans/calculate-emi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"loanId\": \"LN20240101000001\",\n" +
                                "  \"emiAmount\": 20665.30,\n" +
                                "  \"totalPayments\": 60,\n" +
                                "  \"totalInterest\": 239918.0\n" +
                                "}")));
    }
}