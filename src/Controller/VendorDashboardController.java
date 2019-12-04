package Controller;
import bll.UserOrder;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VendorDashboardController implements Initializable {

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
    private TableColumn<UserOrder, String> user_name;

    @FXML
    private TableColumn<UserOrder, String> food_name;

    @FXML
    private TableColumn<UserOrder, Integer> quantity;

    @FXML
    private TableColumn<UserOrder, Integer> total_price;

    @FXML
    private TableColumn<UserOrder, String> status;

    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnTopUpUser;

    @FXML
    private ImageView btnLogout;

    @FXML
    private ImageView myProfile;


    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnSearch;


    @FXML
    private JFXDialog dialog;

    @FXML
    private StackPane stackPane;


    @FXML
    private JFXDatePicker initialDate;

    @FXML
    private JFXDatePicker finalDate;

    @FXML
    private JFXButton btnGo;

    @FXML
    void loadUserOrders(KeyEvent event) {
        if (txtSearch.getText().isEmpty()) {
            orderTable.getItems().clear();
            loadOrder();
        }

    }

    @FXML
    void btnGo(MouseEvent event) {
        LocalDate initialDateValue= initialDate.getValue();
        LocalDate finalDateValue= finalDate.getValue();
        System.out.println(Date.valueOf(initialDateValue));
        System.out.println(finalDateValue);
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
            String sql = "select user_order.order_id,user_order.quantity, user_order.total_price,user_order.status,user_order.date, menu.food_name, user.first_name FROM user_order INNER JOIN menu ON user_order.food_id= menu.food_id INNER JOIN user ON user_order.id=user.id where date='2019-12-2'";

            PreparedStatement ps = cn.prepareStatement(sql);
            //ps.setString(1, String.valueOf(initialDateValue));
            //ps.setString(2, String.valueOf(finalDateValue));
            ResultSet rs = ps.executeQuery();
            orderTable.getItems().clear();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );

            }
            if(txtSearch.getText().isEmpty()){
                loadOrder();

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnRefresh(MouseEvent event) { // Refresh the order table
        orderTable.getItems().clear();
        loadOrder();
    }


    @FXML
    void btnSearch(MouseEvent event) { // Search the user order by its First Name
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
            String sql = "select user_order.order_id ,user_order.quantity, user_order.total_price," +
                    " user_order.status,user_order.date, menu.food_name," +
                    " user.first_name, user.last_name " +
                    "FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id " +
                    "where first_name=?";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, txtSearch.getText() );
            ResultSet rs = ps.executeQuery();
            orderTable.getItems().clear();

            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );

            }
            if(txtSearch.getText().isEmpty()){
                loadOrder();

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnUpdate(MouseEvent event) throws IOException {

    }

    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }


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
    void loadOrder(){
        try {
//            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder")
//            ResultSet rs=ud.getUserOrder();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
            ResultSet rs= cn.createStatement().executeQuery(
                    "select user_order.order_id,user_order.quantity,user_order.date, " +
                            " user_order.total_price, user_order.status, menu.food_name, " +
                            "user.first_name, user.last_name " +
                            "FROM user_order " +
                            "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                            "INNER JOIN user ON user_order.id=user.id");
            while(rs.next())
            {
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );

            }

            order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(oblist);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::loadOrder);
         //load the user_order in the table

    }
}