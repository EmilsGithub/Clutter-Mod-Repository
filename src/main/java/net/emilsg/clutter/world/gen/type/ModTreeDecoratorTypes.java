package net.emilsg.clutter.world.gen.type;

import net.emilsg.clutter.mixin.TreeDecoratorTypeInvoker;
import net.emilsg.clutter.world.gen.tree.WallMushroomTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModTreeDecoratorTypes {
    public static final TreeDecoratorType<?> TRUNK_WALL_MUSHROOM = TreeDecoratorTypeInvoker.callRegister("trunk_wall_mushroom", WallMushroomTreeDecorator.CODEC);

    public static void register() {

    }

}
