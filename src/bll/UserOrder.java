package bll;

import java.io.Serializable;
import java.util.Date;

public class UserOrder implements Serializable {
    public int order_id,user_id,food_id,quantity, total_price;
    public String status;
    public Date date;
    private static final long serialVersionUID =1L;

    public UserOrder(int order_id, int user_id, int food_id, int quantity, int total_price, String status, Date date) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.status = status;
        this.date = date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
