package utilities.events;

/**
 * Represents a EventSubscription to be used with an {@link utilities.events.EventHandler} and
 * {@link utilities.events.EventRouter}
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0
 */
public abstract class EventSubscription<T>{

    private final String SUBSCRIBER_NAME;
    private final Priority PRIORITY;
    private final EventHandler<T> EVENT_HANDLER;

    public String getSUBSCRIBER_NAME() {
        return SUBSCRIBER_NAME;
    }

    public Priority getPRIORITY() {
        return PRIORITY;
    }

    public EventHandler<T> getEVENT_HANDLER() {
        return EVENT_HANDLER;
    }

    public EventSubscription(String SUBSCRIBER_NAME, Priority PRIORITY, EventHandler<T> EVENT_HANDLER) {
        this.PRIORITY = PRIORITY;
        this.SUBSCRIBER_NAME = SUBSCRIBER_NAME;
        this.EVENT_HANDLER = EVENT_HANDLER;
    }

    /**
     * You must implement this method as each {@link utilities.events.EventRouter} may be different
     */
    public abstract void submitRegistration();

    /**
     * You must implement this method as each {@link utilities.events.EventRouter} may be different
     */
    public abstract void removeRegistration();

    @Override
    public String toString() {
        return "EventSubscription{" +
                "SUBSCRIBER_NAME='" + SUBSCRIBER_NAME + '\'' +
                ", PRIORITY=" + PRIORITY +
                ", EVENT_HANDLER=" + EVENT_HANDLER +
                '}';
    }
}

