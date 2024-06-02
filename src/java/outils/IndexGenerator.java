package outils;

public class IndexGenerator {
    private int index;

    private static IndexGenerator instance;

    private IndexGenerator() {
        this.index = 1;
    }

    /**
     * @return instance of FabriqueNumero
     */
    public static IndexGenerator getInstance() {
        if (instance == null) {
            instance = new IndexGenerator();
        }
        return instance;
    }

    public int getIndex() { return index++; }

    public void reset() { index = 1; }
}
