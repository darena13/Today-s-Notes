import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class SQLiteHelper {
    private static Connection con;
    private static boolean hasData = false;

    //подключаемся к базе данных
    private void getConnection() throws ClassNotFoundException, SQLException {
        //загружаем класс драйвера
        Class.forName("org.sqlite.JDBC");
        //если нужно, БД будет создана в корне проекта
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteNotes.db");
        initialise();
    }

    private void initialise() throws SQLException {
        if( !hasData ) {
            hasData = true;
            //создаем Statement для отправления запросов к БД
            Statement stateToCheck = con.createStatement();
            //получаем результат запроса к БД
            ResultSet res = stateToCheck.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='notes'");
            //если таблицы notes еще нет, нужно её создать
            if( !res.next()) {
                System.out.println("Building table 'notes'.");
                //создаем таблицу
                Statement stateForBuilding = con.createStatement();
                stateForBuilding.executeUpdate("create table notes(id integer,"
                        + "date varchar(100)," + "note varchar(100)," + "primary key (id));");

                //добавляем одну запись
                PreparedStatement prep = con.prepareStatement("insert into notes values(?,?,?);");
                prep.setString(2, new Date().toString());
                prep.setString(3, "Today was a good day. I spend some time outside. It was nice.");
                prep.execute();
            }
        }
    }

    public void addNote(Note note) throws ClassNotFoundException, SQLException {
        //подключаемся, если еще не
        if(con == null) {
            getConnection();
        }
        //готовим запрос к БД
        PreparedStatement prep = con
                .prepareStatement("insert into notes values(?,?,?);");
        prep.setString(2, note.getDate().toString());
        prep.setString(3, note.getText());
        //и исполняем его
        prep.execute();
    }

    public ResultSet getNotes() throws SQLException, ClassNotFoundException {
        //подключаемся, если еще не
        if(con == null) {
            getConnection();
        }
        //получаем результаты запроса к БД
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("select date, note from notes");
        return res;
    }
}
