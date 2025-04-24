package com.dadash.butriplot;

public class StudentDetails {
   String tf="False";
public void studentenrollmentnumber22(String enrollmentnumber) {
    try {
        if ((enrollmentnumber.contains("20") || enrollmentnumber.contains("21")
                || enrollmentnumber.contains("22") || enrollmentnumber.contains("23"))
        && enrollmentnumber.endsWith("@bennett.edu.in")) {
            tf = "True";
        } else tf = "False";
    }catch (Exception e){
       // Log.i("Check College EmailID",e.getMessage());
     }
    }
}
