package Storage.Model;

import java.util.HashMap;
import java.util.Map;

public class Manufacturer {
    private String manufacturerName;
    private Map<String, Integer> products = new HashMap<>();

    public Manufacturer(String name) {
        this.manufacturerName = name;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public int getProductAmount(String productName) {
        return products.get(productName);
    }

    public void deliverProduct(String productName, int amount) {
        products.put(productName, products.get(productName) - amount);
        int test = products.get(productName);
    }
}
