package carnet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class Carnet extends SujetObserve{
    private final Cover cover;
    private ArrayList<DayPage> pages;
    private int currentPage;
    private Carnet savedCarnetImage;

    public Carnet()
    {
        super();
        cover = new Cover();
        pages = new ArrayList<>();
        createPage();

        currentPage = 1;
        savedCarnetImage = null;
    }

    /**
     * switch to the next page
     */
    public void nextPage() {
        currentPage++;
    }

    /**
     * switch to the previous page
     */
    public void previousPage() {
        currentPage--;
    }

    /**
     * create a new page
     */
    public void createPage() {
        pages.add(new DayPage(pages.size()+2));
    }

    /**
     * @param page page number to set as current
     */
    public void setCurrentPage(int page) { currentPage = page; }

    /**
     * @return current page number
     */
    public int getCurrentPageNb() { return currentPage; }

    /**
     * @return amount of pages in a diary
     */
    public int getNbPages() { return pages.size() + 1; }

    /**
     * @return current page
     */
    public DayPage getCurrentPage() {
        return pages.get(currentPage - 2);
    }

    /**
     * @return cover page of the diary
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * @return ArrayList with all the pages
     */
    public ArrayList<DayPage> getPages() { return pages; }

    /**
     * @return true if diary is not changed, false otherwise
     */
    public boolean isNew() {
        boolean equals = this.equals(new Carnet());
        System.gc();
        return equals;
    }

    /**
     * @return true if diary is saved after last modification, false otherwise
     */
    public boolean isSaved() { return this.equals(savedCarnetImage); }

    /**
     * save an image of a carnet to compare it when this one is changed
     */
    public void saveCarnetImage() {
        // to avoid infinite loop in a constructor
        if (savedCarnetImage == null) {
            savedCarnetImage = new Carnet();
        }

        savedCarnetImage.copyCarnet(this);
    }

    /**
     * copies a given carnet into this one
     * @param carnet to copy from
     */
    public void copyCarnet(Carnet carnet) {
        Cover newCover = carnet.getCover();
        Cover thisCover = this.getCover();
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

    /**
     * export to JSON
     * @param filePath path to export folder and a filename
     */
    public void export(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // checks if file has .json suffix to avoid repetitions
        String fileName = (filePath.endsWith(".json")) ? filePath : (filePath + ".json");
        // Converts Java object to File
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(this, writer);
            saveCarnetImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * read from JSON
     * @param filePath path to the source file
     */
    public void importCarnet(String filePath) {
        // default compact print
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            // Convert JSON File to Java Object
            Carnet carnet = gson.fromJson(reader, Carnet.class);

            // switch to the imported carnet
            copyCarnet(carnet);
            saveCarnetImage();
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
