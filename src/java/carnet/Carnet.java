package carnet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private final Cover cover;
    private final ArrayList<DayPage> pages;
    private int currentPage;

    public Carnet()
    {
        super();
        cover = new Cover();
        pages = new ArrayList<>();
        pages.add(new DayPage(2));
        currentPage = 1;
    }

    public void nextPage() {
        currentPage++;
    }
    public void previousPage() {
        currentPage--;
    }

    public void createPage() {
        pages.add(new DayPage(pages.size() + 2));
    }

    public void setCurrentPage(int page) { currentPage = page; }
    public int getCurrentPageNb() { return currentPage; }
    public int getNbPages() { return pages.size() + 1; }
    public DayPage getCurrentPage() {
        return pages.get(currentPage - 2);
    }
    public Cover getCover() {
        return cover;
    }

    public void export() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(pages);
        System.out.println(json);
    }
}
