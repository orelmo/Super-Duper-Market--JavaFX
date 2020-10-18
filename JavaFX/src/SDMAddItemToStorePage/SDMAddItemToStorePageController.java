package SDMAddItemToStorePage;

import SDMSystem.SDMSystem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class SDMAddItemToStorePageController {

    @FXML
    private VBox storesVbox;
    @FXML
    private VBox itemsVbox;
    @FXML
    private TextField priceTextField;
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
        this.priceTextField.disableProperty().bind(isItemSelected.not());
        this.submitButton.disableProperty().bind(priceTextField.textProperty().isEmpty());

        this.uiAdapter = new UIAdapter(storesVbox, itemsVbox, this);
    }

    void onStoreSelected(ActionEvent event) {
        this.isStoreSelected.setValue(true);
        this.isItemSelected.setValue(false);
        this.itemsVbox.getChildren().clear();
        this.selectedStoreId = extractId(((RadioButton) event.getSource()).getText());
        this.logic.fillAddableItemsPerStore(selectedStoreId, this.uiAdapter);
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
            this.logic.addItemToStore(selectedStoreId, selectedItemId, Integer.parseInt(this.priceTextField.getText()));
            this.priceTextField.clear();
            this.isItemSelected.setValue(false);
            this.itemsVbox.getChildren().clear();
            this.logic.fillAddableItemsPerStore(selectedStoreId, this.uiAdapter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (e instanceof NumberFormatException) {
                alert.setContentText("The system doesn't support such high prices!");
            } else {
                alert.setContentText("Unknown error occurred");
            }
            alert.show();
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
        if (Character.isDigit(event.getCharacter().charAt(0)) == false) {
            event.consume();
        }
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void fillStoresNames() {
        this.logic.fillStoresNamesToUiAdapter(uiAdapter);
    }
}