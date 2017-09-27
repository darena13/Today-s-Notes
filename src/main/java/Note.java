import javafx.beans.property.SimpleStringProperty;

public class Note {
    private SimpleStringProperty date;
    private SimpleStringProperty text;

    public Note(String date, String text) {
        this.date = new SimpleStringProperty(date);
        this.text = new SimpleStringProperty(text);
    }


    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getText() {
        return text.get();
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
