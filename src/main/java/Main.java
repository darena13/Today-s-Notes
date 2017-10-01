import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private Stage primaryStage;
    private GridPane rootLayout;

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

        MainController mainController = loader.getController();

        //запускаем БД-тред
        new Thread(new DBThread(mainController)).start();

        //оставливаем БД-тред при закрытие главного окна приложения
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mainController.getEventQueue().add(new ExitEvent());
            }
        });
    }

    //возвращаем главную сцену
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //обычно не вызывается, но на всякий пожарный надо чтоб был
    public static void main(String[] args) {
        launch(args);
    }
}
