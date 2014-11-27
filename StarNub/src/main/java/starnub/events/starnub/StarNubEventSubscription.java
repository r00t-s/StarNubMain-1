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

package starnub.events.starnub;

import starnub.StarNub;
import utilities.events.EventSubscription;
import utilities.events.types.Event;

/**
 * Represents StarNubs StarNubEventSubscription that can self register or be manually registered with the
 * {@link starnub.events.starnub.StarNubEventRouter}
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public class StarNubEventSubscription extends EventSubscription<Event<String>> {


    private final String EVENT_KEY;

    /**
     * Recommended: For Plugin Developers & Anyone else.
     * <p>
     * Uses: This will set up an event subscription but it will not be registered, you must invoke submitRegistration
     *
     * @param SUBSCRIBER_NAME String representing the subscribers name
     * @param EVENT_KEY Class extending Packet which represents the Packet Event Key
     * @param EVENT_HANDLER EventHandler representing the event handler that will do some logic you choose to write
     */
    public StarNubEventSubscription(String SUBSCRIBER_NAME, String EVENT_KEY, StarNubEventHandler<Event<String>> EVENT_HANDLER) {
        super(SUBSCRIBER_NAME, EVENT_HANDLER);
        this.EVENT_KEY = EVENT_KEY;
    }

    /**
     * Recommended: For Plugin Developers & Anyone else.
     * <p>
     * Uses: This will set up an event subscription and register it if you supplied a true boolean for the value register
     *
     * @param SUBSCRIBER_NAME String representing the subscribers name
     * @param EVENT_KEY Class extending Packet which represents the Packet Event Key
     * @param register boolean do you want to auto register this event
     * @param EVENT_HANDLER EventHandler representing the event handler that will do some logic you choose to write
     */
    public StarNubEventSubscription(String SUBSCRIBER_NAME, String EVENT_KEY, boolean register, StarNubEventHandler<Event<String>> EVENT_HANDLER) {
        super(SUBSCRIBER_NAME, EVENT_HANDLER);
        this.EVENT_KEY = EVENT_KEY;
        if (register) {
            submitRegistration();
        }
    }

    /**
     * This will register this Event Subscription with the {@link starnub.events.starnub.StarNubEventRouter}
     */
    @Override
    public void submitRegistration() {
        StarNub.getStarNubEventRouter().registerEventSubscription(EVENT_KEY, this);
    }

    /**
     * This will remove this Event Subscription from the {@link starnub.events.starnub.StarNubEventRouter}
     */
    @Override
    public void removeRegistration() {
        StarNub.getStarNubEventRouter().removeEventSubscription(this);
    }
}
