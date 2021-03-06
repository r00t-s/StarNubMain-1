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

package starnubserver.connections.player.session.location;

import starbounddata.types.vectors.Vec2I;

import java.util.UUID;

public class ShipLocation extends ExactLocation {

    private UUID uuid;

    public ShipLocation() {
    }

    public ShipLocation(UUID uuid) {
        this.uuid = uuid;
    }

    public ShipLocation(String universe, String sector, String solarSystem, Vec2I coordinate, UUID uuid) {
        super(universe, sector, solarSystem, coordinate);
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ShipLocation{" +
                "uuid=" + uuid +
                "} " + super.toString();
    }
}
