package Tasks;

import SDMShowCustomersPage.UIAdapter;
import SDMSystem.SDMSystemCustomer;
import SDMSystem.SDMSystemCustomers;
import javafx.concurrent.Task;

public class ShowCustomersTask extends Task<Boolean> {
    private SDMSystemCustomers sdmSystemCustomers;
    private UIAdapter uiAdapter;

    public ShowCustomersTask(UIAdapter uiAdapter, SDMSystemCustomers sdmSystemCustomers) {
        this.sdmSystemCustomers = sdmSystemCustomers;
        this.uiAdapter = uiAdapter;
    }

    @Override
    protected Boolean call() throws Exception {
        if (this.sdmSystemCustomers.getIterable() != null) {
            for(SDMSystemCustomer customer : this.sdmSystemCustomers.getIterable()){
                this.uiAdapter.addCustomerToShow(customer);
            }
        }

        return Boolean.TRUE;
    }
}