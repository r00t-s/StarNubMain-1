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

public class ExactLocation extends Location {

    private Vec2I coordinate;

    public ExactLocation() {
    }

    public ExactLocation(String universe, String sector, String solarSystem, Vec2I coordinate) {
        super(universe, sector, solarSystem);
        this.coordinate = coordinate;
    }

    public Vec2I getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Vec2I coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "PlanetLocation{" +
                "coordinate=" + coordinate +
                "} " + super.toString();
    }
}
