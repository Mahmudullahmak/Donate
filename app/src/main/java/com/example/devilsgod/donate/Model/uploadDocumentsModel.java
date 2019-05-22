package com.example.devilsgod.donate.Model;

import android.net.Uri;

public class uploadDocumentsModel {
    private Uri documentsUri;

    public uploadDocumentsModel() {
    }

    public uploadDocumentsModel(Uri documentsUri) {
        this.documentsUri = documentsUri;
    }

    public Uri getDocumentsUri() {
        return documentsUri;
    }

    public void setDocumentsUri(Uri documentsUri) {
        this.documentsUri = documentsUri;
    }
}
