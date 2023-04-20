package system.UnitTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.EventMessage;
import system.User;

import java.util.HashSet;
import java.util.Set;

public class EventMessageTests {

    private User owner;
    private User nonOwner;
    private EventMessage privateMessage;
    private EventMessage publicMessage;
    private static final String PUBLIC_CONTENTS = "PUBLIC_CONTENTS";
    private static final String PRIVATE_CONTENTS = "PRIVATE_CONTENTS";

    @BeforeEach
    void setUp() {
        owner = new User();
        nonOwner = new User();
        Set<User> owners = new HashSet<>();
        owners.add(owner);
        privateMessage = new EventMessage(owners, PUBLIC_CONTENTS, PRIVATE_CONTENTS);
        publicMessage = EventMessage.publicMessage(PUBLIC_CONTENTS);
    }

    @Test
    void testPrivateMessageOwnership() {
        Assertions.assertTrue(privateMessage.isOwnedBy(owner));
        Assertions.assertFalse(privateMessage.isOwnedBy(nonOwner));
    }

    @Test
    void testPrivateMessageHasPrivateContents() {
        Assertions.assertTrue(privateMessage.hasPrivateContents());
    }

    @Test
    void testPublicMessageHasNoPrivateContents() {
        Assertions.assertFalse(publicMessage.hasPrivateContents());
    }

    @Test
    void testPublicMessageContentsIdentical() {
        Assertions.assertEquals(publicMessage.getPublicContents(), publicMessage.getPrivateContents());
    }

    @Test
    void testDistinctContents() {
        Assertions.assertNotEquals(privateMessage.getPrivateContents(), privateMessage.getPublicContents());
    }

    @Test
    void testOwnerSeesPrivate() {
        Assertions.assertEquals(PRIVATE_CONTENTS, privateMessage.getContentsFor(owner));
    }

    @Test
    void testNonOwnerSeesPublic() {
        Assertions.assertEquals(PUBLIC_CONTENTS, privateMessage.getContentsFor(nonOwner));
    }
}
