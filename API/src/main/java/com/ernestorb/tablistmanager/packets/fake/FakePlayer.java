package com.ernestorb.tablistmanager.packets.fake;

import com.ernestorb.tablistmanager.utils.FakePlayerUtil;

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
     * Utility method to generate a FakePlayer that should be put on the tablist.
     *
     * @return The Fakeplayer with a name generated by {@link FakePlayerUtil#randomName() randomName()}
     */
    public static FakePlayer randomFakePlayer() {
        return new FakePlayer(FakePlayerUtil.randomName());
    }

}
