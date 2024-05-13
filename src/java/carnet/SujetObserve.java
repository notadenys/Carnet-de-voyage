package carnet;

import controller.Observateur;
import java.util.ArrayList;


public class SujetObserve {
    private final ArrayList<Observateur> observers;

    public SujetObserve() {observers = new ArrayList<>();}

    public void addObserver(Observateur o) {
        observers.add(o);
    }

    public void notifyObservers()
    {
        observers.forEach(Observateur::reagir);
    }
}
