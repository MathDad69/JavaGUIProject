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

    public OrderDetails getOrder() {
        return order;
    }
}
