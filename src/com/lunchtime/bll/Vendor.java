/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */

package com.lunchtime.bll;
import java.io.Serializable;

public class Vendor  implements Serializable {
    //private member data
    private  int vendor_id;
    private String vendor_email;
    private String vendor_name;
    private String picture;
    private String vendor_number;

    // getter method for vendor_number
    public String getVendor_number() {
        return vendor_number;
    }

    // setter method for vendor_number
    public void setVendor_number(String vendor_number) {
        this.vendor_number = vendor_number;
    }

    // getter method for picture
    public String getPicture() {
        return picture;
    }

    // setter method for picture
    public void setPicture(String picture) {
        this.picture = picture;
    }

    // getter method for vendor_id
    public int getVendor_id() {
        return vendor_id;
    }

    // setter method for vendor_id
    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    // getter method for vendor_email
    public String getVendor_email() {
        return vendor_email;
    }

    // setter method for vendor_email
    public void setVendor_email(String vendor_email) {
        this.vendor_email = vendor_email;
    }

    // getter method for vendor_name
    public String getVendor_name() {
        return vendor_name;
    }

    // setter method for vendor_name
    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }
}
