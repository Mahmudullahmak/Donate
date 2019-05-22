package com.example.devilsgod.donate.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InsertPatientDataModel {
    private String victimsPhoto;
    private String victimsName;
    private String diseaseName;
    private Long phoneNo;
    private Long amount;
   ArrayList<String>documents;
   private Long donatedAmount;

    public InsertPatientDataModel() {
    }

    public InsertPatientDataModel(String victimsPhoto, String victimsName, String diseaseName, Long phoneNo, Long amount, ArrayList<String> documents) {
        this.victimsPhoto = victimsPhoto;
        this.victimsName = victimsName;
        this.diseaseName = diseaseName;
        this.phoneNo = phoneNo;
        this.amount = amount;
        this.documents = documents;
    }

    public InsertPatientDataModel(Long donatedAmount) {
        this.donatedAmount = donatedAmount;
    }

    public Long getDonatedAmount() {
        return donatedAmount;
    }

    public void setDonatedAmount(Long donatedAmount) {
        this.donatedAmount = donatedAmount;
    }

    public String getVictimsPhoto() {
        return victimsPhoto;
    }

    public void setVictimsPhoto(String victimsPhoto) {
        this.victimsPhoto = victimsPhoto;
    }

    public String getVictimsName() {
        return victimsName;
    }

    public void setVictimsName(String victimsName) {
        this.victimsName = victimsName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }
}
