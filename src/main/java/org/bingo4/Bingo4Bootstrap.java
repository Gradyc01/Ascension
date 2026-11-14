package org.bingo4;

import io.papermc.paper.datapack.Datapack;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Bingo4Bootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {

//        context.getLifecycleManager().registerEventHandler(
//                LifecycleEvents.DATAPACK_DISCOVERY,
//                event -> {
//
//                    DatapackRegistrar registrar = event.registrar();
//
//                    registerDatapack(
//                            registrar,
//                            "/datapacks/worldgen_one",
//                            "worldgen_one",
//                            Datapack.Position.BOTTOM
//                    );
//
//                    // Biome Remover
//                    registerDatapack(
//                            registrar,
//                            "/datapacks/worldgen_two",
//                            "worldgen_two",
//                            Datapack.Position.TOP
//                    );
//
//                }
//        );
    }

    private void registerDatapack(
            DatapackRegistrar registrar,
            String resourcePath,
            String packId,
            Datapack.Position position
    ) {

        try {
            URI uri = Objects.requireNonNull(
                    Bingo4Bootstrap.class.getResource(resourcePath),
                    "Could not find datapack: " + resourcePath
            ).toURI();

            var discovered = registrar.discoverPack(
                    uri,
                    packId,
                    config -> {
                        config.autoEnableOnServerStart(true);
                        config.position(true, position);
                    }
            );

            if (discovered == null) {
                throw new IllegalStateException(
                        "Failed to discover datapack: " + packId
                );
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(
                    "Could not register datapack: " + packId, e
            );
        }
    }
}