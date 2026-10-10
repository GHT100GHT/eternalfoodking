package com.ght100.eternalfoodking;

import com.ght100.eternalfoodking.Item.SpringAutumnSausage;
import com.ght100.eternalfoodking.Item.WildDogMilk;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class Items {


    public static Item register(String id, Item item) {
        return register(Identifier.fromNamespaceAndPath(Eternalfoodking.MOD_ID, id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(ResourceKey.create(Registries.ITEM, id), item);
    }

    public static Item register(ResourceKey<@NotNull Item> key, Item item) {
        return Registry.register(net.minecraft.core.registries.BuiltInRegistries.ITEM, key, item);
    }


    public static ResourceKey<Item> getItemID(String id) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Eternalfoodking.MOD_ID, id));
    }


    public static Item register(String id, Class<? extends Item> clazz, Item.Properties properties)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        properties.setId(getItemID(id));
        Constructor<?> constructor = clazz.getDeclaredConstructor(Item.Properties.class);
        return register(id, (Item) constructor.newInstance(properties));
    }


    public static final Item DOG_MILK_BUCKET;
    public static final Item CHRYSANTHEMUM_TEA;
    public static final Item ECHO_STEAK;
    public static final Item SPRING_AUTUMN_SAUSAGE;
    public static final Item STERILIZED_DOG_MILK_BUCKET;
    public static final Item WILD_DOG_MILK_BUCKET;
    private static final Item SPATIAL_UPGRADE_SMITHING_TEMPLATE;

    static {
        try {
            DOG_MILK_BUCKET = register("dog_milk_bucket", Item.class,
                    new Item.Properties()
                            .stacksTo(1)
                            .craftRemainder(BUCKET)
                            .usingConvertsTo(BUCKET)
                            .food(new FoodProperties(2, 0.1f, false),
                                    new Consumable(1.6f,
                                            ItemUseAnimation.DRINK,
                                            SoundEvents.GENERIC_DRINK,
                                            true,
                                            Collections.singletonList(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.REGENERATION, 100, 4)
                                            ))
                                    )
                            )
            );

            CHRYSANTHEMUM_TEA = register("chrysanthemum_tea", Item.class,
                    new Item.Properties()
                            .stacksTo(16)
                            .usingConvertsTo(GLASS_BOTTLE)
                            .food(new FoodProperties(10, 0.0f, false),
                                    new Consumable(0.5f,
                                            ItemUseAnimation.DRINK,
                                            SoundEvents.GENERIC_DRINK,
                                            true,
                                            Collections.singletonList(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.STRENGTH, 200, 9)
                                            ))
                                    )
                            )
            );
            ECHO_STEAK = register("echo_steak", Item.class,
                    new Item.Properties()
                            .stacksTo(64)
                            .food(new FoodProperties(8, 0.8f, false),
                                    new Consumable(1.6f,
                                            ItemUseAnimation.EAT,
                                            SoundEvents.GENERIC_EAT,
                                            true,
                                            Collections.singletonList(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.DARKNESS, 60, 0)
                                            ))
                                    )
                            )
            );

            SPRING_AUTUMN_SAUSAGE = register("spring_autumn_sausage", SpringAutumnSausage.class,
                    new Item.Properties()
                            .stacksTo(1)
                            .food(new FoodProperties(4, 0.3f, false))
            );
            STERILIZED_DOG_MILK_BUCKET = register("sterilized_dog_milk_bucket", Item.class,
                    new Item.Properties()
                            .stacksTo(1)
                            .craftRemainder(BUCKET)
                            .usingConvertsTo(BUCKET)
                            .food(new FoodProperties(2, 0.1f, false),
                                    new Consumable(1.6f, ItemUseAnimation.DRINK,
                                            SoundEvents.GENERIC_DRINK, true,
                                            Collections.singletonList(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.REGENERATION, 100, 1)
                                            ))
                                    )
                            )
            );


            WILD_DOG_MILK_BUCKET = register("wild_dog_milk_bucket", WildDogMilk.class,
                    new Item.Properties()
                            .stacksTo(1)
                            .food(new FoodProperties(0, 0.0f, true))
            );
            SPATIAL_UPGRADE_SMITHING_TEMPLATE = register("spatial_upgrade_smithing_template", Item.class,
                    new Item.Properties().rarity(Rarity.UNCOMMON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerAll() {

    }
}