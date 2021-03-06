package com.rasturize.anticheat.protocol.packet.in;

import com.rasturize.anticheat.protocol.api.NMSObject;
import com.rasturize.anticheat.protocol.api.ProtocolVersion;
import com.rasturize.anticheat.protocol.reflection.FieldAccessor;
import com.rasturize.anticheat.protocol.packet.types.Vec3D;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter
public class WrappedInUseEntityPacket extends NMSObject {
    private static String packet = Client.USE_ENTITY;

    private static FieldAccessor<Integer> fieldId = fetchField(packet, int.class, 0);
    private static FieldAccessor<Enum> fieldAction = fetchField(packet, Enum.class, 0);

    // Vec3d
    private static FieldAccessor<Object> vec = fetchField(packet,"{nms}.Vec3D", 0);

    private int id;
    private EnumEntityUseAction action;
    private Entity entity;
    private Vec3D vector;

    public WrappedInUseEntityPacket(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        id = fetch(fieldId);
        action = EnumEntityUseAction.values()[fetch(fieldAction).ordinal()];
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity.getEntityId() == id) this.entity = entity;
        }

        Object vecObj = fetch(vec);
        if (vecObj != null) {
            vector = new Vec3D(vecObj);
        }
    }

    public enum EnumEntityUseAction {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }
}
