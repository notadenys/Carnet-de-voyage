package carnet;

import java.time.LocalDate;

public class Cover extends Page{
    private String title;
    private String startDate;
    private String endDate;
    private String author;
    private String participants;


    public Cover() {
        super();
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

    @Override
    public boolean equals(Object o) {
        return o instanceof Cover &&
                title.equals(((Cover) o).title) &&
                startDate.equals(((Cover) o).startDate) &&
                endDate.equals(((Cover) o).endDate) &&
                author.equals((((Cover) o).author)) &&
                participants.equals(((Cover) o).participants);
    }
}
