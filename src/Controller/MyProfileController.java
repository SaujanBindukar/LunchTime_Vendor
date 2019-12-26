package Controller;
import bll.UploadResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import dao.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.UUID;

public class MyProfileController implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane myProfilePane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnSalesDetails;

    @FXML
    private JFXButton btnTopUpUser;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ImageView btnLogout;

    @FXML
    private ImageView myProfile;

    @FXML
    private JFXTextField vendorName;

    @FXML
    private JFXTextField vendorEmail;

    @FXML
    private JFXTextField vendorNumber;


    @FXML
    private Circle profilePictureView;

    @FXML
    private Circle addPictureView;

    @FXML
    private Circle profilePictureView1;

    public static String image;
    public static  String email;

    private boolean nameIsEmpty = false;
    private boolean nameIsValid= true;
    private boolean emailIsEmpty = false;
    private boolean emailIsValid = true;
    private boolean phoneIsEmpty = false;
    private boolean phoneIsValid = true;



    final FileChooser fileChooser = new FileChooser();
    File file;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

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
                    myProfilePane.getChildren().setAll(pane);

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

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        myProfilePane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    @FXML
    void changeEmail(KeyEvent event) {
        btnUpdate.setDisable(false);

    }

    @FXML
    void changeName(KeyEvent event) {
        btnUpdate.setDisable(false);

    }

    @FXML
    void changePhoneNumber(KeyEvent event) {
        btnUpdate.setDisable(false);

    }

    @FXML
    void btnUpdate(MouseEvent event) {
        if(!nameIsEmpty && nameIsValid && !emailIsEmpty && emailIsValid && !phoneIsEmpty && phoneIsValid){
//            System.out.println("vendor_email:"+vendorEmail.getText());
//            System.out.println(dt);
//            if(vendorEmail.getText().equals(email)){
//                JFXDialogLayout content = new JFXDialogLayout();
//                content.setHeading(new Text("Error"));
//                content.setBody(new Text("Email already exist!"));
//                JFXButton okButton = new JFXButton("OK");
//                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
//                okButton.setOnAction(e->{
//                    dialog.close();
//                });
//                content.setActions(okButton);
//                dialog.show();
//
//
//            }

            //else
                if(file==null){
                System.out.println("File is null");
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to update your profile?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successful"));
                        content1.setBody(new Text("Profile Updated Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 -> {
                            dialog1.close();
                            try {
                                StackPane pane;
                                pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
                                myProfilePane.getChildren().setAll(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                        content1.setActions(yesButton1);

                        okButton.setOnAction(e ->{
                                            try {
                                                VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
                                                vd.updateVendorProfile(LoginController.id, vendorName.getText(), vendorEmail.getText(), vendorNumber.getText(), image );
                                                dialog1.show();
                                            } catch (RemoteException ex) {
                                                System.out.println(ex);
                                            } catch (NotBoundException ex) {
                                                System.out.println(ex);
                                            } catch (MalformedURLException ex) {
                                                System.out.println(ex);
                                            }

                            catch(Exception ex){
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

            }else{

                System.out.println("File is not null");
                try{
                    Platform.runLater(() -> {
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Confirmation"));
                        content.setBody(new Text("Do you really want to update your profile?"));
                        JFXButton okButton = new JFXButton("Yes");
                        JFXButton cancelButton = new JFXButton("Cancel");
                        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                        JFXDialogLayout content1 = new JFXDialogLayout();
                        content1.setHeading(new Text("Successful"));
                        content1.setBody(new Text("Profile Updated Successfully"));
                        JFXButton yesButton1 = new JFXButton("OK");
                        JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                        yesButton1.setOnAction(event1 -> {
                            dialog1.close();
                            try {
                                StackPane pane;
                                pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
                                myProfilePane.getChildren().setAll(pane);
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
                                            try {
                                                VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
                                                vd.updateVendorProfile(LoginController.id, vendorName.getText(), vendorEmail.getText(), vendorNumber.getText(), picture );
                                                dialog1.show();
                                            } catch (RemoteException ex) {
                                                System.out.println(ex);
                                            } catch (NotBoundException ex) {
                                                System.out.println(ex);
                                            } catch (MalformedURLException ex) {
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
    void getVendorInfo(){
        try{
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            ResultSet getInfo=vd.getInfo(LoginController.id);
            while(getInfo.next()){
                try{
                    image= getInfo.getString("picture");
                    profilePictureView.setFill(new ImagePattern(new Image(image)));
                    profilePictureView1.setFill(new ImagePattern(new Image(image)));
                    vendorEmail.setText(getInfo.getString("vendor_email"));
                    vendorName.setText(getInfo.getString("vendor_name"));
                    vendorNumber.setText(getInfo.getString("vendor_number"));
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }

    @FXML
    void profilePicture(KeyEvent event) {
        btnUpdate.setDisable(false);

    }

    @FXML
    void btnAddPicture(MouseEvent event) throws MalformedURLException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg"));
        file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.toURI().toURL().toString();
            profilePictureView.setFill(new ImagePattern(new Image(imagePath)));
            btnUpdate.setDisable(false);
            //profilePictureView.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color: grey; -fx-background-image: url( " + imagePath + ");");
        } else {
            System.out.println("Error");
        }


    }

    private void fieldValidators(){

        //Field Required validator for firstName
        RequiredFieldValidator firstNameRequiredFieldValidator = new RequiredFieldValidator();
        vendorName.getValidators().add(firstNameRequiredFieldValidator);
        firstNameRequiredFieldValidator.setMessage("Please enter firstName!");
        vendorName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorName.validate()) {
                    System.out.println("firstName not empty");
                    nameIsEmpty = false;
                } else {
                    System.out.println("firstName empty");
                    nameIsEmpty = true;
                }

            }
        });
        vendorName.textProperty().addListener((observable, oldValue, newValue) -> vendorName.validate());


        // food nameValidator
        RegexValidator name = new RegexValidator();
        name.setRegexPattern("[a-zA-Z ]+");
        vendorName.setValidators(name);
        name.setMessage("Name is invalid!");
        vendorName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorName.validate()) {
                    System.out.println("Name valid");
                    nameIsValid = true;
                } else {
                    System.out.println("Name not valid");
                    nameIsValid = false;
                }
            }
        });
        vendorName.textProperty().addListener((observable, oldValue, newValue) -> vendorName.validate());





        //Field Required validator for email
        RequiredFieldValidator emailRequiredFieldValidator = new RequiredFieldValidator();
        vendorEmail.getValidators().add(emailRequiredFieldValidator);
        emailRequiredFieldValidator.setMessage("Please enter an email!");
        vendorEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorEmail.validate()) {

                    System.out.println("Email not empty");
                    emailIsEmpty = false;
                } else {
                    System.out.println("Email empty");
                    emailIsEmpty = true;
                }

            }
        });
        vendorEmail.textProperty().addListener((observable, oldValue, newValue) -> vendorEmail.validate());

        //Field Required validator for phone
        RequiredFieldValidator phoneRequiredFieldValidator = new RequiredFieldValidator();
        vendorNumber.getValidators().add(phoneRequiredFieldValidator);
        phoneRequiredFieldValidator.setMessage("Please enter an phone!");
        vendorNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorNumber.validate()) {

                    System.out.println("Phone not empty");
                    phoneIsEmpty = false;
                } else {
                    System.out.println("Phone empty");
                    phoneIsEmpty = true;
                }

            }
        });
        vendorNumber.textProperty().addListener((observable, oldValue, newValue) -> vendorNumber.validate());


//----------------------------------------------------------------------------------------------------------------------------------//

//        //Email Validator
        RegexValidator emailValidator = new RegexValidator();
        emailValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        vendorEmail.setValidators(emailValidator);
        emailValidator.setMessage("Email is invalid!");
        vendorEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorEmail.validate()) {
                    System.out.println("Email valid");
                    emailIsValid = true;
                } else {
                    System.out.println("Email not valid");
                    emailIsValid = false;
                }
            }
        });
        vendorEmail.textProperty().addListener((observable, oldValue, newValue) -> vendorEmail.validate());

