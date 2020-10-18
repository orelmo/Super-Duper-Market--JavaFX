package Tasks;

import SDMShowStoresPage.UIAdapter;
import StoresDetailsConteiner.StoreDetailsContainer;
import StoresDetailsConteiner.StoresDetailsContainer;
import javafx.concurrent.Task;

public class ShowStoresTask extends Task<Boolean> {
    private UIAdapter uiAdapter;
    private StoresDetailsContainer storesDetailsContainer;

    public ShowStoresTask(UIAdapter uiAdapter, StoresDetailsContainer storesDetailsContainer) {
        this.uiAdapter = uiAdapter;
        this.storesDetailsContainer = storesDetailsContainer;
    }

    @Override
    protected Boolean call() throws Exception {
        for(StoreDetailsContainer storeDetailsContainer : this.storesDetailsContainer.getStoresDetailsContainer()){
            this.uiAdapter.addStoreToShow(storeDetailsContainer);
        }

        return Boolean.TRUE;
    }
}
