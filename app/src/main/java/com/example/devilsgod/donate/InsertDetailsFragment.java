package com.example.devilsgod.donate;



import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.DocumentsListModel;
import com.example.devilsgod.donate.Model.InsertPatientDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertDetailsFragment extends Fragment {
    int SELECT_PICTURES=1;
   public Uri imageUri;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private StorageReference storageReference;
    private StorageReference documentsRefference;
private Button donationMethodsButton;
private FragmentManager fm;
private FragmentTransaction ft;
private LinearLayout linearLayout;
    private DatabaseReference rootRef;
    private DatabaseReference patientDataRef;
private Button insertDocumentsBtn,victimsPhoto,saveButton;
private ImageView patientImage;
public Uri filePath;
    DocumentsListModel dmodel=new DocumentsListModel();
public String downloadUrl;
ArrayList<Uri>documentsList=new ArrayList<>();
ArrayList<String>documents=new ArrayList<>();
InsertPatientDataModel model=new InsertPatientDataModel();
    private EditText vName,vDisease,vPhnNo,neededAmountET;
    public InsertDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_insert_details, container, false);
linearLayout=view.findViewById(R.id.insertLinearid);
insertDocumentsBtn=view.findViewById(R.id.insertDocumentsId);
victimsPhoto=view.findViewById(R.id.chooseImageId);
patientImage=view.findViewById(R.id.patientImage);
saveButton=view.findViewById(R.id.saveButtonId);
vName=view.findViewById(R.id.vic_nameId);
vDisease=view.findViewById(R.id.diseaseNameId);
vPhnNo=view.findViewById(R.id.vic_contactNo);
neededAmountET=view.findViewById(R.id.neededAmountId);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rootRef= FirebaseDatabase.getInstance().getReference("rootDataRef");
        patientDataRef=rootRef.child("PatientData");
        user=FirebaseAuth.getInstance().getCurrentUser();
      //  documentsRefference=storage.getReference();
        user=FirebaseAuth.getInstance().getCurrentUser();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadImage();
                uploadDocumetns();
                Toast.makeText(getContext(), "Need Admin Approval", Toast.LENGTH_SHORT).show();
                patientImage.setImageResource(R.mipmap.ic_launcher);
             /*   vName.setText("");
                vDisease.setText("");
              //  vPhnNo.setText("");
                neededAmountET.setText("");*/
            }
        });
        insertDocumentsBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chooseDocuments();
    }
});
victimsPhoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chooseImage();
    }
});
      donationMethodsButton=view.findViewById(R.id.donationMethodsButton);
      donationMethodsButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SetDonationMethodsFragment setDonationMethodsFragment=new SetDonationMethodsFragment();
              fm=getActivity().getSupportFragmentManager();
              ft=fm.beginTransaction()
                      .replace(R.id.insertDetailsFragmentHolder,setDonationMethodsFragment);
              ft.addToBackStack(null);
              linearLayout.setVisibility(View.GONE);
              ft.commit();
          }
      });
        return view;
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES);
    }
  public void chooseDocuments(){
      Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
      startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES);
  }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        documentsList.clear();
        if(requestCode == SELECT_PICTURES) {
            if(resultCode == RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++) {
                         imageUri = data.getClipData().getItemAt(i).getUri();
                         documentsList.add(imageUri);

                         dmodel.setDocumentList(documentsList);

                    }
                }
            } else if(data!=null&&data.getData() != null) {
                Uri imagePath = data.getData();
            }
        }
        if(requestCode == SELECT_PICTURES && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                patientImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
    private void uploadImage() {

        if(filePath != null) {
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();

                            Task<Uri> dUrlRef=ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=uri.toString();

                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Image failed"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }





    }
    public void uploadDocumetns(){
            documents.clear();
          if (imageUri!=null){
          Uri[] uris=new Uri[documentsList.size()];

            for (int i=0;i<documentsList.size();i++) {
                uris[i] = documentsList.get(i);
                final StorageReference documentsRef = storageReference.child("documents/" + UUID.randomUUID().toString());
                documentsRef.putFile(uris[i])
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getContext(), "documents saved", Toast.LENGTH_SHORT).show();

                                Task<Uri> dUrlRef = documentsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String downUrl = uri.toString();
                                        documents.add(downUrl);
                                        model.setDocuments(documents);

                                         if (model.getDocuments().size()>=3){
                                             String currentUserId = user.getUid().toString();
                                             String name = vName.getText().toString();
                                             String disease = vDisease.getText().toString();
                                             Long phnNo = Long.valueOf(vPhnNo.getText().toString());
                                             Long amountN = Long.valueOf(neededAmountET.getText().toString());


                                             InsertPatientDataModel modell = new InsertPatientDataModel(downloadUrl, name, disease, phnNo, amountN, model.getDocuments());

                                             String key = patientDataRef.push().getKey();
                                             patientDataRef.child(currentUserId).child(key).setValue(modell).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void aVoid) {
                                                     Toast.makeText(getContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                                                 }
                                             }).addOnFailureListener(new OnFailureListener() {
                                                 @Override
                                                 public void onFailure(@NonNull Exception e) {
                                                     Log.e("Failure", e.toString());
                                                 }
                                             });
                                         }

                                    }


                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Documents failed" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }


        }







    }




}

