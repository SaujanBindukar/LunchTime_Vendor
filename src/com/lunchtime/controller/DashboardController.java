/**
 * @author Saujan Bindukar
 * This is the controller of the Dashboard. This controller fetch the number of food,
 * number of student, total canteen coins deposited and total sales till date.
 * This controller also shows the piechart of highest food solds and also the vendor can
 * customize the pie charts using dates. Also bar graph showing the student spending highest
 * number of canteen coins is also shown which can be also customized using dates.
 * Naviagtion to different pages can also be done from this page.
 */

package com.lunchtime.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.lunchtime.dao.FoodDao;
import com.lunchtime.dao.StudentDao;
import com.lunchtime.dao.UserOrderDao;
import com.lunchtime.dao.VendorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private Circle profilePictureView;

    @FXML
    private Label labelStudentNumber;

    @FXML
    private Label labelCanteenCoins;

    @FXML
    private Label labelSales;

    @FXML
    private Label labelTotalFood;

    @FXML
    private PieChart foodPreferenceChart;

    @FXML
    private BarChart<String, Integer> topUserChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXDatePicker foodInitialDate;

    @FXML
    private JFXDatePicker foodFinalDate;

    @FXML
    private JFXDatePicker userFinalDate;

    @FXML
    private JFXDatePicker userInitialDate;

    private ObservableList<PieChart.Data> data= FXCollections.observableArrayList();

    /**
     * Navigation to dashboard page.
     */
    @FXML
    void btnDashboard() throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /**
     * Navigation to Sales Detail page.
     */
    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /**
     * Navigation to My Profile page.
     */

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /**
     * Naviagtion to Topup User page
     */

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /**
     * Navigation to Add Food page.
     */

    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /**
     * Navigation to User Order page.
     */
    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /**
     *Confirmation dialog appears when the user press the logout button.
     * After confirmation page navigate to login screen, otherwise remains in same page.
     */
    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        try{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Confirmation"));
            content.setBody(new Text("Do you really want to logout?"));
            JFXButton okButton = new JFXButton("Yes");
            JFXButton cancelButton = new JFXButton("Cancel");
            JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

            okButton.setOnAction(e->{
                try{
                    dialog.close();
                    StackPane pane = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
                    rootStackPane.getChildren().setAll(pane);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            });
            cancelButton.setOnAction(ex->dialog.close());
            content.setActions(okButton, cancelButton);
            dialog.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Fetch the information of vendor using the id they is fetched after successfull login.
     * imagepath variable stores the profile picture of the vendor.
     */
    void getVendorInfo(){
        try{
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            ResultSet getInfo=vd.getInfo(LoginController.id);
            while(getInfo.next()){
                try{
                    String imagePath= getInfo.getString("picture");
                    profilePictureView.setFill(new ImagePattern(new Image(imagePath)));

                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }

    /**
     * Fetch the total number of students by sending request to RMI Server.
     * number of students is set to labelStudentNumber and number of canteen
     * coins is set to labelCanteenCoins.
     */
    void getTotalStudents() {
        try {
            StudentDao sd= (StudentDao) Naming.lookup("rmi://localhost/HelloStudent");
            ResultSet rs= sd.getTotalStudent();
            while (rs.next()) {
                labelStudentNumber.setText(rs.getString("id"));
                labelCanteenCoins.setText(rs.getString("balance"));
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }

    /**
     * Counts the total sales by sending request to RMI Server and set the total price to
     * the variable labelSales.
     */

    void getTotalSales() {
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getTotalSales();
            while (rs.next()) {
                labelSales.setText(rs.getString("total_price"));
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }

    /**
     * Counts the total number of food available by ssending request to RMI Server and
     * sets the total sales in the variable labelTotalFood.
     */

    void  getTotalFood(){
        try{
            FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");
            ResultSet rs= fd.getTotalFood();
            while (rs.next()) {
                labelTotalFood.setText(rs.getString("food_id"));
            }
        }catch(Exception e){
            System.out.println(e);

        }
    }

    /**
     * Fetch the highest food sold in canteen by sending request to RMI server and
     * sets the data in the Pie-Chart.
     */

    void loadFoodPreferenceChart(){
        try{
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs=ud.getFoodPreference();
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
            }
            foodPreferenceChart.setData(data);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Fetch the student record spending highest number of canteen coins by sending request to RMI Server
     * and sets the data into Bar Chart.
     */

    void loadTopUser(){
        XYChart.Series<String, Integer> series= new XYChart.Series<>();
        try
        {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getTopUser();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            topUserChart.getData().addAll(series);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Fetch the highest food sold in canteen from database by sending request to RMI Server
     * and sets the data into Pie Chart with date preference.
     *
     */

    @FXML
    void btnFoodPreference(MouseEvent event) {
        LocalDate initialDate= foodInitialDate.getValue();
        LocalDate finalDate= foodFinalDate.getValue();
        foodPreferenceChart.getData().clear();

        try{
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getFoodPreferenceByDate(initialDate,finalDate);
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
            }
            foodPreferenceChart.setData(data);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Fetch the student record spending highest canteen coins with date preference and sets the data
     * into Bar Chart by sending request to RMI Server.
     */
    @FXML
    void btnTopUser(MouseEvent event) {
        LocalDate initialDate= userInitialDate.getValue();
        LocalDate finalDate= userFinalDate.getValue();
        XYChart.Series<String, Integer> series= new XYChart.Series<>();
        topUserChart.getData().clear();
        try{
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs =ud.getTopUserByDate(initialDate, finalDate);
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            topUserChart.getData().addAll(series);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    /**
     * Initialization of different method when page is loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        xAxis.setLabel("Student ID");
        yAxis.setLabel("Canteen Coins");
        btnDashboard.setStyle("-fx-background-color: #c92052");
        getVendorInfo();
        getTotalStudents();
        getTotalSales();
        getTotalFood();
        loadFoodPreferenceChart();
        loadTopUser();
    }
}


