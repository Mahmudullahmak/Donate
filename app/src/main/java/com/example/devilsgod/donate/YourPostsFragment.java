package com.example.devilsgod.donate;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.ImportAdminModel;
import com.example.devilsgod.donate.Model.ImportYourPostsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourPostsFragment extends Fragment {
    private RecyclerView yourRecycler;
 DatabaseReference rootRef;
 DatabaseReference childRef;
 FirebaseDatabase database;
 FirebaseAuth mAuth;
 private String currentUserId;
 private String noPost="You have not posted yet";
    public YourPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_your_posts, container, false);
          mAuth=FirebaseAuth.getInstance();
          currentUserId=mAuth.getCurrentUser().getUid().toString();
          database=FirebaseDatabase.getInstance();
          rootRef=database.getReference("rootDataRef");
          childRef=rootRef.child("PatientData").child(currentUserId);
          yourRecycler=view.findViewById(R.id.yourRecyclerId);
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        yourRecycler.setLayoutManager(llm);






        FirebaseRecyclerOptions<ImportYourPostsModel> options=
                new FirebaseRecyclerOptions.Builder<ImportYourPostsModel>()
                        .setQuery(childRef,ImportYourPostsModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportYourPostsModel,ItemViewHolder> adapter
                =new FirebaseRecyclerAdapter<ImportYourPostsModel,ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ItemViewHolder holder, int position, @NonNull ImportYourPostsModel model) {

                final String name =model.getVictimsName();
                final String disease =model.getDiseaseName();
                Picasso.get().load(model.getVictimsPhoto()).into(holder.patientImage);
                holder.vName.setText(name);
                holder.vDisease.setText(disease);
                holder.vNeededAmount.setText(""+model.getAmount());
                holder.vDonatedAmount.setText(""+model.getDonatedAmount());

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final CharSequence[] items = {"Delete"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    childRef.child(getRef(which).getKey()).removeValue();
                                    Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                });

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.your_posts_single_row,parent,false);
                ItemViewHolder holder=new ItemViewHolder(v);
                return holder;
            }
        };

        yourRecycler.setAdapter(adapter);
        adapter.startListening();
        return view;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView vName,vDisease,vDonatedAmount,vNeededAmount;
        ImageView patientImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vName=itemView.findViewById(R.id.your_row_nameText);
            vDisease=itemView.findViewById(R.id.your_rowDiseaseDetails);
            vDonatedAmount=itemView.findViewById(R.id.your_row_donatedAmountTVId);
            vNeededAmount=itemView.findViewById(R.id.your_needed_amountTVId);
            patientImage=itemView.findViewById(R.id.your_row_ImageView);
        }
    }
}
