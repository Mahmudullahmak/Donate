package com.example.devilsgod.donate;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devilsgod.donate.Adapter.AdminAdapter;
import com.example.devilsgod.donate.Model.ImportAdminModel;
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
public class AdminApprovalFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference patientDataRef;
    private RecyclerView adminRecycler;
    FirebaseAuth mAuth;
    public AdminAdapter adapter;
    private List<ImportAdminModel> items = new ArrayList<ImportAdminModel>();
    private String name;
    private String desc;
    private String image;
    private Context context;
    private TextView goHome;

    public AdminApprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_approval, container, false);
        mAuth = FirebaseAuth.getInstance();
         goHome=view.findViewById(R.id.goHomeId);
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("rootDataRef");
        patientDataRef = rootRef.child("PatientData");


        adminRecycler = view.findViewById(R.id.adminRecyclerId);
        adapter=new AdminAdapter(items,getActivity().getApplicationContext());
        LinearLayoutManager glm = new LinearLayoutManager(context);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        adminRecycler.setLayoutManager(glm);
        adminRecycler.setAdapter(adapter);
        recyclerData();
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
    private void recyclerData(){
        items.clear();
        patientDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    final String currentUserId=d.getKey();
                    patientDataRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dd:dataSnapshot.getChildren()){
                                String userIds=dd.getKey();
                                ImportAdminModel model=dd.getValue(ImportAdminModel.class);
                                name=model.getVictimsName();
                                desc=model.getDiseaseName();
                                image=model.getVictimsPhoto();
                                items.add(new ImportAdminModel(image,name,desc,currentUserId,userIds));
                                adapter.notifyDataSetChanged();


                            }
                        }

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
