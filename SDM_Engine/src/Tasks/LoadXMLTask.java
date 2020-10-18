package Tasks;

import SDMSystem.SDMSystem;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jaxbClasses.SuperDuperMarketDescriptor;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class LoadXMLTask extends Task<Boolean> {

    private final long SLEEP_TIME = 100;
    private final long TOTAL_WORK = 80;
    private int progressCounter = 0;
    private File xmlFile;
    private SDMSystem currentSystem;
    private Stage primaryStage;
    private Node prevRoot;

    public LoadXMLTask(SDMSystem currentSystem, File xmlFile, Stage primaryStage, Node prevRoot){
        this.currentSystem = currentSystem;
        this.xmlFile = xmlFile;
        this.primaryStage = primaryStage;
        this.prevRoot = prevRoot;
    }

    @Override
    protected Boolean call() {
        try {
            updateMessage("Analyzing XML File...");
            updateProgress(progressCounter, TOTAL_WORK);
            JAXBContext jaxbContent = JAXBContext.newInstance(SuperDuperMarketDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContent.createUnmarshaller();
            SuperDuperMarketDescriptor sdm = (SuperDuperMarketDescriptor) jaxbUnmarshaller.unmarshal(this.xmlFile);
            increaseProgress();
            SDMSystem newSystemToTest = new SDMSystem();
            extractSDMClasses(newSystemToTest, sdm);
            newSystemToTest.checkIfAllItemsHaveSeller();
            currentSystem.setNewSystemValues(newSystemToTest);
            currentSystem.setValidFile(true);
            updateMessage("Finished Successfully!");
            this.updateValue(Boolean.TRUE);
        }catch (Exception e){
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();

                ((BorderPane)(this.primaryStage.getScene().getRoot())).setCenter(this.prevRoot);
            });

            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void increaseProgress() {
        for (int i = 0; i < 20; ++i) {
            updateProgress(++progressCounter, TOTAL_WORK);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
            }
        }
    }

    private void extractSDMClasses(SDMSystem newSystem, SuperDuperMarketDescriptor sdm) throws NoSuchAttributeException {
        updateMessage("Extracting System Items...");
        newSystem.extractSDMItems(sdm.getSDMItems());
        increaseProgress();
        updateMessage("Extracting System Stores...");
        newSystem.extractSDMStores(sdm.getSDMStores());
        increaseProgress();
        updateMessage("Extracting System Customers...");
        newSystem.extractSDMCustomers(sdm.getSDMCustomers());
        increaseProgress();
    }
}
