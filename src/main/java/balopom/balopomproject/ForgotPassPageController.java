package balopom.balopomproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ForgotPassPageController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button bringInfoButton;

    @FXML
    private TextField emailTextField;
    @FXML
    private Button returnHomeButton;
    @FXML
    private Label infoLabel;
    private static Scanner x;
    // Unutulan kullanıcı bilgilerini getirmek için email doğrulaması yapan metot
    public static boolean verifyEmail(String email,String filepath){  // email doğrulamasını yapar
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

                if(tempEmail.trim().equals(email.trim())){
                    isFound = true;
                    validate = true;
                    System.out.println("Hesap bilgileriniz: "+tempID+" "+tempUsername+" "+tempPassword+" "+tempEmail);
                }
            }

            x.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return validate;
    }
    // doğrulanan email'i alert olarak ekrana almak için kullanılacak metot
    public static String showData(String email, String filepath){
        boolean isFound = false;
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

                if(tempEmail.trim().equals(email.trim())){
                    isFound = true;
                }
            }

            x.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return "ID: "+tempID+" Kullanıcı Adı: "+tempUsername+" Şifre: "+tempPassword+" Email: "+tempEmail;
    }
    // Login Page'e dönmemizi sağlayan metot
    @FXML
    void returnHomePage(ActionEvent event) throws IOException { // login ekranına geri döner
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Giriş Sayfası");
        stage.show();
    }
    File file = new File("userfile.txt");
    // İlgili dosyanın varlığını kontrol eden metot
    void checkFile() throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
    }
    // girilen email doğruysa terminal ve alert box içerisinde kullanıcı bilgilerini veren metot
    @FXML
    void showInfo(ActionEvent event) throws IOException {
        checkFile();
        boolean verifySuccess = verifyEmail(emailTextField.getText(),file.getPath());
        Alert alert;
        if(verifySuccess == true){
            if(!emailTextField.getText().equals("admin@admin.com")){
                infoLabel.setText("Bilgileriniz terminalde ve açılan bilgi ekranında gösterilmiştir.");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kullanıcı Bilgileriniz");
                alert.setHeaderText("Kullanıcı bilgileriniz aşağıda verilmiştir.");
                alert.setContentText(showData(emailTextField.getText(),"userfile.txt"));
                alert.showAndWait();
            }else{
                infoLabel.setText("Admin bilgilerine bu panelden erişilemez. Ama terminalden okunabilir :]");
            }

        }else{
            infoLabel.setText("Girdiğiniz bilgilere sahip bir kullanıcı kaydı bulunamadı.");
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kullanıcı Bilgileriniz");
            alert.setHeaderText("Lütfen girilen email adresini kontrol ediniz.");
            alert.setContentText("Girdiğiniz bilgilere sahip bir kullanıcı kaydı bulunamadı.");
            alert.showAndWait();
        }
    }
}
