/**
 * @author Saujan Bindukar
 * This controller is used for updating the profile of the vendor.
 * Validation is used in every fields and Image upload API is used for uploading the image.
 */
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
    private JFXButton btnUpdate;

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

    final FileChooser fileChooser = new FileChooser();
    File file;

    /** Variable for validation*/
    private boolean nameIsEmpty = false;
    private boolean nameIsValid= true;
    private boolean emailIsEmpty = false;
    private boolean emailIsValid = true;
    private boolean phoneIsEmpty = false;
    private boolean phoneIsValid = true;



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

    /** Navigation to dashboard page*/
    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }

    /** Navigation to sales detail page*/
    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    /** Navigation to my profile page*/
    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    /** Navigation to topup user page*/
    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }

    /** Navigation to add food page*/
    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }
    /** Navigation to user order page*/
    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/userOrder.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }
    /** Enable update button*/
    @FXML
    void changeEmail(KeyEvent event) {
        btnUpdate.setDisable(false);
    }
    /** Enable update button*/
    @FXML
    void changeName(KeyEvent event) {
        btnUpdate.setDisable(false);
    }
    /** Enable update button*/
    @FXML
    void changePhoneNumber(KeyEvent event) {
        btnUpdate.setDisable(false);

    }

    /**
     * checks if the field is empty and update the profile of vendor after successful insertion of fields.
     */
    @FXML
    void btnUpdate(MouseEvent event) {
        if(!nameIsEmpty && nameIsValid && !emailIsEmpty && emailIsValid && !phoneIsEmpty && phoneIsValid) {
            if (file == null) {
                /** If the selcted image is null*/
                System.out.println("File is null");
                try {
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
                        okButton.setOnAction(e -> {
                            try {
                                VendorDao vd = (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
                                vd.updateVendorProfile(LoginController.id, vendorName.getText(), vendorEmail.getText(), vendorNumber.getText(), image);
                                dialog1.show();
                            } catch (RemoteException ex) {
                                System.out.println(ex);
                            } catch (NotBoundException ex) {
                                System.out.println(ex);
                            } catch (MalformedURLException ex) {
                                System.out.println(ex);
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e -> dialog.close());
                        content.setActions(cancelButton, okButton);
                        dialog.show();
                    });
                } catch (Exception e) {
                    System.out.print(e);
                }

            } else {
                /** If the selected image is not null */
                System.out.println("File is not null");
                try {
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

                        okButton.setOnAction(e -> {
                            try {
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

                                    /** Getting response of image upload*/
                                    @Override
                                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

                                        Platform.runLater(() -> {
                                            String picture = response.body().getEager().get(0).getSecureUrl();
                                            try {
                                                VendorDao vd = (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
                                                vd.updateVendorProfile(LoginController.id, vendorName.getText(), vendorEmail.getText(), vendorNumber.getText(), picture);
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
                                    /** Error if uploading image is failed.*/
                                    @Override
                                    public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                                        System.out.println("Cannot upload image");
                                    }
                                });
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            dialog.close();
                        });
                        cancelButton.setOnAction(e -> dialog.close());
                        content.setActions(cancelButton, okButton);
                        dialog.show();
                    });
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        }
    }

    /**
     * Fetch all the information of vendor by sending request to RMI Server
     */
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

    /** Disable the update button*/
    @FXML
    void profilePicture(KeyEvent event) {
        btnUpdate.setDisable(false);
    }

    /**
     * Allows to select the picture from the storage and sets it to the imagepath.
     */
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
        } else {
            System.out.println("Error");
        }


    }

    /** Regex validator and field required validator of different fields*/
    private void fieldValidators(){
        /** field required validator for first name*/
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


        /** Regex validation for name*/
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

        /** Field Required validator for email*/
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

        /** Field Required validation for phone number*/
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


        /** Regex validation for email */

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

        /** Field Required validation for Phone Number*/
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



        /** Phone Number length validation*/
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

    /** Check duplicate email for updating email */
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

    /** Initialization of methods*/
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