package com.example.devilsgod.donate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devilsgod.donate.Model.ImportNewsFeedModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ImportNewsViewHolder>  {
    public List<ImportNewsFeedModel> newsRecyclerList;
    Context context;
   private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;

    public class ImportNewsViewHolder extends RecyclerView.ViewHolder {
        public TextView vNameTV,vDiseaseTV,amountTV;
        ImageView vImage;





        public ImportNewsViewHolder(View view) {
            super(view);
            vNameTV=view.findViewById(R.id.news_row_nameText);
            vDiseaseTV=view.findViewById(R.id.news_rowDiseaseDetails);
            vImage=view.findViewById(R.id.news_row_ImageView);
            relativeLayout=view.findViewById(R.id.news_relativeId);
            amountTV=view.findViewById(R.id.news_row_amountId);
            recyclerView=view.findViewById(R.id.newsFeedRecyclerId);




        }
    }
    public NewsFeedAdapter(List<ImportNewsFeedModel> newsRecyclerList, Context context) {
        this.newsRecyclerList = newsRecyclerList;
        this.context = context;

    }
    @NonNull
    @Override
    public ImportNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_single_row, parent, false);

        return new ImportNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImportNewsViewHolder holder, final int position) {
        ImportNewsFeedModel recycleList = newsRecyclerList.get(position);
        holder.vNameTV.setText(recycleList.getVictimsName());
        holder.vDiseaseTV.setText(recycleList.getDiseaseName());
        Picasso.get().load(recycleList.getVictimsPhoto()).into(holder.vImage);
        holder.amountTV.setText(""+recycleList.getAmount());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportNewsFeedModel recycleList = newsRecyclerList.get(position);
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                NewsFeedDetails myFragment = new NewsFeedDetails();
                Bundle argss=new Bundle();
            /*    argss.putString("name",recycleList.getVictimsName());
                argss.putString("desc",recycleList.getDiseaseName());
                argss.putString("image",recycleList.getVictimsPhoto());
                argss.putLong("amount",recycleList.getAmount());
                argss.putLong("phnn",recycleList.getPhoneNo());
                argss.putStringArrayList("documents",(ArrayList<String>)recycleList.getDocuments());*/
             argss.putSerializable("model",recycleList);
             argss.putString("cuId",recycleList.getCurrentUserId());
             argss.putString("key",recycleList.getKey());
                myFragment.setArguments(argss);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.placeHolderId, myFragment).addToBackStack(null).commit();


            }

        });

    }

    @Override
    public int getItemCount() {
        return newsRecyclerList.size();
    }


}
