package Storage.Model;

import Storage.Events.Initiater;

import java.util.ArrayList;

public class StorageManager extends Initiater<Message> {
    private ArrayList<Manufacturer> manufacturers;

    private static StorageManager manager = new StorageManager();

    public static StorageManager getManager() {
        return manager;
    }

    public void processOrder(Order newOrder) {
        synchronized(this){
            ArrayList availabilityResponses = getResponsesForAvailability(newOrder.getProductName());

            ArrayList<Message<StorageManager, Manufacturer>> requests =
                    buildRequestsToManufacturers(availabilityResponses, newOrder.getAmount());

            if (requests != null) {
                deliverItemsFromManufacturers(requests);
            }
        }
    }

    private ArrayList<Message<Manufacturer, StorageManager>>
        getResponsesForAvailability(String productName) {

        ArrayList<Message<Manufacturer, StorageManager>> result = new ArrayList<>();

        for (int i = 0; i < manufacturers.size(); i++) {
            Message response = manufacturers.get(i).getProductAmount(productName, this);
            result.add(response);
        }

        return result;
    }

    private boolean canProceed(ArrayList<Message> responses, int requestedAmount) {
        int availableAmount = 0;

        for (int i = 0; i < responses.size(); i++) {
            availableAmount += responses.get(i).getAmount();
        }

        return availableAmount >= requestedAmount;
    }

    private void deliverItemsFromManufacturers(ArrayList<Message<StorageManager, Manufacturer>> requests) {
        for (int i = 0; i < requests.size(); i++) {
            final int requestIndex = i;

            new Thread(() -> {
                try {
                    requests.get(requestIndex)
                        .getReceiver()
                        .deliverProduct(
                            requests.get(requestIndex).getProductName(),
                            requests.get(requestIndex).getAmount(),
                            this
                        );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private ArrayList<Message<StorageManager, Manufacturer>>
        buildRequestsToManufacturers(ArrayList<Message<Manufacturer, StorageManager>> availabilityResponses, int requestedAmount) {

        ArrayList<Message<StorageManager, Manufacturer>> requests = new ArrayList<>();
        int satisfiedAmount = 0;

        for (int i = 0; i < availabilityResponses.size() && satisfiedAmount < requestedAmount; i++) {
            int amountToRequest = Math.min(requestedAmount - satisfiedAmount, availabilityResponses.get(i).getAmount());
            if (amountToRequest == 0) continue;

            requests.add(
                new Message<StorageManager, Manufacturer>(
                    this,
                    availabilityResponses.get(i).getSender(),
                    availabilityResponses.get(i).getProductName(),
                    amountToRequest
                )
            );

            satisfiedAmount += amountToRequest;
        }


        return satisfiedAmount == requestedAmount ? requests : null;
    }

    public void receiveDeliveringResponse(Message<Manufacturer, StorageManager> response) {
        doSomething(response);
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }
}
