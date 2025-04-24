package com.dadash.butriplot;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dadash.butriplot.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.home){
                replacefragment( new HomeFragment());
            }
            else if(item.getItemId()==R.id.profile){
                replacefragment( new ProfileFragment());
            }
            else if(item.getItemId()==R.id.addnew){
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.getCurrentUser().reload();
                firebaseUser =firebaseAuth.getCurrentUser();
                if (firebaseUser.isEmailVerified()) {
                    startActivity(new Intent(getApplicationContext(), addrequest.class));
                }else {
                    Toast.makeText(this, "Please Verify your email to post TripLot", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        });
    }
    private  void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}