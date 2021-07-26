package me.ernestorb.tablistmanager.packets;

public class Tablist {

    private PacketSender packet;

    protected Tablist(TablistTemplate template) {
        this.packet = new TabListPacket(template);
    }

    protected PacketSender getPacket() {
        return this.packet;
    }

}
