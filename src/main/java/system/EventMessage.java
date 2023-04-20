package system;

import java.util.Collections;
import java.util.Set;

public class EventMessage {
    private final Set<User> owners;
    private final String publicContents;
    private final String privateContents;

    public EventMessage(Set<User> myOwners, String msgPublicContents, String msgPrivateContents) {
        this.owners = myOwners;
        this.publicContents = msgPublicContents;
        this.privateContents = msgPrivateContents;
    }

    public static EventMessage publicMessage(String msgPublicContents) {
        return new EventMessage(Collections.emptySet(), msgPublicContents, null);
    }

    public boolean hasPrivateContents() {
        return this.privateContents != null;
    }

    public boolean isOwnedBy(User user) {
        return owners.contains(user);
    }

    public String getContentsFor(User user) {
        if (isOwnedBy(user)) {
            return getPrivateContents();
        }
        return getPublicContents();
    }

    public String getPrivateContents() {
        return hasPrivateContents() ? this.privateContents : this.publicContents;
    }
    public String getPublicContents() {
        return this.publicContents;
    }
}
