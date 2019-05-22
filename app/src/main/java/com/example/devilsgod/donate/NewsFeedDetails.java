package com.example.devilsgod.donate;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.donate.Model.ImportNewsFeedModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedDetails extends Fragment implements Serializable {

private ImageView vImage,document1,document2,document3;
private TextView vName,vDetails,percentageTV,totalAmountTV;
private ProgressBar progressBar;
private Button donateNow;
private ScrollView scrollView;
private int progressStatus= 0;
public Long donatedAmount;
public Long totalAmount;
private TextView donatedTV;
    private boolean zoomOut =  false;
    private ArrayList<String>docu=new ArrayList<>();
    public NewsFeedDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_feed_details, container, false);

        vImage=view.findViewById(R.id.details_vImage);
        document1=view.findViewById(R.id.documetn1Id);
        document2=view.findViewById(R.id.documetn2Id);
        document3=view.findViewById(R.id.documetn3Id);
        vName=view.findViewById(R.id.details_vname);
        vDetails=view.findViewById(R.id.details_vDetails);
        progressBar=view.findViewById(R.id.amountProgress);
        percentageTV=view.findViewById(R.id.percentageId);
        totalAmountTV=view.findViewById(R.id.totalAMountid);
        donateNow=view.findViewById(R.id.DonateNoWIdNFD);
        scrollView=view.findViewById(R.id.scrollViewId);
        donatedTV=view.findViewById(R.id.donatedAmountIdTV);
       // progressStatus=((donatedAmount*100)/totalAmount);
        progressBar.setProgress(progressStatus);
       // progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));


        loadDetails();
donateNow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DonateNowFragment donateNowFragment=new DonateNowFragment();
        Bundle bundle=new Bundle();
        String currentUser=getArguments().getString("cuId");
        String key=getArguments().getString("key");
        bundle.putString("cuID",currentUser);
        bundle.putString("key",key);
        donateNowFragment.setArguments(bundle);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction()
                .replace(R.id.donationPlaceHolder,donateNowFragment);
        ft.addToBackStack(null);
        scrollView.setVisibility(View.GONE);
        ft.commit();

    }
});
        return view;
    }
    public void loadDetails(){

                  Bundle bundle=getArguments();
        ImportNewsFeedModel model= (ImportNewsFeedModel) bundle.getSerializable("model");
        String name=model.getVictimsName();
        String details=model.getDiseaseName();
        Picasso.get().load(model.getVictimsPhoto()).into(vImage);

          donatedAmount=model.getDonatedAmount();

          totalAmount=model.getTotalAmount();
        vName.setText(name);
        vDetails.setText(details);
        totalAmountTV.setText(""+totalAmount);
        if (donatedAmount!=null){
            if (donatedAmount>0){
                progressStatus=Integer.valueOf(String.valueOf((donatedAmount*100)/totalAmount));
                percentageTV.setText(progressStatus+"%");
                donatedTV.setText(""+donatedAmount);

                if (progressStatus>80){
                    progressBar.setProgress(progressStatus);
                    //  progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                else if (progressStatus>50){
                    progressBar.setProgress(progressStatus);
                    //  progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                }
                else {
                    progressBar.setProgress(progressStatus);
                    // progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
            }
        }

        Log.e("Docu",model.getDocuments().toString());
         Picasso.get().load(model.getDocuments().get(0)).into(document1);
         Picasso.get().load(model.getDocuments().get(1)).into(document2);
         Picasso.get().load(model.getDocuments().get(2)).into(document3);

         document1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(zoomOut) {
                     document1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                     document1.setAdjustViewBounds(true);
                     zoomOut =false;
                 }else {
                     document1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                     document1.setScaleType(ImageView.ScaleType.FIT_XY);
                     zoomOut = true;
                 }
             }
         });
         document2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(zoomOut) {
                     document2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                     document2.setAdjustViewBounds(true);
                     zoomOut =false;
                 }else {
                     document2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                     document2.setScaleType(ImageView.ScaleType.FIT_XY);
                     zoomOut = true;
                 }
             }
         });


         document3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(zoomOut) {
                     document3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                     document3.setAdjustViewBounds(true);
                     zoomOut =false;
                 }else {
                     document3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                     document3.setScaleType(ImageView.ScaleType.FIT_XY);

                     zoomOut = true;
                 }
             }
         });
    }

    }

