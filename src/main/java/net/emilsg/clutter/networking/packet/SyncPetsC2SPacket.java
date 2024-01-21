package net.emilsg.clutter.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class SyncPetsC2SPacket {

    private static final String[] TITLES = {
            "King", "Queen", "Emperor", "Empress", "Prince", "Princess", "Archduke", "Archduchess", "Duke", "Duchess",
            "Marquess", "Marchioness", "Count", "Countess", "Viscount", "Viscountess", "Baron", "Baroness", "Baronet", "Baronetess", "Knight", "Dame",
            "Lord", "Lady", "Sir", "Squire", "Master", "Mistress", "Chancellor", "Magistrate", "Ambassador", "Bishop", "Archmage", "Paladin"
    };

    private static final String[] NAMES = {
            "George",
            "Michael",
            "Matt",
            "Jaren",
            "Catalina",
            "Walter",
            "Cullen",
            "Ana",
            "Alvin",
            "Carter",
            "Matthias",
            "Lee",
            "Kaitlin",
            "Morgan",
            "Conner",
            "Tia",
            "Keith",
            "Jett",
            "Shirley",
            "Ellie",
            "Danny",
            "Peter",
            "Kendall",
            "Garrett",
            "Nathan",
            "Dexter",
            "Orlando",
            "Randy",
            "Nilson",
            "Chris",
            "Haylee",
            "Reagan",
            "Bryce",
            "Jacob",
            "Emilee",
            "Danna",
            "Talon",
            "Emelia",
            "Hugh",
            "Khloe",
            "Ismael",
            "Derick",
            "Dominic",
            "Kassidy",
            "Stanley",
            "Isabelle",
            "Terramce",
            "Javier",
            "Ali",
            "Ray",
            "Elijah",
            "Sydnee",
            "Amber",
            "Julia",
            "Grace",
            "Mathew",
            "Aaron",
            "Malia",
            "Max",
            "Hector",
            "Rafael",
            "Rowan",
            "Jaelynn",
            "Talan",
            "Beckett",
            "Lindsay",
            "Case",
            "Ryan",
            "Gia",
            "Kassandra",
            "Daisy",
            "Kiera",
            "Tamia",
            "Anthony",
            "Keegan",
            "Van",
            "Abbie",
            "Brooke",
            "Kaylen",
            "Penelope",
            "Troy",
            "Raymond",
            "Juliana",
            "Linda",
            "Kinley",
            "Livia",
            "Angelo",
            "Marques",
            "Avery",
            "Braiden",
            "Sophie",
            "Alfonso",
            "Esmeralda",
            "Milo",
            "Helena",
            "Todd",
            "Sarah",
            "Adalyn",
            "Larissa",
            "Lance",
            "Melissa",
            "Mark",
            "Kermit",
            "Croak",
            "Hopper",
            "Freddo",
            "Frogger",
            "Pepe",
            "Naveen",
            "Edward",
            "Jeremiah",
            "Jelly",
            "Bogart",
            "Trevor",
            "April",
            "Athena",
            "Belle",
            "Charlotte",
            "Daphne",
            "Dixie",
            "Dot",
            "Ethyl",
            "Gidget",
            "Gigi",
            "Holly",
            "Honey",
            "Misty",
            "Trixie",
            "Xena",
            "Zoe",
            "Zookie",
            "Artie",
            "Bartles",
            "Bob",
            "Bubba",
            "Bumble",
            "Burt",
            "Buster",
            "Chester",
            "Caesar",
            "Dart",
            "Diego",
            "Homer",
            "Jasper",
            "Jesper",
            "Jojo",
            "Paco",
            "Dapylil",
            "Fatso",
            "Froggo",
            "Gorf",
            "Jafar",
            "Aatrox",
            "Xolaani"
    };


    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        Vec3d cameraPos = player.getCameraPosVec(1.0F);
        Vec3d rotationVec = player.getRotationVec(1.0F);
        Vec3d viewVec = cameraPos.add(rotationVec.multiply(10, 10, 10));

        List<Entity> nearbyEntities = player.getWorld().getOtherEntities(player, player.getBoundingBox().expand(3));
        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            Optional<Vec3d> raycastResult = entity.getBoundingBox().raycast(cameraPos, viewVec);
            if (raycastResult.isPresent()) {
                double distance = cameraPos.squaredDistanceTo(raycastResult.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity != null) {
            Entity finalClosestEntity = closestEntity;

            if(!(finalClosestEntity instanceof FrogEntity) && !Objects.equals(player.getName().getString(), "E7Smy")) {
                player.sendMessage(Text.of("You pet " + finalClosestEntity.getName().getString() + "."));
            } else if (!Objects.equals(finalClosestEntity.getName().getString(), "Frog")) {
                player.sendMessage(Text.of("You pet " + finalClosestEntity.getName().getString() + "."));
            } else {
                    player.sendMessage(Text.of("You bequeathed " + finalClosestEntity.getName().getString() + " with the name: "  + getRandomFrogName(finalClosestEntity) + "."));
                    finalClosestEntity.setCustomName(Text.of(getRandomFrogName(finalClosestEntity)));
            }

            server.execute(() -> {
                ParticleEffect particleEffect = ParticleTypes.HEART;

                if(finalClosestEntity instanceof PassiveEntity passiveEntity) {
                    passiveEntity.playAmbientSound();
                }

                Box hitbox = finalClosestEntity.getBoundingBox();
                double hitboxWidth = hitbox.getXLength();
                double hitboxHeight = hitbox.getYLength();
                double hitboxDepth = hitbox.getZLength();

                for (int i = 0; i < (int)(10 * finalClosestEntity.getWidth()); i++) {
                    Random random = new Random();

                    double offsetX = hitbox.minX + (hitboxWidth * random.nextDouble());
                    double offsetY = hitbox.minY + (hitboxHeight * random.nextDouble());
                    double offsetZ = hitbox.minZ + (hitboxDepth * random.nextDouble());

                    player.getServerWorld().spawnParticles(particleEffect, offsetX, offsetY, offsetZ, 1, 0, 0, 0, 0.0);
                }

            });
        }

    }

    private static String getRandomFrogName(Entity entity) {
        String entityId = String.valueOf(entity.getUuid());
        assert entityId != null;
        long idAsLong = stingToHash(entityId);
        int longAsInt = (int) Math.abs(idAsLong);
        Random random = new Random(longAsInt);

        return random.nextInt(1000) == 1 ? "Bobr Kurwa" : (TITLES[(longAsInt % TITLES.length)] + " " + NAMES[(longAsInt % NAMES.length)]);
    }

    private static long stingToHash(String input) {
        return input.hashCode();
    }
}
