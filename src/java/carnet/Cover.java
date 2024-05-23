package carnet;

public class Cover extends Page{
    private String author;
    private String participants;


    public Cover() {
        super(1);
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
}
