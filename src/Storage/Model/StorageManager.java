package Storage.Model;

import java.util.ArrayList;

public class StorageManager {
    private ArrayList<Manufacturer> manufacturers;

    private static StorageManager manager = new StorageManager();

    public static StorageManager getManager() {
        return manager;
    }

    public void processOrder(Order newOrder) {
        synchronized(this){
            ArrayList availabilityResponses = getResponsesForAvailability(newOrder.getProductName());

            if (canProceed(availabilityResponses, newOrder.getAmount())) {
                new Thread(() -> {
                    deliverItemsFromManufacturers(newOrder);
                }).start();
            }
        }
    }

    private ArrayList<RequestBody<Manufacturer, StorageManager>> getResponsesForAvailability(String productName) {
        ArrayList<RequestBody<Manufacturer, StorageManager>> result = new ArrayList<>();

        for (int i = 0; i < manufacturers.size(); i++) {
            RequestBody response = manufacturers.get(i).getProductAmount(productName, this);
            result.add(response);
        }

        return result;
    }

    private boolean canProceed(ArrayList<RequestBody> responses, int requestedAmount) {
        int availableAmount = 0;

        for (int i = 0; i < responses.size(); i++) {
            availableAmount += responses.get(i).getAmount();
        }

        return availableAmount >= requestedAmount;
    }

    private void deliverItemsFromManufacturers(Order newOrder) {
        for (int i = 0; i < manufacturers.size(); i++) {
            synchronized(this){
                manufacturers.get(i).deliverProduct(newOrder.getProductName(), newOrder.getAmount());
            }
        }
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }
}
