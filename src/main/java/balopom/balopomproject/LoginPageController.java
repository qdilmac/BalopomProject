package balopom.balopomproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LoginPageController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox passCheckBox;

    @FXML
    private PasswordField passwordPassField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button registerButton;
    @FXML
    private Button forgotPassButton;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label loginStatusLabel;
    private static Scanner x;
    // girilen bilgileri alıp mevcut dosyayla doğrulayacak olan metot
    public static boolean verifyLogin(String username, String password,String filepath){
        boolean isFound = false;
        boolean validate = false;
        String tempID = "";
        String tempUsername = "";
        String tempPassword = "";
        String tempEmail = "";

        try{
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while(x.hasNext()&&!isFound){
                tempID = x.next();
                tempUsername = x.next();
                tempPassword = x.next();
                tempEmail = x.next();

                if(tempUsername.trim().equals(username.trim())&& tempPassword.trim().equals(password.trim())){
                    isFound = true;
                    validate = true;
                    System.out.println(tempID+" "+tempUsername+" "+tempPassword+" "+tempEmail);
                }
            }

            x.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return validate;
    }
    // girilen bilgileri yazılan metotları kullanarak karşılaştıracak, eşleşirse kullanıcı girişini sağlayacak metot
    @FXML
    void loginButtonOnClick(ActionEvent event) throws IOException {
        boolean loginSuccess = verifyLogin(usernameTextField.getText(),passwordPassField.getText(),"userfile.txt");
        if(loginSuccess == true){
            loginStatusLabel.setText("Giriş başarılı!");

            if(usernameTextField.getText().equals("admin") && passwordPassField.getText().equals("admin")){
                Parent root = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("=*=Balopom Yönetici Sayfası=*=");
                stage.show();
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("productOperationsTab.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Balopom Kullanıcı Sayfası");
                stage.show();
            }

        }else{
            loginStatusLabel.setText("Giriş bilgilerinizi kontrol ediniz.");
        }
    }
    // Kullanıcı kayıt sayfasına gönderecek olan metot
    @FXML
    void registerButtonOnClick(ActionEvent event) throws IOException {  // kayıt olma sayfasına yönlendirecek olan buton
        Parent root = FXMLLoader.load(getClass().getResource("registerPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Kayıt Sayfası");
        stage.show();
    }
    // Şifreyi görünür kulacak olan metot
    @FXML
    void showPassword(ActionEvent event) {
        if(passCheckBox.isSelected()){
            passwordTextField.setText(passwordPassField.getText());
            passwordPassField.setVisible(false);
            passwordTextField.setVisible(true);
        }else{
            passwordPassField.setText(passwordTextField.getText());
            passwordPassField.setVisible(true);
            passwordTextField.setVisible(false);
        }
    }
    // Şifremi Unuttum sayfasına götürecek olan metot
    @FXML
    void forgotPassOnClick(ActionEvent event) throws IOException {  // şifremi unuttum sayfasına götürecek olan buton
        Parent root = FXMLLoader.load(getClass().getResource("forgotPassPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Şifremi Unuttum");
        stage.show();
    }

}
