package Storage.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    public void proceedOrder(String productName, StorageManager storage, String orderGUID) throws InterruptedException {
        synchronized (this) {
            // mock some delivering delay
            Thread.sleep(1000 * ThreadLocalRandom.current().nextInt(2, 10));
//            Thread.sleep(1000 * 5);

            Message<Manufacturer, StorageManager> response = new Message<Manufacturer, StorageManager>(
                    this,
                    storage,
                    new OrderDetails(productName, products.getOrDefault(productName, 0))
            );

            int amount = storage.getNotificationAboutAvailability(response, orderGUID);

            if (amount > 0) {
                deliverProduct(productName, amount, storage, orderGUID);
            }
        }
    }

    public void deliverProduct(String productName, int amount, StorageManager storage, String guid) throws InterruptedException {
        products.put(productName, products.get(productName) - amount);

//        // mock some delivering delay
//        Thread.sleep(1000 * ThreadLocalRandom.current().nextInt(2, 10));

        // send response
        Message<Manufacturer, StorageManager> response = new Message<Manufacturer, StorageManager>(
                this,
                storage,
                new OrderDetails(productName, amount)
        );

        storage.receiveDeliveringResponse(response, guid);
    }
}
