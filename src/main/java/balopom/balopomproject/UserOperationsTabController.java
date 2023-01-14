package balopom.balopomproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.Scanner;

public class UserOperationsTabController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private Button updateButton;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private ListView<String> listView;
    private static Scanner x;

    // initialize metodu içinde userfile.txt dosyasını listview'a eklemek için kullanılacak metot
    public static ArrayList<String> addData(String filepath){
        boolean isFound = false;
        String tempID = "";
        String tempUsername = "";
        String tempPassword = "";
        String tempEmail = "";
        ArrayList<String> users = new ArrayList<>();

        try{
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while(x.hasNext()&&!isFound){
                tempID = x.next();
                tempUsername = x.next();
                tempPassword = x.next();
                tempEmail = x.next();
                users.add(tempID+" "+tempUsername+" "+tempPassword+" "+tempEmail+"\n");

                if(!x.hasNext()){
                    isFound = true;
                }
            }

            x.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    @FXML
    void returnToLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Giriş Sayfası");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList items = addData("userfile.txt");
        listView.getItems().addAll(items);
    }
    public int getIDFromAdmin() {
        int reqID = 0;
        try {
            reqID = Integer.parseInt(idTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Girilen ID sayısal değerlerden oluşmalıdır!");
            alert.showAndWait();
        }
        return reqID;
    }
    @FXML
    void setOnMouseClicked(MouseEvent event) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                idTextField.setText(t1);
                int maxLength = 7;
                if(idTextField.getText().length()>maxLength){
                    String setID = idTextField.getText().substring(0,maxLength);
                    idTextField.setText(setID);
                    getUserInfo(event);
                }
            }
        });
    }
    // setOnMouseClicked eventi içerisinde id alınıyor. Geri kalan bilgiler buradan geliyor.
    @FXML
    void getUserInfo(MouseEvent event) {
        boolean isFound = false;
        int tempID = 0;
        String tempUsername = "";
        String tempPassword = "";
        String tempEmail = "";
        File filepath = new File("userfile.txt");
        try{
            x = new Scanner(filepath);
            x.useDelimiter("[,\n]");

            while(x.hasNext()&&!isFound){
                tempID = Integer.parseInt(x.next());
                tempUsername = x.next();
                tempPassword = x.next();
                tempEmail = x.next();

                if(getIDFromAdmin() == tempID){
                    isFound = true;
                }
            }
            idTextField.setText(String.valueOf(tempID));
            usernameTextField.setText(tempUsername);
            passwordTextField.setText(tempPassword);
            emailTextField.setText(tempEmail);
            x.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Bilgilendirme alert box'ı getirecek olan metot
    @FXML
    void showInfoOnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kullanıcı Paneli Kullanma Ön Bilgisi");
        alert.setHeaderText("Programın kullanım özeti aşağıdaki gibidir:");
        alert.setContentText("1- Bu panel sadece admin yetkilerine sahip kullanıcılar için tasarlanmış olup" +
                " sisteme erişimi olan üyelerin bilgilerini okumak için yapılmıştır.");
        alert.showAndWait();
    }
}
