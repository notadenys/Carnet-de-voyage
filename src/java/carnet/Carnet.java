package carnet;

import java.time.LocalDate;
import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    private final ArrayList<Page> pages;
    private int currentPage;

    public Carnet()
    {
        super();
        pages = new ArrayList<>();
        pages.add(new Cover());
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
    public int getCurrentPageNb() { return currentPage; }
    public int getNbPages() { return pages.size(); }
    public Page getCurrentPage() { return pages.get(currentPage-1); }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
