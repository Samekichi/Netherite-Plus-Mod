package com.oroarmor.netherite_plus;

import java.util.Arrays;
import java.util.function.Consumer;

import com.oroarmor.netherite_plus.block.NetheritePlusModBlocks;
import com.oroarmor.netherite_plus.item.NetheritePlusModItems;
import com.oroarmor.netherite_plus.render.NetheriteShulkerBoxBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class NetheritePlusClientMod implements ClientModInitializer {
	public static final SpriteIdentifier NETHERITE_SHIELD_BASE = new SpriteIdentifier(
			SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier("netherite_plus", "entity/netherite_shield_base"));

	public static final SpriteIdentifier NETHERITE_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(
			SpriteAtlasTexture.BLOCK_ATLAS_TEX,
			new Identifier("netherite_plus", "entity/netherite_shield_base_nopattern"));

	public static final Identifier SHULKER_BOXES_ATLAS_TEXTURE = new Identifier("netherite_plus",
			"textures/atlas/shulker_boxes.png");

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(NetheritePlusModBlocks.NETHERITE_SHULKER_BOX_ENTITY,
				NetheriteShulkerBoxBlockEntityRenderer::new);

		ClientSpriteRegistryCallback.event(SHULKER_BOXES_ATLAS_TEXTURE)
				.register(NetheritePlusClientMod::registerShulkerBoxTextures);
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
				.register(NetheritePlusClientMod::registerShieldTextures);

		NetheritePlusModItems.registerItemsWithModelProvider();
	}

	public static void makeAtlases(Consumer<SpriteIdentifier> consumer) {
		consumer.accept(
				new SpriteIdentifier(SHULKER_BOXES_ATLAS_TEXTURE, new Identifier("netherite_plus", makePath(null))));
		Arrays.stream(DyeColor.values()).forEach(c -> consumer.accept(
				new SpriteIdentifier(SHULKER_BOXES_ATLAS_TEXTURE, new Identifier("netherite_plus", makePath(c)))));
	}

	private static String makePath(DyeColor color) {
		if (color != null) {
			return "entity/netherite_shulker/netherite_shulker_" + color.getName();
		}

		return "entity/netherite_shulker/netherite_shulker";
	}

	@SuppressWarnings("unused")
	public static void registerShulkerBoxTextures(SpriteAtlasTexture atlas,
			ClientSpriteRegistryCallback.Registry registry) {
		registry.register(new Identifier("netherite_plus", makePath(null)));
		Arrays.stream(DyeColor.values()).forEach(c -> registry.register(new Identifier("netherite_plus", makePath(c))));
	}

	@SuppressWarnings("unused")
	public static void registerShieldTextures(SpriteAtlasTexture atlas,
			ClientSpriteRegistryCallback.Registry registry) {
		registry.register(NETHERITE_SHIELD_BASE.getTextureId());
		registry.register(NETHERITE_SHIELD_BASE_NO_PATTERN.getTextureId());
	}
}
