package com.example.devilsgod.donate.Model;

public class ImportYourPostsModel {
    private String victimsPhoto;
    private String victimsName;
    private String diseaseName;
    private Long DonatedAmount;
    private Long amount;


    public ImportYourPostsModel() {
    }

    public ImportYourPostsModel(String victimsPhoto, String victimsName, String diseaseName, Long donatedAmount, Long amount) {
        this.victimsPhoto = victimsPhoto;
        this.victimsName = victimsName;
        this.diseaseName = diseaseName;
        DonatedAmount = donatedAmount;
        this.amount = amount;
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

    public Long getDonatedAmount() {
        return DonatedAmount;
    }

    public void setDonatedAmount(Long donatedAmount) {
        DonatedAmount = donatedAmount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
