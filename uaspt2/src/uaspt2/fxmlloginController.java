/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package uaspt2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 *
 * @author HD
 */
public class fxmlloginController implements Initializable {

    private Button btn_create;

    @FXML
    private Button closebtn;

    @FXML
    private TextField create_answer;

    @FXML
    private PasswordField create_password;

    @FXML
    private ComboBox<?> create_question;

    @FXML
    private TextField create_username;
    
    @FXML
    private Button login;

    @FXML
    private AnchorPane loginform;

    @FXML
    private PasswordField password;

    @FXML
    private Button side_createnew;

    @FXML
    private AnchorPane side_form;

    @FXML
    private Button side_haveaccount;

    @FXML
    private TextField username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    public void loginBtn() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Username atau Password tidak boleh kosong!");
            alert.showAndWait();
        } else {
            String selectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            connect = database.connectDb();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, username.getText());
                prepare.setString(2, password.getText());

                result = prepare.executeQuery();
                //Jika berhasil login, maka proses akan berlanjut ke main form
                if (result.next()) {
                    
                    data.displayusername = username.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Berhasil");
                    alert.showAndWait();
                    login.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/mainfxml.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene (root);
                    stage.setScene(scene);
                    stage.show();
                    
                    login.getScene().getWindow().hide();
                  
                } else{ //Jika terdapat kesalahan, maka akan muncul pop up
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username atau Password Salah");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void regBtn() {
        if (create_username.getText().isEmpty() || create_password.getText().isEmpty()
                || create_question.getSelectionModel().getSelectedItem() == null
                || create_answer.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Mohon diisi terlebih dahulu");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date)"
                    + "VALUES(?,?,?,?,?)";
            connect = database.connectDb();
            try {
                //Cek Jika USERNAME tertulis di database
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + create_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username sudah terpakai!");
                    alert.showAndWait();
                } else if (create_password.getText().length() < 6) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password minimal 6 huruf");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, create_username.getText());
                    prepare.setString(2, create_password.getText());
                    prepare.setString(3, (String) create_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, create_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Berhasil membuat akun!");
                    alert.showAndWait();

                    create_username.setText("");
                    create_password.setText("");
                    create_question.getSelectionModel().clearSelection();
                    create_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(1));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_haveaccount.setVisible(false);
                        side_createnew.setVisible(true);
                    });
                    slider.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] questionlist = {"Apa hobi mu?", "Apa mobil favorit mu?", "Apa game favorit mu?"};

    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();
        for (String data : questionlist) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        create_question.setItems(listData);
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_createnew) {
            slider.setNode(side_form);
            slider.setToX(453);
            slider.setDuration(Duration.seconds(1));

            slider.setOnFinished((ActionEvent e) -> {
                side_haveaccount.setVisible(true);
                side_createnew.setVisible(false);
                regLquestionList();
            });
            slider.play();

        } else if (event.getSource() == side_haveaccount) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(1));

            slider.setOnFinished((ActionEvent e) -> {
                side_haveaccount.setVisible(false);
                side_createnew.setVisible(true);
            });
            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
