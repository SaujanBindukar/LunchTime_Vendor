package Controller;
import bll.FoodMenu;
import bll.UploadResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import dao.FoodDao;
import dao.UploadAPI;
import dao.VendorDao;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.sql.RowSet;
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
    private AnchorPane addFoodItemsPane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnSalesDetails;

    @FXML
    private JFXTextField txtFoodName;

    @FXML
    private JFXTextField txtFoodPrice;

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
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnGo;


    final FileChooser fileChooser = new FileChooser();
    File file;


    @FXML
    private Pane foodPictureView;

    @FXML
    private Circle profilePictureView;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TableColumn<FoodMenu, String> food_picture;

    @FXML
    private ImageView btnChoosePicture;

    int foodId;
    String imagePath;

    private boolean foodNameIsEmpty = true;
    private boolean priceIsEmpty = true;
    private boolean priceIsValid = false;
    private boolean foodIsEmpty = true;
    private boolean foodIsValid = false;

    ObservableList<FoodMenu> oblist = FXCollections.observableArrayList();

    FoodDao fd= (FoodDao) Naming.lookup("rmi://localhost/HelloFoodMenu");

    public AddFoodController() throws RemoteException, NotBoundException, MalformedURLException {

    }

    void clearFields(){
        txtFoodName.clear();
        txtFoodPrice.clear();
    }
    @FXML
    private JFXButton btnDashboard;

    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    @FXML
    void btnUpdate(MouseEvent event) throws IOException, ClassNotFoundException {
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
                                            String picture=response.body().getEager().get(0).getSecureUrl();
                                            System.out.println("Picture: "+ picture);
                                            try {
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


    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
            System.out.println("Sales Detail Button is pressed");
            StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
            rootStackPane.getChildren().setAll(pane);

    }




    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    @FXML
    void btnClear(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    @FXML
    void btnSubmitMenu(ActionEvent event) {
        String foodName=txtFoodName.getText();
        String foodPrice=txtFoodPrice.getText();
        System.out.println(foodName);
        System.out.println(foodPrice);
        if (txtFoodName.getText().isEmpty() || txtFoodPrice.getText().isEmpty() || file==null) {
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
                        dialog1.close();

                    });
                    content1.setActions(yesButton1);

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
                                            fd.addMenu(fm);
                                        } catch (RemoteException ex) {
                                            ex.printStackTrace();
                                        }
                                        MenuTable.getItems().clear();
                                        loadData();
                                        clearFields();
                                        dialog1.show();
                                    });
                                }

                                @Override
                                public void onFailure(Call<UploadResponse> call, Throwable throwable) {

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
    @FXML
    void loadFoodMenu(KeyEvent event) {
        if (txtSearchFoodName.getText().isEmpty()) {
            MenuTable.getItems().clear();
            loadData();
        }

    }

    @FXML
    void btnDelete(MouseEvent event) {

//        if(txtFoodName.getText().isEmpty() || txtFoodPrice.getText().isEmpty() || file==null){
//            Platform.runLater(() -> {
//                JFXDialogLayout content = new JFXDialogLayout();
//                content.setHeading(new Text("Error"));
//                content.setBody(new Text("No items to delete"));
//                JFXButton yesButton = new JFXButton("OK");
//                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
//
//                yesButton.setOnAction(e ->{
//                    dialog.close();
//                });
//                content.setActions(yesButton);
//                dialog.show();
//            });
//
//        }
//        else{


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
                        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
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





      //  }
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

    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }
    @FXML
    void changeFoodImage(KeyEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

    @FXML
    void changeFoodName(KeyEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

    @FXML
    void changeFoodPrice(KeyEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

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

    public void fieldValidators() {

        //Field Required validator for FoodName
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

        // food nameValidator
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



        //Field Required validator for food price
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

        //number validator for food price
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            getVendorInfo();
            //String imagePath = "http://view.dreamstalk.ca/breeze5/images/no-photo.png";
            //foodPictureView.setStyle(imagePath);
            //foodPictureView.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color: grey; -fx-background-image: url( " + imagePath + ");");
            loadData();
        fieldValidators();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnAddFood.setStyle("-fx-background-color: #bb346f");




    }

}
