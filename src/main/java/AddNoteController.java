import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AddNoteController {
    @FXML
    private TextField textField;

    private Stage dialogStage;
    private BlockingQueue<DBEvent> eventQueue;

    private Note note = new Note(null, null);

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void addNote(ActionEvent actionEvent) {
        if (isInputValid()) {
            //заполняем поля note
            note.setDate(new Date().toString());
            note.setText(textField.getText());
            //добавляем событие в очередь событий, чтобы БД-поток узнал о нем
            eventQueue.add(new AddEvent(note));
            //закрываем окно
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        return true;
    }

    public void setEventQueue(BlockingQueue<DBEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }
}
