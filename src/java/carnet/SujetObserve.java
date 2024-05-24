package carnet;

import com.google.gson.annotations.Expose;
import controller.Observateur;
import java.util.ArrayList;


public class SujetObserve {
    @Expose(serialize = false, deserialize = false)
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
