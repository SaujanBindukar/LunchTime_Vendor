package Login;

import dao.VendorDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    @FXML
    private AnchorPane login_pane;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSignin;

    @FXML
    private Hyperlink goto_Register;

    @FXML
    private PasswordField txtPassword;

    Alert a = new Alert(Alert.AlertType.NONE);


    public void checkUser() {
        System.out.print("Login Button Pressed");
        String emailText = txtEmail.getText();
        String passwordText = txtPassword.getText();
        System.out.println(emailText);
        System.out.println(passwordText);
        try {
            VendorDao vd= (VendorDao) Naming.lookup("rmi://localhost/HelloServer");
            //VendorDao vd = new VendorDaoImpl();
            Boolean rs = vd.checkVendor(txtEmail.getText(), txtPassword.getText());

            try {
                if (rs) {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../Dashboard/dashboard.fxml"));
                    System.out.print("Moved to next page");
                    login_pane.getChildren().setAll(pane);
                } else {

                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Invalid Username or Password");
                    a.show();

                }
            } catch (IOException ex) {
                System.out.print(ex);
                System.out.print("Database not connected1");
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


    }


    public void goto_Register() throws IOException, NotBoundException {
//        Registry registry = LocateRegistry.getRegistry("localhost");
//        VendorDao vd = (VendorDao) registry.lookup("HelloServer");
//        System.out.println("Server object " + vd + " found");
//        vd.printSomething();
        System.out.println("Button Pressed");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../Register/register.fxml"));
        login_pane.getChildren().setAll(pane);

    }
}
