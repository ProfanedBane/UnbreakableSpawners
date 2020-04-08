package com.gmail.profanedbane.UnbreakableSpawners;

import net.minecraft.server.v1_12_R1.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

// overrides mob spawners to be bedrock tier
public class UnbreakableSpawners extends JavaPlugin {

    // store old field values to revert to on stop
    private Block spawner = Block.getByName("mob_spawner");
    private Field durability;
    private Field strength;
    private float oldDurability;
    private float oldStrength;

    @Override
    public void onLoad(){
        try {
            durability = Block.class.getDeclaredField("durability");
            strength = Block.class.getDeclaredField("strength");
            durability.setAccessible(true);
            strength.setAccessible(true);

            oldDurability = durability.getFloat(spawner);
            oldStrength = strength.getFloat(spawner);

            durability.set(spawner, 3600000f);
            strength.set(spawner, -1f);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // apparantly best practice is to revert on stop
    @Override
    public void onDisable(){
        try {
            durability.set(spawner, oldDurability);
            strength.set(spawner, oldStrength);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

