package com.banking.api.automation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockApiSetup {

    /**
     * Setup Mock: Create Loan Application (Success)
     */
    public static void setupMockCreateLoanSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/loans/create"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"loanId\": \"LN20260623000001\"," +
                                "\"status\": \"CREATED\"," +
                                "\"message\": \"Loan application created successfully\"," +
                                "\"approvedAmount\": 500000," +
                                "\"interestRate\": 8.5" +
                                "}")));

        System.out.println("✓ Mock setup: POST /api/v1/loans/create (201)");
    }

    /**
     * Setup Mock: Create Loan Application (Bad Request)
     */
    public static void setupMockCreateLoanBadRequest(WireMockServer wireMockServer) {
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/loans/create"))
                .withRequestBody(containing("300000"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"message\": \"Insufficient annual income for loan amount\"," +
                                "\"status\": \"BAD_REQUEST\"" +
                                "}")));

        System.out.println("✓ Mock setup: POST /api/v1/loans/create (400)");
    }

    /**
     * Setup Mock: Get Loan by ID (Success)
     */
    public static void setupMockGetLoanSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get(urlMatching("/api/v1/loans/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"loan_id\": \"LN20260623000001\"," +
                                "\"customer_id\": \"CUST001\"," +
                                "\"loan_amount\": 500000," +
                                "\"loan_status\": \"PENDING\"," +
                                "\"tenure_months\": 60," +
                                "\"interest_rate\": 8.5" +
                                "}")));

        System.out.println("✓ Mock setup: GET /api/v1/loans/{loanId} (200)");
    }

    /**
     * Setup Mock: Get Loan by ID (Not Found)
     */
    public static void setupMockGetLoanNotFound(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/loans/INVALID"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"message\": \"Loan not found\"," +
                                "\"status\": \"NOT_FOUND\"" +
                                "}")));

        System.out.println("✓ Mock setup: GET /api/v1/loans/INVALID (404)");
    }

    /**
     * Setup Mock: Approve Loan (Success)
     */
    public static void setupMockApproveLoanSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(put(urlMatching("/api/v1/loans/.*/approve"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"message\": \"Loan approved successfully\"," +
                                "\"loan_id\": \"LN20260623000001\"," +
                                "\"status\": \"APPROVED\"" +
                                "}")));

        System.out.println("✓ Mock setup: PUT /api/v1/loans/{loanId}/approve (200)");
    }

    /**
     * Setup Mock: Calculate EMI (Success)
     */
    public static void setupMockCalculateEmiSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/loans/calculate-emi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"emi_amount\": 10321.29," +
                                "\"total_payments\": 60," +
                                "\"total_interest\": 119277.40" +
                                "}")));

        System.out.println("✓ Mock setup: POST /api/v1/loans/calculate-emi (200)");
    }
    /**
     * Mock: GET /loans/{loanId} → Returns 200 OK
     */
    public static void mockGetLoanByIdTwo() {
        stubFor(get(urlMatching("/api/v1/loans/LaonID"))
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
        System.out.println("✓ Mock setup: GET /api/v1/loans/LoanID (200)");
    }
    /**
     * Setup All Common Mocks
     */
    public static void setupAllMocks(WireMockServer wireMockServer) {
        setupMockCreateLoanSuccess(wireMockServer);
        setupMockCreateLoanBadRequest(wireMockServer);
        setupMockGetLoanSuccess(wireMockServer);
        setupMockGetLoanNotFound(wireMockServer);
        setupMockApproveLoanSuccess(wireMockServer);
        setupMockCalculateEmiSuccess(wireMockServer);
        System.out.println("✓ All WireMock mocks configured");
    }

}