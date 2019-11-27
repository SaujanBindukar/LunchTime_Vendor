package Controller;

import bll.Student;
import dao.StudentDao;
import dao.VendorDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterController {
    @FXML
    private AnchorPane register_pane;
    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    void registerUser() throws RemoteException, NotBoundException {
        try{

            StudentDao sd= (StudentDao) Naming.lookup("rmi://localhost/HelloStudent");
            Student s=new Student();
            s.setFirstName(txtFirstName.getText());
            s.setLastName(txtLastName.getText());
            s.setEmail(txtEmail.getText());
            s.setPhoneNumber(txtPhoneNumber.getText());
            s.setPassword(txtPassword.getText());
            sd.addVendor(s);


        }catch (Exception e){
            System.out.print(e);

        }



    }


}
