package Models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User implements Serializable {

    String Fname, Lname, Location, PhoneNo,Email, RegistrationDate;

    public User(String fname, String lname, String phoneNo, String email) {
        Fname = fname;
        Lname = lname;
        PhoneNo = phoneNo;
        Email = email;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(calendar.getTime());
        RegistrationDate=todayDate;
    }
    public User(){

    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "Fname='" + Fname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", Location='" + Location + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", Email='" + Email + '\'' +
                ", RegistrationDate='" + RegistrationDate + '\'' +
                '}';
    }
}
