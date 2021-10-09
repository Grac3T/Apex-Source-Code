package com.rasturize.anticheat.protocol.packet.out;

import com.rasturize.anticheat.protocol.api.NMSObject;
import com.rasturize.anticheat.protocol.api.ProtocolVersion;
import com.rasturize.anticheat.protocol.reflection.FieldAccessor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class WrappedOutTransaction extends NMSObject {
    private static final String packet = Server.TRANSACTION;

    private static FieldAccessor<Integer> fieldId = fetchField(packet, int.class, 0);
    private static FieldAccessor<Short> fieldAction = fetchField(packet, short.class, 0);
    private static FieldAccessor<Boolean> fieldAccepted = fetchField(packet, boolean.class, 0);

    private int id;
    private short action;
    private boolean accept;

    public WrappedOutTransaction(byte id, short action, boolean accept) {
        setPacket(packet, id, action, accept);
    }

    public WrappedOutTransaction(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        id = fetch(fieldId);
        action = fetch(fieldAction);
        accept = fetch(fieldAccepted);
    }
}
