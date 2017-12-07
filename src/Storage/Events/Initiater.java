package Storage.Events;

import java.util.ArrayList;

public class Initiater<InfoType> {
    private ArrayList<ProjectEventListener> listeners = new ArrayList<>();

    public void addListener(ProjectEventListener toAdd) {
        listeners.add(toAdd);
    }

    public void doSomething(InfoType data) {
        for (ProjectEventListener hl : listeners)
            hl.actionDone(data);
    }
}