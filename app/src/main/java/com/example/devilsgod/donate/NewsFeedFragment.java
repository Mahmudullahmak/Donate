package com.example.devilsgod.donate;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.ImportNewsFeedModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference patientDataRef;
    private RecyclerView newsRecycler;
    FirebaseAuth mAuth;
   private ArrayList<String>docu=new ArrayList<>();
    public NewsFeedAdapter adapter;
    private List<ImportNewsFeedModel> items = new ArrayList<ImportNewsFeedModel>();
    private String name;
    private String desc;
    private String image;
    private long phnNo;
    Long donatedAmount;
    private long amount;
    String approve;
    private Context context;
    private  String currentUserId;
    private TextView notAvailable;
    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_feed, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("rootDataRef");
        patientDataRef = rootRef.child("PatientData");
        notAvailable=view.findViewById(R.id.notAvailableId);
            newsRecycler = view.findViewById(R.id.newsFeedRecyclerId);
            adapter=new NewsFeedAdapter(items,getActivity().getApplicationContext());
            LinearLayoutManager glm = new LinearLayoutManager(context);
            glm.setOrientation(LinearLayoutManager.VERTICAL);
            newsRecycler.setLayoutManager(glm);
            newsRecycler.setAdapter(adapter);
            recyclerData();


       return  view;
    }
    private void recyclerData(){
        docu.clear();
        items.clear();
        patientDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    currentUserId=d.getKey();
                    patientDataRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dd:dataSnapshot.getChildren()){
                                docu.clear();
                                String userIds=dd.getKey();
                                Log.e("ddKeys",userIds);

                                ImportNewsFeedModel model=dd.getValue(ImportNewsFeedModel.class);

                             String approval=model.getApproval();
                             if (approval!=null){
                                 name=model.getVictimsName();
                                 desc=model.getDiseaseName();
                                 image=model.getVictimsPhoto();
                                 phnNo=model.getPhoneNo();

                                 donatedAmount=model.getDonatedAmount();
                                 patientDataRef.child(currentUserId).child(userIds).child("DonatedAmount").setValue(donatedAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         // Toast.makeText(getContext(), "Succedd"+donatedAmount, Toast.LENGTH_SHORT).show();

                                     }
                                 });

                                 amount=model.getAmount();
                                 if (donatedAmount != null){
                                     Long amnt= amount-donatedAmount;
                                     //  Log.e("documents",docu.toString());

                                     items.add(new ImportNewsFeedModel(image,name,desc,phnNo,amnt, (ArrayList<String>) dd.child("documents").getValue(),currentUserId,userIds,amount,donatedAmount));
                                     adapter.notifyDataSetChanged();
                                     docu.clear();
                                 }
                                 else {
                                     items.add(new ImportNewsFeedModel(image,name,desc,phnNo,amount, (ArrayList<String>) dd.child("documents").getValue(),currentUserId,userIds,amount,donatedAmount));
                                     adapter.notifyDataSetChanged();
                                     docu.clear();
                                 }
                             }
                             else {

                                 notAvailable.setText("Nothing is Available Right Now");
                             }

                                }


                            }
                     //   }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
