package carnet;

import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private final ArrayList<Page> pages;
    private int currentPage;

    public Carnet()
    {
        super();
        pages = new ArrayList<>();
        pages.add(new DayPage());
        pages.add(new DayPage());
        currentPage = 1;
    }

    public void nextPage() {
        currentPage++;
    }
    public void previousPage() {
        currentPage--;
    }

    public void createPage() {
        pages.add(new DayPage());
    }

    public void setCurrentPage(int page) { currentPage = page; }
    public int getCurrentPage() { return currentPage; }
    public int getNbPages() { return pages.size(); }
    public DayPage getCurrentDayPage() { return (DayPage) pages.get(currentPage-1); }
}
