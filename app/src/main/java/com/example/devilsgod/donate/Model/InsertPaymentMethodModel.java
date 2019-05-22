package com.example.devilsgod.donate.Model;

import android.util.Log;

import java.io.Serializable;

public class InsertPaymentMethodModel implements Serializable {
    private String methodType;
    private String branchName;
    private Long accountNo;

    public InsertPaymentMethodModel() {
    }

    public InsertPaymentMethodModel(String methodType, String branchName, Long accountNo) {
        this.methodType = methodType;
        this.branchName = branchName;
        this.accountNo = accountNo;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }
}
