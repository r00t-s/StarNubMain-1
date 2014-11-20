package server.database.tables;

import com.j256.ormlite.support.ConnectionSource;
import server.connectedentities.player.groups.Tag;
import server.database.TableWrapper;

import java.sql.SQLException;

public class Tags extends TableWrapper<Tag, Integer> {

    public Tags(Class<Tag> typeParameterDBClass, Class<Integer> typeParameterIDClass) {
        super(typeParameterDBClass, typeParameterIDClass);
    }

    public Tags(ConnectionSource connectionSource, int oldVersion, Class<Tag> typeParameterDBClass, Class<Integer> typeParameterIDClass) {
        super(connectionSource, oldVersion, typeParameterDBClass, typeParameterIDClass);
    }

    @Override
    public void tableUpdater(ConnectionSource connection, int oldVersion) throws SQLException {

    }
}