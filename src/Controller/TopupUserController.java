

package Controller;

        import bll.UserOrder;
        import com.jfoenix.controls.JFXButton;
        import dao.StudentDao;
        import dao.UserOrderDao;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;

        import java.io.IOException;
        import java.io.Serializable;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.rmi.Naming;
        import java.rmi.NotBoundException;
        import java.rmi.RemoteException;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.Date;
        import java.util.Observable;
        import java.util.ResourceBundle;

public class TopupUserController {

    @FXML
    private AnchorPane userOrderPane;

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
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }


}