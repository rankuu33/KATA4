package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader {

    private final Connection connection;

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    public static TrackLoader with(Connection connection) {
        return new SqliteTrackLoader(connection);
    }


    @Override
    public List<Track> loadTracks() {
        try {
            return load(queryAll());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT \n" +
                "    tracks.Name AS TrackName,\n" +
                "    tracks.Composer AS Composer,\n" +
                "    albums.Title AS AlbumTitle,\n" +
                "    artists.Name AS ArtistName,\n" +
                "    tracks.Milliseconds / 1000 AS DurationInSeconds\n" +
                "FROM \n" +
                "    tracks\n" +
                "JOIN \n" +
                "    albums ON tracks.AlbumId = albums.AlbumId\n" +
                "JOIN \n" +
                "    artists ON albums.ArtistId = artists.ArtistId;");

    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        while (resultSet.next()) {
            tracks.add(trackFrom(resultSet));
        }
        return tracks;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("TrackName"),
                resultSet.getString("Composer"),
                resultSet.getString("AlbumTitle"),
                resultSet.getString("ArtistName"),
                resultSet.getInt("DurationInSeconds")
        );
    }

}
