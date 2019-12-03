package bll;



import java.sql.Timestamp;
import java.util.Date;

public class UserOrder implements java.io.Serializable {
    public int order_id,quantity, total_price;
    public Date date;
    public String user_name, food_name, status;
    public static final long serialVersionUID =2L;

    public UserOrder(int order_id,int quantity, int total_price,
                     String status, Date date, String user_name, String food_name
    ) {
        this.order_id = order_id;
        this.date = date;
        this.user_name= user_name;
        this.food_name= food_name;
        this.quantity = quantity;
        this.total_price = total_price;
        this.status = status;




    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
