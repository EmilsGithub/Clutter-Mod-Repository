package net.emilsg.clutter.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final IntProperty LAYERS = IntProperty.of("layers", 0, 7);
    public static final IntProperty COIN_LAYERS = IntProperty.of("coin_layers", 1, 8);
    public static final BooleanProperty FLOWERING = BooleanProperty.of("flowering");
    public static final BooleanProperty CLIPPED = BooleanProperty.of("clipped");
    public static final BooleanProperty FILLED = BooleanProperty.of("filled");
    public static final IntProperty SLICES = IntProperty.of("slices", 1, 7);
    public static final IntProperty HATCH = IntProperty.of("hatch", 0, 3);
    public static final BooleanProperty CAN_HATCH = BooleanProperty.of("can_hatch");
    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final BooleanProperty ON_CHAIN = BooleanProperty.of("on_chain");
    public static final BooleanProperty HAS_WATER = BooleanProperty.of("has_water");
    public static final BooleanProperty ON = BooleanProperty.of("on");
    public static final BooleanProperty INFINITE = BooleanProperty.of("infinite");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty LEGS = BooleanProperty.of("legs");
    public static final IntProperty BOTTLES = IntProperty.of("bottles", 0, 2);
    public static final IntProperty GLASSES = IntProperty.of("glasses", 0, 2);
    public static final BooleanProperty CANDLES = BooleanProperty.of("candles");
    public static final IntProperty PAD_AMOUNT = IntProperty.of("pad_amount", 1, 4);
}
