module balopom.balopomproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens balopom.balopomproject to javafx.fxml;
    exports balopom.balopomproject;
}