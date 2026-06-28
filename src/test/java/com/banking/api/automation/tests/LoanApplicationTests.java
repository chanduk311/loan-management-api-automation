package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.LoanApplication;
import com.banking.api.automation.pojos.LoanResponse;
import com.banking.api.automation.utils.APIUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanApplicationTests extends BaseTests {

    @Test(priority = 1)
    public void testCreateLoanApplicationSuccessfully() {
        // Arrange
        LoanApplication loanApp = new LoanApplication(
                "CUST001",
                500000.0,
                60,
                1500000.0,
                "SALARIED",
                "PROPERTY"
        );

        // Act - WireMock will intercept and return mocked response
        Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 201, "Should return 201");

        LoanResponse loanResponse = response.as(LoanResponse.class);
        Assert.assertNotNull(loanResponse.getLoanId(), "Loan ID should not be null");
        Assert.assertEquals(loanResponse.getStatus(), "CREATED", "Status should be CREATED");
    }

    @Test(priority = 2)
    public void testCreateLoanWithInsufficientIncome() {
        // Arrange
        LoanApplication loanApp = new LoanApplication(
                "CUST002",
                1000000.0,
                60,
                300000.0,  // Low income
                "SELF_EMPLOYED",
                "PROPERTY"
        );

        // Act - WireMock will return 400
        Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Should return 400");
    }

}