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

package starnub.connections.player.session;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import starbounddata.packets.chat.ChatReceivePacket;
import starbounddata.packets.chat.ChatSendPacket;
import starbounddata.packets.connection.ClientDisconnectRequestPacket;
import starbounddata.packets.connection.ServerDisconnectPacket;
import starnub.StarNub;
import starnub.connections.player.StarNubProxyConnection;
import starnub.connections.player.character.CharacterIP;
import starnub.connections.player.character.PlayerCharacter;
import starnub.events.events.StarNubEvent;
import utilities.events.types.StringEvent;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

/**
 * StarNub's Player class that represents a sender. This class
 * purpose of this class is to represent a Players Session.
 * <p>
 * The data in this class will always change between log-ins. The
 * Account and Character classes will save the permanent data to
 * a database.
 * <p>
 * All data is based on a "Session" with StarNub.
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0
 */
@DatabaseTable(tableName = "PLAYER_SESSION_LOG")
public class Player extends StarNubProxyConnection {

    /**
     * Represents this sessions unique sessionId that was generated by the database tools
     */
    @DatabaseField(generatedId = true, columnName = "SESSION_ID")
    private volatile int sessionID;

    /**
     * Represents this Players IP ina string mainly used for the database
     */
    @DatabaseField(dataType = DataType.STRING, columnName = "IP")
    private volatile String sessionIpString;

    /**
     * Represents the start time in UTC from when the Player starbounddata.packets.connection was completely excepted
     */
    @DatabaseField(dataType = DataType.DATE_TIME, columnName = "START_TIME")
    private volatile DateTime startTimeUtc;

    /**
     * Represents the start time in UTC from when the Player starbounddata.packets.connection was completely excepted
     */
    @DatabaseField(dataType = DataType.DATE_TIME, columnName = "END_TIME")
    private volatile DateTime endTimeUtc;

    /**
     * Represents the Players StarNub ID if they have registered the Character
     */
    @DatabaseField(dataType = DataType.INTEGER, canBeNull = true, columnName = "STARNUB_ID")
    private volatile int account;

    /**
     * Represents the Character for this Player
     */
    @DatabaseField(foreign = true, canBeNull = false, columnName = "CHARACTER_ID")
    private volatile PlayerCharacter playerCharacter;

    /**
     * Represents the Starbound Client ID assigned by the Starbound starbounddata.packets.starbounddata.packets.starnub
     */
    private volatile long starboundClientId;

    /**
     * Represents the characters full name with color tags and all
     */
    private volatile String gameName;

    /**
     * Represents the Nick name of the Player, with all of the color strings and other stuff in it
     */
    private volatile String nickName;

    /**
     * Represents the Clean version of the Nick name where we stripped everything out that inter fears with starbounddata.packets.starbounddata.packets.starnub logs and administration
     */
    private volatile String cleanNickName;


    /**
     * This indicates weather this account is an operator, which gives them complete control
     * over the starbounddata.packets.starbounddata.packets.starnub.
     * <p>
     * NOTE: Use with caution. This is really not the right way to give a person admin or mod access. You
     * should use a group with the correct permissions.
     */
    private volatile boolean isOp;

    /**
     * Represents if the Player is Away From Keyboard (AFK), this is manually set by a Player or Plugin
     */
    private volatile boolean afk;

    /**
     * Represents the ChannelHandlerContext of who not to send a message to
     */

    private volatile ArrayList<Channel> doNotSendMessageList;

    public int getSessionID() {
        return sessionID;
    }

    public String getSessionIpString() {
        return sessionIpString;
    }

    public DateTime getStartTimeUtc() {
        return startTimeUtc;
    }

    public DateTime getEndTimeUtc() {
        return endTimeUtc;
    }

