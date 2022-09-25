package com.example.nameless_cat.client.renderer;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.client.renderer.layer.CatCollarLayer;
import com.example.nameless_cat.entity.NamelessCatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;

@OnlyIn(Dist.CLIENT)
public class NamelessCatEntityRenderer extends MobRenderer<NamelessCatEntity, CatModel<NamelessCatEntity>> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(NamelessCatMod.MODID, "textures/entity/nameless_cat.png");

	// TODO add a transparency / opacity of 5%
	// TODO when the renderToBuffer inside the renderType != null check if render function in LivingEntityRenderer
	// can change the last parameter "flag1 ? 0.15F : 1.0F" with is 1.0F this is causing transparency

	// START of code from cat renderer
	public NamelessCatEntityRenderer(Context context) {
		super(context, new CatModel<>(context.bakeLayer(ModelLayers.CAT)), 0.4f);
		this.addLayer(new CatCollarLayer(this, context.getModelSet()));
	}

	public ResourceLocation getTextureLocation(NamelessCatEntity entity) {
		return TEXTURE;
	}

	protected void scale(NamelessCatEntity entity, PoseStack poseStack, float p_113954_) {
		super.scale(entity, poseStack, p_113954_);
		poseStack.scale(0.8F, 0.8F, 0.8F);
//		poseStack.scale(5.8F, 5.8F, 5.8F); // giant_soul_kitty this is for giant zombie
	}

	protected void setupRotations(NamelessCatEntity entity, PoseStack poseStack, float p_113958_, float p_113959_, float p_113960_) {
		super.setupRotations(entity, poseStack, p_113958_, p_113959_, p_113960_);
		float f = entity.getLieDownAmount(p_113960_);
		if (f > 0.0F) {
			poseStack.translate((double)(0.4F * f), (double)(0.15F * f), (double)(0.1F * f));
			poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.rotLerp(f, 0.0F, 90.0F)));
			BlockPos blockpos = entity.blockPosition();

			for(Player player : entity.level.getEntitiesOfClass(Player.class, (new AABB(blockpos)).inflate(2.0D, 2.0D, 2.0D))) {
				if (player.isSleeping()) {
					poseStack.translate((double)(0.15F * f), 0.0D, 0.0D);
					break;
				}
			}
		}
	}

	// START custom code
	protected int getBlockLightLevel(NamelessCatEntity entity, BlockPos blockPos) {
		// 0–15
		float i = entity.getHealth() / entity.getMaxHealth();
		return (int)(i * 15);
	}
}
