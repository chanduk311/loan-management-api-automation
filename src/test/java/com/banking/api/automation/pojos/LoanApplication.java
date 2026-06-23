package com.banking.api.automation.pojos;

public class LoanApplication {

    private String customerId;
    private double loanAmount;
    private int tenureMonths;
    private double annualIncome;
    private String employmentType;
    private String collateralType;

    // Default Constructor
    public LoanApplication() {
    }

    // Constructor with all parameters
    public LoanApplication(String customerId, double loanAmount, int tenureMonths,
                           double annualIncome, String employmentType, String collateralType) {
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.tenureMonths = tenureMonths;
        this.annualIncome = annualIncome;
        this.employmentType = employmentType;
        this.collateralType = collateralType;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "customerId='" + customerId + '\'' +
                ", loanAmount=" + loanAmount +
                ", tenureMonths=" + tenureMonths +
                ", annualIncome=" + annualIncome +
                ", employmentType='" + employmentType + '\'' +
                ", collateralType='" + collateralType + '\'' +
                '}';
    }
}