package com.dadash.butriplot;

public class RetriveTripLot {
    String Addrress, Name, Phone_Number, Date, Vehicle, Time;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    boolean expanded;

    RetriveTripLot(){
            
    }

    public RetriveTripLot(String Addrress, String Name, String Phone_Number, String date, String vehicle, String Time) {
        this.Addrress = Addrress;
        this.Name = Name;
        this.Phone_Number = Phone_Number;
        this.Date = Date;
        this.Vehicle = vehicle;
        this.Time = Time;
        this.expanded=false;
    }

    public String getAddrress() {
        return Addrress;
    }

    public void setAddrress(String addrress) {
        this.Addrress = addrress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.Phone_Number = phone_Number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}
