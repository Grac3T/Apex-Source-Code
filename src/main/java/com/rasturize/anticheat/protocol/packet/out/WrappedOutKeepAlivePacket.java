package com.rasturize.anticheat.protocol.packet.out;

import com.rasturize.anticheat.protocol.api.NMSObject;
import com.rasturize.anticheat.protocol.api.ProtocolVersion;
import com.rasturize.anticheat.protocol.reflection.FieldAccessor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class WrappedOutKeepAlivePacket extends NMSObject {
    private static final String packet = Server.KEEP_ALIVE;

    private static FieldAccessor<Integer> fieldLegacy = fetchField(packet, int.class, 0);
    private static FieldAccessor<Long> field = fetchField(packet, long.class, 0);

    private long time;

    public WrappedOutKeepAlivePacket(long time) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_12)) setPacket(packet, (int) time);
        else setPacket(packet, time);
    }

    public WrappedOutKeepAlivePacket(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_12)) time = fetch(fieldLegacy);
        else time = fetch(field);
    }
}
