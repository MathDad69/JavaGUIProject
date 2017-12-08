package Storage.Model;

public class ProceededOrder {
    private OrderDetails order;
    private int satisfiedAmount;
    private int deliveredAmount;

    public ProceededOrder(OrderDetails order) {
        this.order = order;
        this.satisfiedAmount = 0;
        this.deliveredAmount = 0;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public int getSatisfiedAmount() {
        return satisfiedAmount;
    }

    public void addToSatisfiedAmount(int amount) {
        this.satisfiedAmount += amount;
    }

    public void addToDeliveredAmount(int amount) {
        this.deliveredAmount += amount;
    }

    public int getDeliveredAmount() {
        return deliveredAmount;
    }
}
