package lk.ijse.play_tech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.play_tech.bo.BOFactory;
import lk.ijse.play_tech.bo.custom.UserBO;
import lk.ijse.play_tech.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private Button btnCreateAcc;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUser;

    @FXML
    private ImageView proPic;

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    static String userName;

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        userName = txtUser.getText();

        boolean isValid = userBO.valid(userName);

        if(isValid) {

            try {
                Parent parent = null;
                parent = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/client_view.fxml"));
                Scene scene1 = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene1);
                stage.setTitle("Chat Room");
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException();
            }

            /*try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/play_tech/view/client_view.fxml"));
                Parent root = loader.load();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/client_view.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Chat Room");
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/play_tech/view/client_view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Stage stage1 = new Stage();
        Scene scene2 = new Scene(root);
        stage1.setScene(scene2);
        stage1.setTitle("Chat App - Client");
        stage1.show();*/

        }else{
            new Alert(Alert.AlertType.ERROR, "Login Failed!\nInvalid Username").show();
        }
    }

    public void btnCreateAccOnAction(ActionEvent actionEvent) {
        /*try {
            Parent parent = null;
            parent = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/create_acc_view.fxml"));
            Scene scene1 = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene1);
            stage.setTitle("Create Account");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }*/


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/play_tech/view/create_acc_view.fxml"));
            Parent root = loader.load();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/create_acc_view.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //AdminDashboardFormController adDash = loader.getController();
        //adDash.setName(userName);

    }

    public void txtUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String username = txtUser.getText();

        ResultSet resultSet;
        resultSet = userBO.getImage(username);
        Image image = null;
        image = new Image(resultSet.getBinaryStream("pic"));
        proPic.setImage(image);
        proPic.setPreserveRatio(false);

        btnLogin.requestFocus();
    }
}
