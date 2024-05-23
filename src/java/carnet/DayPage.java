package carnet;

public class DayPage extends Page{
    private String title;
    private String text;
    private String photo;
    private int emotions;

    public DayPage(int nbPage) {
        super(nbPage);
        title = "";
        text = "";
        photo = "";
        emotions = 0;
    }

    /**
     * Puts URL of a photo in corresponding field
     * @param photo URL of a photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setTitle(String title) {this.title = title;}
    public void setText(String text) {this.text = text;}
    public String getTitle() {return title;}
    public String getText() {return text;}
    public String getPhoto() {return photo;}
    public int getEmotions() {return emotions;}
    public void setEmotions(int emotions) {this.emotions = emotions;}

    /**
     * Compare if page equals to a page given in parameter
     * @param p page to compare with
     * @return true if equals
     */
    public boolean equals(DayPage p) {
        return title.equals(p.getTitle()) &&
                text.equals(p.getText()) &&
                photo.equals(p.getPhoto()) &&
                emotions == p.getEmotions();
    }
}
