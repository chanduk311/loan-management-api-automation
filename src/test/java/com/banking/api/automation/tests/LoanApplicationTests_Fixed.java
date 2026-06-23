package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.LoanApplication;
import com.banking.api.automation.pojos.LoanResponse;
import com.banking.api.automation.utils.APIUtil;
import com.banking.api.automation.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanApplicationTests_Fixed {

    /**
     * TEST 1: Create Loan Application - Happy Path
     * Scenario: Customer applies for a loan with valid details
     */
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

        // Act
        Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 201, "Loan application should return 201");

        LoanResponse loanResponse = response.as(LoanResponse.class);
        Assert.assertNotNull(loanResponse.getLoanId(), "Loan ID should not be null");
        Assert.assertEquals(loanResponse.getStatus(), "CREATED", "Status should be CREATED");

        // Database Validation
        String dbLoanStatus = DatabaseUtil.getLoanStatus(loanResponse.getLoanId());
        Assert.assertEquals(dbLoanStatus, "PENDING", "Database should have PENDING status");
    }

    /**
     * TEST 2: Create Loan - Insufficient Income
     * Scenario: Customer applies with insufficient annual income
     */
    @Test(priority = 2)
    public void testCreateLoanWithInsufficientIncome() {
        // Arrange
        LoanApplication loanApp = new LoanApplication(
                "CUST002",
                1000000.0,
                60,
                300000.0,
                "SELF_EMPLOYED",
                "PROPERTY"
        );

        // Act
        Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Should return 400 Bad Request");
        String responseMessage = response.jsonPath().getString("message");
        Assert.assertTrue(responseMessage.contains("Income"), "Error message should mention income");
    }

    /**
     * TEST 3: Validate Loan Amount Limits
     * Scenario: Loan amount exceeds maximum limit
     */
    @Test(priority = 3)
    public void testCreateLoanExceedsMaximumAmount() {
        // Arrange
        LoanApplication loanApp = new LoanApplication(
                "CUST003",
                5000000.0,
                60,
                2000000.0,
                "SALARIED",
                "PROPERTY"
        );

        // Act
        Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 422, "Should return 422 Unprocessable Entity");
    }

    /**
     * TEST 4: Retrieve Loan Application
     * Scenario: Get loan application details by loan ID
     */
    @Test(priority = 4)
    public void testGetLoanApplicationById() {
        // Arrange - First create a loan
        LoanApplication loanApp = new LoanApplication(
                "CUST004",
                400000.0,
                48,
                1200000.0,
                "SALARIED",
                "PROPERTY"
        );

        // Act - Create loan
        Response createResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = createResponse.as(LoanResponse.class).getLoanId();

        // Act - Retrieve loan
        Response getResponse = APIUtil.getRequestWithPathParam(
                EndPoints.GET_LOAN_BY_ID,
                "loanId",
                loanId
        );

        // Assert
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Should return 200");
        Assert.assertEquals(getResponse.jsonPath().getString("loan_id"), loanId);
    }

}