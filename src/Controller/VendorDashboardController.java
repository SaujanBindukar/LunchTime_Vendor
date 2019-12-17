package Controller;
import bll.UserOrder;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.rowset.CachedRowSetImpl;
import dao.FoodDao;
import dao.UserOrderDao;
import dao.VendorDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javax.sql.rowset.CachedRowSet;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VendorDashboardController implements Initializable {

    @FXML
    private TableView<UserOrder> orderTable;

    @FXML
    private TableColumn<UserOrder, Integer> order_id;

    @FXML
    private TableColumn<UserOrder, Date> date;

    @FXML
    private TableColumn<UserOrder, String> user_name;

    @FXML
    private TableColumn<UserOrder, String> food_name;

    @FXML
    private TableColumn<UserOrder, Integer> quantity;

    @FXML
    private TableColumn<UserOrder, Integer> total_price;

    @FXML
    private TableColumn<UserOrder, String> status;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXDatePicker initialDate;

    @FXML
    private JFXDatePicker finalDate;

    @FXML
    private Circle profilePictureView;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private JFXButton btnUserOrder;

    @FXML
    private JFXButton btnDashboard;

    ObservableList<UserOrder> oblist = FXCollections.observableArrayList();



    @FXML
    void loadUserOrders(KeyEvent event) {
        if (txtSearch.getText().isEmpty()) {
            orderTable.getItems().clear();
            loadOrder();
        }

    }

    @FXML
    void btnGo(MouseEvent event) {
        LocalDate initialDateValue= initialDate.getValue();
        LocalDate finalDateValue= finalDate.getValue();

        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getUserOrderByDate(initialDateValue, finalDateValue);
            orderTable.getItems().clear();
            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );

            }

            order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(oblist);

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
    void btnRefresh(MouseEvent event) { // Refresh the order table
        orderTable.getItems().clear();
        loadOrder();
    }


    @FXML
    void btnSearch(MouseEvent event) { // Search the user order by its First Name
        try {

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/lunchtime", "root", "");
//            String sql = "select user_order.order_id ,user_order.quantity, user_order.total_price," +
//                    " user_order.status,user_order.date, menu.food_name," +
//                    " user.first_name, user.last_name " +
//                    "FROM user_order " +
//                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
//                    "INNER JOIN user ON user_order.id=user.id " +
//                    "where first_name=?";
//
//            PreparedStatement ps = cn.prepareStatement(sql);
//            ps.setString(1, txtSearch.getText() );
//            ResultSet rs = ps.executeQuery();
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs= ud.getUserOrderByName(txtSearch.getText());
            orderTable.getItems().clear();
            System.out.println(rs);

            while(rs.next()){
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );

            }

            order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(oblist);
            if(txtSearch.getText().isEmpty()){
                loadOrder();

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



    @FXML
    void btnLogout(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void btnDashboard(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void btnSalesDetails(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/salesDetail.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void myProfile(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/myProfile.fxml"));
        rootStackPane.getChildren().setAll(pane);

    }

    @FXML
    void btnTopUpUser(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/topupUser.fxml"));
        rootStackPane.getChildren().setAll(pane);


    }


    @FXML
    void btnAddFood(ActionEvent event) throws IOException {
        System.out.println("Add food button is pressed");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/addFoodItems.fxml"));
        rootStackPane.getChildren().setAll(pane);


    }

    @FXML
    void btnUserOrder(ActionEvent event) throws IOException {
        System.out.println("User Order Button is pressed.");
        StackPane pane = FXMLLoader.load(getClass().getResource("../View/VendorDashboard.fxml"));
        rootStackPane.getChildren().setAll(pane);
    }
    void loadOrder(){
        try {
            UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
            ResultSet rs=ud.getUserOrder();

            while(rs.next())
            {
                oblist.add(new UserOrder(
                        rs.getInt("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getString("status"),
                        rs.getDate("date"),
                        rs.getString("first_name"),
                        rs.getString("food_name"))
                );
            }
            order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            food_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(oblist);



            orderTable.setOnMouseClicked(e ->{
                String orderStatus=orderTable.getSelectionModel().getSelectedItem().getStatus();
                System.out.println("OrderStatus:"+ orderStatus);
                if (e.getClickCount()==2){
                    switch(orderStatus){
                        case "Received":
                            System.out.println("Received order status");
                            break;
                        case "Pending":
                            Platform.runLater(() -> {
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Confirmation"));
                                content.setBody(new Text("Are you sure the user has received the order?"));
                                JFXButton yesButton = new JFXButton("Yes");
                                JFXButton cancelButton = new JFXButton("Cancel");
                                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
                                cancelButton.setOnAction(event -> dialog.close());
                                yesButton.setOnAction(event ->{
                                    try{
                                        //UserOrderDao ud= (UserOrderDao) Naming.lookup("rmi://localhost/HelloUserOrder");
                                        ud.updateStatus(orderTable.getSelectionModel().getSelectedItem().getOrder_id());
                                        orderTable.getItems().clear();
                                        loadOrder();
                                        dialog.close();
                                    }catch(Exception ex){
                                        System.out.println(ex);

                                    }
                                    dialog.close();
                                });
                                content.setActions(cancelButton, yesButton);
                                dialog.show();
                            });
                            System.out.println("Received order pending");
                            break;
                    }
                }
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
                btnUserOrder.setStyle("-fx-background-color: #bb346f");
                loadOrder();
                getVendorInfo();


    }
}