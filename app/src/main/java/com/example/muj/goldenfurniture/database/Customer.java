package com.example.muj.goldenfurniture.database;

/**
 * Created by MUJ on 09-Mar-18.
 */

public class Customer
{
    String customer_id,phone;
    String email,customer_name, photo_id;

    public Customer() {
    }

    public Customer(String customer_id, String phone, String email, String customer_name, String photo_id)
    {
        this.customer_id = customer_id;
        this.phone = phone;
        this.email = email;
        this.customer_name = customer_name;
        this.photo_id = photo_id;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }
}
