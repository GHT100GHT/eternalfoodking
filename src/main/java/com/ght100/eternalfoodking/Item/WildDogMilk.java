package com.ght100.eternalfoodking.Item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;


public class WildDogMilk extends Item {
    private static final String COUNT_KEY = "dog_milk_count";

    public WildDogMilk(Properties properties) {
        super(properties);
    }


    public static int getCount(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag nbt = data.copyTag();
            return nbt.getInt(COUNT_KEY).orElse(1); // 默认1
        }
        return 1;
    }

    public static void setCount(ItemStack stack, int count) {
        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.putInt(COUNT_KEY, Math.max(1, count));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!level.isClientSide()) {
            int count = getCount(stack);
            int duration = 200 * count;

            user.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, duration, 254));
            user.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, duration, 254));
            user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, 254));

            if(!(user instanceof Player player) ||player.gameMode()!= GameType.CREATIVE) stack.shrink(1);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> builder, TooltipFlag flag) {
        int count = getCount(stack);
        builder.accept(Component.literal("净含量: " + count*1000 + "ml")
                .withStyle(ChatFormatting.GOLD));
    }
}