package lk.ijse.play_tech.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.play_tech.bo.BOFactory;
import lk.ijse.play_tech.bo.custom.UserBO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientController extends Thread {

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    private Button btnSend;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtField;

    @FXML
    private VBox vBox;

    @FXML
    private ImageView picUser;

    BufferedReader reader;

    PrintWriter writer;

    private FileChooser fileChooser;

    private File filePath;

    Socket socket;

    //ServerSocket serverSocket;

    DataInputStream dataInputStream;

    DataOutputStream dataOutputStream;

    String message = "";

    private void getImage(String userName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        //resultSet = UserModel.getImage(userName);
        resultSet = userBO.getImage(userName);
        Image image = null;
        image = new Image(resultSet.getBinaryStream("pic"));
        picUser.setImage(image);
        picUser.setPreserveRatio(false);
    }

    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        String userName = LoginFormController.userName;
        lblName.setText(userName);
        getImage(userName);
        try {
            socket = new Socket("localhost", 6000);
            //System.out.println("Socket is connected with the server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String jaString = new String("\u65e5\u672c\u8a9e\u6587\u5b57\u5217");
        writeOutput(jaString);
        String inputString = readInput();
        String displayString = jaString + " " + inputString;
        new ShowString(displayString, "Conversion Demo");*/
    }

    @Override
    public void run() {
        try {
            while (true) {

                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }

                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }

                //sending images
                if (firstChars.equalsIgnoreCase("IMG")) {
                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblName.getText())) {

                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);
                        //Text text1 = new Text("Me : ");
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);

                    }
                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                } else {
                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200); //200
                    TextFlow flow = new TextFlow(tempFlow);
                    HBox hBox = new HBox(12); //12

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {

                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);

                    } else {

                        Text text2 = new Text( "Me : "+fullMsg);
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                    }
                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /*public void btnCOnAction(ActionEvent actionEvent) {

        try {
            dataOutputStream.writeUTF(txtFieldC.getText().trim());
            dataOutputStream.flush();
            txtFieldC.setText("");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }*/

    public void btnSendOnAction(ActionEvent actionEvent) {
        String msg = txtField.getText();
        writer.println(lblName.getText() + ": " + msg);

        txtField.clear();

        if(msg.equalsIgnoreCase("bye") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
        txtField.requestFocus();
    }

    public void ImgOnMouseClicked(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        //FileChooser fileChooser = new FileChooser();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + filePath.getPath());

    }

    public void txtFiledOnAction(ActionEvent actionEvent) {
        btnSend.requestFocus();
    }

    static void writeOutput(String str) {
        try {
            FileOutputStream fos = new FileOutputStream("test.txt");
            Writer out = new OutputStreamWriter(fos, "UTF8");
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char)ch);
            }
            in.close();
            return buffer.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /*public void initialize(int rNum) {
        new Thread(()->{

            try {
                socket = new Socket("localhost", 3005);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equalsIgnoreCase("finish")){
                    message = dataInputStream.readUTF();
                    txtAreaC.appendText("\nServer : "+message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }*/

    /*public void btnCOnAction(ActionEvent actionEvent) {

        try {
            dataOutputStream.writeUTF(txtFieldC.getText().trim());
            dataOutputStream.flush();
            txtFieldC.setText("");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }*/

    /*public void txtFieldCOnAction(ActionEvent actionEvent) {
        btnC.requestFocus();
    }*/

    /*public void btnLogoutOnAction(ActionEvent actionEvent) {

        ButtonType yes = new ButtonType("Yes, Log me out", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No, Just Kidding ;)", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Oh you're gonna leave!\nAre you sure to delete? :/", yes,no).showAndWait();

        if (result.orElse(no) == yes) {
            try {
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk/ijse/play_tech/view/login_view.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = null;

                if (pane != null && pane.getScene() != null) {
                    stage = (Stage) pane.getScene().getWindow();
                } else {
                    // Handle the case when pane or pane.getScene() is null
                    // Add appropriate error handling or fallback behavior
                }

                if (stage != null) {
                    stage.setScene(scene);
                    stage.setTitle("Login");
                    stage.centerOnScreen();
                } else {
                    // Handle the case when stage is null
                    // Add appropriate error handling or fallback behavior
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }*/


}
