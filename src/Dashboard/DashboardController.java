package Dashboard;

import bll.FoodMenu;
import dao.FoodDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;


public class DashboardController {

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

    ObservableList<FoodMenu> data=FXCollections.observableArrayList();
    Alert a = new Alert(Alert.AlertType.NONE);



    @FXML
    void btnSubmitMenu(ActionEvent event) throws RemoteException, NotBoundException {
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

                txtFoodName.clear();
                txtFoodPrice.clear();

            }catch (Exception e){
                System.out.print(e);

            }

        }




    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloStudent");
//            ResultSet rs=fd.showMenu();
//            while (rs.next()) {
//               // data.add(new FoodMenu(rs.getInt("food_id"),rs.getString("food_name"),rs.getInt("food_price")));
//
//             }
//
//
//            food_id.setCellValueFactory(new PropertyValueFactory<>("food_id"));
//            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
//            food_price.setCellValueFactory(new PropertyValueFactory<>("food_price"));
//            MenuTable.setItems(data);
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        food_id.setCellValueFactory(new PropertyValueFactory<>("food_id"));
//        food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
//        food_price.setCellValueFactory(new PropertyValueFactory<>("food_price"));
//
//
//    }
}