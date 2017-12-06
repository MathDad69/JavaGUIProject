package Storage.Model;

import java.util.ArrayList;

public class StorageManager {
    private ArrayList<Manufacturer> manufacturers;

    private static StorageManager manager = new StorageManager();

    public static StorageManager getManager() {
        return manager;
    }

    public void Run() {
        Order newOrder = new Order("Koks1", 5);

        for (int i = 0; i < manufacturers.size(); i++) {
           int value = manufacturers.get(i).getProductAmount(newOrder.getProductName());
        }
    }

    public void processOrder(Order newOrder) {
        synchronized(this){
            int total = 0;
            for (int i = 0; i < manufacturers.size(); i++) {
                total += manufacturers.get(i).getProductAmount(newOrder.getProductName());
            }

            if (newOrder.getAmount() <= total) {
                new Thread(() -> {
                    getItemsFromManufacturers(newOrder);
                }).start();
            }
        }
    }

    private void getItemsFromManufacturers(Order newOrder) {
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
