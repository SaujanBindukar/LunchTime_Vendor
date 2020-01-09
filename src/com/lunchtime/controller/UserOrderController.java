/**
 * @author Saujan Bindukar
 * This controller fetch the user order of food by sending the request to RMI server and sets the value in the table.
 * Also the vendor can search the user order by their name, search the order by the data, current date and also filters
 * the pending order. Order status can be changed by double clicking on the table values.
 */
package com.lunchtime.controller;
import com.lunchtime.bll.UserOrder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.dao.UserOrderDao;
import com.lunchtime.dao.VendorDao;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserOrderController implements Initializable {

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

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXDatePicker initialDate;

    @FXML
    private JFXDatePicker finalDate;

    @FXML
    private Circle profilePictureView;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private JFXButton btnUserOrder;

    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();

    /**
     * load is user order if the textfield for search is cleared all.
     */
    @FXML
    void loadUserOrders(KeyEvent event) {
        if (txtSearch.getText().isEmpty()) {
            orderTable.getItems().clear();
            loadOrder();
        }
    }

    /** Fetch the user order by date and set the value in table by sending request to RMI Server.*/
    @FXML
    void btnGo(MouseEvent event) {
        LocalDate initialDateValue= initialDate.getValue();
        LocalDate finalDateValue= finalDate.getValue();
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getUserOrderByDate(initialDateValue, finalDateValue);
            orderTable.getItems().clear();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name")
                        )
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
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /** Refresh the user order table*/
    @FXML
    void btnRefresh(MouseEvent event) {
        orderTable.getItems().clear();
        loadOrder();
    }

    /** Search the user order by First Name and sets the value in the table */
    @FXML
    void btnSearch(MouseEvent event) {
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getUserOrderByName(txtSearch.getText());
            orderTable.getItems().clear();
            oblist.clear();
            System.out.println(rs);

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

            order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(oblist);
            if(txtSearch.getText().isEmpty()){
                loadOrder();

            }
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
                    rootStackPane .getChildren().setAll(pane);
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

    /** Navigation to Dashboard page**/
    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    /** Navigation to Sales Detail page**/
    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    /** Navigation to My Profile page**/
    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    /** Navigation to Topup User  page**/
    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        rootStackPane.getChildren().setAll(pane);


    }

    /** Navigation to Add Food page**/
    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);


    }
    /** Navigation to User Order page**/
    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /**
     * Fetch the user order from database by sending request to RMI Server and sets the value in table.
     */

    void loadOrder(){
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs=ud.getUserOrder();

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


            orderTable.setOnMouseClicked(e ->{
                /**
                 * Sets the order status of the user in the variable orderStatus.
                 */
                String orderStatus=orderTable.getSelectionModel().getSelectedItem().getStatus();
                System.out.println("OrderStatus:"+ orderStatus);
                /**
                 * Opens the dialog box only when the value is double clicked.
                 */
                if (e.getClickCount()==2){
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Food Status"));
                        content.setBody(new Text("What's the food status?"));
                        // Declaring different buttons
                        JFXButton pendingButton = new JFXButton("Pending");
                        JFXButton processingButton = new JFXButton("Processing");
                        JFXButton readyButton = new JFXButton("Ready");
                        JFXButton receivedButton = new JFXButton("Received");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        /** Change the background color of order status button
                         * according to order status*/
                        switch (orderStatus){
                            case "Pending":
                                pendingButton.setStyle("-fx-background-color: yellow");
                                break;
                            case "Processing":
                                processingButton.setStyle("-fx-background-color: yellow");
                                break;
                            case "Ready":
                                readyButton.setStyle("-fx-background-color: yellow");
                                break;
                            case "Received":
                                receivedButton.setStyle("-fx-background-color: yellow");
                                break;
                            case "Cancelled":
                                cancelButton.setStyle("-fx-background-color: yellow");
                                break;
                        }
                        /**Sets the order status to cancel*/
                        cancelButton.setOnAction(event -> {
                            try{
                                ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id(), "Cancelled");
                                orderTable.getItems().clear();
                                loadOrder();
                                dialog.close();
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        /** Sets the order status to pending */
                        pendingButton.setOnAction(event ->{
                            try{
                                ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id(), "Pending");
                                orderTable.getItems().clear();
                                loadOrder();
                                dialog.close();
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        /** Sets the order status to Processing */
                        processingButton.setOnAction(event ->{
                            try{
                                ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id(), "Processing");
                                orderTable.getItems().clear();
                                loadOrder();
                                dialog.close();
                            }catch(Exception ex){
                                System.out.println(ex);

                            }
                            dialog.close();
                        });
                        /** Sets the order status to Ready */
                        readyButton.setOnAction(event ->{
                            try{
                                ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id(), "Ready");
                                orderTable.getItems().clear();
                                loadOrder();
                                dialog.close();
                            }catch(Exception ex){
                                System.out.println(ex);

                            }
                            dialog.close();
                        });
                        /** Sets the order status to Received */
                        receivedButton.setOnAction(event ->{
                            try{
                                ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id(), "Received");
                                orderTable.getItems().clear();
                                loadOrder();
                                dialog.close();
                            }catch(Exception ex){
                                System.out.println(ex);

                            }
                            dialog.close();
                        });
                        content.setActions(pendingButton,processingButton,readyButton,receivedButton,cancelButton);
                        dialog.show();
                    });

                }
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


    /** Fetch the pending order only and set the values in the user order table. */
    @FXML
    void showPendingOrder(MouseEvent event) {
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getPendingOrder();

            orderTable.getItems().clear();
            oblist.clear();

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
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }


    /** Fetch the user order of current date only.*/
    @FXML
    void showTodaysOrder(MouseEvent event) {
        orderTable.getItems().clear();
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getTodaysOrder();

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
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /** Initialization of methods*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                btnUserOrder.setStyle("-fx-background-color: #c92052");
                loadOrder();
                getVendorInfo();


    }
}