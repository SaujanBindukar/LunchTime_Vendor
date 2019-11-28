package Controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MyProfileController{

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
    private ImageView btnLogout;

    @FXML
    private ImageView myProfile;

    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        myProfilePane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        myProfilePane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        myProfilePane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        myProfilePane.getChildren().setAll(pane);


    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        myProfilePane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        myProfilePane.getChildren().setAll(pane);
    }


}