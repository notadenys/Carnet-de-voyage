package carnet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private final Cover cover;
    private ArrayList<DayPage> pages;
    private int currentPage;

    public Carnet()
    {
        super();
        cover = new Cover();
        pages = new ArrayList<>();
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
    public int getNbPages() { return pages.size() + 1; }
    public DayPage getCurrentPage() {
        return pages.get(currentPage - 2);
    }
    public Cover getCover() {
        return cover;
    }

    public ArrayList<DayPage> getPages() { return pages; }
    public DayPage getPage(int page) { return pages.get(page-2); }
    public Cover getCoverPage() { return cover; }

    public boolean isNew() { return this.equals(new Carnet()); }

    private void copyCarnet(Carnet carnet) {
        Cover newCover = carnet.getCoverPage();
        Cover thisCover = this.getCoverPage();
        thisCover.setTitle(newCover.getTitle());
        thisCover.setAuthor(newCover.getAuthor());
        thisCover.setStartDate(newCover.getStartDate());
        thisCover.setEndDate(newCover.getEndDate());
        thisCover.setParticipants(newCover.getParticipants());

        this.pages = carnet.getPages();
        notifyObservers();
    }

    public void export(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Converts Java object to File
        try (Writer writer = new FileWriter(filePath + ".json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importCarnet(String filePath) {

    }

    @Override
    public boolean equals(Object obj) {
        boolean isInstance = obj instanceof Carnet;
        Carnet carnet;
        if (isInstance) {
            carnet = (Carnet) obj;
        } else {
            return false;
        }
        for (DayPage page : pages) {
            if (!page.equals(carnet.getPage(page.getNbPage()))) {
                return false;
            }
        }

        return getCover().equals(carnet.getCover());
    }
}
