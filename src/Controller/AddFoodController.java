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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;

import javax.sql.RowSet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ResourceBundle;

public class AddFoodController implements Initializable {

    @FXML
    private StackPane rootStackPane;

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

    @FXML
    private TextField txtSearchFoodName;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnGo;

    @FXML
    private TextArea foodPictureField;

    @FXML
    private TableColumn<FoodMenu, String> food_picture;

    int foodId;


    ObservableList<FoodMenu> oblist = FXCollections.observableArrayList();

    Alert a = new Alert(Alert.AlertType.NONE);
    FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");

    public AddFoodController() throws RemoteException, NotBoundException, MalformedURLException {
    }


    @FXML
    void btnUpdate(MouseEvent event) throws IOException, ClassNotFoundException {

        try{
            fd.updateMenu(txtFoodName.getText(),Integer.parseInt(txtFoodPrice.getText()), foodId, food_picture.getText());
            MenuTable.getItems().clear();
            loadData();

            txtFoodName.clear();
            txtFoodPrice.clear();
            foodPictureField.clear();
            foodPictureField.clear();

            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText(" Updated Successfully");
            a.show();

        }
        catch (Exception e){
            System.out.print(e);
        }




    }


    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);


    }





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

                //FoodDao sd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
                FoodMenu fm= new FoodMenu();

                fm.setPicture(foodPictureField.getText());
                fm.setFood_name(txtFoodName.getText());
                fm.setFood_price(Integer.parseInt(txtFoodPrice.getText()));

                fd.addMenu(fm);

                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Successfully Added");
                a.show();
                MenuTable.getItems().clear();
                loadData();
                txtFoodName.clear();
                txtFoodPrice.clear();
                foodPictureField.clear();
                foodPictureField.clear();
            }catch (Exception e){
                System.out.print(e);
            }
        }
    }
    @FXML
    void loadFoodMenu(KeyEvent event) {
        if (txtSearchFoodName.getText().isEmpty()) {
            MenuTable.getItems().clear();
            loadData();
        }

    }

    @FXML
    void btnDelete(MouseEvent event) {

        try {
            if(txtFoodName.getText().isEmpty() || txtFoodPrice.getText().isEmpty()){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please fill all the fields");
                a.show();
            }
            else{
                //FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
                fd.deleteMenu(txtFoodName.getText());

                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Delete Successfully");
                a.show();
                txtFoodName.clear();
                txtFoodPrice.clear();
                MenuTable.getItems().clear();
                loadData();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnSearch(ActionEvent event) {
        try{

            FoodDao sd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
            ResultSet rs= sd.getFoodByName(txtSearchFoodName.getText());

            MenuTable.getItems().clear();
            while(rs.next()){
                System.out.print(rs.getString("food_name"));
                oblist.add(new FoodMenu(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getInt("food_price"),
                        rs.getString("picture")));

            }

            if (txtSearchFoodName.getText().isEmpty()) {

                loadData();
            }
            MenuTable.setItems(oblist);



        }catch(Exception e){
            System.out.println(e);
        }

    }
    void loadData(){
        try {
           FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
           ResultSet rs=  fd.showMenu();
            while(rs.next()){
                oblist.add(new FoodMenu(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getInt("food_price"),
                        rs.getString("picture")));
            }
            food_id.setCellValueFactory(new PropertyValueFactory<>("food_id"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            food_price.setCellValueFactory(new PropertyValueFactory<>("food_price"));
            food_picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
            MenuTable.setItems(oblist);

            MenuTable.setOnMouseClicked(e ->{
                txtFoodName.setText(MenuTable.getSelectionModel().getSelectedItem().getFood_name());
                txtFoodPrice.setText(String.valueOf(MenuTable.getSelectionModel().getSelectedItem().getFood_price()));
                foodId= Integer.parseInt(String.valueOf(MenuTable.getSelectionModel().getSelectedItem().getFood_id()));
                foodPictureField.setText(String.valueOf(MenuTable.getSelectionModel().getSelectedItem().getPicture()));



            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        addFoodItemsPane.getChildren().setAll(pane);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            loadData();


    }

}
