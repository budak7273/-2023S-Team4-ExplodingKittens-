package system.messages;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
