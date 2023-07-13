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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.play_tech.bo.BOFactory;
import lk.ijse.play_tech.bo.custom.UserBO;
import lk.ijse.play_tech.db.DBConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountController {


    @FXML
    Button btnCreate;
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsername;
    @FXML
    ImageView proPic;
    @FXML
    Button btnPic;


    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    public void btnCreateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String userName = txtUsername.getText();

        boolean isSaved = userBO.save(userName);

        if(isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Account successfully created!\nLogin with your new Username").show();
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/play_tech/view/login_view.fxml"));
            Parent root = loader.load();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/login_view.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/login_view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene1 = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene1);
        stage.setTitle("Login");
        stage.show();*/
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        btnPic.requestFocus();

        String userName = txtUsername.getText();

        //btnCreate.requestFocus();
    }

    public void btnPicOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String userName = txtUsername.getText();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);

                String sql = "INSERT INTO pic_user (username, pic) VALUES (?, ?)";
                PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement(sql);
                pstm.setString(1, userName);
                pstm.setBinaryStream(2, fis, fis.available());
                int x = pstm.executeUpdate();

                if (x > 0) {
                    //String id = userBO.getId(userName);
                    ResultSet resultSet = userBO.getImage(userName);
                    if (resultSet.next()) {
                        Image image = new Image(resultSet.getBinaryStream("pic"));
                        proPic.setImage(image);
                        /*ResultSet resultSet1;
                        //resulStet = UserModel.getImage(userName);
                        resultSet1 = userBO.getImage(userName);
                        Image image = null;
                        image = new Image(resultSet1.getBinaryStream("pic"));
                        proPic.setImage(image);
                        proPic.setPreserveRatio(false);*/
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        ResultSet resultSet;
        resultSet = userBO.getImage(userName);
        Image image = null;
        image = new Image(resultSet.getBinaryStream("pic"));
        proPic.setImage(image);
        proPic.setPreserveRatio(false);

        txtUsername.requestFocus();
    }
}
