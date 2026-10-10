package com.ght100.eternalfoodking.Item;

import com.ght100.eternalfoodking.PlayerDiedRecord;
import com.ght100.eternalfoodking.imixin.IDiedPlayerRecord;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;
import java.util.function.Consumer;


public class SpringAutumnSausage extends Item {
    public SpringAutumnSausage(Properties properties) {
        super(properties);
    }
    private static final String USES_KEY = "uses";

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int uses = getUses(stack);


        builder.accept(Component.literal("剩余使用次数: " + uses )
                .withStyle(ChatFormatting.GRAY));

    }
    public static int getUses(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag nbt = data.copyTag();
            return nbt.getInt(USES_KEY).orElse(0);
        }
        return 0;
    }
    public static void setUses(ItemStack stack, int uses) {
        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.putInt(USES_KEY, Math.max(0, uses));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(level.isClientSide())return stack;

        if(entity instanceof ServerPlayer player1 && getUses(stack)>0){
            if(player1 instanceof IDiedPlayerRecord player) {
                if (!player.getDiedRecord().empty()) {
                    ItemStack resultStack=stack;
                    Stack<PlayerDiedRecord> playerDiedRecords = player.getDiedRecord();
                    PlayerDiedRecord pos=playerDiedRecords.peek();
                    BlockPos lastDeathPos = pos.getPos();
                    String dimId=pos.getLevel();
                    Identifier id = Identifier.parse(dimId);
                    ResourceKey<@NotNull Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, id);
                    if (level.getServer().getLevel(dimensionKey)==null){
                        ((Player) player).sendOverlayMessage(Component.literal("维度记录异常，采用主世界作为默认值"));
                        dimensionKey=Level.OVERWORLD;
                    }
                    player1.teleportTo(level.getServer().getLevel(dimensionKey), lastDeathPos.getX() + 0.5, lastDeathPos.getY(), lastDeathPos.getZ() + 0.5, java.util.EnumSet.noneOf(net.minecraft.world.entity.Relative.class), player1.getYRot(), player1.getXRot(), false);;

                    playerDiedRecords.pop();
                    setUses(stack, getUses(stack) - 1);
                    if (getUses(stack) <= 0) {
                        resultStack = ItemStack.EMPTY;
                    }
                    return resultStack;
                } else {
                    ((Player) player).sendOverlayMessage(Component.literal("你没有死亡记录，无法逆转时间！"));
                    return stack;
                }
            }
        }
        return stack;
    }
}
