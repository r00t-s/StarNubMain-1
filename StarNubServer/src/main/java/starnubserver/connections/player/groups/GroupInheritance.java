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

package starnubserver.connections.player.groups;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.exception.ExceptionUtils;
import starnubserver.StarNub;
import starnubserver.database.tables.Characters;
import starnubserver.database.tables.GroupInheritances;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "GROUP_INHERITANCE")
public class GroupInheritance {

    private final static GroupInheritances GROUP_INHERITANCES_DB = GroupInheritances.getInstance();

    /**
     * Represents a group inheritance id
     */

    @DatabaseField(generatedId =true, dataType = DataType.INTEGER, columnName = "GROUP_INHERITANCE_ID")
    private volatile int groupInheritanceId;

    /**
     * Represents this sessions unique sessionId that was generated by the database tools
     */

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "MAIN_GROUP")
    private volatile Group mainGroup;

    /**
     * Represents this Players IP ina string mainly used for the database
     */

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "INHERITED_GROUP")
    private volatile Group inheritedGroup;

    /**
     * Constructor for database purposes
     */
    public GroupInheritance(){}

    public int getGroupInheritanceId() {
        return groupInheritanceId;
    }

    public Group getMainGroup() {
        return mainGroup;
    }

    public Group getInheritedGroup() {
        return inheritedGroup;
    }

    /**
     * Constructor used in adding, removing or updating a group inheritance
     * @param mainGroup int representing main group that is inheriting a group
     * @param inheritedGroup int representing the group that is being inherited
     */
    public GroupInheritance(Group mainGroup, Group inheritedGroup) {
        this.mainGroup = mainGroup;
        this.inheritedGroup = inheritedGroup;
    }


    public GroupInheritance getGroupInheritance (Group group, Group inherited) {
        GroupInheritance groupAssignment = null;
        try {
            QueryBuilder<GroupInheritance, Integer> queryBuilder =
                    getTableDao().queryBuilder();
            Where<GroupInheritance, Integer> where = queryBuilder.where();
            queryBuilder.where()
                    .eq("MAIN_GROUP", group)
                    .and()
                    .eq("INHERITED_GROUP", inherited);
            PreparedQuery<GroupInheritance> preparedQuery = queryBuilder.prepare();
            groupAssignment = getTableDao().queryForFirst(preparedQuery);
        } catch (Exception e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return groupAssignment;
    }
    public List<GroupInheritance> getGroupInheritance(Group mainGroupId){
        try {
            return getTableDao().queryBuilder().where()
                    .eq("MAIN_GROUP", mainGroupId)
                    .query();
        } catch (SQLException e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
        return null;
    }

    public void deleteGroupInheritance(Group mainGroupId){
        try {
            DeleteBuilder<GroupInheritance, Integer> deleteBuilder =
                    getTableDao().deleteBuilder();
            deleteBuilder.where().eq("MAIN_GROUP", mainGroupId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            StarNub.getLogger().cFatPrint("StarNub", ExceptionUtils.getMessage(e));
        }
    }
}
