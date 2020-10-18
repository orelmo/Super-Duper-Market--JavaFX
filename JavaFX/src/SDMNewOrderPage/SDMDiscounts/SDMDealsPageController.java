package SDMNewOrderPage.SDMDiscounts;

import EngineClasses.Order.Order;
import EngineClasses.Store.Discount.Discount;
import EngineClasses.Store.Discount.Offer;
import EngineClasses.Store.Store;
import OrderConteiner.ItemsPerStoreContainer;
import OrderConteiner.OrderContainer;
import SDMSystem.SDMSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDMDealsPageController {

    @FXML
    private FlowPane storesDealsFlowPane;

    private Map<Store, List<SDMStoreDealsController>> storeToStoreDealsController = new HashMap<>();
    private OrderContainer orderContainer;
    private SDMSystem logic;
    private Stage dealsPageStage;

    public void setDealsPageStage(Stage dealsPageStage) {
        this.dealsPageStage = dealsPageStage;
    }

    @FXML
    void onApplyDeals(ActionEvent event) {
        OrderContainer dealsContainer = getSelectedDeals();
        this.orderContainer.setStoreIdToDeals(dealsContainer.getStoreIdToSeller());
        this.orderContainer.setItemsPrice(this.orderContainer.getItemsPrice() + dealsContainer.getItemsPrice());
        this.orderContainer.setTotalOrderPrice(this.orderContainer.getTotalOrderPrice() + dealsContainer.getTotalOrderPrice());
        this.dealsPageStage.close();
    }

    public void setOrderContainer(OrderContainer orderContainer) {
        this.orderContainer = orderContainer;
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public OrderContainer getSelectedDeals() {
        OrderContainer dealsOrderContainer = new OrderContainer();
        analyzeDealsSelection(dealsOrderContainer);
        return dealsOrderContainer;
    }

    private void analyzeDealsSelection(OrderContainer dealsOrderContainer) {
        for (Store store : this.storeToStoreDealsController.keySet()) {
            ItemsPerStoreContainer itemsPerStoreContainer = new ItemsPerStoreContainer();
            for (SDMStoreDealsController dealsController : this.storeToStoreDealsController.get(store)) {
                itemsPerStoreContainer.addItems(dealsController.analyzeStoreDeals());
            }
            dealsOrderContainer.addItemsPerStoreContainer(store.getId(), itemsPerStoreContainer);
        }
    }

    public void showDeals() {
        List<Discount> discounts = null;
        boolean isEmptyDeals = true;
        for(Integer storeId : orderContainer.getStoreIdToSeller().keySet()){
            discounts = this.logic.getRelevantDeals(storeId, orderContainer.getStoreIdToSeller().get(storeId));
            for(Discount discount : discounts){
                isEmptyDeals = false;
                addDealToPage(this.logic.getStore(storeId),discount);
            }
        }

        if(isEmptyDeals){
            addEmptyDealsMessage();
        }
    }

    private void addEmptyDealsMessage() {
        Text emptyDealsText = new Text("There are no deals to offer");
        emptyDealsText.setFont(Font.font(20));
        this.storesDealsFlowPane.getChildren().add(emptyDealsText);
    }

    private void addDealToPage(Store store, Discount discount) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMNewOrderPage/SDMDiscounts/SDMStoreDeals.fxml"));
            Parent root = fxmlLoader.load();
            if(this.storeToStoreDealsController.containsKey(store)){
                this.storeToStoreDealsController.get(store).add(fxmlLoader.getController());
            } else{
                List<SDMStoreDealsController> dealsPageControllerList = new ArrayList<>();
                dealsPageControllerList.add(fxmlLoader.getController());
                this.storeToStoreDealsController.put(store, dealsPageControllerList);
            }
            ((SDMStoreDealsController)fxmlLoader.getController()).setLogic(this.logic);
            ((SDMStoreDealsController)fxmlLoader.getController()).buildDeal(store, discount);
            this.storesDealsFlowPane.getChildren().add(root);
        }catch (Exception e){
            errorPopup();
        }
    }

    private void errorPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("FXML error occurred");
        alert.show();
    }
}