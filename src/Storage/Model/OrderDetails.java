package Storage.Model;

public class OrderDetails {
    private String productName;
    private int amount;
    private String orderGUID;

    public OrderDetails(String productName, int amount) {
        this.productName = productName;
        this.amount = amount;
        this.orderGUID = java.util.UUID.randomUUID().toString();
    }

    public String getProductName(){
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    public String getOrderGUID() {
        return orderGUID;
    }
}
