package me.ernestorb.tablistmanager.packets.fake;

import java.util.Random;
import java.util.UUID;

public class FakePlayer {

    private UUID fakeUUID = UUID.randomUUID();
    private String name;
    private String displayName;
    private TablistAddFakePlayerPacket tablistAddPacket;
    private TablistRemoveFakePlayerPacket tablistRemovePacket;

    public FakePlayer(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.tablistAddPacket = new TablistAddFakePlayerPacket(fakeUUID, this.name, this.displayName);
        this.tablistRemovePacket = new TablistRemoveFakePlayerPacket(fakeUUID, this.name, this.displayName);
    }

    public FakePlayer(String name) {
        this(name, " ");
    }

    public TablistAddFakePlayerPacket getTablistAddPacket() {
        return tablistAddPacket;
    }

    public TablistRemoveFakePlayerPacket getTablistRemovePacket() {
        return tablistRemovePacket;
    }


    public static FakePlayer randomFakePlayer() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return new FakePlayer(generatedString);
    }

}
