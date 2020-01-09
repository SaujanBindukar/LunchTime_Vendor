/**
 * @author Saujan Bindukar
 * This controller fetch the available food from database by sending request to RMI Server. Adding food item and
 * updating the food items including name, price and image is handled by this controller.
 * Image upload API is used for uploading the images of Food items.
 * */

package com.lunchtime.controller;
import com.lunchtime.bll.FoodMenu;
import com.lunchtime.bll.UploadResponse;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.dao.FoodDao;
import com.lunchtime.dao.UploadAPI;
import com.lunchtime.dao.VendorDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddFoodController implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXTextField txtFoodName;

    @FXML
    private JFXTextField txtFoodPrice;

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
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Pane foodPictureView;

    @FXML
    private Circle profilePictureView;

    @FXML
    private TableColumn<FoodMenu, String> food_picture;


    int foodId;
    String imagePath;
    final FileChooser fileChooser = new FileChooser();
    File file;

    ObservableList<FoodMenu> oblist = FXCollections.observableArrayList();

    /** Variables for validation*/
    private boolean foodNameIsEmpty = true;
    private boolean priceIsEmpty = true;
    private boolean priceIsValid = false;
    private boolean foodIsEmpty = true;
    private boolean foodIsValid = false;



    FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");

    /** Constructor for throwing Exceptions*/
    public AddFoodController() throws RemoteException, NotBoundException, MalformedURLException {

    }
    /** Clears the text fields*/
    void clearFields(){
        txtFoodName.clear();
        txtFoodPrice.clear();
    }

    /** Navigation to dashboard page */
    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /**
     * Check the validation for all the fields for adding, updating and deleting the food items.
     */
    @FXML
    void btnUpdate(MouseEvent event) {
        if (txtFoodPrice.getText().isEmpty() || txtFoodName.getText().isEmpty()) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Error"));
            content.setBody(new Text("Please enter all the fields"));
            JFXButton okButton = new JFXButton("OK");
            JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
            content.setActions(okButton);
            dialog.show();
            okButton.setOnAction(e->dialog.close());

        }else{
            if(file==null){
                /** If the selected image is empty*/
                System.out.println("File is null");
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to update the items?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successful"));
                        content1.setBody(new Text("Items Updated Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 -> {
                            dialog1.close();
                            try {
                                StackPane pane;
                                pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
                                rootStackPane.getChildren().setAll(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                        content1.setActions(yesButton1);
                        okButton.setOnAction(e ->{
                            try{
                                Platform.runLater(()->{
                                    try {
                                        fd.updateMenu(txtFoodName.getText(),Integer.parseInt(txtFoodPrice.getText()), foodId, imagePath);
                                        MenuTable.getItems().clear();
                                        loadData();
                                        clearFields();
                                        btnUpdate.setDisable(true);
                                        dialog1.show();
                                    } catch (RemoteException ex) {
                                        System.out.println(ex);
                                    }
                                });
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e-> dialog.close());
                        content.setActions(cancelButton,okButton);
                        dialog.show();
                    });
                }catch (Exception e){
                    System.out.print(e);
                }
            }
            else{
                /** If the selected image is not empty*/
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to update the items?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successful"));
                        content1.setBody(new Text("Items Updated Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 -> {
                            dialog1.close();
                            try {
                                StackPane pane;
                                pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
                                rootStackPane.getChildren().setAll(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                        content1.setActions(yesButton1);

                        okButton.setOnAction(e ->{
                            try{
                            	 //------------------For creating the signature for using in api---------------------//
                                long timestamp = System.currentTimeMillis();
                                String apiKey = "588753441842251";
                                String eager = "w_400,h_400,c_pad";
                                String publicId = String.valueOf(UUID.randomUUID());
                                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                                messageDigest.update(("eager=w_400,h_400,c_pad&public_id=" + publicId + "&timestamp=" + timestamp + "oWEOZ2sxuB2cpixDPaa6XhLS23E").getBytes());
                                String signature = DatatypeConverter.printHexBinary(messageDigest.digest());
                                
                                //Create a multipart builder for the request body
                                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                builder.addFormDataPart("timestamp", String.valueOf(timestamp)) //Timestamp used when creating the signature
                                        .addFormDataPart("public_id", publicId) //Unique name for the upload file so that it will not override the existing file in the server
                                        .addFormDataPart("api_key", apiKey) //Unique API Key provided by the API provider.
                                        .addFormDataPart("eager", eager)  //Custom Dimension file the request in the response for smaller file sizes
                                        .addFormDataPart("signature", signature)  //Unique signature for upload
                                        .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file)); //Picture file

                                RequestBody requestBody = builder.build();
                                
                                //Run the Upload Api with the request body
                                Call<UploadResponse> call = UploadAPI.apiService.upload(requestBody);

                                call.enqueue(new Callback<UploadResponse>() {
                                    @Override
                                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                                        Platform.runLater(()->{
                                        	// if picture is uploaded successfully then image is set to variable
                                            String picture=response.body().getEager().get(0).getSecureUrl();
                                            
                                            try {
                                            	//update food menu
                                                fd.updateMenu(txtFoodName.getText(),Integer.parseInt(txtFoodPrice.getText()), foodId, picture);
                                                MenuTable.getItems().clear();
                                                loadData();
                                                clearFields();
                                                btnUpdate.setDisable(true);
                                                dialog1.show();
                                            } catch (RemoteException ex) {
                                                System.out.println(ex);
                                            }
                                        });
                                    }
                                    //if picture is not uploaded
                                    @Override
                                    public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                                        System.out.println("Cannot upload image");
                                    }
                                });
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e-> dialog.close());
                        content.setActions(cancelButton,okButton);
                        dialog.show();
                    });
                }catch (Exception e){
                    System.out.print(e);
                }
            }
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
                    rootStackPane.getChildren().setAll(pane);
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
    /** Navigation to Sales Detail Page*/
    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
            StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
            rootStackPane.getChildren().setAll(pane);
    }
    /** Navigation to  My Profile Page*/
    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    /** Navigation to Add Food Page*/
    @FXML
    void btnClear(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    /** Navigation to Top up user Page*/
    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /**
     * Add new food items after correct validation of text fields and images.
     * Image upload API is used for uploading images of food items.
     */
    @FXML
    void btnSubmitMenu(ActionEvent event) {
        String foodName=txtFoodName.getText();
        String foodPrice=txtFoodPrice.getText();
        System.out.println(foodName);
        System.out.println(foodPrice);

        if (txtFoodName.getText().isEmpty() || txtFoodPrice.getText().isEmpty()) {
            Platform.runLater(() -> {
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Error"));
                content.setBody(new Text("Please enter all the fields"));
                JFXButton okButton = new JFXButton("OK");
                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
                okButton.setOnAction(e ->
                        dialog.close()
                );
                content.setActions(okButton);
                dialog.show();
            });
        }
        else{
            if(file==null){
                System.out.println("File is null");
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to add new food?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successfull"));
                        content1.setBody(new Text("New food Added Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 ->{
                        	System.out.print("yes button pressed");
                            //loadData();
                            dialog1.close();
                           
							try {
								 StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
								 rootStackPane.getChildren().setAll(pane);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                          
                            
                        });
                        content1.setActions(yesButton1);
                        okButton.setOnAction(e ->{
                            try{
                                Platform.runLater(()->{
                                    FoodMenu fm= new FoodMenu();
                                    fm.setPicture(imagePath);
                                    fm.setFood_name(txtFoodName.getText());
                                    fm.setFood_price(Integer.parseInt(txtFoodPrice.getText()));
                                    try {
                                        String message = fd.addMenu(fm);
                                        System.out.println(message);
                                        if (message.equals("Duplicate")){
                                            System.out.println(message);
                                            Platform.runLater(()->{
                                                System.out.println(message);
                                                JFXDialogLayout content2 = new JFXDialogLayout();
                                                content2.setHeading(new Text("Error"));
                                                content2.setBody(new Text("Food Name already exist!"));
                                                JFXButton okButton1 = new JFXButton("Ok");
                                                JFXDialog dialog2 = new JFXDialog(rootStackPane, content2, JFXDialog.DialogTransition.CENTER);

                                                okButton1.setOnAction(event1 ->{
                                                    dialog2.close();

                                                });
                                                content2.setActions(okButton1);
                                                dialog2.show();
                                            });
                                        }
                                        else if(message=="Success"){
                                            Platform.runLater(()->{
                                                dialog1.show();
                                            });
                                        }
                                    } catch (RemoteException ex) {
                                        ex.printStackTrace();
                                    }
                                });
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e-> dialog.close());
                        content.setActions(cancelButton,okButton);
                        dialog.show();
                    });
                }catch (Exception e){
                    System.out.print(e);
                }
            }
            else{
                System.out.println("File is not null");
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to add new food?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successfull"));
                        content1.setBody(new Text("New food Added Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 ->{
                            loadData();
                            dialog1.close();
                            
                            try{
                                StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
                                rootStackPane.getChildren().setAll(pane);
                            }catch (Exception e){
                                System.out.println(e);
                            }
                            dialog1.close();
                        });
                        content1.setActions(yesButton1);
                        dialog1.show();

                        okButton.setOnAction(e ->{
                            try{
                                long timestamp = System.currentTimeMillis();
                                String apiKey = "588753441842251";
                                String eager = "w_400,h_400,c_pad";
                                String publicId = String.valueOf(UUID.randomUUID());
                                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                                messageDigest.update(("eager=w_400,h_400,c_pad&public_id=" + publicId + "&timestamp=" + timestamp + "oWEOZ2sxuB2cpixDPaa6XhLS23E").getBytes());
                                String signature = DatatypeConverter.printHexBinary(messageDigest.digest());


                                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                builder.addFormDataPart("timestamp", String.valueOf(timestamp))
                                        .addFormDataPart("public_id", publicId)
                                        .addFormDataPart("api_key", apiKey)
                                        .addFormDataPart("eager", eager)
                                        .addFormDataPart("signature", signature)
                                        .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));
                                RequestBody requestBody = builder.build();


                                Call<UploadResponse> call = UploadAPI.apiService.upload(requestBody);
                                call.enqueue(new Callback<UploadResponse>() {
                                    @Override
                                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

                                        Platform.runLater(()->{
                                            FoodMenu fm= new FoodMenu();
                                            fm.setPicture(response.body().getEager().get(0).getSecureUrl());
                                            fm.setFood_name(txtFoodName.getText());
                                            fm.setFood_price(Integer.parseInt(txtFoodPrice.getText()));
                                            try {
                                                String message = fd.addMenu(fm);
                                                System.out.println(message);

                                                if (message.equals("Duplicate")){
                                                    System.out.println(message);
                                                    Platform.runLater(()->{
                                                        System.out.println(message);
                                                        JFXDialogLayout content2 = new JFXDialogLayout();
                                                        content2.setHeading(new Text("Error"));
                                                        content2.setBody(new Text("Food Name already exist!"));
                                                        JFXButton okButton = new JFXButton("Ok");
                                                        JFXDialog dialog2 = new JFXDialog(rootStackPane, content2, JFXDialog.DialogTransition.CENTER);

                                                        okButton.setOnAction(event1 ->{
                                                            dialog2.close();
                                                        });
                                                        content2.setActions(okButton);
                                                        dialog2.show();
                                                    });
                                                }
                                                else if(message=="Success"){
                                                    dialog1.show();
//                                                    try{
//                                                        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
//                                                        rootStackPane.getChildren().setAll(pane);
//                                                    }catch (Exception e){
//                                                        System.out.println(e);
//                                                    }
                                                }

                                            } catch (RemoteException ex) {
                                                ex.printStackTrace();
                                            }
                                            MenuTable.getItems().clear();
                                            loadData();
                                            clearFields();
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                                        System.out.println("Error uploading image");
                                    }
                                });
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e-> dialog.close());
                        content.setActions(cancelButton,okButton);
                        dialog.show();
                    });
                }catch (Exception e){
                    System.out.print(e);
                }
            }
        }

    }

    /** Load food items in table*/
    @FXML
    void loadFoodMenu(KeyEvent event) {
        if (txtSearchFoodName.getText().isEmpty()) {
            MenuTable.getItems().clear();
            loadData();
        }

    }

    /**
     * Delete the food items
     */
    @FXML
    void btnDelete(MouseEvent event) {
            Platform.runLater(() -> {
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Confirmation"));
                content.setBody(new Text("Do you really want to delete the item?"));
                JFXButton yesButton = new JFXButton("Yes");
                JFXButton cancelButton = new JFXButton("Cancel");
                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                JFXDialogLayout content1 = new JFXDialogLayout();
                content1.setHeading(new Text("Successful"));
                content1.setBody(new Text("Item deleted successfully."));
                JFXButton yesButton1 = new JFXButton("OK");
                JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                yesButton1.setOnAction(event1 -> {
                    dialog1.close();
                    try{
                        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
                        rootStackPane.getChildren().setAll(pane);
                    } catch(Exception e){
                        System.out.print(e);
                    }
                });
                content1.setActions(yesButton1);
                cancelButton.setOnAction(eventClose -> dialog.close());
                yesButton.setOnAction(eventConfirm ->{
                    try{
                        fd.deleteMenu(txtFoodName.getText());
                        dialog1.show();
                        txtFoodName.clear();
                        txtFoodPrice.clear();
                        MenuTable.getItems().clear();
                        loadData();

                    }catch(Exception e){
                        System.out.println(e);
                    }
                    dialog.close();
                });
                content.setActions(cancelButton, yesButton);
                dialog.show();
            });
    }

    /**
     * Search the food by its name by sending request to RMI Server and sets the data to the table.
     */
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

    /**
     * Loads the food items into table by sending request to RMI Server.
     */
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
                btnDelete.setDisable(false);
                btnUpdate.setDisable(true);
                imagePath = MenuTable.getSelectionModel().getSelectedItem().getPicture();
                foodPictureView.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color: grey; -fx-background-image: url( " + imagePath + ");");
                txtFoodName.setText(MenuTable.getSelectionModel().getSelectedItem().getFood_name());
                txtFoodPrice.setText(String.valueOf(MenuTable.getSelectionModel().getSelectedItem().getFood_price()));
                foodId= Integer.parseInt(String.valueOf(MenuTable.getSelectionModel().getSelectedItem().getFood_id()));
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
    /** Navigation to Add Food page */
    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /** Navigation to User Order page */
    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    /** Enable update button and disable delete button */
    @FXML
    void changeFoodName(KeyEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }
    /** Enable update button and enable delete button */
    @FXML
    void changeFoodPrice(KeyEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

    /**  Open dialog to select the image   */
    @FXML
    void btnChoosePicture(MouseEvent event) {
        try{
            new FileChooser.ExtensionFilter("Image Files",
                    "*.png", "*.jpg", "*.jpeg");
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String imagePath = file.toURI().toURL().toString();
                foodPictureView.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color: grey; -fx-background-image: url( " + imagePath + ");");
            } else {
                System.out.println("Error");
            }

        }catch(Exception e){
            System.out.println(e);

        }
    }

    /**
     * Fetch all information of vendor by sending request to RMI Server.
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
     * Regex validation and field required validation of different text fields.
     */
    public void fieldValidators() {

        /**Field Required validator for FoodName*/
        RequiredFieldValidator firstNameRequiredFieldValidator = new RequiredFieldValidator();
        txtFoodName.getValidators().add(firstNameRequiredFieldValidator);
        firstNameRequiredFieldValidator.setMessage("Please enter Name!");
        txtFoodName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtFoodName.validate()) {
                    System.out.println("firstName not empty");
                    foodNameIsEmpty = false;
                } else {
                    System.out.println("firstName empty");
                    foodNameIsEmpty = true;
                }

            }
        });
        txtFoodName.textProperty().addListener((observable, oldValue, newValue) -> txtFoodName.validate());

        /**food nameValidator*/
        RegexValidator foodName = new RegexValidator();
        foodName.setRegexPattern("[a-zA-Z ]+");
        txtFoodName.setValidators(foodName);
        foodName.setMessage("Name is invalid!");
        txtFoodName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtFoodName.validate()) {
                    System.out.println("Food Name valid");
                    foodIsValid = true;
                } else {
                    System.out.println("Food Name not valid");
                    foodIsValid = false;
                }
            }
        });
        txtFoodName.textProperty().addListener((observable, oldValue, newValue) -> txtFoodName.validate());



        /** Field Required validator for food price */
        RequiredFieldValidator phoneRequiredFieldValidator = new RequiredFieldValidator();
        txtFoodPrice.getValidators().add(phoneRequiredFieldValidator);
        phoneRequiredFieldValidator.setMessage("Please enter a price");
        txtFoodPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtFoodPrice.validate()) {
                    System.out.println("Price not empty");
                    priceIsEmpty = false;
                } else {
                    System.out.println("Price empty");
                    priceIsEmpty = true;
                }
            }
        });
        txtFoodPrice.textProperty().addListener((observable, oldValue, newValue) -> txtFoodPrice.validate());

        /**number validator for food price */
        NumberValidator phoneFieldValidator = new NumberValidator();
        txtFoodPrice.getValidators().add(phoneFieldValidator);
        phoneFieldValidator.setMessage("Only numbers accepted!");
        txtFoodPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtFoodPrice.validate()) {
                    System.out.println("Price valid");
                    priceIsValid = true;
                } else {
                    System.out.println("Price not valid");
                    priceIsValid = false;
                }
            }
        });
        txtFoodPrice.textProperty().addListener((observable, oldValue, newValue) -> txtFoodPrice.validate());
    }

    /** Initialization of methods*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            getVendorInfo();
            loadData();
        fieldValidators();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnAddFood.setStyle("-fx-background-color: #c92052");




    }

}
