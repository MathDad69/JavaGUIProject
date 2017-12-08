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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderGUID() {
        return orderGUID;
    }

    public void setOrderGUID(String orderGUID) {
        this.orderGUID = orderGUID;
    }
}
