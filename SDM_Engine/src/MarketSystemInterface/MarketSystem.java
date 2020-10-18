package MarketSystemInterface;

import ItemsDetailsContainer.ItemsDetailsContainer;
import LoadFile.LoadFileController;
import OrderConteiner.OrderContainer;
import OrderConteiner.OrdersContainer;
import SDMMainPage.UIAdapter;
import StoresDetailsConteiner.StoresDetailsContainer;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public interface MarketSystem {
    public void loadFile(File xmlFile, LoadFileController loadFileController, Stage primaryStage, Node prevRoot);
    public void fillStoresDetailsContainer(StoresDetailsContainer container);
    public ItemsDetailsContainer getAllItemsDetails();
    public void executeOrder(OrderContainer container);
    public void updateItemPriceInStore(int storeId, int itemId, int itemNewPrice);
    public void addItemToStore(int storeId, int itemId, int itemPrice);
    public void deleteItemFromStore(int storeId, int itemId);
    public void exitSystem();
}
