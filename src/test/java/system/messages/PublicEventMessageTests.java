package system.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PublicEventMessageTests {
    private EventMessage publicMessage;
    private static final String PUBLIC_CONTENTS = "PUBLIC_CONTENTS";

    @BeforeEach
    void setUp() {
        publicMessage = EventMessage.publicMessage(PUBLIC_CONTENTS);
    }


    @Test
    void testPublicMessageHasNoPrivateContents() {
        assertFalse(publicMessage.hasPrivateContents());
    }

    @Test
    void testPublicMessageContentsIdentical() {
        assertEquals(publicMessage.getPublicContents(), publicMessage.getPrivateContents());
    }
}
