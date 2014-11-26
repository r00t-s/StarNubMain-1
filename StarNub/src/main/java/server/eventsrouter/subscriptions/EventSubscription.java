package server.eventsrouter.subscriptions;

public class EventSubscription<T> {


    private final String SUBSCRIBER_NAME;
    private final T EVENT_HANDLER;

    public String getSUBSCRIBER_NAME() {
        return SUBSCRIBER_NAME;
    }

    public T getEVENT_HANDLER() {
        return EVENT_HANDLER;
    }

    public EventSubscription(String SUBSCRIBER_NAME, T EVENT_HANDLER) {
        this.SUBSCRIBER_NAME = SUBSCRIBER_NAME;
        this.EVENT_HANDLER = EVENT_HANDLER;
    }
}