//        //Field Required validator for phone
        NumberValidator phoneFieldValidator = new NumberValidator();
        vendorNumber.getValidators().add(phoneFieldValidator);
        phoneFieldValidator.setMessage("Only numbers accepted!");
        vendorNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorNumber.validate()) {
                    System.out.println("Phone valid");
                    phoneIsValid = true;
                } else {
                    System.out.println("Phone not valid");
                    phoneIsValid = false;
                }
            }
        });
        vendorNumber.textProperty().addListener((observable, oldValue, newValue) -> vendorNumber.validate());



//        //Phone length Validator
        RegexValidator phoneLengthValidator = new RegexValidator();
        phoneLengthValidator.setRegexPattern("^.{10}$");
        vendorNumber.setValidators(phoneLengthValidator);
        phoneLengthValidator.setMessage("Phone Number should be 10 characters long!");
        vendorNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (vendorNumber.validate()) {
                    System.out.println("Phone valid");
                    phoneIsValid = true;
                } else {
                    System.out.println("Phone not valid");
                    phoneIsValid = false;
                }
            }
        });
        vendorNumber.textProperty().addListener((observable, oldValue, newValue) -> vendorNumber.validate());
    }


    void checkEmail(){
        try{
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            ResultSet  rs= vd.getAllVendorInfo();
            while (rs.next()) {
                email=rs.getString("vendor_email");
                System.out.println(email);
            }

        }catch(Exception e){
            System.out.println(e);

        }
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getVendorInfo();
        fieldValidators();
        checkEmail();
        try{
            String image= "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX5697410.jpg";
            addPictureView.setFill(new ImagePattern(new Image(image)));
        }catch(Exception e){
            System.out.println(e);
        }
        btnUpdate.setDisable(true);


    }
}