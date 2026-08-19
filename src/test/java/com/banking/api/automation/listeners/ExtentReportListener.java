package com.banking.api.automation.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.banking.api.automation.utils.ReportUtil;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test = ReportUtil.getInstance()
                .createTest(result.getMethod().getMethodName());

        extentTest.set(test);

        test.info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().pass("Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail("Test failed");

        if (result.getThrowable() != null) {
            extentTest.get().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().skip("Test skipped");

        if (result.getThrowable() != null) {
            extentTest.get().skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        ReportUtil.flush();
    }
}