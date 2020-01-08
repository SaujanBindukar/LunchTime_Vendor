/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */

package bll;
import java.util.Date;

public class UserOrder implements java.io.Serializable {
    //private data member
    public int order_id,quantity, total_price;
    public Date date;
    public String user_name, food_name, status;
    private static final long serialVersionUID = -7273230871957691871L;

    // constructor with parameters
    public UserOrder(Date date, int total_price) {
        this.date=date;
        this.total_price=total_price;
    }
    // constructor with parameters
    public UserOrder(int order_id,int quantity, int total_price,
                     String status, Date date, String user_name, String food_name) {
        this.order_id = order_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.status = status;
        this.date = date;
        this.user_name= user_name;
        this.food_name= food_name;
    }
    // getter method for order_id
    public int getOrder_id() {
        return order_id;
    }

    // setter method for order_id
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    // getter method for quantity
    public int getQuantity() {
        return quantity;
    }

    // setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // getter method for total_price
    public int getTotal_price() {
        return total_price;
    }

    // setter method for total_price
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    // getter method for date
    public Date getDate() {
        return date;
    }

    // setter method for date
    public void setDate(Date date) {
        this.date = date;
    }

    // getter method for user_name
    public String getUser_name() {
        return user_name;
    }

    // setter method for user_name
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    // getter method for food_name
    public String getFood_name() {
        return food_name;
    }

    // setter method for food_name
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    // getter method for status
    public String getStatus() {
        return status;
    }

    // setter method for status
    public void setStatus(String status) {
        this.status = status;
    }


}
