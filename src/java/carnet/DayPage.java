package carnet;

public class DayPage extends Page{
    private String title;
    private String text;
    private String photo;
    private boolean existsPhoto;

    public DayPage() {
        title = "Add Title";
        text = "Write your notes here...";
        photo = "";
        existsPhoto = false;
    }

    /**
     * Puts URL of a photo in corresponding field
     * @param photo URL of a photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
        setExistsPhoto(true);
    }

    public void setTitle(String title) {this.title = title;}
    public void setText(String text) {this.text = text;}
    public String getTitle() {return title;}
    public String getText() {return text;}
    public String getPhoto() {return photo;}
    public boolean getExistsPhoto() {return existsPhoto;}
    public void setExistsPhoto(boolean existsPhoto) {this.existsPhoto = existsPhoto;}

    /**
     * Compare if page equals to a page given in parameter
     * @param p page to compare with
     * @return true if equals
     */
    public boolean equals(DayPage p) {
        return title.equals(p.getTitle()) && text.equals(p.getText()) && photo.equals(p.getPhoto());
    }
}
