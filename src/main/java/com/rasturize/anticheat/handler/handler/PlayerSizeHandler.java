package com.rasturize.anticheat.handler.handler;

import com.rasturize.anticheat.utils.world.types.SimpleCollisionBox;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PlayerSizeHandler {

    PlayerSizeHandler instance = getInstance();

    double height(Player player);
    double width(Player player);

    boolean isGliding(Player player);

    default SimpleCollisionBox bounds(Player player) {
        Location l = player.getLocation();
        return bounds(player,l.getX(),l.getY(),l.getZ());
    }

    default SimpleCollisionBox bounds(Player player,double x, double y, double z) {
        double width = width(player);
        return new SimpleCollisionBox().offset(x,y,z).expand(width,0,width).expandMax(0,height(player),0);
    }

    static PlayerSizeHandler getInstance() {
        if (instance!=null)
            return instance;
        try {
            return new PlayerSizeHandlerModern();
        } catch (Exception e) {
            return new PlayerSizeHandlerLegacy();
        }
    }

}
