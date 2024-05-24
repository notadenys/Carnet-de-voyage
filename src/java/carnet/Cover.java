package carnet;

import java.time.LocalDate;

public class Cover extends Page{
    private String title;
    private String startDate;
    private String endDate;
    private String author;
    private String participants;


    public Cover() {
        super(1);
        title = "";
        startDate = "";
        endDate = "";
        this.author = "";
        this.participants = "";
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getParticipants() {
        return participants;
    }
    public void setParticipants(String participants) {
        this.participants = participants;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getStartDate() {
        if(!startDate.isEmpty())
        {
            return LocalDate.parse(startDate);
        }
        return null;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.toString();
    }
    public LocalDate getEndDate() {
        if(!endDate.isEmpty()) {
            return LocalDate.parse(endDate);
        }
        return null;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate.toString();
    }
}
