package com.banking.api.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportUtil {

    private static ExtentReports extent;

    public static void initialize() {
        getInstance();
        System.out.println("Report initialized");
    }

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "test-reports/ExtentReport.html");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Optional useful information in the report
            extent.setSystemInfo("Project", "Loan Management API Automation");
            extent.setSystemInfo("Framework", "RestAssured + TestNG");
            extent.setSystemInfo("Environment", "WireMock");
        }

        return extent;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}