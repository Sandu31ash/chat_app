package lk.ijse.play_tech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class  ServerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/login_view.fxml"));
        Scene scene1 = new Scene(parent);
        //scene1.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene1);
        stage.setTitle("Login");
        stage.show();


        //FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("server_view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load()/*, 320, 240*/);
        //stage.setTitle("Chat App - Server");
        //stage.setScene(scene);
        //stage.show();

        //FXMLLoader fxmlLoader1 = new FXMLLoader(ServerApplication.class.getResource("client_view.fxml"));
        //Scene scene1 = new Scene(fxmlLoader1.load()/*,320, 240*/);
        //stage.setTitle("Chat App - Client");
        //stage.setScene(scene1);
        //stage.show();

        /*Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/server_view.fxml"));
        Scene scene1 = new Scene(parent);
        stage.setScene(scene1);
        stage.setTitle("Play tech - Server");
        stage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/play_tech/view/client_view.fxml"));
        Parent root = loader.load();
        Stage stage1 = new Stage();
        Scene scene2 = new Scene(root);
        stage1.setScene(scene2);
        stage1.setTitle("Play tech - Client");
        stage1.show();*/

    }

    public static void main(String[] args) {
        launch();
    }
}