    public int getAccount() {
        return account;
    }

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public long getStarboundClientId() {
        return starboundClientId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCleanNickName() {
        return cleanNickName;
    }

    public boolean isOp() {
        return isOp;
    }

    public boolean isAfk() {
        return afk;
    }

    public ArrayList<Channel> getDoNotSendMessageList() {
        return doNotSendMessageList;
    }

    public Player(StarNubProxyConnection proxyConnection, String playerName, UUID playerUUID) {
        super(StarNub.getStarNubEventRouter(), proxyConnection.getCLIENT_CTX(), proxyConnection.getSERVER_CTX());
        this.gameName = playerCharacter.getName();
        this.nickName = playerCharacter.getName();
        this.cleanNickName = playerCharacter.getCleanName();
        this.startTimeUtc = DateTime.now();
        InetAddress playerIP = proxyConnection.getClientIP();
        this.sessionIpString = StringUtils.remove(playerIP.toString(), "/");
        PlayerCharacter playerCharacter = StarNub.getDatabaseTables().getCharacters().getCharacterFromNameUUIDCombo(playerName, playerUUID);
        if (playerCharacter == null) {
            playerCharacter = new PlayerCharacter(playerName, playerUUID);
            new StarNubEvent("Player_New_Character", playerCharacter);
        }
        this.playerCharacter = playerCharacter;

        CharacterIP characterIP = new CharacterIP(playerCharacter, proxyConnection.getClientIP());
        if (!StarNub.getDatabaseTables().getCharacterIPLog().isCharacterIDAndIPComboRecorded(characterIP)) {
            StarNub.getDatabaseTables().getCharacterIPLog().create(characterIP);
            new StarNubEvent("Player_Character_New_IP", playerCharacter);
        }
        this.account = playerCharacter.initialLogInProcessing(sessionIpString, playerUUID.toString());
        try {
            this.isOp = StarNub.getConnections().getCONNECTED_PLAYERS().getOPERATORS().collectionContains("uuids");
        } catch (Exception e){
            this.isOp = false;
        }
        StarNub.getDatabaseTables().getPlayerSessionLog().create(this);
        new StarNubEvent("Player_Character_New_Session", playerCharacter);
    }

    @Override
    public void removeConnection() {
        StarNub.getConnections().getCONNECTED_PLAYERS().remove(CLIENT_CTX);
    }

    /**
     *
     * @param endTimeUtc long allows the setting of this session when it ends
     */
    public void setEndTimeUtc(DateTime endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
        StarNub.getDatabaseTables().getPlayerSessionLog().update(this);
    }

    /**
     *
     * @param account Account which this session belongs too
     */
    public void setAccount(int account) {
        this.account = account;
        StarNub.getDatabaseTables().getPlayerSessionLog().update(this);
    }

    /**
     *
     * WARNING: Do not use this method, not for public consumption.
     *
     * @param starboundClientId int that set the Starbound Client ID
     */
    public void setStarboundClientId(long starboundClientId) {
        this.starboundClientId = starboundClientId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     *
     * @param nickName String set the nick name that includes colors and other characters
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @param cleanNickName String to set the clean version of the nick name, colors and special characters removed
     */
    public void setCleanNickName(String cleanNickName) {
        this.cleanNickName = cleanNickName;
    }

    public void setOp(boolean isOp) {
        this.isOp = isOp;
    }

    /**
     *
     * @param afk boolean to set if this session/character is ask or not
     */
    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public void setDoNotSendMessageList() {
        this.doNotSendMessageList = new ArrayList<Channel>();
    }

    public void reloadIgnoreList(){

    }

    public void addToIgnoreList(){

    }

    public void removeFromIgnoreList(){

    }

    public void sendChatMessage(Object sender, ChatReceivePacket.ChatReceiveChannel channel, String message){
        if (sender instanceof Player) {
//            new ChatReceivePacket(CLIENT_CTX, channel, "", 0, msgUnknownNameBuilder(sender, tags, false), message).routeToDestination();
        }
    }
    
    public void sendServerChatMessage(ChatSendPacket.ChatSendChannel channel, String message){
        new ChatSendPacket(SERVER_CTX, channel, message).routeToDestination();
    }

    @Override
    public boolean disconnect() {
        boolean disconnect = super.disconnect();
        packetDisconnect();
        return disconnect;
    }

    public boolean disconnectReason(String reason) {
        boolean disconnected = super.disconnect();
        if (disconnected) {
            Player player = StarNub.getConnections().getCONNECTED_PLAYERS().remove(CLIENT_CTX);
            new StringEvent("Player_Disconnect_" + reason, player);
            disconnectCleanUp();
            if (!reason.equalsIgnoreCase("quit")){
                packetDisconnect();
            }
        }
        return disconnected;
    }

    private void disconnectCleanUp(){
        this.setEndTimeUtc(DateTime.now());
        playerCharacter.updatePlayedTimeLastSeen();
    }

    private void packetDisconnect(){
        new ClientDisconnectRequestPacket(SERVER_CTX);
        new ServerDisconnectPacket(CLIENT_CTX,"");
    }

    public boolean hasBasePermission(Player playerSession, String basePermission){
        if (playerSession.isOp()) {
            return true;
        } else if (playerSession.getPlayerCharacter().getAccount() != null) {
            return playerSession.getPlayerCharacter().getAccount().hasBasePermission(basePermission);
        } else {
//            if (groupSync.getNoAccountGroup() != null) {
//                return groupSync.getNoAccountGroup().hasBasePermission(basePermission);
//            } else {
                return false;
//            }
        }
    }

    public boolean hasPermission(String permission, boolean checkWildCards){
        String[] perms;
        String perm3 = null;
        boolean fullPermission = false;
        try {
            perms = permission.split("\\.", 3);
            perm3 = perms[2];
            fullPermission = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            perms = permission.split("\\.", 2);
        }
        return hasPermission(perms[0], perms[1], perm3, fullPermission, checkWildCards);
    }

    public boolean hasPermission(String pluginCommandNamePermission, String commandPermission, boolean checkWildCards){
        return hasPermission(pluginCommandNamePermission, commandPermission, null, false, checkWildCards);
    }

    public boolean hasPermission(String pluginCommandNamePermission, String mainArgOrVariable, String commandPermission, boolean checkWildCards){
        return hasPermission(pluginCommandNamePermission, commandPermission, mainArgOrVariable, true, checkWildCards);
    }

    @SuppressWarnings("all")
    public boolean hasPermission(String pluginCommandNamePermission, String commandPermission, String mainArgOrVariable, boolean fullPermission, boolean checkWildCards){
        if (this.isOp() && checkWildCards) {
            return true;
        } else if (this.getPlayerCharacter().getAccount() != null) {
            return this.getPlayerCharacter().getAccount().hasPermission(pluginCommandNamePermission, commandPermission, mainArgOrVariable, fullPermission, checkWildCards);
        } else {
//            if (groupSync.getNoAccountGroup() != null) {
//                return groupSync.getNoAccountGroup().hasPermission(pluginCommandNamePermission, commandPermission, mainArgOrVariable, fullPermission, checkWildCards);
//            } else {
                return false;
//            }
        }
    }

    public String getPermissionVariable(String permission){
        String[] perms;
        try {
            perms = permission.split("\\.", 3);
        } catch (ArrayIndexOutOfBoundsException e) {
            perms = permission.split("\\.", 2);
        }
        return getPermissionVariable(perms[0], perms[1]);
    }



    @SuppressWarnings("all")
    public String getPermissionVariable( String pluginCommandNamePermission, String commandPermission){
        if (this.isOp()) {
            return "OP";
        } else if (this.getPlayerCharacter().getAccount() != null) {
            return this.getPlayerCharacter().getAccount().getPermissionSpecific(pluginCommandNamePermission, commandPermission);
        } else {
//            if (groupSync.getNoAccountGroup() != null) {
//                return groupSync.getNoAccountGroup().getPermissionSpecific(pluginCommandNamePermission, commandPermission);
//            } else {
                return null;
//            }
        }
    }

    public int getPermissionVariableInteger(Player playerSession, String permission){
        String[] perms;
        try {
            perms = permission.split("\\.", 3);
        } catch (ArrayIndexOutOfBoundsException e) {
            perms = permission.split("\\.", 2);
        }
        return getPermissionVariableInteger(playerSession, perms[0], perms[1]);
    }

    public int getPermissionVariableInteger(Player playerSession, String pluginCommandNamePermission, String commandPermission){
        String permissionVariable = getPermissionVariable(pluginCommandNamePermission, commandPermission);
        if (permissionVariable == null) {
            return -100001;
        } else if (permissionVariable.equals("OP")) {
            return -100000;
        } else {
            try {
                return Integer.parseInt(permissionVariable);
            } catch (NumberFormatException e) {
                return -100002;
            }
        }
    }



    //remove from op
}




