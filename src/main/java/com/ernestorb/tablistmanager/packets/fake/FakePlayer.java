package com.ernestorb.tablistmanager.packets.fake;

import java.util.Random;
import java.util.UUID;

/**
 * Represents a FakePlayer for tablist purposes.
 */
public class FakePlayer {

    private final TablistAddFakePlayerPacket tablistAddPacket;
    private final TablistRemoveFakePlayerPacket tablistRemovePacket;

    /**
     * @param name Name used for sorting
     * @param displayName Name used to show on the client
     */
    public FakePlayer(String name, String displayName) {
        UUID fakeUUID = UUID.randomUUID();
        this.tablistAddPacket = new TablistAddFakePlayerPacket(fakeUUID, name, displayName);
        this.tablistRemovePacket = new TablistRemoveFakePlayerPacket(fakeUUID, name, displayName);
    }

    /**
     * Same as FakePlayer(name, "")
     * @param name Name used for sorting
     */
    public FakePlayer(String name) {
        this(name, " ");
    }

    public TablistAddFakePlayerPacket getTablistAddPacket() {
        return tablistAddPacket;
    }

    public TablistRemoveFakePlayerPacket getTablistRemovePacket() {
        return tablistRemovePacket;
    }


    /**
     * Utility method for generate a FakePlayer that should be put on the tablist bottom.
     * @return The Fakeplayer with a name that always start with "zz" + 8 [A-Za-z0] characters
     */
    public static FakePlayer randomFakePlayer() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();
        String generatedString = "zz";
        generatedString += random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return new FakePlayer(generatedString);
    }

}
