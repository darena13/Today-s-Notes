import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class Note {
    private ObjectProperty<LocalDate> date;
    private SimpleStringProperty text;

    public Note(String date, String text) {
        this.date = new SimpleObjectProperty<LocalDate>(new Date(Long.parseLong(date)).toLocalDate());
        this.text = new SimpleStringProperty(text);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
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
