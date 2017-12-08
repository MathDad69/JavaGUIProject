package Storage.Model;

public class Message<SenderType, ReceiverType> {
    private SenderType sender;
    private ReceiverType receiver;
    private OrderDetails order;

    public Message(SenderType sender, ReceiverType receiver, OrderDetails order) {
        this.sender = sender;
        this.receiver = receiver;
        this.order = order;
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

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }
}
