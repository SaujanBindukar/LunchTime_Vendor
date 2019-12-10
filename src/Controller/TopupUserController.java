package Controller;
import bll.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import dao.StudentDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

public class TopupUserController  implements  Initializable{

    Alert a = new Alert(Alert.AlertType.NONE);


    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane userOrderPane;

    @FXML
    private TableView<Student> userTable;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, String > firstName;

    @FXML
    private TableColumn<Student, String> lastName;

    @FXML
    private TableColumn<Student, String> phoneNumber;

    @FXML
    private TableColumn<Student, Integer> balance;

    @FXML
    private TableColumn<Student, String> email;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtphoneNumber;

    @FXML
    private JFXTextField txtAddBalance;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtCurrentBalance;

    private  int userId;

    ObservableList<Student> oblist = FXCollections.observableArrayList();

    @FXML
    void loadAllUsers(KeyEvent event) {
        if (txtSearch.getText().isEmpty()) {
            userTable.getItems().clear();
            loadUser();
        }
    }



    @FXML
    void btnSearch(MouseEvent event) {
        try {
            StudentDao sd= (StudentDao) Naming.lookup("rmi://localhost/HelloStudent");
            ResultSet rs= sd.searchUser(txtSearch.getText());
            userTable.getItems().clear();
            while(rs.next()){
                oblist.add(new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getInt("balance"),
                        rs.getString("email")
                ));
            }
            if(txtSearch.getText().isEmpty()){
                loadUser();

            }
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

    void clearAllField(){
        txtFirstName.clear();
        txtLastName.clear();
        txtCurrentBalance.clear();
        txtphoneNumber.clear();
        txtEmail.clear();
        txtAddBalance.clear();
    }


    @FXML
    void btnClear(MouseEvent event) {
        clearAllField();
    }

    @FXML
    void btnUpdate(MouseEvent event) {
        try{
            if(txtFirstName.getText().isEmpty() ||
                    txtLastName.getText().isEmpty() ||
                    txtCurrentBalance.getText().isEmpty()||
                    txtphoneNumber.getText().isEmpty() ||
                    txtEmail.getText().isEmpty() ||
                    txtAddBalance.getText().isEmpty())
            {
                Platform.runLater(() -> {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Error !"));
                    content.setBody(new Text("Please fill all the fields."));
                    JFXButton yesButton = new JFXButton("OK");
                    JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                    yesButton.setOnAction(eventConfirm ->{
                        dialog.close();
                    });

                    content.setActions(yesButton);
                    dialog.show();
                });

            }else{



                Platform.runLater(() -> {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Confirmation"));
                    content.setBody(new Text("Are you sure you want to update the User?"));
                    JFXButton yesButton = new JFXButton("Yes");
                    JFXButton cancelButton = new JFXButton("Cancel");
                    JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

                    JFXDialogLayout content1 = new JFXDialogLayout();
                    content1.setHeading(new Text("Successfull"));
                    content1.setBody(new Text("User updated successfully."));
                    JFXButton yesButton1 = new JFXButton("OK");
                    JFXDialog dialog1 = new JFXDialog(rootStackPane, content1, JFXDialog.DialogTransition.CENTER);
                    yesButton1.setOnAction(event1 -> dialog1.close());
                    content1.setActions(yesButton1);

                    cancelButton.setOnAction(eventClose -> dialog.close());

                    yesButton.setOnAction(eventConfirm ->{
                        try{
                            StudentDao sd= (StudentDao) Naming.lookup("rmi://localhost/HelloStudent");
                            sd.updateUser(
                                    txtFirstName.getText(),
                                    txtLastName.getText(),
                                    txtEmail.getText(),
                                    txtphoneNumber.getText(),
                                    Integer.parseInt(txtCurrentBalance.getText()),
                                    Integer.parseInt(txtAddBalance.getText()),
                                    userId);
                            userTable.getItems().clear();
                            loadUser();
                            clearAllField();
                            dialog1.show();

                        }catch(Exception e){
                            System.out.println(e);
                        }
                        dialog.close();
                    });
                    content.setActions(cancelButton, yesButton);
                    dialog.show();
                });

            }

        }
        catch(Exception e ){
            System.out.print(e);

        }


    }



    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        userOrderPane.getChildren().setAll(pane);

    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        userOrderPane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        userOrderPane.getChildren().setAll(pane);
    }

    void loadUser(){
        try{
            StudentDao sd= (StudentDao) Naming.lookup("rmi://localhost/HelloStudent");
            ResultSet  rs= sd.getAllUser();
            while(rs.next()){
                oblist.add(new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getInt("balance"),
                        rs.getString("email")
                        ));
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            userTable.setItems(oblist);

            userTable.setOnMouseClicked(e ->{
                txtFirstName.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getFirstName()));
                txtLastName.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getLastName()));
                txtphoneNumber.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getPhoneNumber()));
                txtCurrentBalance.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getBalance()));
                txtEmail.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getEmail()));
                userId=userTable.getSelectionModel().getSelectedItem().getId();
            });
        }catch(Exception e){
            System.out.println(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadUser();
    }
}