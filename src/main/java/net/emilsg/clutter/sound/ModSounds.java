package net.emilsg.clutter.sound;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent COIN_PILE_BREAK = registerSoundEvent("coin_pile_break");
    public static SoundEvent COIN_PILE_PLACE = registerSoundEvent("coin_pile_place");
    public static SoundEvent COIN_PILE_FALL = registerSoundEvent("coin_pile_fall");
    public static SoundEvent COIN_PILE_HIT = registerSoundEvent("coin_pile_hit");
    public static final BlockSoundGroup COIN_PILE_SOUNDS = new BlockSoundGroup(1f, 1f, COIN_PILE_BREAK, COIN_PILE_PLACE, COIN_PILE_PLACE, COIN_PILE_HIT, COIN_PILE_FALL);
    public static SoundEvent COIN_POUCH_USE = registerSoundEvent("coin_pouch_use");
    public static SoundEvent ENTITY_KIWI_CALL = registerSoundEvent("entity_kiwi_call");
    public static SoundEvent ENTITY_NETHER_NEWT_HURT = registerSoundEvent("entity_nether_newt_hurt");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Clutter.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
