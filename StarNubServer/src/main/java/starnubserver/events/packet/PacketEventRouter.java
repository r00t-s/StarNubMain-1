package starnubserver.events.packet;

import starbounddata.packets.Packet;
import starbounddata.packets.Packets;
import starnubserver.StarNub;
import utilities.events.EventRouter;
import utilities.events.EventSubscription;

import java.util.HashSet;

/**
 * Represents StarNubs PacketEventRouter to be used with {@link starnubserver.events.packet.PacketEventSubscription} and
 * {@link starnubserver.events.packet.PacketEventHandler}
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public class PacketEventRouter extends EventRouter<Class<? extends Packet>, Packet, Packet> {

    /**
     * This is instantiated to build out the Packets enum at start up
     */
    private static final Packets INSTANCE = Packets.PROTOCOLVERSION;

    public PacketEventRouter(){
        super();
    }

    /**
     * Recommended: For internal use with StarNub.
     * <p>
     * Uses: This is used for internal Packet Event Handling.
     *
     * @param packet Packet representing the packet to be handled
     * @return Packet the packet that was handled
     */
    @Override
    public Packet eventNotify(Packet packet) {
        return handleEvent(packet);
    }

    /**
     * Recommended: For internal use with StarNub.
     * <p>
     * Uses: This is used for internal Packet Event Handling.
     *
     * @param packet Packet representing the packet to be handled
     * @return Packet the packet that was handled
     */
    @Override
    @SuppressWarnings("unchecked")
    public Packet handleEvent(Packet packet){
        HashSet<EventSubscription> eventSubscriptions = getEVENT_SUBSCRIPTION_MAP().get(packet.getClass());
        if (eventSubscriptions == null){
            return packet;
        } else {
            for (EventSubscription<Packet> eventSubscription : eventSubscriptions){
                packet = eventSubscription.getEVENT_HANDLER().onEvent(packet);
                try {
                    if (packet.isRecycle()) {
                        packet.recycle();
                        return packet;
                    }
                } catch (NullPointerException e){
                    StarNub.getLogger().cFatPrint("StarNub", "CRITICAL ERROR. A PLUGIN DID NOT RETURN A PACKET. THIS WILL CAUSE A CLIENT TO DISCONNECT.");
                }
            }
            return packet;
        }
    }
}
