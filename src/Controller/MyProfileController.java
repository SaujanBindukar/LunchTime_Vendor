package Controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import dao.FoodDao;
import dao.UserOrderDao;
import dao.VendorDao;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

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
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        myProfilePane.getChildren().setAll(pane);

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
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        myProfilePane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
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
        try{
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            vd.updateVendorProfile(LoginController.id, vendorName.getText(), vendorEmail.getText(), vendorNumber.getText());
            Platform.runLater(() -> {
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Profile Update"));
                content.setBody(new Text("Your Profile has been successfully updated."));
                JFXButton yesButton = new JFXButton("OK");
                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
                yesButton.setOnAction(e ->{
                    try{
                        dialog.close();
                        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
                        myProfilePane.getChildren().setAll(pane);

                    }catch(Exception ex){
                        System.out.println("Exception:"+ex);

                    }
                }


                );
                content.setActions(yesButton);
                dialog.show();
            });


        }catch (Exception e){
            System.out.print(e);
        }

    }
    void getVendorInfo(){
        try{
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            ResultSet getInfo=vd.getInfo(LoginController.id);
            while(getInfo.next()){
                vendorEmail.setText(getInfo.getString("vendor_email"));
                vendorName.setText(getInfo.getString("vendor_name"));
                vendorNumber.setText(getInfo.getString("vendor_number"));
            }



        }catch(Exception e){
            System.out.println("Exception: "+e);

        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getVendorInfo();
        btnUpdate.setDisable(true);
    }
}