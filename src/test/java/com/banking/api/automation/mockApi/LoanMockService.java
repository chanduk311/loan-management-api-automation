package com.banking.api.automation.mockApi;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LoanMockService {
    public static void mockLoanSuccess() {

        WireMockManager.getServer().stubFor(
                get(urlEqualTo("/api/v1/loan/1001"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody("""
                                        {
                                            "loanId":1001,
                                            "customerName":"Chandu",
                                            "loanAmount":500000,
                                            "status":"APPROVED"
                                        }
                                        """)
                        )
        );
    }
}
