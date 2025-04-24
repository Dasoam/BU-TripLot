package com.dadash.butriplot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class register extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;
    private EditText Enterpassword,Confirmpassword,EnrollmentNo,Uniquecode,Username,Phone;
     private String enterpassword,confirmpassword,enrollmentno,uniquecode,username,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        Enterpassword=findViewById(R.id.setpassword);
        Confirmpassword=findViewById(R.id.confirmpassword);
        EnrollmentNo=findViewById(R.id.enrollID);
        Uniquecode=findViewById(R.id.unicode);
        Username=findViewById(R.id.username);
        Phone=findViewById(R.id.phonenumber);
    }
    public void register(View view){
        Intent intent=getIntent();
        //String username=intent.getStringExtra("Username");
        String gmail=intent.getStringExtra("Email");
       // String phone=intent.getStringExtra("Phone");

        enterpassword=Enterpassword.getText().toString().trim();
        confirmpassword=Confirmpassword.getText().toString().trim();
        enrollmentno=EnrollmentNo.getText().toString().trim().toLowerCase();
        uniquecode=Uniquecode.getText().toString().trim();
        username=Username.getText().toString().trim();
        phone=Phone.getText().toString().trim();


        StudentDetails stdt=new StudentDetails();
        stdt.studentenrollmentnumber22(enrollmentno);
        String checkenroll=stdt.tf;
        //String uniquecodecheck=stdt.utf;

        if (enterpassword.equals(confirmpassword) && enterpassword.length()>=8 && !enrollmentno.isEmpty()
                && !enrollmentno.equals(null) && checkenroll.equals("True") &&
        !username.isEmpty() && !phone.isEmpty() && phone.length()==10){
            firebaseAuth.createUserWithEmailAndPassword(gmail,confirmpassword).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(register.this, "Welcome "+username, Toast.LENGTH_LONG).show();
//                                Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
//                                vibrator.vibrate(500);
                                startActivity(new Intent(getApplicationContext(),dashboard.class));
                                finish();

                                FirebaseUser currentuser=firebaseAuth.getCurrentUser();
                                currentuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(register.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                HashMap<String,String> userregisterdata=new HashMap<>();
                                userregisterdata.put("Name",username);
                                userregisterdata.put("Phone Number",phone);
                                userregisterdata.put("Email",gmail);
                                userregisterdata.put("Password",confirmpassword);
                                userregisterdata.put("College EmailID",enrollmentno);
                                //one way to save data to firestore
                                database.document("Users/"+currentuser.getEmail()).set(userregisterdata);
                                //second way
//                                DocumentReference documentReference=database.collection("Users").document(currentuser.getEmail());
//                                documentReference.set(userregisterdata).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d("Userdata of %s is created",currentuser.getUid());
//                                    }
//                                });
                            }
                            else {
                                Toast.makeText(register.this, "Please Register Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            if (enterpassword.length()<8){
                Enterpassword.setError("Password must be 8 or more character long");
            } else if (enrollmentno==null || enrollmentno.isEmpty() || enrollmentno.length()!=26) {
                EnrollmentNo.setError("Enter Correct College EmailID");
            } else if (checkenroll.equals("False")
                    || checkenroll.equals(null)) {
                EnrollmentNo.setError("Enter Correct College MailID");
//            } else if (uniquecodecheck.equals("False")) {
//                Uniquecode.setError("Please Enter valid Code");
            } else if (username.isEmpty()) {
                Username.setError("Please Enter your Name");
            } else if (phone.isEmpty()) {
                Phone.setError("Please Enter Your Mobile No.");
            } else if (phone.length()!=10) {
                Phone.setError("Please Enter valid Indian Mobile No.");
                Toast.makeText(this, "Users will use this no. to contact you", Toast.LENGTH_LONG).show();
            } else {
                Confirmpassword.setError("Password does not match");
            }
        }
    }
}
