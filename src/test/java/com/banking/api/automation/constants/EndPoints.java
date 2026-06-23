package com.banking.api.automation.constants;

public class EndPoints {

    // Loan Application Endpoints
    public static final String CREATE_LOAN = "/loans/create";
    public static final String GET_LOAN_BY_ID = "/loans/{loanId}";
    public static final String GET_ALL_LOANS = "/loans";
    public static final String UPDATE_LOAN = "/loans/{loanId}";
    public static final String DELETE_LOAN = "/loans/{loanId}";

    // Loan Approval Endpoints
    public static final String APPROVE_LOAN = "/loans/{loanId}/approve";
    public static final String REJECT_LOAN = "/loans/{loanId}/reject";
    public static final String GET_LOAN_STATUS = "/loans/{loanId}/status";

    // EMI Calculation Endpoints
    public static final String CALCULATE_EMI = "/loans/calculate-emi";
    public static final String GET_EMI_SCHEDULE = "/loans/{loanId}/emi-schedule";
    public static final String GET_PENDING_EMIS = "/loans/{loanId}/pending-emis";

    // Customer Endpoints
    public static final String GET_CUSTOMER_LOANS = "/customers/{customerId}/loans";
    public static final String GET_CUSTOMER_BY_ID = "/customers/{customerId}";
    public static final String GET_CUSTOMER_CREDIT_SCORE = "/customers/{customerId}/credit-score";

    // Transaction Endpoints
    public static final String GET_LOAN_TRANSACTIONS = "/loans/{loanId}/transactions";
    public static final String RECORD_PAYMENT = "/loans/{loanId}/payment";

    // Audit Endpoints
    public static final String GET_LOAN_AUDIT = "/loans/{loanId}/audit";

    // Document Verification Endpoints
    public static final String UPLOAD_DOCUMENT = "/loans/{loanId}/documents/upload";
    public static final String GET_DOCUMENTS = "/loans/{loanId}/documents";
    public static final String VERIFY_DOCUMENT = "/loans/{loanId}/documents/{documentId}/verify";

}