package SDMDeleteItemFromStorePage;

import SDMSystem.SDMSystem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class SDMDeleteItemFromStorePageController {

    @FXML
    private VBox storesVbox;

    @FXML
    private VBox itemsVbox;

    @FXML
    private Button submitButton;

    private SDMSystem logic;
    private UIAdapter uiAdapter;
    private int selectedStoreId;
    private int selectedItemId;
    private BooleanProperty isStoreSelected = new SimpleBooleanProperty(false);
    private BooleanProperty isItemSelected = new SimpleBooleanProperty(false);

    @FXML
    private void initialize() {
        this.itemsVbox.visibleProperty().bind(isStoreSelected);
        this.submitButton.disableProperty().bind(isItemSelected.not());

        this.uiAdapter = new UIAdapter(storesVbox, itemsVbox, this);
    }

    void onStoreSelected(ActionEvent event) {
        this.isStoreSelected.setValue(true);
        this.isItemSelected.setValue(false);
        this.itemsVbox.getChildren().clear();
        this.selectedStoreId = extractId(((RadioButton) event.getSource()).getText());
        this.logic.fillDeletableItemsPerStore(selectedStoreId, this.uiAdapter);
    }

    private int extractId(String text) {
        String[] tokens = text.split("Id: ");
        return Integer.parseInt(tokens[1]);
    }

    void onItemSelected(ActionEvent event) {
        this.isItemSelected.setValue(true);
        this.selectedItemId = extractId(((RadioButton) event.getSource()).getText());
    }

    @FXML
    void submitPressed(ActionEvent event) {
        try {
            this.logic.deleteItemFromStore(selectedStoreId, selectedItemId);
            this.isItemSelected.setValue(false);
            this.itemsVbox.getChildren().clear();
            this.logic.fillDeletableItemsPerStore(selectedStoreId, this.uiAdapter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unknown error occurred");
            alert.show();
        }
    }

    public void fillStoresNames() {
        this.logic.fillStoresNamesToUiAdapter(this.uiAdapter);
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }
}