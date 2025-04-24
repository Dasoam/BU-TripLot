package com.dadash.butriplot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recdata;
    ArrayList<RetriveTripLot> retriveTripLotArrayList;
    Adapter adapter;
    private FirebaseFirestore database;
    private String datetime;

    private AdView adView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recdata=(RecyclerView) view.findViewById(R.id.recdata);


        recdata.setLayoutManager(new LinearLayoutManager(getContext()));


        database= FirebaseFirestore.getInstance();


        retriveTripLotArrayList =new ArrayList<RetriveTripLot>();


        adapter= new Adapter(getContext(),retriveTripLotArrayList);
        recdata.setAdapter(adapter);
        recviewdata();
        return view;

    }


    public void onStart(){
        super.onStart();
        Intent intent=getActivity().getIntent();
        datetime=intent.getStringExtra("Date&Time");


        //ads
        MobileAds.initialize(this.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView =(AdView) getActivity().findViewById(R.id.dashboardadd);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        //recviewdata();
    }
    public void recviewdata(){
        //used for where condition
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        database.collection("Posted TripLot")
                .orderBy("Date",Query.Direction.ASCENDING)
                .orderBy("Time", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("Date",currentDateandTime)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    //Log.i("firestore",error.getMessage());
                    return;
                }
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    switch (documentChange.getType()) {
                        case ADDED:
                            retriveTripLotArrayList.add(documentChange.getDocument().toObject(RetriveTripLot.class));
                           // Log.d("TAG", "New Msg: " + documentChange.getDocument().toObject(RetriveTripLot.class));
                            break;
                        case MODIFIED:
                            retriveTripLotArrayList.add(documentChange.getDocument().toObject(RetriveTripLot.class));
                           // Log.d("TAG", "Modified Msg: " + documentChange.getDocument().toObject(RetriveTripLot.class));
                            break;
                        case REMOVED:
                            retriveTripLotArrayList.remove(documentChange.getDocument().toObject(RetriveTripLot.class));
                           // Log.d("TAG", "Removed Msg: " + documentChange.getDocument().toObject(RetriveTripLot.class));
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}