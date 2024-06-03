package carnet;


public abstract class Page {
    private final int nbPage;

    public Page(int nbPage) { this.nbPage = nbPage; }

    public int getNbPage() {
        return nbPage;
    }
}
