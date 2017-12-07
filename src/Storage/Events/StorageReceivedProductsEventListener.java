package Storage.Events;

import Storage.Model.Manufacturer;
import Storage.Model.Message;
import Storage.Model.StorageManager;

public interface StorageReceivedProductsEventListener extends ProjectEventListener<Message<Manufacturer, StorageManager>> {
}
