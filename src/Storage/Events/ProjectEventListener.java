package Storage.Events;

public interface ProjectEventListener<T1> {
    void actionDone(T1 object);
}
