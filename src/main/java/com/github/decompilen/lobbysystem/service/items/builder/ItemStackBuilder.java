package com.github.decompilen.lobbysystem.service.items.builder;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, staticName = "create")
public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public static ItemStackBuilder of(Material material, int amount, short damage) {
        return ItemStackBuilder.fromItemStack(new ItemStack(material, amount, damage));
    }

    public static ItemStackBuilder of(Material material, int amount) {
        return ItemStackBuilder.fromItemStack(new ItemStack(material, amount));
    }

    public static ItemStackBuilder fromMaterial(Material material) {
        return ItemStackBuilder.fromItemStack(new ItemStack(material));
    }

    public static ItemStackBuilder fromItemStack(ItemStack itemStack) {
        return ItemStackBuilder.create()
                .itemStack(itemStack)
                .itemMeta(itemStack.getItemMeta());
    }

    public ItemStackBuilder displayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder lore(String line) {
        itemMeta.setLore(Lists.newArrayList(line));
        return this;
    }

    public ItemStackBuilder lore(String... lines) {
        itemMeta.setLore(Arrays.asList(lines));
        return this;
    }

    public ItemStackBuilder lore(List<String> lines) {
        itemMeta.setLore(lines);
        return this;
    }

    public ItemStackBuilder appendLore(String line) {
        itemMeta.getLore().add(line);
        return this;
    }

    public ItemStackBuilder clearLore() {
        itemMeta.setLore(Lists.newArrayList());
        return this;
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemStackBuilder subId(byte subId) {
        itemStack.setDurability(subId);
        return this;
    }

    public ItemStackBuilder dyeColor(DyeColor dyeColor) {
        itemStack.setDurability(dyeColor.getData());
        return this;
    }

    public ItemStackBuilder leatherArmorColor(Color color) {
        ((LeatherArmorMeta) itemMeta).setColor(color);
        return this;
    }

    public ItemStackBuilder skullOwner(String skullOwner) {
        ((SkullMeta) itemMeta).setOwner(skullOwner);
        return this;
    }

    @SneakyThrows
    public ItemStackBuilder skullTexture(String textureValue) {
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "null");
        gameProfile.getProperties().put("textures", new Property("textures", textureValue));
        final Field profileField = skullMeta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(skullMeta, gameProfile);
        return this;
    }

    public ItemStackBuilder itemFlags(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemStackBuilder removeItemFlags(ItemFlag... itemFlags) {
        itemMeta.removeItemFlags(itemFlags);
        return this;
    }

    public ItemStackBuilder unbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public ItemStackBuilder clone() {
        return ItemStackBuilder.fromItemStack(itemStack.clone());
    }
}
