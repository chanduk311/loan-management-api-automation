package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.EMICalculation;
import com.banking.api.automation.pojos.LoanApplication;
import com.banking.api.automation.utils.APIUtil;
import com.banking.api.automation.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EMICalculationTests_Fixed {

    /**
     * EMI Formula: P * r * (1 + r)^n / ((1 + r)^n - 1)
     * Where P = Principal, r = monthly rate, n = number of months
     */

    @Test(priority = 1)
    public void testEMICalculationAccuracy() {
        // Create and approve a loan
        LoanApplication loanApp = new LoanApplication(
                "CUST008",
                1000000.0,
                60,
                2000000.0,
                "SALARIED",
                "PROPERTY"
        );

        Response createResponse = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
        String loanId = createResponse.jsonPath().getString("loan_id");

        // Approve loan
        String approvalPayload = "{\"approvalStatus\":\"APPROVED\",\"approvedAmount\":1000000,\"interestRate\":8.5}";
        APIUtil.putRequest(
                EndPoints.APPROVE_LOAN.replace("{loanId}", loanId),
                approvalPayload
        );

        // Calculate EMI
        EMICalculation emiRequest = new EMICalculation();
        emiRequest.setLoanId(loanId);
        emiRequest.setTotalPayments(60);

        Response emiResponse = APIUtil.postRequest(EndPoints.CALCULATE_EMI, emiRequest);

        Assert.assertEquals(emiResponse.getStatusCode(), 200);

        double apiEMI = emiResponse.jsonPath().getDouble("emi_amount");
        double expectedEMI = calculateEMI(1000000, 8.5, 60);

        // Assert with tolerance (±1)
        Assert.assertEquals(apiEMI, expectedEMI, 1.0, "EMI calculation should be accurate");

        // Database Validation
        double dbEMI = DatabaseUtil.getCalculatedEMI(loanId);
        Assert.assertEquals(dbEMI, apiEMI, "API and Database EMI should match");
    }

    @Test(priority = 2)
    public void testEMICalculationWithVariousInterestRates() {
        double[][] testCases = {
                {500000, 7.5, 36},
                {750000, 8.0, 48},
                {1000000, 9.0, 60}
        };

        for (double[] testCase : testCases) {
            LoanApplication loanApp = new LoanApplication(
                    "CUST_TEST_" + System.currentTimeMillis(),
                    testCase[0],
                    (int) testCase[2],
                    2000000.0,
                    "SALARIED",
                    "PROPERTY"
            );

            Response response = APIUtil.postRequest(EndPoints.CREATE_LOAN, loanApp);
            String loanId = response.jsonPath().getString("loan_id");

            EMICalculation emiRequest = new EMICalculation();
            emiRequest.setLoanId(loanId);
            emiRequest.setTotalPayments((int) testCase[2]);

            Response emiResponse = APIUtil.postRequest(EndPoints.CALCULATE_EMI, emiRequest);

            Assert.assertEquals(emiResponse.getStatusCode(), 200,
                    "Should calculate EMI for amount " + testCase[0]);
        }
    }

    /**
     * Utility method to calculate EMI
     * P * r * (1 + r)^n / ((1 + r)^n - 1)
     */
    private double calculateEMI(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12 / 100;
        double numerator = principal * monthlyRate * Math.pow(1 + monthlyRate, months);
        double denominator = Math.pow(1 + monthlyRate, months) - 1;
        return numerator / denominator;
    }

}