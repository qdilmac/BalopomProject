package balopom.balopomproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class RegisterPageController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField emailRegisterTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField passwordRegisterTextField;

    @FXML
    private Button registerReturnButton;

    @FXML
    private Button registerSaveButton;
    private static Scanner x;

    @FXML
    private TextField usernameRegisterTextField;
    File userFile = new File("userfile.txt");
    // 8999997 farklı ihtimal arasında rastgele bir id atanıyor. /*Öz Eleştiri*/ Aynı gelme olasılığı çok düşük olsa da var, düzeltilebilir.
    int idGenerator(){
        Random random = new Random();
        int upperB = 9999999;
        int lowerB = 1000002;
        int rndmID = random.nextInt(lowerB,upperB);
        return rndmID;
    }
    // dosya içine girilen bilgileri kaydedecek olan metot
    void writeData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(userFile,true)); // boolean value makes the file appendable
        writer.write(idGenerator()+","+usernameRegisterTextField.getText()+","+passwordRegisterTextField.getText()+","+emailRegisterTextField.getText()+"\n");
        writer.close();
    }
    // kullanıcı kaydının yapılacağı dosyanın varlığını kontrol eden metot
    void checkFile() throws IOException {
        if(!userFile.exists()){
            userFile.createNewFile();
            System.out.println("Dosya oluşturuldu: "+userFile.getName());
        }else{
            System.out.println("Dosya zaten mevcut.");
        }
    }
    // email doğrulamasını yapacak olan metot
    public static boolean verifyEmail(String email,String filepath){
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
    File file = new File("userfile.txt");
    // girilen bilgiler doğruysa kaydı alacak ve giriş sayfasına yönlendirecek olan metot
    @FXML
    void registerSaveOnClick(ActionEvent event) throws IOException{
        checkFile();
        boolean checkEmail = verifyEmail(emailRegisterTextField.getText(), file.getPath());

        if (usernameRegisterTextField.getText().isBlank() || passwordRegisterTextField.getText().isBlank()||emailRegisterTextField.getText().isBlank()){
            errorLabel.setText("Hiçbir alan boş bırakılamaz!");
        } else if (checkEmail){
            errorLabel.setText("Girilen kullanıcı bilgileri zaten mevcut!");
            System.out.println("Girilen kullanıcı bilgileri zaten mevcut!");
        } else if (!emailRegisterTextField.getText().contains("@")||!emailRegisterTextField.getText().contains(".com")) {
            errorLabel.setText("Geçerli bir email giriniz!");
        } else if (usernameRegisterTextField.getText().equals(passwordRegisterTextField.getText())) {
            errorLabel.setText("Kullanıcı adı ve şifre aynı olamaz.");
        } else if(usernameRegisterTextField.getText().contains(" ")|| passwordRegisterTextField.getText().contains(" ") || emailRegisterTextField.getText().contains(" ")){
            errorLabel.setText("Girilen bilgiler boşluk karakteri içeremez.");
        }else{ // kaydı alır ve dosyaya kaydeder.
            errorLabel.setText("Kaydınız başarıyla alınmıştır.");
            System.out.println("Kayıt alındı ve dosyaya kaydedildi.");

            writeData();
            usernameRegisterTextField.setText("");
            passwordRegisterTextField.setText("");
            emailRegisterTextField.setText("");
        }

    }
    // login sayfasına geri yönlendirecek olan metot
    @FXML
    void registerReturnOnClick(ActionEvent event) throws InterruptedException, IOException {
        TimeUnit.MILLISECONDS.sleep(100);
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Giriş Sayfası");
        stage.show();
    }

}
