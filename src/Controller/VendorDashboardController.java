package Controller;

import bll.UserOrder;
import com.jfoenix.controls.JFXButton;
import dao.StudentDao;
import dao.UserOrderDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;

public class VendorDashboardController implements Initializable, Serializable {

    @FXML
    private AnchorPane userOrderPane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnSalesDetails;

    @FXML
    private TableView<UserOrder> orderTable;

    @FXML
    private TableColumn<UserOrder, Integer> order_id;

    @FXML
    private TableColumn<UserOrder, Date> date;

    @FXML
    private TableColumn<UserOrder, Integer> user_id;

    @FXML
    private TableColumn<UserOrder, Integer> food_id;

    @FXML
    private TableColumn<UserOrder, Integer> quantity;

    @FXML
    private TableColumn<UserOrder, Integer> total_price;

    @FXML
    private TableColumn<UserOrder, String> status;

    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();

    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
//            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
//            ResultSet rs=ud.getUserOrder();
                        Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");

            ResultSet rs= cn.createStatement().executeQuery("select * from user_order");
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("food_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"))
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        food_id.setCellValueFactory(new PropertyValueFactory<>("food_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTable.setItems(oblist);


    }
}