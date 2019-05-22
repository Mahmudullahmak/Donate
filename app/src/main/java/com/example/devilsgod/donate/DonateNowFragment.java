package com.example.devilsgod.donate;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.ImportNewsFeedModel;
import com.example.devilsgod.donate.Model.InsertPatientDataModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonateNowFragment extends Fragment {
    private Spinner methodsSpinner;
    private EditText accountNoET,branchNameEditText,donationAmountET;
    private Button donateButton;
    private String currentUserId;
    private String branchName;
    private Long donationAmount;
    private Long accountNo;
    private String methodtype;
    FirebaseDatabase database;
    DatabaseReference rootRef;
    private LinearLayout linearLayout;
    DatabaseReference methodRef;
    DatabaseReference patientDataRef;
    Long cAmount;
private LinearLayout liinearLayout;
    String value;

    public DonateNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donate_now, container, false);
        methodsSpinner=view.findViewById(R.id.donationSpinner);
        accountNoET=view.findViewById(R.id.accountNoETId);
        branchNameEditText=view.findViewById(R.id.branchNameETId);
        donationAmountET=view.findViewById(R.id.donationAMountId);
        donateButton=view.findViewById(R.id.donateButton);
        linearLayout=view.findViewById(R.id.branchLinewr);
        liinearLayout=view.findViewById(R.id.linearId);
        ArrayAdapter<CharSequence> nameAdapter=ArrayAdapter.createFromResource(getContext(),R.array.paymentMethods,android.R.layout.simple_spinner_item);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodsSpinner.setAdapter(nameAdapter);
methodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String mType=methodsSpinner.getSelectedItem().toString();
        if (mType.equals("Bkash")){
            branchNameEditText.setText("This is not required for Bkash");
            linearLayout.setVisibility(View.INVISIBLE);
        }
        else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        donationProcess();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("rootDataRef");
        methodRef = rootRef.child("PaymentMethod");
        patientDataRef=rootRef.child("PatientData");
        currentUserId=getArguments().getString("cuID");
       // Toast.makeText(getContext(), ""+currentUserId, Toast.LENGTH_SHORT).show();
        donationProcess();

        return view;


    }

    public void donationProcess(){
        currentUserId=getArguments().getString("cuID");
        methodRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                   final String key=d.getKey();
                   if (d.child("methodType").getValue().equals(methodsSpinner.getSelectedItem().toString())){
                       branchName=d.child("branchName").getValue().toString();
                       accountNo=Long.parseLong(d.child("accountNo").getValue().toString());
                       methodtype=d.child("methodType").getValue().toString();
                       accountNoET.setText(""+accountNo);
                       branchNameEditText.setText(branchName);

                       donateButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               donationAmount=Long.valueOf(donationAmountET.getText().toString());

                               String key=getArguments().getString("key");
                               patientDataRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       for (DataSnapshot dd:dataSnapshot.getChildren()){
                                           ImportNewsFeedModel modell=dd.getValue(ImportNewsFeedModel.class);
                                           cAmount=modell.getDonatedAmount();

                                            if (cAmount!=null){
                                                Long newAmount=cAmount+donationAmount;
                                                String key=getArguments().getString("key");
                                                patientDataRef.child(currentUserId).child(key).child("DonatedAmount").setValue(newAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getContext(), "Donation Successful", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                NewsFeedFragment newsFeedFragment=new NewsFeedFragment();

                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.holder,newsFeedFragment).addToBackStack(null)
                                                        .commit();
                                                liinearLayout.setVisibility(View.GONE);
                                            }
                                            else {

                                                String key=getArguments().getString("key");
                                                patientDataRef.child(currentUserId).child(key).child("DonatedAmount").setValue(donationAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getContext(), "Donation Successful", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                NewsFeedFragment newsFeedFragment=new NewsFeedFragment();
                                                   getActivity().getSupportFragmentManager().beginTransaction()
                                                           .replace(R.id.holder,newsFeedFragment).addToBackStack(null)
                                                           .commit();
                                                liinearLayout.setVisibility(View.GONE);
                                            }


                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });

                              /* accountNoET.setText("");
                               branchNameEditText.setText("");
                               donationAmountET.setText("");*/


                           }

                       });
                   }
                      /**/



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Canceled",databaseError.toString());
            }
        });
    }

}
