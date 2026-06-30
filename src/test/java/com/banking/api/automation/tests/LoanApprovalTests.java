package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.LoanApplication;
import com.banking.api.automation.utils.APIUtil;
import com.banking.api.automation.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanApprovalTests extends BaseTest {

    /**
     * TEST 1: Approve Loan - Happy Path
     * Scenario: Loan officer approves a pending loan application
     */

    @Test(priority = 1)
    public void testApproveLoanSuccessfully() {
        // Setup Mocks
        mockCreateLoanSuccess();
        mockApproveLoan();

        // Create loan first
        String createPayload = "{\n" +
                "  \"customerId\": \"CUST005\",\n" +
                "  \"loanAmount\": 600000.0,\n" +
                "  \"tenureMonths\": 60,\n" +
                "  \"annualIncome\": 2000000.0,\n" +
                "  \"employmentType\": \"SALARIED\",\n" +
                "  \"collateralType\": \"PROPERTY\"\n" +
                "}";

        // Approve loan
        String approvalPayload = "{\"approvalStatus\":\"APPROVED\",\"approvedAmount\":600000}";
        Response approveResponse = APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", "LN20240101000001"),
                approvalPayload
        );

        // Assert
        Assert.assertEquals(approveResponse.getStatusCode(), 200);
        Assert.assertEquals(approveResponse.jsonPath().getString("status"), "APPROVED");
        System.out.println("✓ Test Passed: Loan approved successfully");
    }

    /**
     * TEST 2: Reject Loan with Reason
     * Scenario: Loan officer rejects a loan with rejection reason
     */
   /* @Test(priority = 2)
    public void testRejectLoanWithReason() {
        LoanApplication loanApp = new LoanApplication(
                "CUST006",
                800000.0,
                60,
                900000.0,
                "SALARIED",
                "PROPERTY"
        );

        Response createResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = createResponse.jsonPath().getString("loan_id");

        String rejectionPayload = "{\"approvalStatus\":\"REJECTED\",\"rejectionReason\":\"Low credit score\"}";
        Response rejectResponse = APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                rejectionPayload
        );

        Assert.assertEquals(rejectResponse.getStatusCode(), 200);

        // Database Validation
        String dbStatus = DatabaseUtil.getLoanStatus(loanId);
        Assert.assertEquals(dbStatus, "REJECTED", "Database should show REJECTED status");
    }

    *//**
     * TEST 3: Cannot approve already approved loan
     * Scenario: Attempt to approve a loan that's already approved
     *//*
    @Test(priority = 3)
    public void testCannotApproveAlreadyApprovedLoan() {
        LoanApplication loanApp = new LoanApplication(
                "CUST007",
                500000.0,
                60,
                1800000.0,
                "SALARIED",
                "PROPERTY"
        );

        Response createResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = createResponse.jsonPath().getString("loan_id");

        // First approval
        String approvalPayload = "{\"approvalStatus\":\"APPROVED\",\"approvedAmount\":500000}";
        APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                approvalPayload
        );

        // Second approval attempt
        Response secondApproval = APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                approvalPayload
        );

        Assert.assertEquals(secondApproval.getStatusCode(), 400, "Should return 400");
        String errorMsg = secondApproval.jsonPath().getString("message");
        Assert.assertTrue(errorMsg.contains("already"), "Should mention loan is already processed");
    }*/

}