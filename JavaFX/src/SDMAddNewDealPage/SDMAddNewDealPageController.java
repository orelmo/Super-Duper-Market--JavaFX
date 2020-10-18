package SDMAddNewDealPage;

import EngineClasses.Item.ePurchaseCategory;
import EngineClasses.Store.Discount.*;
import EngineClasses.Store.Sell;
import EngineClasses.Store.Store;
import Exceptions.IdAmbiguityException;
import SDMSystem.SDMSystem;
import SDMSystem.SDMSystemItem;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class SDMAddNewDealPageController {

    @FXML
    private ComboBox<Store> storesComboBox;

    @FXML
    private ComboBox<SDMSystemItem> itemsComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private RadioButton irrelevantRadioButton;

    @FXML
    private ToggleGroup dealCategoryToggleGroup;

    @FXML
    private RadioButton allOrNothingRadioButton;

    @FXML
    private RadioButton oneOfRadioButton;

    @FXML
    private ScrollPane benefitsScrollPane;

    @FXML
    private TextField dealNameTextField;

    @FXML
    private GridPane benefitGridPane;

    @FXML
    private Button finishButton;

    private BooleanProperty isValidBenefit = new SimpleBooleanProperty(false);
    private SDMSystem logic;
    private ToggleGroup itemToggleGroup = new ToggleGroup();


    @FXML
    private void initialize() {
        this.benefitsScrollPane.setStyle("-fx-background-color:transparent;");
        this.amountTextField.setText("0");
        this.amountTextField.disableProperty().bind(this.itemsComboBox.valueProperty().isNull());
        this.benefitGridPane.disableProperty().bind(this.dealCategoryToggleGroup.selectedToggleProperty().isNull().or(this.allOrNothingRadioButton.disabledProperty()));
        this.finishButton.disableProperty().bind(this.isValidBenefit.not().
                or(this.storesComboBox.valueProperty().isNull().
                        or(this.itemsComboBox.valueProperty().isNull().
                                or(this.amountTextField.textProperty().isEmpty().
                                        or(this.dealNameTextField.textProperty().isEmpty())))));
        this.itemsComboBox.disableProperty().bind(this.storesComboBox.valueProperty().isNull());
        this.allOrNothingRadioButton.disableProperty().bind(this.amountTextField.textProperty().isEqualTo("0"));
        this.irrelevantRadioButton.disableProperty().bind(this.amountTextField.textProperty().isEqualTo("0"));
        this.oneOfRadioButton.disableProperty().bind(this.amountTextField.textProperty().isEqualTo("0"));
        this.dealNameTextField.disableProperty().bind(this.storesComboBox.valueProperty().isNull());
        this.dealCategoryToggleGroup.getToggles().forEach((t) -> {
            ((RadioButton) (t)).setOnAction(this::fillBenefitItems);
        });
    }

    private void fillBenefitItems(ActionEvent event) {
        if (this.itemToggleGroup.selectedToggleProperty().isNotNull().getValue()){
            this.itemToggleGroup.getSelectedToggle().setSelected(false);
        }

        clearBenefits();

        this.isValidBenefit.set(false);
        int itemRow = 1;
        for(SDMSystemItem item : this.itemsComboBox.getItems()){
            if(this.dealCategoryToggleGroup.getSelectedToggle().equals(this.irrelevantRadioButton)){
                RadioButton itemRadioButton = new RadioButton(item.getName() + ", id: " + item.getId() + ", category: " + item.getPurchaseCategory());
                itemRadioButton.setToggleGroup(this.itemToggleGroup);
                itemRadioButton.setOnAction(this::checkBenefitValidation);
                this.benefitGridPane.getChildren().add(itemRadioButton);
                GridPane.setRowIndex(itemRadioButton, itemRow);
                GridPane.setColumnIndex(itemRadioButton, 0);

            }else{
                CheckBox itemCheckBox = new CheckBox(item.getName() + ", id: " + item.getId() + ", category: " + item.getPurchaseCategory());
                itemCheckBox.setOnAction(this::checkBenefitValidation);
                this.benefitGridPane.getChildren().add(itemCheckBox);
                GridPane.setRowIndex(itemCheckBox, itemRow);
                GridPane.setColumnIndex(itemCheckBox, 0);
            }
            TextField amountTextField = new TextField();
            amountTextField.setText("0");
            amountTextField.setOnKeyReleased(this::checkBenefitValidation);
            if(item.getPurchaseCategory().equals(ePurchaseCategory.Quantity)){
                amountTextField.setOnKeyTyped(this::onQuantityAmountKeyTyped);
            }else{
                amountTextField.setOnKeyTyped(this::onWeightAmountKeyTyped);
            }
            this.benefitGridPane.getChildren().add(amountTextField);
            GridPane.setRowIndex(amountTextField, itemRow);
            GridPane.setColumnIndex(amountTextField, 1);

            TextField priceTextField = new TextField();
            priceTextField.setText("0");
            priceTextField.setOnKeyTyped(this::onQuantityAmountKeyTyped);
            this.benefitGridPane.getChildren().add(priceTextField);
            GridPane.setRowIndex(priceTextField, itemRow);
            GridPane.setColumnIndex(priceTextField, 2);

            itemRow++;
        }
    }

    private void checkBenefitValidation(Event event) {
        this.isValidBenefit.set(false);
        int counter = 0;
        for(Node node : this.benefitGridPane.getChildren()){
            if(node instanceof  RadioButton){
                if(((RadioButton)node).isSelected()){
                    int rowIndex = GridPane.getRowIndex(node);
                    TextField amountTextField = ((TextField) getNodeFromBenefitGridPaneByIndex(rowIndex, 1));
                    if(amountTextField.getText().equals("0")==false){
                        this.isValidBenefit.set(true);
                        return;
                    }
                }
            }else if(node instanceof CheckBox){
                if(((CheckBox)node).isSelected()) {
                    int rowIndex = GridPane.getRowIndex(node);
                    TextField amountTextField = ((TextField) getNodeFromBenefitGridPaneByIndex(rowIndex, 1));
                    if (amountTextField.getText().equals("0") == false) {
                        counter++;
                    }
                    if (counter >= 2) {
                        this.isValidBenefit.set(true);
                        return;
                    }
                }
            }
        }
    }

    private Node getNodeFromBenefitGridPaneByIndex(int rowIndex, int colIndex) {
        for(Node node : this.benefitGridPane.getChildren()){
            if(GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == colIndex){
                return node;
            }
        }

        return null;
    }

    @FXML
    void onFinishPressed(ActionEvent event) {
        try{
            checkIsValidDealName();

            Discount newDeal = buildNewDiscount();

            this.logic.addNewDiscountToStore(this.storesComboBox.getValue(), newDeal);

            resetPage();

        }catch(Exception e){
            errorPopup(e.getMessage());
        }
    }

    private void resetPage() {
        this.storesComboBox.valueProperty().set(null);
        this.dealNameTextField.setText("");
        this.itemsComboBox.getItems().clear();
        this.amountTextField.setText("0");
        this.dealCategoryToggleGroup.getSelectedToggle().setSelected(false);
        clearBenefits();
    }

    private void checkIsValidDealName() throws InvalidNameException {
        String dealName = this.dealNameTextField.getText();
        dealName = dealName.trim();

        if(dealName.length() == 0){
            this.dealNameTextField.setText("");
            throw new InvalidNameException("Deal name can't be empty");
        } else if(this.storesComboBox.getValue().isDealNameExist(dealName)){
            this.dealNameTextField.setText("");
            throw new IdAmbiguityException("Deal name is taken");
        }
    }

    private void errorPopup(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private Discount buildNewDiscount() {
        Discount deal = new Discount();

        IfYouBuy ifYouBuy = new IfYouBuy();
        ifYouBuy.setItemIdNeedToBeBought(this.itemsComboBox.getValue().getId());
        ifYouBuy.setQuantityNeedToBeBought(Double.parseDouble(this.amountTextField.getText()));
        deal.setIfYouBuy(ifYouBuy);

        ThenYouGet thenYouGet = new ThenYouGet();
        switch (((RadioButton)this.dealCategoryToggleGroup.getSelectedToggle()).getText()){
            case "Irrelevant": {
                thenYouGet.setOperator(eOperator.IRRELEVANT);

                Offer offer = buildOfferFromBenefitsGridPane((Node)this.itemToggleGroup.getSelectedToggle());
                thenYouGet.getOptionalBenefitItems().add(offer.getItemId(), offer);
                break;
            } case "One Of":{
                thenYouGet.setOperator(eOperator.ONE_OF);

                for(Node node : this.benefitGridPane.getChildren()){
                    int rowIndex = GridPane.getRowIndex(node);
                    if(node instanceof CheckBox && ((CheckBox)node).isSelected() &&
                            ((TextField)getNodeFromBenefitGridPaneByIndex(rowIndex, 1)).getText().equals("0") == false){
                        Offer offer = buildOfferFromBenefitsGridPane(node);
                        thenYouGet.getOptionalBenefitItems().add(offer.getItemId(), offer);
                    }
                }
                break;
            } case "All Or Nothing":{
                thenYouGet.setOperator(eOperator.ALL_OR_NOTHING);

                for(Node node : this.benefitGridPane.getChildren()){
                    int rowIndex = GridPane.getRowIndex(node);
                    if(node instanceof CheckBox && ((CheckBox)node).isSelected() &&
                            ((TextField)getNodeFromBenefitGridPaneByIndex(rowIndex, 1)).getText().equals("0") == false){
                        Offer offer = buildOfferFromBenefitsGridPane(node);
                        thenYouGet.getOptionalBenefitItems().add(offer.getItemId(), offer);
                    }
                }
                break;
            } default:{
                break;
            }
        }

        deal.setThenYouGet(thenYouGet);

        deal.setName(this.dealNameTextField.getText().trim());

        return deal;
    }

    private Offer buildOfferFromBenefitsGridPane(Node node){
        int itemId;
        if(node instanceof RadioButton){
            itemId = getItemIdFromText(((RadioButton)node).getText());
        } else {
            itemId = getItemIdFromText(((CheckBox)node).getText());
        }
        int itemRow = GridPane.getRowIndex(node);
        double itemAmount = Double.parseDouble(((TextField)getNodeFromBenefitGridPaneByIndex(itemRow, 1)).getText());
        int itemPrice = Integer.parseInt(((TextField)getNodeFromBenefitGridPaneByIndex(itemRow, 2)).getText());

        return new Offer(itemAmount, itemId, itemPrice);
    }

    private int getItemIdFromText(String text) {
        String[] strings = text.split("id: ");
        String[] strings1 = strings[1].split(",");

        return Integer.parseInt(strings1[0]);
    }

    @FXML
    void onItemSelected(ActionEvent event) {
        if(this.itemsComboBox.valueProperty().isNull().getValue()){
            return;
        }

        this.amountTextField.setText("0");

        clearBenefits();

        if (this.dealCategoryToggleGroup.getSelectedToggle() != null) {
            this.dealCategoryToggleGroup.getSelectedToggle().setSelected(false);
        }
        this.isValidBenefit.set(false);
        if (this.itemsComboBox.valueProperty().isNull().getValue()) {
            return;
        }
        if (this.itemsComboBox.valueProperty().get().getPurchaseCategory().equals(ePurchaseCategory.Quantity)) {
            this.amountTextField.setOnKeyTyped(this::onQuantityAmountKeyTyped);
        } else {
            this.amountTextField.setOnKeyTyped(this::onWeightAmountKeyTyped);
        }
    }

    private void onWeightAmountKeyTyped(KeyEvent keyEvent) {
        if (((TextField) keyEvent.getSource()).getText().isEmpty()) {
            ((TextField) keyEvent.getSource()).setText("0");
            ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
        }

        if (keyEvent.getCharacter().charAt(0) != 8 && keyEvent.getCharacter().charAt(0) != 127) {
            if ((Character.isDigit(keyEvent.getCharacter().charAt(0)) == false && keyEvent.getCharacter().charAt(0) != '.') ||
                    (keyEvent.getCharacter().charAt(0) == '.' && ((TextField) keyEvent.getSource()).getText().contains("."))) {
                keyEvent.consume();
            } else {
                if (((TextField) keyEvent.getSource()).getText().equals("0")) {
                    if(keyEvent.getCharacter().charAt(0) == '.'){
                        ((TextField) keyEvent.getSource()).setText("0.");
                    } else {
                        ((TextField) keyEvent.getSource()).setText(String.valueOf(keyEvent.getCharacter().charAt(0)));
                    }
                    ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
                    keyEvent.consume();
                }
            }
        }
    }

    private void onQuantityAmountKeyTyped(KeyEvent keyEvent) {
        if (((TextField) keyEvent.getSource()).getText().isEmpty()) {
            ((TextField) keyEvent.getSource()).setText("0");
            ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
        }

        if (keyEvent.getCharacter().charAt(0) != 8 && keyEvent.getCharacter().charAt(0) != 127) {
            if (Character.isDigit(keyEvent.getCharacter().charAt(0)) == false) {
                keyEvent.consume();
            } else {
                if (((TextField) keyEvent.getSource()).getText().equals("0")) {
                    ((TextField) keyEvent.getSource()).setText(String.valueOf(keyEvent.getCharacter().charAt(0)));
                    ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
                    keyEvent.consume();
                }
            }
        }
    }

    @FXML
    void onStoreSelected(ActionEvent event) {
        if(this.storesComboBox.valueProperty().isNull().getValue()){
            return;
        }
        this.dealNameTextField.setText("");
        if(this.dealCategoryToggleGroup.getSelectedToggle()!=null) {
            this.dealCategoryToggleGroup.getSelectedToggle().setSelected(false);
        }
        this.isValidBenefit.set(false);

        clearBenefits();
        this.itemsComboBox.getItems().clear();

        for(Sell sell : this.storesComboBox.getValue().getStoreItems().getIterable()){
            this.itemsComboBox.getItems().add(new SDMSystemItem(this.logic.getItem(sell.getItemId())));
        }
    }

    private void clearBenefits() {
        List<Node> nodeList = new ArrayList<>();
        for(Node node : this.benefitGridPane.getChildren()){
            if(GridPane.getRowIndex(node) != 0){
                nodeList.add(node);
            }
        }

        this.benefitGridPane.getChildren().removeAll(nodeList);
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void fillStores() {
        this.storesComboBox.getItems().addAll(this.logic.getStores());
    }
}