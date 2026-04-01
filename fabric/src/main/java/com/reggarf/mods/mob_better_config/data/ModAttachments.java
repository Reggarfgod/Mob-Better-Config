package com.reggarf.mods.mob_better_config.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.minecraft.resources.Identifier;

public class ModAttachments {

    public static final AttachmentType<MobStats> MOB_STATS =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath("mob_better_config", "mob_stats"),
                    builder -> builder.initializer(MobStats::new)
            );
}