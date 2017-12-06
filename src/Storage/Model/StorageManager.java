package Storage.Model;

import java.util.ArrayList;

public class StorageManager {
    private ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();

    public StorageManager() {
        manufacturers.add(new Manufacturer("test1"));
        manufacturers.add(new Manufacturer("test2"));

        manufacturers.get(0).getProducts().put("Koks1",12);
        manufacturers.get(1).getProducts().put("Koks1",2);
        manufacturers.get(1).getProducts().put("Koks2",4);
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
