package starnubserver.events.starnub;

import utilities.events.EventHandler;
import utilities.events.types.ObjectEvent;

/**
 * Represents StarNubs StarNubEventHandler that handles {@link utilities.events.types.Event} the
 * onEvent method is overridden so that you may conduct logic with the Event
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public abstract class StarNubEventHandler extends EventHandler<ObjectEvent> {

    /**
     * Recommended: For Plugin Developers & Anyone else.
     * <p>
     * Uses: When your {@link starnubserver.events.packet.PacketEventSubscription} is called this EventHandler
     * will be called and the onEvent() method invoked.
     *
     * NOTE: - YOU WILL NEED TO CAST THE EVENT TO THE DATA TYPE BASED ON THE EVENT LIST OR PLUGIN EVENT LIST
     *              {
     *                  Player player = (Player) eventData.getEVENT_DATA();
     *                  //Do Stuff, player.disconnect();
     *              }
     *
     * @param eventData Event representing the events being shared
     */
    @Override
    public abstract void onEvent(ObjectEvent eventData);

}
