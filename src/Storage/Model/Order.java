package Storage.Model;

public class Order {
    private String productName;
    private int amount;

    public Order(String productName, int amount) {
        this.productName = productName;
        this.amount = amount;
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
}
