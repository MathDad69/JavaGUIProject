package Storage.Model;

import Storage.UI.MainForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class StorageManager {
    private ArrayList<Manufacturer> manufacturers;
    private ArrayList<ProceededOrder> orders = new ArrayList<>();
    private MainForm mainForm;

    private static StorageManager manager = new StorageManager();

    public static StorageManager getManager() {
        return manager;
    }

    public void processOrder(OrderDetails newOrder) throws InterruptedException {
        this.orders.add(new ProceededOrder(newOrder));
        this.getResponsesForAvailability(newOrder.getProductName(), newOrder.getOrderGUID());
    }

    private void getResponsesForAvailability(String productName, String orderGUID) throws InterruptedException {
        for (int i = 0; i < manufacturers.size(); i++) {
            final int requestIndex = i;

            new Thread(() -> {
                try {
                    this.mainForm.handleSendRequest(manufacturers.get(requestIndex).getManufacturerName(), productName);
                    manufacturers.get(requestIndex).proceedOrder(productName, this, orderGUID);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void receiveDeliveringResponse(Message<Manufacturer, StorageManager> response, String orderGUID) throws InterruptedException {
        ProceededOrder order = null;
        for(int i = 0; i < this.orders.size(); i++) {
            if (Objects.equals(this.orders.get(i).getOrder().getOrderGUID(), orderGUID)) {
                order = this.orders.get(i);
                break;
            }
        }

        order.addToDeliveredAmount(response.getOrder().getAmount());
        Thread.sleep(1000);
        this.mainForm.handleDeliveringDoneOnUI(response);
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public ArrayList<ProceededOrder> getOrders() {
        return orders;
    }

    public int getNotificationAboutAvailability(Message<Manufacturer, StorageManager> response, String orderGUID) throws InterruptedException {
        ProceededOrder order = null;

        this.mainForm.handleAvailabilityResponseUI(response);

        for(int i = 0; i < this.orders.size(); i++) {
            if (Objects.equals(this.orders.get(i).getOrder().getOrderGUID(), orderGUID)) {
                order = this.orders.get(i);
                break;
            }
        }

        if (order != null) {
            int amountToRequest = Math.min(order.getOrder().getAmount() - order.getSatisfiedAmount(), response.getOrder().getAmount());
            order.addToSatisfiedAmount(amountToRequest);
            this.mainForm.handleGiveMeProductUI(response.getSender().getManufacturerName(), response.getOrder().getProductName(), amountToRequest);
            return amountToRequest;
        }

        return 0;
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }
}
