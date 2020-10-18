package SDMNewOrderPage;

import EngineClasses.Item.ePurchaseCategory;
import EngineClasses.Store.Store;
import OrderConteiner.OrderContainer;
import SDMNewOrderPage.SDMDiscounts.SDMDealsPageController;
import SDMNewOrderPage.SDMOrderSummary.SDMOrderSummaryPageController;
import SDMSystem.*;
import StoresDetailsConteiner.StoresDetailsContainer;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SDMNewOrderPageController {
    private static final int NAME_COL_INDEX = 0;
    private static final int ID_COL_INDEX = 1;
    private static final int CATEGORY_COL_INDEX = 2;
    private static final int PRICE_COL_INDEX = 3;
    private static final int AMOUNT_COL_INDEX = 4;
    private static final int TOTAL_PRICE_COL_INDEX = 5;


    @FXML
    private ComboBox<SDMSystemCustomer> customersComboBox;

    @FXML
    private DatePicker arrivalDatePicker;

    @FXML
    private RadioButton staticOrderRadioButton;

    @FXML
    private ToggleGroup orderTypeGroup;

    @FXML
    private RadioButton dynamicOrderRadioButton;

    @FXML
    private ComboBox<Store> storesComboBox;

    @FXML
    private ScrollPane tableViewScrollPane;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private TableView<TableViewItem> itemsTableView;

    @FXML
    private TableColumn<TableViewItem, String> nameColumn;

    @FXML
    private TableColumn<TableViewItem, Integer> idColumn;

    @FXML
    private TableColumn<TableViewItem, ePurchaseCategory> purchaseCategoryColumn;

    @FXML
    private TableColumn<TableViewItem, IntegerProperty> priceColumn;

    @FXML
    private TableColumn<TableViewItem, TextField> amountColumn;

    @FXML
    private TableColumn<TableViewItem, FloatProperty> totalPriceColumn;

    @FXML
    private ComboBox<String> animationsComboBox;

    @FXML
    private Button finishOrderButton;

    @FXML
    private ScrollPane suppliersScrollPane;

    @FXML
    private AnchorPane animationAnchorPane;

    @FXML
    private ImageView bottleImage;

    @FXML
    private Accordion suppliersAccordion;

    private SDMSystem logic;
    private LocalDate arrivalDate;
    private SDMSystemCustomer selectedCustomer;
    private Store selectedStore;
    private UIAdapter uiAdapter;
    private StoresDetailsContainer dynamicOrderSummary = new StoresDetailsContainer();
    private OrderContainer orderContainer = new OrderContainer();
    private OrderContainer dealsContainer = new OrderContainer();
    private SDMDealsPageController dealsPageController;
    private BooleanProperty isEmptyOrder = new SimpleBooleanProperty(true);
    private boolean isApprovalOrder = false;
    private StringProperty styleSheet;

    @FXML
    private void initialize() {
        this.staticOrderRadioButton.disableProperty().bind(this.customersComboBox.valueProperty().isNull());
        this.dynamicOrderRadioButton.disableProperty().bind(this.customersComboBox.valueProperty().isNull());
        this.storesComboBox.disableProperty().bind(this.staticOrderRadioButton.selectedProperty().not().or(
                this.customersComboBox.valueProperty().isNull()));
        this.finishOrderButton.disableProperty().bind(
                this.isEmptyOrder.or(
                        this.customersComboBox.valueProperty().isNull().or(
                                this.arrivalDatePicker.valueProperty().isNull().or(
                                        this.staticOrderRadioButton.selectedProperty().and(
                                                this.storesComboBox.valueProperty().isNull()
                                        )
                                )
                        )
                )
        );
        this.deliveryPriceLabel.visibleProperty().bind
                (this.staticOrderRadioButton.selectedProperty().and(this.storesComboBox.valueProperty().isNotNull()));
        this.suppliersScrollPane.visibleProperty().bind(this.dynamicOrderRadioButton.selectedProperty());

        bindTableViewColumnsToItem();

        this.uiAdapter = new UIAdapter(this.storesComboBox, this.customersComboBox, this.itemsTableView,
                this.suppliersAccordion, this);

        this.animationsComboBox.getItems().addAll("On", "Off");
        this.animationsComboBox.setValue("On");
    }

    private void bindTableViewColumnsToItem() {
        this.itemsTableView.getColumns().get(NAME_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("name"));
        this.itemsTableView.getColumns().get(ID_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("id"));
        this.itemsTableView.getColumns().get(CATEGORY_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("purchaseCategory"));
        this.itemsTableView.getColumns().get(PRICE_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("price"));
        this.itemsTableView.getColumns().get(AMOUNT_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("amount"));
        this.itemsTableView.getColumns().get(TOTAL_PRICE_COL_INDEX).setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    @FXML
    void onCustomerSelected(ActionEvent event) {
        this.selectedCustomer = this.customersComboBox.getValue();
    }

    @FXML
    void onDateSelected(ActionEvent event) {
        this.arrivalDate = this.arrivalDatePicker.getValue();
    }

    @FXML
    void onDynamicOrder(ActionEvent event) {
        this.storesComboBox.getSelectionModel().clearSelection();
        this.itemsTableView.getColumns().remove(this.totalPriceColumn);
        this.itemsTableView.getColumns().remove(this.priceColumn);
        this.itemsTableView.getItems().clear();
        this.dynamicOrderSummary = new StoresDetailsContainer();
        this.suppliersAccordion.getPanes().clear();
        GridPane.setColumnSpan(this.tableViewScrollPane, 2);
        this.logic.showAllItems(this.uiAdapter);
    }

    public void setApprovalOrder(boolean approvalOrder) {
        this.isApprovalOrder = approvalOrder;
    }

    @FXML
    void onFinishOrder(ActionEvent event) {
        analyzeOrderDetails();

        offerDeals();

        showOrderSummary();

        if (this.isApprovalOrder) {
            this.logic.executeOrder(this.orderContainer);
        }

        resetPage();
    }

    private void resetPage() {
        this.customersComboBox.getSelectionModel().clearSelection();
        this.dynamicOrderRadioButton.selectedProperty().setValue(false);
        this.staticOrderRadioButton.selectedProperty().setValue(false);
        this.arrivalDatePicker.setValue(null);
        this.storesComboBox.getSelectionModel().clearSelection();
        this.itemsTableView.getItems().clear();
        this.suppliersAccordion.getPanes().clear();
        this.dynamicOrderSummary = new StoresDetailsContainer();
        this.isApprovalOrder = false;

        if (this.itemsTableView.getColumns().contains(this.priceColumn) == false) {
            this.itemsTableView.getColumns().add(3, this.priceColumn);
        }
        GridPane.setColumnSpan(this.tableViewScrollPane, 3);
    }

    private void showOrderSummary() {
        Stage summaryStage = new Stage();
        summaryStage.getIcons().add(new Image("/GUI/Resources/cartIcon.png"));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMNewOrderPage/SDMOrderSummary/SDMOrderSummaryPage.fxml"));
            Parent root = fxmlLoader.load();
            summaryStage.setScene(new Scene(root));
            summaryStage.setTitle("Order Summary");
            if(summaryStage.getScene().getStylesheets().size() != 0){
                summaryStage.getScene().getStylesheets().remove(0);
            }
            summaryStage.getScene().getStylesheets().add(this.styleSheet.getValue());
            ((SDMOrderSummaryPageController)fxmlLoader.getController()).setOrderPageController(this);
            ((SDMOrderSummaryPageController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMOrderSummaryPageController)fxmlLoader.getController()).setOrderContainer(this.orderContainer);
            ((SDMOrderSummaryPageController)fxmlLoader.getController()).setSummaryStage(summaryStage);
            ((SDMOrderSummaryPageController)fxmlLoader.getController()).buildOrderSummaryPage();
            summaryStage.initModality(Modality.APPLICATION_MODAL);
            summaryStage.showAndWait();
        }catch (Exception e){
            errorPopup();
        }
    }

    private void analyzeOrderDetails() {
        this.orderContainer = new OrderContainer();
        this.orderContainer.setCustomer(this.selectedCustomer);
        this.orderContainer.setArrivalDate(this.arrivalDate);
        this.orderContainer.setArrivalLocation(this.selectedCustomer.getLocation());
        Map<Integer, Number> itemsIdToAmount = new HashMap<>();
        for (TableViewItem item : this.itemsTableView.getItems()) {
            if (item.getAmountValue().floatValue() != 0) {
                itemsIdToAmount.put(item.getId(), item.getAmountValue());
                if(this.staticOrderRadioButton.isSelected()) {
                    this.orderContainer.setItemsPrice(this.orderContainer.getItemsPrice() +
                            item.getAmountValue().floatValue() * item.getPrice());
                } else {
                    Store cheapestStore = this.logic.getCheapestSellerForItem(item.getId());
                    this.orderContainer.setItemsPrice(this.orderContainer.getItemsPrice() +
                            item.getAmountValue().floatValue() * cheapestStore.getItemPrice(item.getId()));
                }
            }
        }

        if (this.staticOrderRadioButton.isSelected()) {
            for(Integer itemId : itemsIdToAmount.keySet()){
                this.logic.addItemToSellerForOrderContainer(this.orderContainer,this.selectedStore, itemId, itemsIdToAmount.get(itemId),this.selectedCustomer);
            }
            this.orderContainer.setDeliveryPrice(this.logic.getDeliveryPrice(this.selectedStore, this.selectedCustomer));
        } else {
            Store cheapestStore;
            for (Integer itemId : itemsIdToAmount.keySet()) {
                cheapestStore = this.logic.getCheapestSellerForItem(itemId);
                if(orderContainer.getStoreIdToSeller().keySet().contains(cheapestStore.getId()) == false) {
                    this.orderContainer.setDeliveryPrice(this.orderContainer.getDeliveryPrice() +
                            this.logic.getDeliveryPrice(cheapestStore, this.selectedCustomer));
                }
                this.logic.addItemToSellerForOrderContainer(orderContainer, cheapestStore, itemId, itemsIdToAmount.get(itemId), this.selectedCustomer);
            }
        }

        this.orderContainer.setTotalUnits(this.logic.analyzeOrderContainerTotalUnits(this.orderContainer));
        this.orderContainer.setTotalOrderPrice(this.orderContainer.getDeliveryPrice() + this.orderContainer.getItemsPrice());
    }

    private void offerDeals() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMNewOrderPage/SDMDiscounts/SDMDealsPage.fxml"));
            Parent root = fxmlLoader.load();

            Stage dealsPopUpStage = new Stage();
            dealsPopUpStage.setScene(new Scene(root));
            dealsPopUpStage.setTitle("Hot Deals");
            dealsPopUpStage.getIcons().add(new Image("/GUI/Resources/cartIcon.png"));
            if(dealsPopUpStage.getScene().getStylesheets().size() != 0){
                dealsPopUpStage.getScene().getStylesheets().remove(0);
            }
            dealsPopUpStage.getScene().getStylesheets().add(this.styleSheet.getValue());
            dealsPopUpStage.initModality(Modality.APPLICATION_MODAL);
            this.dealsPageController = (SDMDealsPageController)fxmlLoader.getController();
            this.dealsPageController.setLogic(this.logic);
            this.dealsPageController.setOrderContainer(this.orderContainer);
            this.dealsPageController.setDealsPageStage(dealsPopUpStage);
            this.dealsPageController.showDeals();
            dealsPopUpStage.showAndWait();
        } catch (Exception e) {
            errorPopup();
        }
    }

    @FXML
    void onStaticOrder(ActionEvent event) {
        if (this.itemsTableView.getColumns().contains(this.priceColumn) == false) {
            this.itemsTableView.getColumns().add(PRICE_COL_INDEX, this.priceColumn);
        }
        if(this.itemsTableView.getColumns().contains(this.totalPriceColumn) == false){
            this.itemsTableView.getColumns().add(TOTAL_PRICE_COL_INDEX, this.totalPriceColumn);
        }
        this.itemsTableView.getItems().clear();
        this.storesComboBox.getSelectionModel().clearSelection();
        this.dynamicOrderSummary = new StoresDetailsContainer();
        this.suppliersAccordion.getPanes().clear();
        GridPane.setColumnSpan(this.tableViewScrollPane, 3);
    }

    @FXML
    void onStoreSelected(ActionEvent event) {
        if (this.storesComboBox.getValue() == null) {
            return;
        }
        this.itemsTableView.getItems().clear();
        this.selectedStore = this.storesComboBox.getValue();
        this.deliveryPriceLabel.setText("Delivery Price: " + this.logic.getDeliveryPrice(this.selectedStore, this.selectedCustomer));

        this.logic.fillAllItemsPerStore(this.selectedStore.getId(), this.uiAdapter);
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void fillDetails() {
        fillCustomers();
        fillStores();
    }

    private void fillStores() {
        this.logic.filStoresForNewOrder(this.uiAdapter);
    }

    private void fillCustomers() {
        this.logic.fillCustomersForNewOrder(this.uiAdapter);
    }

    public void onAmountChanged(TableViewItem tableViewItem) {
        if (this.staticOrderRadioButton.isSelected()) {
            return;
        }

        Store cheapestSeller = this.logic.getCheapestSellerForItem(tableViewItem.getId());
        this.logic.updateSummaryForDynamicOrder(this.dynamicOrderSummary, cheapestSeller, tableViewItem.getId(), tableViewItem.getAmountValue(), this.selectedCustomer, uiAdapter);
    }

    public void updateIsEmptyOrder() {
        for(TableViewItem tableViewItem : this.itemsTableView.getItems()){
            if(tableViewItem.getAmount().getText().equals("0") == false){
                this.isEmptyOrder.setValue(false);
                return;
            }
        }

        this.isEmptyOrder.setValue(true);
    }

    public void setStyleSheet(StringProperty currentStyleSheet) {
        this.styleSheet = currentStyleSheet;
    }

    private void errorPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("FXML error occurred");
        alert.show();
    }

    public void showAnimation() {
        if(this.animationsComboBox.getValue().equals("Off")){
            return;
        }

        this.animationAnchorPane.visibleProperty().set(true);

        ArcTo arc = new ArcTo();
        arc.setX(230);
        arc.setY(90);
        arc.setRadiusX(80);
        arc.setRadiusY(100);
        arc.setSweepFlag(true);

        Path path = new Path();
        path.getElements().addAll(new MoveTo(0, 30), arc);

        PathTransition pathTransition = new PathTransition(Duration.millis(2850), path, this.bottleImage);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> this.animationAnchorPane.visibleProperty().set(false) );
        delay.play();
    }
}