package carnet;

public class PageDay extends Page{
    private String title;
    private String text;

    public PageDay() {}

    public void setTitle(String title) {this.title = title;
        System.out.println(this.title);}
    public void setText(String text) {this.text = text;}
    public String getTitle() {return title;}
    public String getText() {return text;}
}
