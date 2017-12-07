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

    public Message getProductAmount(String productName, StorageManager manager) {
        Message<Manufacturer, StorageManager> response = new Message<>(
                this,
                manager,
                productName,
                products.getOrDefault(productName, 0)
        );

        return response;
    }

    public void deliverProduct(String productName, int amount, StorageManager storage) {
        products.put(productName, products.get(productName) - amount);
        sendDeliveringResponse(productName, amount, storage);
    }

    public void sendDeliveringResponse(String productName, int amount, StorageManager storage) {
        Message<Manufacturer, StorageManager> response = new Message<>(
        this,
            storage,
            productName,
            amount
        );

        storage.receiveDeliveringResponse(response);
    }
}
