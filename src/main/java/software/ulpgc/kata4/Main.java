package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Raul/Desktop/chinook.db")) {
            for (Track track: SqliteTrackLoader.with(connection).loadTracks()) {
                System.out.println(track);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
