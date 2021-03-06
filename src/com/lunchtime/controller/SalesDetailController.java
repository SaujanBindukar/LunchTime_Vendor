/**
 * @author Saujan Bindukar
 * This control fetches the sales detail of food till date and also represent them in line chart.
 * The sales detail can be customized by using dates.
 */

package com.lunchtime.controller;
import com.lunchtime.bll.UserOrder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.lunchtime.dao.UserOrderDao;
import com.lunchtime.dao.VendorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
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
    private JFXButton btnSalesDetails;

    @FXML
    private LineChart<String, Integer> salesChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private JFXDatePicker initialDate;

    @FXML
    private JFXDatePicker finalDate;

    @FXML
    private TableView<UserOrder> salesTable;

    @FXML
    private TableColumn<UserOrder, Date> date;

    @FXML
    private TableColumn<UserOrder, Integer> total_price;

    @FXML
    private Circle profilePictureView;

    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();

    /** Navigation to dashboard page */
    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
            StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            userOrderPane.getChildren().setAll(pane);
    }
    /** Navigation to Sales Detail page */
    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }
    /** Navigation to My Profile page */
    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }

    /** Navigation to Top Up user page */
    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }

    /** Navigation to Add Food page */
    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }
    /** Navigation to User Order page */
    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }

    /**
     * Fetch the sales detail using dates by sending request the RMI Server.
     * The data are set to the sales detail page and also represented in line graph.
     * @param event
     */

    @FXML
    void btnGo(MouseEvent event) {
        LocalDate initialDateValue= initialDate.getValue();
        LocalDate finalDateValue= finalDate.getValue();
        try {
            XYChart.Series<String, Integer> series= new XYChart.Series<>();
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getSalesDetailByDate(initialDateValue,finalDateValue);
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

    /**
     *Confirmation dialog appears when the user press the logout button.
     * After confirmation page navigate to login screen, otherwise remains in same page.
     */
    @FXML
    void btnLogout(MouseEvent event){
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
                    userOrderPane.getChildren().setAll(pane);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            });
            cancelButton.setOnAction(ex->dialog.close());
            content.setActions(cancelButton,okButton);
            dialog.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    /**
     * Fetch the sales detail from database by sending request to RMI Server.
     * The data are set to the sales deatil page and represented in line graph.
     */
    void loadSalesDetails(){
        XYChart.Series<String, Integer> series= new XYChart.Series<>();
        try{
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs=ud.getSalesDetail();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getDate("date"),
                        rs.getInt("total_price")));
               series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2))); //getting data in chart
            }
            salesChart.getData().addAll(series);  //adding data in chart

            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            salesTable.setItems(oblist);

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
    /** Initialization of methods */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSalesDetails.setStyle("-fx-background-color: #c92052");
        getVendorInfo();
        loadSalesDetails();
    }
}