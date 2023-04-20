package system.messages;

import system.User;

import java.util.ArrayList;
import java.util.List;

public class EventLog {
    private final List<EventMessage> messages;
    private static final char LINE_SEPARATOR = '\n';

    public EventLog() {
        this(new ArrayList<>());
    }

    EventLog(List<EventMessage> internalList) {
        this.messages = internalList;
    }

    public String getTextRepresentationAs(User user) {
        StringBuilder builder = new StringBuilder();
        for (EventMessage message : messages) {
            builder.append(message.getContentsFor(user));
            builder.append(LINE_SEPARATOR);
        }
        return builder.toString();
    }

    public void addMessage(EventMessage message) {
        messages.add(message);
    }
}
