package com.dadash.butriplot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class addrequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener{
    private Spinner spinner;
    private Button timebtn,datebtn;
    private int minute,hour;
    private EditText addrress,phonen,otherv;
    private String Addrress,Phonen,Otherv;
    private String Name,Phonenumber;
    private String Time,DATE;
    private Button postbtn;
    private String currentdatetime;
    private String hourminute24format;
    private String currentTime,currentDate;
    private int compareToPost;
    private AdView adView,adView2;
   private FirebaseFirestore database;
   private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrequest);


        // one way to change title bar is fro manifest and second way is from java.
//        getActionBar().setTitle("Add ride share request");
        //currently manifest type is enabled

        //Declaration of various objects
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Vehicles, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        datebtn=findViewById(R.id.datebutton);
        timebtn=findViewById(R.id.timebutton);
        postbtn=findViewById(R.id.Save);
        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        //declaration
        addrress=findViewById(R.id.place);
        phonen=findViewById(R.id.contactnumber);
        otherv=findViewById(R.id.othervehicle);

        //function
        retriveuserdata();
        showads();
    }
    //Spinner Code
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String vehiclename= spinner.getSelectedItem().toString();
        if (vehiclename.equals("Other")){
        TextView textView= findViewById(R.id.other);
        textView.setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.othervehicle);
        editText.setVisibility(View.VISIBLE);
        Otherv=otherv.getText().toString();
        }
        else{
            TextView textView= findViewById(R.id.other);
            textView.setVisibility(View.GONE);
            EditText editText = findViewById(R.id.othervehicle);
            editText.setVisibility(View.GONE);
            Otherv=vehiclename;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //Calender date Pick
    public void datesele(View view){
        datepicker();
    }
    private void datepicker(){
        //1 for min date
        Calendar calender=Calendar.getInstance();
        //2 for max date
        Calendar calender2=Calendar.getInstance();
        calender2.add(Calendar.DATE,10);
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calender2.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Year, int Month, int Date) {
        Month=Month+1;
        String month=""+Month;
        if (month.length()==1){
            month="0"+Month;
        } else{
            month=""+Month;
        }
        datebtn.setText(Date+"-"+month+"-"+Year);
        DATE=datebtn.getText().toString();
    }

    //Clock time pick
    public void timesele(View view){
        timepicker();
    }
    private void timepicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                minute=selectedMinute;
                hour=selectedHour;

                //limit set
                String M24f=""+minute;
                String H24f=""+hour;
                if (M24f.length()==1) {M24f="0"+minute;}
                else M24f=""+minute;

                if (H24f.length()==1){ H24f="0"+hour;}
                else H24f=""+hour;
                hourminute24format=H24f+":"+M24f;

                //timebuttubsettext

                String ampm="";
                if (hour>12) {hour=hour-12;  ampm="PM";}
                else if (hour==0) {hour=12;  ampm="AM";}
                else if (hour==12) {hour=hour; ampm="PM";}
                else  {hour=hour; ampm="AM";}

                String Hour=""+hour;
                String Minute=""+minute;

                if (Hour.length()==1) Hour="0"+hour;
                else  Hour=""+hour;

                if (Minute.length()==1) Minute="0"+minute;
                else  Minute=""+minute;


                timebtn.setText(Hour+":"+Minute+" "+ampm);
                Time=timebtn.getText().toString();

            }
        };
        //int style= AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,onTimeSetListener,hour,minute,false);
        timePickerDialog.setTitle("Select Time To Leave");
        timePickerDialog.show();
    }
    public void postTripLot(View view){
        Addrress=addrress.getText().toString().trim();
        Phonen=phonen.getText().toString();
        Calendar calendar3= Calendar.getInstance();
        currentdatetime= String.valueOf(calendar3.getTimeInMillis());// user for doucment name so that no user can post
        //2 times at a time and no data is modified.

        //for verification


        SimpleDateFormat sdft=new SimpleDateFormat("HH:MM ");
        currentTime=sdft.format(new Date());
        SimpleDateFormat sdftd=new SimpleDateFormat("dd-MM-yyyy");
        currentDate=sdftd.format(new Date());
//        Date currentTime1 = Calendar.getInstance().getTime();
//        System.out.println(currentTime1);
        DateFormat df = new SimpleDateFormat("h:mm a");
        String timetostore = df.format(Calendar.getInstance().getTime());
        if (currentDate.equals(DATE)){
            if (hourminute24format!= null){ compareToPost=currentTime.compareTo(hourminute24format);}
            else {compareToPost=-1;}}
        else compareToPost=-1;

        if (Otherv.equals("")){
            Otherv=otherv.getText().toString();
        }
        if (!Addrress.isEmpty() && !Phonen.isEmpty() && (DATE!=null) && (Time!=null) ) {
            if (compareToPost < 0) {
                DocumentReference documentReference = database.collection("Posted TripLot").document("By " + auth.getCurrentUser().getEmail() +" At " + currentdatetime);
                LinkedHashMap<String, String> triplot = new LinkedHashMap<>();
                triplot.put("Addrress", Addrress);
                triplot.put("Name", Name);
                triplot.put("Phone_Number", Phonen);
                triplot.put("Vehicle", Otherv);
                triplot.put("Date", DATE);
                triplot.put("Time", Time);
                documentReference.set(triplot).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addrequest.this, "Your TripLot Is Live....", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                intent.putExtra("Date&Time", DATE + " -- " + Time);
//                            Intent intent2=new Intent(getApplicationContext(), dashboard.class);
//                            intent2.putExtra("username",Name);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                });
                //Second Database
                DocumentReference documentReference1 = database.collection("All TripLots").document("By " + auth.getCurrentUser().getEmail()+" On "+currentDate+" At "+timetostore+" , "+ currentdatetime);
                LinkedHashMap<String, String> triplot1 = new LinkedHashMap<>();
                triplot1.put("Addrress", Addrress);
                triplot1.put("Name", Name);
                triplot1.put("Phone_Number", Phonen);
                triplot1.put("Vehicle", Otherv);
                triplot1.put("Date", DATE);
                triplot1.put("Time", Time);
                triplot1.put("Posted On",currentDate);
                triplot1.put("Post At",timetostore);
                documentReference1.set(triplot1);
            }
            else Toast.makeText(this, "Please Enter Correct Time", Toast.LENGTH_SHORT).show();;
        }
        else {
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_LONG).show();
        }
    }
    public void retriveuserdata(){
        DocumentReference documentReference=database.collection("Users").document(auth.getCurrentUser().getEmail());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Phonenumber=documentSnapshot.getString("Phone Number");
                Name=documentSnapshot.getString("Name");
                phonen.setText(Phonenumber);
            }
        });
    }

     public void showads(){
         MobileAds.initialize(this, new OnInitializationCompleteListener() {
             @Override
             public void onInitializationComplete(InitializationStatus initializationStatus) {
             }
         });
         adView =(AdView)findViewById(R.id.addrequestad1);
         AdRequest adRequest = new AdRequest.Builder().build();
         adView.loadAd(adRequest);
         adView2 =(AdView)findViewById(R.id.addrequestad2);
         AdRequest adRequest2 = new AdRequest.Builder().build();
         adView2.loadAd(adRequest2);
     }
}