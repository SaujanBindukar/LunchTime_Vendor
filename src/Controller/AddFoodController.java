package Controller;

import bll.FoodMenu;
import com.jfoenix.controls.JFXButton;
import dao.FoodDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddFoodController implements Initializable {

    @FXML
    private AnchorPane addFoodItemsPane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnSalesDetails;

    @FXML
    private TextField txtFoodName;

    @FXML
    private TextField txtFoodPrice;

    @FXML
    private Button btnSubmitMenu;

    @FXML
    private TableView<FoodMenu> MenuTable;

    @FXML
    private TableColumn<FoodMenu, Integer> food_id;

    @FXML
    private TableColumn<FoodMenu, String> food_name;

    @FXML
    private TableColumn<FoodMenu, Integer> food_price;


    ObservableList<FoodMenu> oblist = FXCollections.observableArrayList();

    Alert a = new Alert(Alert.AlertType.NONE);




    @FXML
    void btnSubmitMenu(ActionEvent event) {
        String foodName=txtFoodName.getText();
        String foodPrice=txtFoodPrice.getText();
        System.out.println(foodName);
        System.out.println(foodPrice);
        if (txtFoodName.getText().isEmpty() || txtFoodPrice.getText().isEmpty()) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill all the field");
            a.show();

        }
        else{
            try{

                FoodDao sd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
                FoodMenu fm= new FoodMenu();
                fm.setFood_name(txtFoodName.getText());
                fm.setFood_price(Integer.parseInt(txtFoodPrice.getText()));
                sd.addMenu(fm);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Successfully Added");
                a.show();
                loadData();

                txtFoodName.clear();
                txtFoodPrice.clear();


            }catch (Exception e){
                System.out.print(e);

            }

        }

    }
    void loadData(){
        try {
//            FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
//            ResultSet rs=fd.showMenu();
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
            ResultSet rs= cn.createStatement().executeQuery("select * from menu");
            while(rs.next()){
                oblist.add(new FoodMenu(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getInt("food_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.print("Add food button is pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.print("Add food button is pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        food_id.setCellValueFactory(new PropertyValueFactory<>("food_id"));
        food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
        food_price.setCellValueFactory(new PropertyValueFactory<>("food_price"));
        MenuTable.setItems(oblist);


    }
}
