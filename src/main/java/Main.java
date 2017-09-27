import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Main extends Application {
    private Stage primaryStage;
    private GridPane rootLayout;
    //наблюдаемый список заметок
    private ObservableList<Note> noteDataList = FXCollections.observableArrayList();

    //основной метод для JavaFX-приложений
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Today's Notes");

        //загружаем корневой макет из fxml файла
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/main.fxml"));
        rootLayout = loader.load();

        //отображаем сцену, содержащую корневой макет
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        //заполняем наблюдаемый список данными из БД
        fillTheList();

        //даём контроллеру доступ к главному приложению
        MainController controller = loader.getController();
        //заполняем таблицу данными из наблюдаемого списка
        controller.populateTable(this);
    }

    public void fillTheList() {
        //подключаемся к БД
        SQLiteHelper sqLiteHelper = new SQLiteHelper();
        //таблица с данными из результата запроса к БД
        ResultSet rs;
        try {
            rs = sqLiteHelper.getNotes();

            while (rs.next()) {
                noteDataList.add(new Note(rs.getString("date"), rs.getString("note")));
                System.out.println(rs.getString("date") + " " + rs.getString("note"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //возвращаем главную сцену
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //возвращаем наблюдаемый список заметок
    public ObservableList<Note> getNotesData() {
        return noteDataList;
    }

    //обычно не вызывается, но на всякий пожарный надо чтоб был
    public static void main(String[] args) {
        launch(args);
    }
}
