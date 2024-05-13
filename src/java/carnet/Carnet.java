package carnet;

import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private final ArrayList<Page> pages;
    private int currentPage;

    public Carnet()
    {
        super();
        pages = new ArrayList<>();
        pages.add(new PageDay());
        pages.add(new PageDay());
        currentPage = 1;
    }

    public void setCurrentPage(int page) { currentPage = page; }
    public int getCurrentPage()
    {
        return currentPage;
    }
    public PageDay getCurrentPageDay() { return (PageDay) pages.get(currentPage); }
}
