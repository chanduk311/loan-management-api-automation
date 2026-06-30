package com.banking.api.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportUtil {
    private static ExtentReports extent;

    public static void initialize(){
        System.out.println("Report initialized");
    }

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "test-reports/ExtentReport.html");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }

        return extent;
    }
}
