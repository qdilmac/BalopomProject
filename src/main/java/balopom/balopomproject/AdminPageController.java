package balopom.balopomproject;


public class AdminPageController {
    /*
    AdminPageController'ın ayrı olarak yazılmasının tek nedeni FXML dosyalarının bir Controller class'a ihtiyacı olması.
    Admin girişi yapıldığı zaman gelen ekranda hem üyeler hem de ürün sayfası görünüyor. Bunu adminPage.fxml dosyası
    içerisinde ilgili tablar içerisine yerleştirdiğim anchorPane'lere <fx:include source="userOperationsTab.fxml" /> gibi
    bir kodla ayrı fxml dosyalarını import ediyoruz. Böylelikle tıklanan tab aynı sayfa içerisinde başka bir sayfa gösteriyor.
     */
}
