package com.example.nameless_cat.client.renderer.layer;

import com.example.nameless_cat.entity.NamelessCatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatCollarLayer extends RenderLayer<NamelessCatEntity, CatModel<NamelessCatEntity>> {
	private static final ResourceLocation CAT_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
	private final CatModel<NamelessCatEntity> catModel;

	public CatCollarLayer(RenderLayerParent<NamelessCatEntity, CatModel<NamelessCatEntity>> renderLayer, EntityModelSet modelSet) {
		super(renderLayer);
		this.catModel = new CatModel<>(modelSet.bakeLayer(ModelLayers.CAT_COLLAR));
	}

	public void render(PoseStack poseStack, MultiBufferSource p_116667_, int p_116668_, NamelessCatEntity entity, float p_116670_, float p_116671_, float p_116672_, float p_116673_, float p_116674_, float p_116675_) {
		if (entity.isTame()) {
			float[] afloat = entity.getCollarColor().getTextureDiffuseColors();
			coloredCutoutModelCopyLayerRender(this.getParentModel(), this.catModel, CAT_COLLAR_LOCATION, poseStack, p_116667_, p_116668_, entity, p_116670_, p_116671_, p_116673_, p_116674_, p_116675_, p_116672_, afloat[0], afloat[1], afloat[2]);
		}
	}
}