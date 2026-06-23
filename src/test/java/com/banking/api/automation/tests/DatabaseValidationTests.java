package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.LoanApplication;
import com.banking.api.automation.utils.APIUtil;
import com.banking.api.automation.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class DatabaseValidationTests {

    /**
     * TEST 1: Validate API Response with Database
     * Scenario: Create loan via API and validate all fields in database
     */
    @Test(priority = 1)
    public void testAPIResponseMatchesDatabase() {
        // Arrange
        LoanApplication loanApp = new LoanApplication(
                "CUST005",
                700000.0,
                60,
                3000000.0,
                "SALARIED",
                "PROPERTY"
        );

        // Act - Create loan via API
        Response apiResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = apiResponse.jsonPath().getString("loan_id");

        // Act - Retrieve from Database
        Map<String, Object> dbData = DatabaseUtil.getLoanApplicationById(loanId);

        // Assert
        Assert.assertEquals(dbData.get("loan_id"), loanId, "Loan ID should match");
        Assert.assertEquals(dbData.get("customer_id"), "CUST005", "Customer ID should match");
        Assert.assertEquals(dbData.get("loan_amount"), 700000.0, "Loan amount should match");
        Assert.assertEquals(dbData.get("loan_status"), "PENDING", "Status should be PENDING");
    }

    /**
     * TEST 2: Validate Customer Credit Score Update After Loan Approval
     * Scenario: Credit score should decrease when loan is approved
     */
    @Test(priority = 2)
    public void testCreditScoreDecrementOnLoanApproval() {
        // Arrange
        String customerId = "CUST005";

        // Get initial credit score
        int initialScore = DatabaseUtil.getCustomerCreditScore(customerId);

        LoanApplication loanApp = new LoanApplication(
                customerId,
                500000.0,
                60,
                1800000.0,
                "SALARIED",
                "PROPERTY"
        );

        // Act - Create and approve loan
        Response createResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = createResponse.jsonPath().getString("loan_id");

        String approvalPayload = "{\"approvalStatus\":\"APPROVED\",\"approvedAmount\":500000}";
        APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                approvalPayload
        );

        // Get updated credit score
        int updatedScore = DatabaseUtil.getCustomerCreditScore(customerId);

        // Assert
        Assert.assertTrue(updatedScore < initialScore,
                "Credit score should decrease after loan approval");
    }

    /**
     * TEST 3: Count Active Loans
     * Scenario: Validate count of active loans for a customer
     */
    @Test(priority = 3)
    public void testActiveLoansCount() {
        // Arrange
        String customerId = "CUST005";

        // Act - Create 3 loans
        for (int i = 0; i < 3; i++) {
            LoanApplication loanApp = new LoanApplication(
                    customerId,
                    300000.0 + (i * 50000),
                    48,
                    1500000.0,
                    "SALARIED",
                    "PROPERTY"
            );

            Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
            String loanId = response.jsonPath().getString("loan_id");

            // Approve all loans
            String approvalPayload = "{\"approvalStatus\":\"APPROVED\",\"approvedAmount\":" +
                    (300000 + (i * 50000)) + "}";
            APIUtil.putRequest(
                    EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                    approvalPayload
            );
        }

        // Count active loans
        int activeLoansCount = DatabaseUtil.getActiveLoansCount(customerId);

        // Assert
        Assert.assertEquals(activeLoansCount, 3, "Customer should have 3 active loans");
    }

    /**
     * TEST 4: Data Consistency - Loan Amount
     * Scenario: Loan amount in API request should match database
     */
    @Test(priority = 4)
    public void testLoanAmountConsistency() {
        // Arrange
        double[] loanAmounts = {250000, 500000, 750000, 1000000};

        // Act & Assert
        for (double amount : loanAmounts) {
            LoanApplication loanApp = new LoanApplication(
                    "CUST_CONSISTENCY_" + System.currentTimeMillis(),
                    amount,
                    60,
                    2000000.0,
                    "SALARIED",
                    "PROPERTY"
            );

            Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
            String loanId = response.jsonPath().getString("loan_id");

            Map<String, Object> dbData = DatabaseUtil.getLoanApplicationById(loanId);

            Assert.assertEquals(dbData.get("loan_amount"), amount,
                    "Loan amount should be consistent for amount: " + amount);
        }
    }

}