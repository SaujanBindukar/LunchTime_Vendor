package bll;

import java.io.Serializable;

public class Vendor  implements Serializable {
    private  int vendor_id;
    private String vendor_email;
    private String vendor_name;
    private String picture;
    private String vendor_number;

    public String getVendor_number() {
        return vendor_number;
    }

    public void setVendor_number(String vendor_number) {
        this.vendor_number = vendor_number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_email() {
        return vendor_email;
    }

    public void setVendor_email(String vendor_email) {
        this.vendor_email = vendor_email;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }
}
