package SDMSystem;

public enum eMenuOptions {
    LoadFile(1), ShowStoresDetails(2), ShowItemsDetails(3), MakeOrder(4), ShowOrdersHistory(5), UpdateItemOrPrice(6), SaveOrderHistory(7), LoadOrderHistory(8), Exit(9);

    private int id;

    private eMenuOptions(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}