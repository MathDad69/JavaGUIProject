package Storage.Model;

public class RequestBody<SenderType, ReceiverType> {
    private SenderType sender;
    private ReceiverType receiver;
    private String productName;
    private int amount;

    public RequestBody(SenderType sender, ReceiverType receiver, String productName, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.productName = productName;
        this.amount = amount;
    }

    public SenderType getSender() {
        return sender;
    }

    public void setSender(SenderType sender) {
        this.sender = sender;
    }

    public ReceiverType getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverType receiver) {
        this.receiver = receiver;
    }

    public String getProductName() {
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
