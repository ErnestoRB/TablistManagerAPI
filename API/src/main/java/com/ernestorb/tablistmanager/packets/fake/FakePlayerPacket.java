package com.ernestorb.tablistmanager.packets.fake;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.ernestorb.tablistmanager.packets.PacketSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FakePlayerPacket implements PacketSender {

    private final UUID uuid;
    private final String playerName;
    private final String displayText;
    private PacketContainer packet;
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    protected FakePlayerPacket(UUID uuid, String playerName, String displayText) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.displayText = displayText;
    }

    public void setPacket(PacketContainer packet) {
        this.packet = packet;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @Override
    public void sendPacketOnce(Player player) {
        this.protocolManager.sendServerPacket(player, this.packet);
    }

    public static WrappedGameProfile changeGameProfileSkin(WrappedGameProfile profile)
    {
        String signature = "bp8+T+MsWDjMJLMIcbXHunMLhheJ4PuwmtLTqK0Uljd2q7ZserFaSYqQITYQOekUbnZSNlKmFNNB8HtmzLU6Mqt19p20WSmgmgXm6rZcDAkYAeNPvtjz3Cl2ID4qaPRuOf4rf4NnZDeqQhtyWo7VptGw1u68W+0wsUHxggOatTVEc9KOlLQ7kZCTzHFFJRy1g+FybaXr5igTjcN/sTLkCLB7Q6P+MdIoJmoyhcJbCWfAek4RqvZFf474mk0C9gPyWLIFikbshJ9pmOS85Wh7DKPN73P6jlIqmsVCKFqTlFsx6JJdn/KmC7WMEMIXCTsgQj8HBuYW8EtTUrN93ZK2g8zpH+QF8FNu78wPzu+GPp203q9yx5AvJ3ODtyJHKH2WTvjrJZkJ7FtOKDHm9eWKIDSrW4TVsZZJCQ2sRZNGRoiWXGTi6RDmfHcqBnJo+dkZFdfpBhlMk1DDbAw1clihoAQ1m8pcW4SlFe8BlY9RPLnI05CTX1Fw54kwp2oqJq6k0gZssQsBFUlbr6yOY0kX8G4hZmwjF6lAYxX2pBsWgiiDj4x8Qk1upk4PEzFegQ/vfT2H/m/skGLkakmIvv9JelL1ooZgfPAlmL+VLjbrPVt68ww1Yikoa6uqxxGfXE5yOxBYiRDKDxDUonsXn/caBZ+c2dHb29TUpMRxYpRaIYI=";
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyODM5MzQxMzM5MywKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhiZDlhNDVkNzI0ZDM5MDRiY2Y3M2RlMzI5MjdiMjU4MTQzOGY2MWVlZGJiZWQxZWMzOTU0ZWE4NWRhNjlmNzgiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
        profile.getProperties().put("textures", new WrappedSignedProperty("textures", texture,signature));
        return profile;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDisplayText() {
        return displayText;
    }
}
