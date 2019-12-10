package Controller;
import bll.FoodMenu;
import bll.UserOrder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import dao.FoodDao;
import dao.UserOrderDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public class SalesDetailController implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane userOrderPane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnSalesDetails;


    @FXML
    private JFXButton btnTopUpUser;

    @FXML
    private ImageView btnLogout;

    @FXML
    private ImageView myProfile;

    @FXML
    private AreaChart<String, Integer> salesChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private JFXDatePicker initialDate;

    @FXML
    private JFXDatePicker finalDate;

    @FXML
    private JFXButton btnGo;

    @FXML
    private TableView<UserOrder> salesTable;

    @FXML
    private TableColumn<UserOrder, Date> date;

    @FXML
    private TableColumn<UserOrder, Integer> total_price;


    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();

    @FXML
    void btnGo(MouseEvent event) {
        LocalDate initialDateValue= initialDate.getValue();
        LocalDate finalDateValue= finalDate.getValue();

        try {
            XYChart.Series<String, Integer> series= new XYChart.Series<>();
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getUserOrderByDate(initialDateValue, finalDateValue);

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
//            String sql = "SELECT date, sum(total_price) total_price " +
//                    "FROM user_order  " +
//                    "where status='Received' and date between '"+initialDateValue+"' and '"+finalDateValue+"' group by date ";
//
//            PreparedStatement ps = cn.prepareStatement(sql);
            //ResultSet rs = ps.executeQuery();
            salesTable.getItems().clear();
            salesChart.getData().clear();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getDate("date"),
                        rs.getInt("total_price")));
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            salesChart.getData().addAll(series);




        }catch(Exception e){
            System.out.println("Exception:"+e);
        }



    }

    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }


    void loadSalesDetails(){
        XYChart.Series<String, Integer> series= new XYChart.Series<>();
        try{
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs=ud.getSalesDetail();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getDate("date"),
                        rs.getInt("total_price")));
               series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));


            }
            salesChart.getData().addAll(series);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            salesTable.setItems(oblist);

        }catch(Exception e){
            System.out.println(e);

        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSalesDetails();
    }
}