package com.example.devilsgod.donate;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.InsertPaymentMethodModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetDonationMethodsFragment extends Fragment {
private Spinner methodsSpinner;
private EditText accountNOET,branchNameET;
private String methodType;
private String branchName;
private Long accountNo;
private Button saveButton;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference rootRef;
    private DatabaseReference paymentRef;
    String currentUserId;
    public SetDonationMethodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_set_donation_methods, container, false);
methodsSpinner=view.findViewById(R.id.spinnerId);
accountNOET=view.findViewById(R.id.accountNumberInsertId);
branchNameET=view.findViewById(R.id.branchNameId);
saveButton=view.findViewById(R.id.setDonationSaveButton);

        rootRef= FirebaseDatabase.getInstance().getReference("rootDataRef");
        paymentRef=rootRef.child("PaymentMethod");
        user=FirebaseAuth.getInstance().getCurrentUser();

//add spinner
        ArrayAdapter<CharSequence> nameAdapter=ArrayAdapter.createFromResource(getContext(),R.array.paymentMethods,android.R.layout.simple_spinner_item);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodsSpinner.setAdapter(nameAdapter);

       saveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               uploadData();
               accountNOET.setText("");
               branchNameET.setText("");

           }
       });
        return view;

    }
    public void uploadData(){
        methodType= methodsSpinner.getSelectedItem().toString();
        accountNo=Long.valueOf(accountNOET.getText().toString());
        branchName=branchNameET.getText().toString();
        currentUserId=user.getUid().toString();
        String uKey=paymentRef.push().getKey();
        InsertPaymentMethodModel methodModel=new InsertPaymentMethodModel(methodType,branchName,accountNo);
        paymentRef.child(currentUserId).child(uKey).setValue(methodModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Method Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Failed",e.toString());
            }
        });
    }

}
