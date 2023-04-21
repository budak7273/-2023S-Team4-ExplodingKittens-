package system.messages;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventMessageTests {

    private User owner;
    private User nonOwner;
    private EventMessage privateMessage;
    private EventMessage publicMessage;
    private static final String PUBLIC_CONTENTS = "PUBLIC_CONTENTS";
    private static final String PRIVATE_CONTENTS = "PRIVATE_CONTENTS";

    @BeforeEach
    void setUp() {
        owner = EasyMock.createNiceMock(User.class);
        nonOwner = EasyMock.createNiceMock(User.class);
        Set<User> owners = new HashSet<>();
        owners.add(owner);
        privateMessage = new EventMessage(owners, PUBLIC_CONTENTS, PRIVATE_CONTENTS);
        publicMessage = EventMessage.publicMessage(PUBLIC_CONTENTS);
    }

    @Test
    void testPrivateMessageOwnedByOwner() {
        assertTrue(privateMessage.isOwnedBy(owner));
    }
    void testPrivateMessageOwnedByNonOwner() {
        assertFalse(privateMessage.isOwnedBy(nonOwner));
    }

    @Test
    void testPrivateMessageHasPrivateContents() {
        assertTrue(privateMessage.hasPrivateContents());
    }

    @Test
    void testPublicMessageHasNoPrivateContents() {
        assertFalse(publicMessage.hasPrivateContents());
    }

    @Test
    void testPublicMessageContentsIdentical() {
        assertEquals(publicMessage.getPublicContents(), publicMessage.getPrivateContents());
    }

    @Test
    void testDistinctContents() {
        assertNotEquals(privateMessage.getPrivateContents(), privateMessage.getPublicContents());
    }

    @Test
    void testOwnerSeesPrivate() {
        assertEquals(PRIVATE_CONTENTS, privateMessage.getContentsFor(owner));
    }

    @Test
    void testNonOwnerSeesPublic() {
        assertEquals(PUBLIC_CONTENTS, privateMessage.getContentsFor(nonOwner));
    }
}
