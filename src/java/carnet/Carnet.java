package carnet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import outils.IndexGenerator;

import java.io.*;
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

    public void copyCarnet(Carnet carnet) {
        IndexGenerator.getInstance().reset();
        Cover newCover = carnet.getCoverPage();
        Cover thisCover = this.getCoverPage();
        thisCover.setTitle(newCover.getTitle());
        thisCover.setAuthor(newCover.getAuthor());

        if (newCover.getStartDate() != null) {
            thisCover.setStartDate(newCover.getStartDate());
        } else {
            thisCover.emptyStartDate();
        }
        if (newCover.getEndDate() != null) {
            thisCover.setEndDate(newCover.getEndDate());
        } else {
            thisCover.emptyEndDate();
        }
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
        // default compact print
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            // Convert JSON File to Java Object
            Carnet carnet = gson.fromJson(reader, Carnet.class);

            // switch to the imported carnet
            copyCarnet(carnet);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            boolean found = false;
            for (DayPage otherPage : carnet.getPages()) {
                if (page.equals(otherPage)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }

        return getCover().equals(carnet.getCover());
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
