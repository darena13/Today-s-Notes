import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;

public class AddNoteController {
    @FXML
    private TextField textField;

    private Stage dialogStage;
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

            //подключаемся к БД
            SQLiteHelper sqLiteHelper = new SQLiteHelper();
            try {
                //добавляем в бд новую запись
                sqLiteHelper.addNote(note);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //закрываем окно
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        return true;
    }
}
