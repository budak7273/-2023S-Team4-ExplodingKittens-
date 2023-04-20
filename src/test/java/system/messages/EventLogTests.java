package system.messages;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.User;
import system.messages.EventLog;
import system.messages.EventMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventLogTests {

    private EventLog log;
    private List<EventMessage> internalList;

    @BeforeEach
    void setUp() {
        internalList = new ArrayList<>();
        log = new EventLog(internalList);
    }

    @Test
    void testNewEventLogIsEmpty() {
        assertEquals(0, internalList.size());
        assertEquals("", log.getTextRepresentationAs(EasyMock.createNiceMock(User.class)));
    }
}
