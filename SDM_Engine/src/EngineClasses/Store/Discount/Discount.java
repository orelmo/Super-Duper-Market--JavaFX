package EngineClasses.Store.Discount;

public class Discount {
    private String name;
    private IfYouBuy trigger;
    private ThenYouGet benefit;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIfYouBuy(IfYouBuy ifYouBuy) {
        this.trigger = ifYouBuy;
    }

    public void setThenYouGet(ThenYouGet thenYouGet) {
        this.benefit=thenYouGet;
    }

    public IfYouBuy getTrigger() {
        return this.trigger;
    }

    public ThenYouGet getBenefit() {
        return this.benefit;
    }
}
