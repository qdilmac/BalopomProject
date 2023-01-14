package balopom.balopomproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ProductOperationsTabController implements Initializable, Serializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Products> mainTable;

    @FXML
    private TableColumn<Products, Integer> pIdColumn;

    @FXML
    private TextField pIdTextField;

    @FXML
    private TableColumn<Products, String> pNameColumn;

    @FXML
    private TextField pNameTextField;

    @FXML
    private TableColumn<Products, Integer> pPriceColumn;

    @FXML
    private TextField pPriceTextField;

    @FXML
    private TableColumn<Products, Integer> pStockColumn;

    @FXML
    private TextField pStockTextField;

    private final ObservableList<Products> productData = FXCollections.observableArrayList();
    // tablo değerlerini set edecek olan metot
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pIdColumn.setCellValueFactory(new PropertyValueFactory<Products,Integer>("productID"));
        pNameColumn.setCellValueFactory(new PropertyValueFactory<Products,String>("productName"));
        pStockColumn.setCellValueFactory(new PropertyValueFactory<Products,Integer>("productStock"));
        pPriceColumn.setCellValueFactory(new PropertyValueFactory<Products,Integer>("productPrice"));
    }
    Integer index;
    // Tıklanan satırdaki ürün değerlerini, ilgili text field'lara getiren metot
    @FXML
    void setOnMouseClick(MouseEvent event) {
        index = mainTable.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        pIdTextField.setText(pIdColumn.getCellData(index).toString());
        pNameTextField.setText(pNameColumn.getCellData(index));
        pStockTextField.setText(pStockColumn.getCellData(index).toString());
        pPriceTextField.setText(pPriceColumn.getCellData(index).toString());
    }
    // Belirli bir aralıkta rastgele id oluşturacak olan metot
    int idGenerator() {
        Random random = new Random();
        int upperB = 9999999;
        int lowerB = 1000002;
        int rndmID = random.nextInt(lowerB, upperB);
        return rndmID;
    }
    // csv/txt dosyasından veriyi çekmemizi sağlayan metot
    public void getData(){
        String file = "productlist.txt";
        String FieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                String[] indexs = line.split(FieldDelimiter, -1);

                Products record = new Products(Integer.parseInt(indexs[0]), indexs[1], Integer.parseInt(indexs[2]),
                        Integer.parseInt(indexs[3]));
                productData.add(record);

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    // İlgili butona tıklanınca tabloya ürün ekleyecek olan metot
    @FXML
    void addProductOnClick(ActionEvent event) throws IOException {
        int pid = 0;
        int pstock = 0;
        int pprice = 0;
        boolean breaker = true;
        String file = "productlist.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true)); // boolean value makes the file appendable
        try {
            pid = idGenerator();
            pstock = Integer.parseInt(pStockTextField.getText());
            pprice = Integer.parseInt(pPriceTextField.getText());
        } catch (NumberFormatException e) {
            breaker = false; // hata yakalanırsa breaker değeri false olacak ve ürün tabloya eklenmeyecek
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Girilen ID/Stok/Fiyat sayısal değerlerden oluşmalıdır!");
            alert.showAndWait();

        }
        if(breaker){
            productData.add(new Products(pid,pNameTextField.getText(),pstock,pprice));
            writer.write(idGenerator()+","+pNameTextField.getText()+","+pStockTextField.getText()+","+pPriceTextField.getText()+"\n");
            pIdTextField.setText("");
            pNameTextField.setText("");
            pStockTextField.setText("");
            pPriceTextField.setText("");
        }
        mainTable.getItems();
        mainTable.setItems(productData);
        writer.close();
    }
    // Tabloda seçili ürünü ilgili butona tıklayınca silecek olan metot
    @FXML
    void deleteProductDataOnClick(ActionEvent event) {
        ObservableList<Products> allData,SingleData;
        allData = mainTable.getItems();
        SingleData=mainTable.getSelectionModel().getSelectedItems();
        SingleData.forEach(allData::remove);


    }
    // İlgili butona tıklanınca tabloyu yenileyecek olan metot
    @FXML
    void refreshTableOnClick(ActionEvent event) throws IOException, ClassNotFoundException {
        mainTable.getItems().clear();
        productData.clear();
        getData();
        mainTable.setItems(productData);
        System.out.println("Tablo Yenilendi.");
    }
    // Tabloyu kaydet butonuna tıklandığında tableView'ı kaydedecek olan metot
    @FXML
    void saveTableOnClick(ActionEvent event) throws IOException {
        File file = new File("productlist.txt");
        PrintWriter pw = new PrintWriter("productlist.txt");
        pw.write(""); // dosya içeriği sıfırlanıyor ki üzerine yazılabilsin tekrardan
        pw.close();
        int maxIndex = mainTable.getItems().size();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
        for (int index = 0; index < maxIndex; index++) {
            writer.write(pIdColumn.getCellData(index).toString()+","+ pNameColumn.getCellData(index) +","+
                    pStockColumn.getCellData(index).toString()+","+pPriceColumn.getCellData(index).toString()+"\n");
        }
        mainTable.getItems().clear(); // gerek yok ama tablodaki verilerin dosyaya yazıldığının testi için tabloyu temizliyorum.
        System.out.println("Tablo Kaydedildi.");
        writer.close();
    }
    // Bilgilendirme alert box'ı getirecek olan metot
    @FXML
    void infoButtonOnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ürün Paneli Kullanma Ön Bilgisi");
        alert.setHeaderText("Programın kullanım özeti aşağıdaki gibidir:");
        alert.setContentText("1- Tabloyu Yenile butonu ile (varsa) önceden kaydı yapılan ürünler tabloya getirilir.\n" +
                "2- Ürün Ekle butonu ile ürün eklenince tablo otomatik olarak ürün kaydını yapar.\n" +
                "3- Ürün Sil butonu ile tablo üzerinde seçilen satırda bulunan ürün tablodan silinir.\n" +
                "4- İhtiyaç duyulması halinde (bknz. ürün silme işleminden sonra, ürün bilgilerini güncellemeden sonra) " +
                "Tabloyu Kaydet butonu ile tablonun kaydı yapılabilir.");
        alert.showAndWait();
    }
    // Seçili ürünün değerlerinin güncellenmesi için kullanılacak olan metot
    @FXML
    void updateSelectedProduct(ActionEvent event) {
        ObservableList<Products> currentProductTable = mainTable.getItems();
        int currentProductID = Integer.parseInt(pIdTextField.getText());
        try{
            for (Products p : currentProductTable) {
                if (p.getProductID() == currentProductID) {
                    p.setProductName(pNameTextField.getText());
                    p.setProductStock(Integer.parseInt(pStockTextField.getText()));
                    p.setProductPrice(Integer.parseInt(pPriceTextField.getText()));

                    mainTable.setItems(currentProductTable);
                    mainTable.refresh();
                    System.out.println("Seçili ürün bilgileri güncellendi.");
                    break;
                }
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Girilen ID/Stok/Fiyat sayısal değerlerden oluşmalıdır!");
            alert.showAndWait();
        }
    }
    // Giriş sayfasına dönmemizi sağlayacak olan metot.
    @FXML
    void returnToLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Balopom Giriş Sayfası");
        stage.show();
    }
}
