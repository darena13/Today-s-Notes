import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        //for extra special maven's idiocy before we do sacred "mvn compile":
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Today's Notes");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        SQLiteHelper test = new SQLiteHelper();
        ResultSet rs;

        try {
            rs = test.getNotes();

            while (rs.next()) {
                System.out.println(rs.getString("date") + " " + rs.getString("note"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
