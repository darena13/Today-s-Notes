import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class DBThread implements Runnable {
    final MainController mainController;
    final private ObservableList<Note> noteDataList = FXCollections.observableArrayList();
    final private BlockingQueue<DBEvent> eventQueue;

    public DBThread(MainController mainController) {
        this.mainController = mainController;
        //получаем ссылку на очередь событий
        this.eventQueue = mainController.getEventQueue();
    }

    @Override
    public void run() {
        fillAndPopulate();
        boolean isItTimeToLetGo = false;
        while (!isItTimeToLetGo) {
            try {
                DBEvent event = eventQueue.take();
                switch (event.getType()) {
                    case ADD: {
                        addNoteAndRefresh((AddEvent) event);
                        break;
                    }
                    case EXIT: {
                        isItTimeToLetGo = true;
                        break;
                    }
                }
            } catch (InterruptedException ignore) {
                //ничего не делаем
            }
        }
    }

    public void fillAndPopulate() {
        noteDataList.clear();
        fillTheList();
        //сделать в UI-потоке
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //заполняем таблицу данными из наблюдаемого списка
                mainController.populateTable(noteDataList);
            }
        });
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

        } catch (Exception ignore) {
            //ничего не делаем
        }
    }

    public void addNoteAndRefresh(AddEvent addEvent) {
        //подключаемся к БД
        SQLiteHelper sqLiteHelper = new SQLiteHelper();
        try {
            //добавляем в бд новую запись
            sqLiteHelper.addNote(addEvent.getNote());
            fillAndPopulate();
        } catch (ClassNotFoundException | SQLException ignore) {
            //ничего не делаем
        }
    }
}
