package balopom.balopomproject;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
// TableView içerisine eklenecek olan ürünleri tanımlayan Products class'ı
public class Products implements Serializable {
    private final SimpleIntegerProperty productID,productStock,productPrice;
    private final SimpleStringProperty productName;

    public Products(int productID, String productName, int productStock, int productPrice) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productStock = new SimpleIntegerProperty(productStock);
        this.productPrice = new SimpleIntegerProperty(productPrice);
    }

    public int getProductID() {
        return productID.get();
    }

    public SimpleIntegerProperty productIDProperty() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public int getProductStock() {
        return productStock.get();
    }

    public SimpleIntegerProperty productStockProperty() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock.set(productStock);
    }

    public int getProductPrice() {
        return productPrice.get();
    }

    public SimpleIntegerProperty productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice.set(productPrice);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String toString(){
        return productID+","+productName+","+productStock+","+productPrice+"\n";
    }
}
