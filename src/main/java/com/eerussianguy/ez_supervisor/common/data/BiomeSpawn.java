package com.eerussianguy.ez_supervisor.common.data;

import java.util.List;
import com.eerussianguy.ez_supervisor.EZSupervisor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public record BiomeSpawn(EntityType<?> type, List<ResourceLocation> biomes, int minCount, int maxCount, int weight)
{
    public static List<BiomeSpawn> readAll(JsonArray json)
    {
        return EZSupervisor.mapArray(json, e -> create(e.getAsJsonObject()));
    }

    public static BiomeSpawn create(JsonObject json)
    {
        final EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(GsonHelper.getAsString(json, "entity")));
        final List<ResourceLocation> biomes = !json.has("biomes") ? List.of() : EZSupervisor.mapArray(json.getAsJsonArray("biomes"), e -> new ResourceLocation(e.getAsString()));
        final int minCount = GsonHelper.getAsInt(json, "min_count", 1);
        final int maxCount = GsonHelper.getAsInt(json, "max_count", 4);
        final int weight = GsonHelper.getAsInt(json, "weight", 1);
        return new BiomeSpawn(type, biomes, minCount, maxCount, weight);
    }

}
