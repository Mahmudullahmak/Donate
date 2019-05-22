package com.example.devilsgod.donate.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ImportNewsFeedModel implements Serializable{
    private String victimsPhoto;
    private String victimsName;
    private String diseaseName;
    private long phoneNo;
    private long amount;
    private ArrayList<String>documents=new ArrayList<>();
    String currentUserId;
    String key;
   private Long DonatedAmount;
   private Long totalAmount;
   private String approval;
    public ImportNewsFeedModel() {
    }

    public ImportNewsFeedModel(String victimsPhoto, String victimsName, String diseaseName, long phoneNo, long amount, ArrayList<String> documents, String currentUserId, String key,Long totalAmount,Long DonatedAmount) {
        this.victimsPhoto = victimsPhoto;
        this.victimsName = victimsName;
        this.diseaseName = diseaseName;
        this.phoneNo = phoneNo;
        this.amount = amount;
        this.documents = documents;
        this.currentUserId = currentUserId;
        this.key = key;
        this.totalAmount=totalAmount;
        this.DonatedAmount=DonatedAmount;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getVictimsPhoto() {
        return victimsPhoto;
    }

    public Long getDonatedAmount() {
        return DonatedAmount;
    }

    public void setDonatedAmount(Long donatedAmount) {
        DonatedAmount = donatedAmount;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
