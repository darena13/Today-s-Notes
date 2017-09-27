import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class Controller {
    @FXML
    private Button addNote;
    @FXML
    private TableView<Note> noteTable;
    @FXML
    private TableColumn<Note, LocalDate> dateColumn;
    @FXML
    private TableColumn<Note, String> textColumn;

    private Main mainApp;

    //вызывается автоматически после загрузки fxml-файла
    @FXML
    private void initialize() {
        //инициализация таблицы заметок с двумя столбцами: какое поле объекта Note в какой столбец
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        textColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
    }

    //основной класс приложения сам вызовет этот метод
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        //добавляем в таблицу данные из наблюдаемого списка ObservableList<Note>
        noteTable.setItems(mainApp.getNotesData());
    }

    public void addNote(ActionEvent actionEvent) {
        //показать окошко
        //получить данные, создать объект
        //отправить обхект в addNote хэлпера
        //и наблюдаемому списку рассказать про новый объект
    }
}
