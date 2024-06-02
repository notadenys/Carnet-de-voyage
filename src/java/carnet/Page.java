package carnet;

import outils.IndexGenerator;

public abstract class Page {
    private final int nbPage;

    public Page() { this.nbPage = IndexGenerator.getInstance().getIndex(); }

    public int getNbPage() {
        return nbPage;
    }
}
