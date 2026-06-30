package com.banking.api.automation.tests;

import com.banking.api.automation.constants.EndPoints;
import com.banking.api.automation.pojos.EMICalculation;
import com.banking.api.automation.utils.APIUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EMICalculationTests extends BaseTest {

    @Test(priority = 1)
    public void testEMICalculationAccuracy() {
        // Setup Mock
        mockCalculateEMI();

        // Arrange
        EMICalculation emiRequest = new EMICalculation();
        emiRequest.setLoanId("LN20240101000001");
        emiRequest.setTotalPayments(60);

        // Act
        Response response = APIUtil.postRequest(EndPoints.CALCULATE_EMI, emiRequest);

        // Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        double emiAmount = response.jsonPath().getDouble("emiAmount");
        Assert.assertEquals(emiAmount, 20665.30, 1.0, "EMI should be accurate");
        System.out.println("✓ Test Passed: EMI calculation is accurate");
    }
}