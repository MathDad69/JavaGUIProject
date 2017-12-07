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
                requests.get(requestIndex)
                    .getReceiver()
                    .deliverProduct(
                        requests.get(requestIndex).getProductName(),
                        requests.get(requestIndex).getAmount(),
                        this
                    );
            }).start();
        }
    }

    private ArrayList<Message<StorageManager, Manufacturer>>
        buildRequestsToManufacturers(ArrayList<Message<Manufacturer, StorageManager>> availabilityResponses, int requestedAmount) {

        ArrayList<Message<StorageManager, Manufacturer>> requests = new ArrayList<>();
        int satisfiedAmount = 0;

        for (int i = 0; i < availabilityResponses.size() && satisfiedAmount < requestedAmount; i++) {
            int canSatisfy = availabilityResponses.get(i).getAmount();

            if (canSatisfy == 0) {
               continue;
            }

            Message<StorageManager, Manufacturer> request =
                    new Message<StorageManager, Manufacturer>(
                            this,
                            availabilityResponses.get(i).getSender(),
                            availabilityResponses.get(i).getProductName(),
                            0);

            if (satisfiedAmount + canSatisfy <= requestedAmount) {
                request.setAmount(canSatisfy);
            } else {
                request.setAmount(requestedAmount - satisfiedAmount);
            }

            requests.add(request);
            satisfiedAmount += request.getAmount();
        }


        return satisfiedAmount == requestedAmount ? requests : null;
    }

    public void receiveDeliveringResponse(Message<Manufacturer, StorageManager> response) {
        // here goes a UI part
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }
}
