package carnet;

public class PageDay extends Page{
    private String title;
    private String text;

    public PageDay() {
        title = "Title";
        text = "Text";
    }

    public void setTitle(String title) {this.title = title;
        System.out.println(this.title);}
    public void setText(String text) {this.text = text;}
    public String getTitle() {return title;}
    public String getText() {return text;}
}
