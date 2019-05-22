package com.example.devilsgod.donate.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.ImportAdminModel;
import com.example.devilsgod.donate.NewsFeedFragment;
import com.example.devilsgod.donate.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ImportAdminViewHolder> {
    public List<ImportAdminModel> adminRecycler_list;
    Context context;

    public class ImportAdminViewHolder extends RecyclerView.ViewHolder {
        public TextView vNameTV,vDiseaseTV;
        ImageView vImage;
        Button approveBtn;
        Button removeBtn;
         LinearLayout relativeLayout;




        public ImportAdminViewHolder(View view) {
            super(view);
            vNameTV=view.findViewById(R.id.single_row_nameText);
            vDiseaseTV=view.findViewById(R.id.single_rowDiseaseDetails);
            vImage=view.findViewById(R.id.single_row_ImageView);
            approveBtn=view.findViewById(R.id.approveId);
            removeBtn=view.findViewById(R.id.removeBtnId);
            relativeLayout=view.findViewById(R.id.relativeId);


        }
    }
    public AdminAdapter(List<ImportAdminModel> adminRecycler_list, Context context) {
        this.adminRecycler_list = adminRecycler_list;
        this.context = context;

    }
    @NonNull
    @Override
    public ImportAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_single_row, parent, false);

        return new ImportAdminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImportAdminViewHolder holder, int position) {
        final ImportAdminModel recycleList = adminRecycler_list.get(position);
        if (recycleList.getApproval()!=null){
            holder.approveBtn.setText("Aprroved");

            holder.vNameTV.setText(recycleList.getVictimsName());
            holder.vDiseaseTV.setText(recycleList.getDiseaseName());
            Picasso.get().load(recycleList.getVictimsPhoto()).into(holder.vImage);

            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentUserId=recycleList.getCurrentUserId();
                    String uKey=recycleList.getUkey();
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference  rootRef = database.getReference("rootDataRef");
                    DatabaseReference patientDataRef = rootRef.child("PatientData");
                    patientDataRef.child(currentUserId).child(uKey).removeValue();

                    holder.removeBtn.setText("Removed");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            holder.relativeLayout.setVisibility(View.GONE);
                        }
                    }, 2000);


                }
            });
            holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Already Approved", Toast.LENGTH_SHORT).show();
                }
            });
            holder.approveBtn.setText("Approved");
        }
        else {
            holder.vNameTV.setText(recycleList.getVictimsName());
            holder.vDiseaseTV.setText(recycleList.getDiseaseName());
            Picasso.get().load(recycleList.getVictimsPhoto()).into(holder.vImage);
            holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NewsFeedFragment newsFeedFragment=new NewsFeedFragment();
              /*  Bundle bundle=new Bundle();
                bundle.putString("approved","Approved");
                bundle.putString("current",recycleList.getCurrentUserId());
                bundle.putString("uKey",recycleList.getUkey());
                newsFeedFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.adminPlaceHolder,newsFeedFragment).addToBackStack(null).commit();*/
                    String currentUserId=recycleList.getCurrentUserId();
                    String uKey=recycleList.getUkey();
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference  rootRef = database.getReference("rootDataRef");
                    DatabaseReference patientDataRef = rootRef.child("PatientData");
                    patientDataRef.child(recycleList.getCurrentUserId()).child(recycleList.getUkey()).child("approval").setValue("Approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("aprroval","Added");
                        }
                    });
                    holder.approveBtn.setText("Aprroved");
                    if (recycleList.getApproval()!=null){
                        Toast.makeText(context, "Already Approved", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentUserId=recycleList.getCurrentUserId();
                    String uKey=recycleList.getUkey();
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference  rootRef = database.getReference("rootDataRef");
                    DatabaseReference patientDataRef = rootRef.child("PatientData");
                    patientDataRef.child(currentUserId).child(uKey).removeValue();

                    holder.removeBtn.setText("Removed");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            holder.relativeLayout.setVisibility(View.GONE);
                        }
                    }, 2000);


                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return adminRecycler_list.size();
    }


}
