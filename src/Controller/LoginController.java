package Controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import dao.VendorDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane login_pane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    Alert a = new Alert(Alert.AlertType.NONE);

    public static int id;

    private boolean emailIsValid = false;
    private boolean emailIsEmpty = true;

    private boolean passwordIsValid = false;
    private boolean passwordIsEmpty = true;

    private void fieldValidators(){
        //Field Required validator for email
        RequiredFieldValidator emailRequiredFieldValidator = new RequiredFieldValidator();
        txtEmail.getValidators().add(emailRequiredFieldValidator);
        emailRequiredFieldValidator.setMessage("Please enter an email!");
        txtEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtEmail.validate()) {
                    System.out.println("Email not empty");
                    emailIsEmpty = false;
                } else {
                    System.out.println("Email empty");
                    emailIsEmpty = true;
                }

            }
        });
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> txtEmail.validate());



        //Field Required validator for password
        RequiredFieldValidator passwordRequiredFieldValidator = new RequiredFieldValidator();
        txtPassword.getValidators().add(passwordRequiredFieldValidator);
        passwordRequiredFieldValidator.setMessage("Please enter a password!");
        txtPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (txtPassword.validate()) {
                    System.out.println("Password not empty");
                    passwordIsEmpty = false;
                } else {
                    System.out.println("Password empty");
                    passwordIsEmpty = true;
                }
            }
        });
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> txtPassword.validate());


       // Email Validator
//      RegexValidator emailValidator = new RegexValidator();
//        emailValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//        email_field.setValidators(emailValidator);
//        emailValidator.setMessage("Email is invalid!");
//        email_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (email_field.validate()) {
//                    System.out.println("Email valid");
//                    emailIsValid = true;
//                } else {
//                    System.out.println("Email not valid");
//                    emailIsValid = false;
//                }
//            }
//        });
//        email_field.textProperty().addListener((observable, oldValue, newValue) -> email_field.validate());
//
//
//        //Password Validator
//        RegexValidator passwordValidator = new RegexValidator();
//        passwordValidator.setRegexPattern("^.{8,}$");
//        password_field.setValidators(passwordValidator);
//        passwordValidator.setMessage("Password should be atleast 8 characters long!");
//        password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (password_field.validate()) {
//                    System.out.println("Password valid");
//                    passwordIsValid = true;
//                } else {
//                    System.out.println("Password not valid");
//                    passwordIsValid = false;
//                }
//            }
//        });
//        password_field.textProperty().addListener((observable, oldValue, newValue) -> password_field.validate());

    }

    public void checkUser() {
        System.out.println("Login Button Pressed");
        String emailText = txtEmail.getText();
        String passwordText = txtPassword.getText();
        System.out.println(emailText);
        System.out.println(passwordText);

        if(emailText.isEmpty() || passwordText.isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please enter all the fields");
            a.show();


        }else{
            try {

                VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
                Boolean rs = vd.checkVendor(txtEmail.getText(), txtPassword.getText());
                try {
                    if (rs) {
                        ResultSet rs1= vd.getVendorInfo(txtEmail.getText());
                        while (rs1.next()) {
                            id = rs1.getInt("vendor_id");
                            System.out.println("Vendor_id is:"+id);

                        }
                        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
                        System.out.print("Moved to next page");
                        login_pane.getChildren().setAll(pane);
                    } else {


                        Platform.runLater(() -> {
                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setHeading(new Text("Error"));
                            content.setBody(new Text("Invalid Username or Password."));
                            JFXButton yesButton = new JFXButton("OK");
                            JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.RIGHT);
                            yesButton.setOnAction(event ->{
                                dialog.close();
                            });
                            content.setActions(yesButton);
                            dialog.show();
                        });

                    }
                } catch (IOException ex) {
                    System.out.print(ex);

                }


            } catch (RemoteException re) {
                System.out.println();
                System.out.println("RemoteException");
                System.out.println(re);
            } catch (ArithmeticException ae) {
                System.out.println();
                System.out.println(
                        "java.lang.ArithmeticException");
                System.out.println(ae);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldValidators();
    }
}
