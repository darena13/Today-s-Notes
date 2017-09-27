import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private TableView<Note> noteTable;
    @FXML
    private TableColumn<Note, String> dateColumn;
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
    public void populateTable(Main mainApp) {
        this.mainApp = mainApp;
        //добавляем в таблицу данные из наблюдаемого списка ObservableList<Note>
        noteTable.setItems(mainApp.getNotesData());
    }

    public void addNoteDialog(ActionEvent actionEvent) {
        //показать окошко
        //получить данные, создать объект
        //отправить обхект в addNote хэлпера
        //и наблюдаемому списку рассказать про новый объект
        try {
            //создаем диалоговое окно
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/add_note.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add note");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //контроллер диалогового окна
            AddNoteController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //ждём пока пользователь не закроет
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
