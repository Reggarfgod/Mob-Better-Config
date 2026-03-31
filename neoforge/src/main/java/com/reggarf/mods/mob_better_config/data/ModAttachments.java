package com.reggarf.mods.mob_better_config.data;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    "mob_better_config"
            );

    public static final Supplier<AttachmentType<MobStats>> MOB_STATS =
            ATTACHMENTS.register("mob_stats",
                    () -> AttachmentType.builder(holder -> new MobStats()).build());
}