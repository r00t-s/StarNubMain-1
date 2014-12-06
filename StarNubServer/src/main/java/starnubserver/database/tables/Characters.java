/*
 * Copyright (C) 2014 www.StarNub.org - Underbalanced
 *
 * This utilities.file is part of org.starnub a Java Wrapper for Starbound.
 *
 * This above mentioned StarNub software is free software:
 * you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free
 * Software Foundation, either version  3 of the License, or
 * any later version. This above mentioned CodeHome software
 * is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 * the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License in
 * this StarNub Software.  If not, see <http://www.gnu.org/licenses/>.
 */

package starnubserver.database.tables;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import starnubserver.StarNub;
import starnubserver.connections.player.character.PlayerCharacter;
import starnubserver.database.DatabaseConnection;
import starnubserver.database.TableWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents Characters Table that extends the TableWrapper class
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public class Characters extends TableWrapper<PlayerCharacter, Integer> {

    /**
     * Represents the only instance of this class - Singleton Pattern
     */
    private static final Characters instance = new Characters();

    /**
     * This constructor is private - Singleton Pattern
     */
    private Characters(){
        super(DatabaseConnection.getConnection(), 0, PlayerCharacter.class, Integer.class);
    }

    public static Characters getInstance() {
        return instance;
    }

    @Override
    public void tableUpdater(ConnectionSource connection, int oldVersion) throws SQLException {
        if (oldVersion == 0) {
//            updateTable("ALTER TABLE `ACCOUNTS` ADD COLUMN `DISABLED_REASON` VARCHAR(255);");
        }
    }

    public PlayerCharacter getCharacterFromNameUUIDCombo(String nameString, UUID UUID) {
        try {
            QueryBuilder<PlayerCharacter, Integer> queryBuilder =
                    getTableDao().queryBuilder();
            Where<PlayerCharacter, Integer> where = queryBuilder.where();
            queryBuilder.where()
                    .eq("NAME", nameString)
                    .and()
                    .eq("uuid", UUID);
            PreparedQuery<PlayerCharacter> preparedQuery = queryBuilder.prepare();
            return getTableDao().queryForFirst(preparedQuery);
        } catch (Exception e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
            return null;
        }
    }

    public PlayerCharacter getCharacterFromCleanNameCombo(String nameString) {
        PlayerCharacter playerCharacter = null;
        try {
            QueryBuilder<PlayerCharacter, Integer> queryBuilder =
                    getTableDao().queryBuilder();
            Where<PlayerCharacter, Integer> where = queryBuilder.where();
            queryBuilder.where()
                    .like("CLEAN_NAME", nameString);
            PreparedQuery<PlayerCharacter> preparedQuery = queryBuilder.prepare();
            return getTableDao().queryForFirst(preparedQuery);
        } catch (Exception e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
            return null;
        }
    }

    public List<PlayerCharacter> getCharacterByName(String characterString){
        try {
            return getTableDao().queryBuilder().where()
                    .like("CLEAN_NAME", characterString)
                    .query();
        } catch (SQLException e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return null;
    }

    public List<PlayerCharacter> getCharactersListFromStarnubId(int starnubId) {
        try {
            return getTableDao().queryForEq("STARNUB_ID", starnubId);
        } catch (SQLException e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return null;
    }

    public ArrayList<Integer> getCharacterIDsListFromStarnubId(int starnubId){
        try {
            /* Get UUIDs with that match the Starnub ID */
            GenericRawResults<Object[]> rawResults = getTableDao().queryRaw("select CHARACTER_ID from CHARACTERS where STARNUB_ID = " + starnubId, new DataType[] { DataType.INTEGER });
            /* Get results of the query */
            List<Object[]> results = rawResults.getResults();
            ArrayList<Integer> characterIdList = new ArrayList<Integer>();
            for (Object[] result : results) {
                for (Object objectResult : result) {
                    if (!characterIdList.contains(objectResult)) {
                        characterIdList.add((Integer) objectResult);
                    }
                }
            }
            return characterIdList;
        } catch (Exception e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return null;
    }

    public ArrayList<UUID> getCharactersUUIDListFromStarnubId(int starnubId) {
        try {
            /* Get UUIDs with that match the Starnub ID */
            GenericRawResults<Object[]> rawResults = getTableDao().queryRaw("select uuid from CHARACTERS where STARNUB_ID = "+ starnubId, new DataType[] { DataType.UUID });
            /* Get results of the query */
            List<Object[]> results = rawResults.getResults();
            ArrayList<UUID> uuidList = new ArrayList<UUID>();
            for (Object[] result : results) {
                for (Object objectResult : result) {
                    if (!uuidList.contains(objectResult)) {
                        uuidList.add((UUID) objectResult);
                    }
                }
            }
            return uuidList;
        } catch (Exception e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return null;
    }
}