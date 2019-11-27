package bll;

import java.io.Serializable;

public class FoodMenu implements Serializable {
    private int food_id, food_price;
    private String food_name;



    public FoodMenu(int food_id, String food_name, int food_price) {
        this.food_id=food_id;
        this.food_name=food_name;
        this.food_price=food_price;
    }

    public FoodMenu() {

    }


    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
