package com.example.devilsgod.donate.Model;

import java.util.ArrayList;

public class ImportAdminModel {
    private String victimsPhoto;
    private String victimsName;
    private String diseaseName;
    private String currentUserId;
    private String ukey;
    private String approval;


    public ImportAdminModel() {
    }

    public ImportAdminModel(String victimsPhoto, String victimsName, String diseaseName, String currentUserId, String ukey) {
        this.victimsPhoto = victimsPhoto;
        this.victimsName = victimsName;
        this.diseaseName = diseaseName;
        this.currentUserId = currentUserId;
        this.ukey = ukey;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
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

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
}
