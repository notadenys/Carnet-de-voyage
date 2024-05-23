package carnet;

public abstract class Page extends SujetObserve {
    private int nbPage;

    public Page(int nbPage) { this.nbPage = nbPage; }

    public int getNbPage() {
        return nbPage;
    }
    public void setNbPage(int nbPage) {
        this.nbPage = nbPage;
    }
}
