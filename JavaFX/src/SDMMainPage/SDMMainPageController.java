package SDMMainPage;

import SDMAddItemToStorePage.SDMAddItemToStorePageController;
import SDMAddNewDealPage.SDMAddNewDealPageController;
import SDMDeleteItemFromStorePage.SDMDeleteItemFromStorePageController;
import SDMNewOrderPage.SDMNewOrderPageController;
import SDMShowMapPage.SDMShowMapPageController;
import SDMShowOrderHistory.SDMShowOrderHistoryController;
import SDMSystem.SDMSystem;
import SDMUpdateItemInStorePage.SDMUpdateItemInStorePageController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SDMMainPageController {
    @FXML
    private Menu fileButton;
    @FXML
    private MenuItem loadXMLButton;
    @FXML
    private MenuItem loadOrderHistoryButton;
    @FXML
    private MenuItem saveOrderHistoryButton;
    @FXML
    private MenuItem exitButton;
    @FXML
    private Menu showButton;
    @FXML
    private MenuItem showStoresButton;
    @FXML
    private MenuItem showItemsButton;
    @FXML
    private MenuItem showOrderHistoryButton;
    @FXML
    private MenuItem showCostumersButton;
    @FXML
    private Menu orderButton;
    @FXML
    private Menu itemsButton;
    @FXML
    private MenuItem updateItemPriceButton;
    @FXML
    private MenuItem deleteItemButton;
    @FXML
    private MenuItem addItemButton;
    @FXML
    private MenuItem dealsButton;

    private Stage primaryStage;
    private SDMSystem logic;
    private Node prevPage;
    private StringProperty currentStyleSheet = new SimpleStringProperty();

    @FXML
    void addItemToStore(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMAddItemToStorePage/SDMAddItemToStorePage.fxml"));
            Parent root = fxmlLoader.load();
            ((SDMAddItemToStorePageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMAddItemToStorePageController)fxmlLoader.getController()).fillStoresNames();

            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

        } catch (Exception e) {
           errorPopup();
        }
    }

    @FXML
    void deleteItemFromStore(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMDeleteItemFromStorePage/SDMDeleteItemFromStorePage.fxml"));
            Parent root = fxmlLoader.load();
            ((SDMDeleteItemFromStorePageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMDeleteItemFromStorePageController)fxmlLoader.getController()).fillStoresNames();

            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

        } catch (Exception e) {
            errorPopup();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        this.primaryStage.close();
    }

    @FXML
    void loadXmlFile(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
            File xmlFile = fileChooser.showOpenDialog(this.primaryStage);
            if(xmlFile == null){
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoadFile/LoadFile.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            this.logic.loadFile(xmlFile, fxmlLoader.getController(), this.primaryStage, this.prevPage);

        } catch (Exception e) {
            errorPopup();
        }
    }

    private void errorPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Unknown error occurred");
        alert.show();
    }

    @FXML
    void onShowMap(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMShowMapPage/SDMShowMapPage.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            ((SDMShowMapPageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMShowMapPageController)fxmlLoader.getController()).buildMap();

        }catch (Exception e){
            errorPopup();
        }
    }

    @FXML
    void showOrderHistory(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMShowOrderHistory/SDMShowOrderHistoryPage.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            ((SDMShowOrderHistoryController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMShowOrderHistoryController)fxmlLoader.getController()).setOrderHistory(this.logic.getOrdersHistory());

            ((SDMShowOrderHistoryController)fxmlLoader.getController()).showOrderHistory();
        }catch (Exception e){
            errorPopup();
        }
    }

    @FXML
    void showStores(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMShowStoresPage/SDMShowStoresPage.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            Accordion accordion = ((Accordion)((AnchorPane)((BorderPane)((ScrollPane)root).getContent()).getCenter()).getChildren().get(0));

            SDMShowStoresPage.UIAdapter uiAdapter = new SDMShowStoresPage.UIAdapter(accordion);
            this.logic.showAllStores(uiAdapter);
        }catch (Exception e){
            errorPopup();
        }
    }

    @FXML
    void showSystemItems(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMShowItemsPage/SDMShowItemsPage.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            Accordion accordion = ((Accordion)((AnchorPane)((BorderPane)((ScrollPane)root).getContent()).getCenter()).getChildren().get(0));

            SDMShowItemsPage.UIAdapter uiAdapter = new SDMShowItemsPage.UIAdapter(accordion);
            this.logic.showAllItems(uiAdapter);
        }catch (Exception e){
            errorPopup();
        }
    }

    @FXML
    void showCostumers(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMShowCustomersPage/SDMShowCustomersPage.fxml"));
            Parent root = fxmlLoader.load();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

            Accordion accordion = ((Accordion)((AnchorPane)((BorderPane)((ScrollPane)root).getContent()).getCenter()).getChildren().get(0));

            SDMShowCustomersPage.UIAdapter uiAdapter = new SDMShowCustomersPage.UIAdapter(accordion);
            this.logic.showAllCustomers(uiAdapter);
        }catch (Exception e){
            errorPopup();
        }
    }

    @FXML
    void updateItemPriceInStore(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMUpdateItemInStorePage/SDMUpdateItemInStorePage.fxml"));
            Parent root = fxmlLoader.load();
            ((SDMUpdateItemInStorePageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMUpdateItemInStorePageController)fxmlLoader.getController()).fillStoresNames();

            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

        } catch (Exception e) {
            errorPopup();
        }
    }

    @FXML
    void onNewOrderClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMNewOrderPage/SDMNewOrderPage.fxml"));
            Parent root = fxmlLoader.load();
            ((SDMNewOrderPageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMNewOrderPageController)fxmlLoader.getController()).fillDetails();
            ((SDMNewOrderPageController)fxmlLoader.getController()).setStyleSheet(this.currentStyleSheet);
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

        } catch (Exception e) {
            errorPopup();
        }
    }

    @FXML
    void onBlueModeSelected(ActionEvent event) {
        if(this.primaryStage.getScene().getStylesheets().size() != 0) {
            this.primaryStage.getScene().getStylesheets().remove(0);
        }
        this.primaryStage.getScene().getStylesheets().add(getClass().getResource("BlueMode.css").toExternalForm());
        this.currentStyleSheet.set(this.primaryStage.getScene().getStylesheets().get(0));
    }

    @FXML
    void onDarkModeSelected(ActionEvent event) {
        if(this.primaryStage.getScene().getStylesheets().size() != 0) {
            this.primaryStage.getScene().getStylesheets().remove(0);
        }
        this.primaryStage.getScene().getStylesheets().add(getClass().getResource("DarkMode.css").toExternalForm());
        this.currentStyleSheet.setValue(this.primaryStage.getScene().getStylesheets().get(0));
    }

    @FXML
    void onLightModeSelected(ActionEvent event) {
        if(this.primaryStage.getScene().getStylesheets().size() != 0) {
            this.primaryStage.getScene().getStylesheets().clear();
        }
        this.currentStyleSheet.set("");
    }

    @FXML
    void onAddDealPressed(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMAddNewDealPage/SDMAddNewDealPage.fxml"));
            Parent root = fxmlLoader.load();
            ((SDMAddNewDealPageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMAddNewDealPageController)fxmlLoader.getController()).fillStores();
            ((BorderPane) (primaryStage.getScene().getRoot())).setCenter(root);

        } catch (Exception e) {
            errorPopup();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.prevPage = ((BorderPane) (primaryStage.getScene().getRoot())).getCenter();
    }

    public void setLogic(SDMSystem sdmSystem) {
        this.logic = sdmSystem;
        bindToLogic();
    }

    private void bindToLogic() {
        if (this.logic != null) {
            showButton.disableProperty().bind(logic.isValidFileProperty().not());
            orderButton.disableProperty().bind(logic.isValidFileProperty().not());
            itemsButton.disableProperty().bind(logic.isValidFileProperty().not());
            dealsButton.disableProperty().bind(logic.isValidFileProperty().not());
        }
    }

    public void setStyleSheet(String styleSheet){
        this.currentStyleSheet.set(styleSheet);
    }
}