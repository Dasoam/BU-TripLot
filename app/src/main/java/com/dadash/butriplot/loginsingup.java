package com.dadash.butriplot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginsingup extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private TextView Username,Gamil,Password,Phone;
    String username,gmail,password,phone;

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), dashboard.class));
            finish();
                }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_loginsingup);
       // Username=findViewById(R.id.username);
        Gamil=findViewById(R.id.email);
       // Phone=findViewById(R.id.phonenumber);
        Password=findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public void login(View view) {
        //username=Username.getText().toString().trim();
        gmail = Gamil.getText().toString().trim();
        password = Password.getText().toString();
        //phone=Phone.getText().toString().trim();

//        if (username.isEmpty()){
//            Username.setError("Please Enter Your Name");
    //}
         if (gmail.isEmpty() || !gmail.endsWith("@gmail.com")) {
             Gamil.setError("Please Enter Valid Gmail");
//        } else if (phone.isEmpty() || phone.length()!=10) {
//            Phone.setError("Please Enter Your Phone no.");
//            Toast.makeText(this, "Users will use this no. to contact you", Toast.LENGTH_LONG).show();
             //}
         }else if (password.length()<8) {
            Password.setError("Password Length must be 8 or more");

        } else {

            //signin
            firebaseAuth.signInWithEmailAndPassword(gmail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(getApplicationContext(), dashboard.class));
                                finish();
                                Toast.makeText(loginsingup.this,"Login Successful",
                                        Toast.LENGTH_SHORT).show();
//                                Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
//                                vibrator.vibrate(500);
                            } else {
                                // If sign in fails, display a message to the user.
                                String checkuser = task.getException().toString().trim();
                                if (checkuser.equals("com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                    Intent intent = new Intent(getApplicationContext(), register.class);
                                    //intent.putExtra("Username", username);
                                    intent.putExtra("Email", gmail);
                                    //intent.putExtra("Phone", phone);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(loginsingup.this, "New user, Please Register", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(loginsingup.this, "Email & Password Does not match", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    }

    public void forgotpassword(View view){
        EditText ResetEmail= new EditText(view.getContext());
        //String resetemail= ResetEmail.getText().toString();
        final AlertDialog.Builder resetdialog= new AlertDialog.Builder(view.getContext());
        resetdialog.setTitle("RESET PASSWORD-");
        resetdialog.setMessage("Enter Registered GmailID");
        resetdialog.setView(ResetEmail);

// Positive button is created to have more control over its behavior
        resetdialog.setPositiveButton("RESET", null);

        final AlertDialog resetDialog = resetdialog.create();
        resetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button resetButton = resetDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = ResetEmail.getText().toString().trim();

                        if (email.isEmpty()) {
                           ResetEmail.setError("Field cannot be empty");
                        } else {
                            firebaseAuth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(loginsingup.this, "Reset Link Sent to Registered Mail",
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(loginsingup.this, "User does not exist or is no longer valid",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            resetDialog.dismiss(); // Dismiss the dialog after processing
                                        }
                                    });
                        }
                    }
                });
            }
        });

//        resetdialog.setTitle("RESET PASSWORD-");
//        resetdialog.setMessage("Enter Registered GmailID");
//        resetdialog.setView(ResetEmail);
//        resetdialog.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //if (resetemail==null) {
//                    firebaseAuth.sendPasswordResetEmail(ResetEmail.getText().toString().trim()).
//                            addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(loginsingup.this, "Password reset link has been sent to your GmailID.", Toast.LENGTH_SHORT).show();
////                                    startActivity(new Intent(getApplicationContext(),loginsingup.class));
////                                    finish();
//                                    } else {
//                                        Toast.makeText(loginsingup.this, "User Does not exist", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//               // }else {
//                    //ResetEmail.setError("Enter Valid GmailID");
//                    Toast.makeText(loginsingup.this, "Please Enter GmailID", Toast.LENGTH_SHORT).show();
//               // }
//            }
//        });
        resetDialog.show();
    }
}

