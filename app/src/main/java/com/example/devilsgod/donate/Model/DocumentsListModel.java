package com.example.devilsgod.donate.Model;

import android.net.Uri;

import java.util.ArrayList;

public class DocumentsListModel {
    ArrayList<Uri>documentList;

    public DocumentsListModel() {
    }

    public DocumentsListModel(ArrayList<Uri> documentList) {
        this.documentList = documentList;
    }

    public ArrayList<Uri> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<Uri> documentList) {
        this.documentList = documentList;
    }
}
