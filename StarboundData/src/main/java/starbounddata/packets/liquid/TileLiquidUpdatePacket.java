/*
 * Copyright (C) 2014 www.StarNub.org - Underbalanced
 *
 * This file is part of org.starnub a Java Wrapper for Starbound.
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

package starbounddata.packets.liquid;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import starbounddata.ByteBufferUtilities;
import starbounddata.packets.Packet;
import starbounddata.packets.Packets;
import starbounddata.types.liquid.LiquidNetUpdate;
import starbounddata.types.vectors.Vec2I;

/**
 * Represents the DamageTileGroup and methods to generate a packet data for StarNub and Plugins
 * <p>
 * Notes: This packet can be edited freely. Please be cognisant of what values you change and how they will be interpreted by the starnubclient.
 * <p>
 * Packet Direction: Client -> Server
 * <p>
 * Starbound 1.0 Compliant (Versions 622, Update 1)
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public class TileLiquidUpdatePacket extends Packet {

    private Vec2I tilePosition = new Vec2I();
    private LiquidNetUpdate liquidNetUpdate = new LiquidNetUpdate();

    /**
     * Recommended: For connections StarNub usage.
     * <p>
     * Uses: This is used to pre-construct packets for a specific side of a connection
     * <p>
     *
     * @param DIRECTION       Direction representing the direction the packet flows to
     * @param SENDER_CTX      ChannelHandlerContext which represents the sender of this packets context (Context can be written to)
     * @param DESTINATION_CTX ChannelHandlerContext which represents the destination of this packets context (Context can be written to)
     */
    public TileLiquidUpdatePacket(Direction DIRECTION, ChannelHandlerContext SENDER_CTX, ChannelHandlerContext DESTINATION_CTX) {
        super(DIRECTION, Packets.TILELIQUIDUPDATE.getPacketId(), SENDER_CTX, DESTINATION_CTX);
    }

    /**
     * Recommended: For Plugin Developers & Anyone else.
     * <p>
     * Uses: This method will be used to send a packet to the client with the server version. You only need the destination in order t
     * router this packet
     * <p>
     *
     * @param DESTINATION_CTX ChannelHandlerContext which represents the destination of this packets context (Context can be written to)
     * @param tilePosition Vac2IArray representing the riles to be damaged
     */
    public TileLiquidUpdatePacket(ChannelHandlerContext DESTINATION_CTX, Vec2I tilePosition, LiquidNetUpdate liquidNetUpdate) {
        super(Packets.TILELIQUIDUPDATE.getDirection(), Packets.TILELIQUIDUPDATE.getPacketId(), null, DESTINATION_CTX);
        this.tilePosition = tilePosition;
        this.liquidNetUpdate = liquidNetUpdate;
    }

    public TileLiquidUpdatePacket(Direction DIRECTION, ChannelHandlerContext DESTINATION_CTX) {
        super(DIRECTION, Packets.TILELIQUIDUPDATE.getPacketId(), null, DESTINATION_CTX);
    }

    /**
     * Recommended: For internal StarNub use with copying
     * <p>
     * Uses: This will construct a new packet from a packet
     *
     * @param packet DamageTileGroupPacket representing the packet to construct from
     */
    public TileLiquidUpdatePacket(TileLiquidUpdatePacket packet) {
        super(packet);
        this.tilePosition = packet.getTilePosition().copy();
        this.liquidNetUpdate = packet.getLiquidNetUpdate().copy();
    }

    public Vec2I getTilePosition() {
        return tilePosition;
    }

    public void setTilePosition(Vec2I tilePositions) {
        this.tilePosition = tilePositions;
    }

    public LiquidNetUpdate getLiquidNetUpdate() {
        return liquidNetUpdate;
    }

    public void setLiquidNetUpdate(LiquidNetUpdate liquidNetUpdate) {
        this.liquidNetUpdate = liquidNetUpdate;
    }

    /**
     * This will provide a new object while copying all of the internal data as well into this
     * new Object
     *
     * @return DamageTileGroupPacket the new copied object
     */
    @Override
    public TileLiquidUpdatePacket copy() {
        return new TileLiquidUpdatePacket(this);
    }

    /**
     * Recommended: For connections StarNub usage.
     * <p>
     * Uses: This method will read in a {@link io.netty.buffer.ByteBuf} into this packets fields
     * <p>
     * Note: This particular read will discard the packet if the tile radius exceed that of the {@link starbounddata.types.vectors.Vec2IArray} constructor
     *
     * @param in ByteBuf representing the reason to be read into the packet
     */
    @Override
    public void read(ByteBuf in) {
        ByteBufferUtilities.print(in);
        try {
            this.tilePosition.read(in);
            this.liquidNetUpdate.read(in);
        } catch (ArrayIndexOutOfBoundsException e) {
            super.recycle();
            in.skipBytes(in.readableBytes());
        }
    }

    /**
     * Recommended: For connections StarNub usage.
     * <p>
     * Uses: This method will write to a {@link io.netty.buffer.ByteBuf} using this packets fields
     * <p>
     *
     * @param out ByteBuf representing the space to write out the packet reason
     */
    @Override
    public void write(ByteBuf out) {
        this.tilePosition.write(out);
        this.liquidNetUpdate.write(out);
    }

    @Override
    public String toString() {
        return "TileLiquidUpdatePacket{" +
                "tilePositions=" + tilePosition +
                ", liquidNetUpdate=" + liquidNetUpdate +
                "} " + super.toString();
    }
}