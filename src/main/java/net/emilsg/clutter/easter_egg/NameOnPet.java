package net.emilsg.clutter.easter_egg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;
import java.util.Random;

public class NameOnPet {

    private static final String[] TITLES = {
            "King", "Queen", "Emperor", "Empress", "Prince", "Princess", "Archduke", "Archduchess", "Duke", "Duchess", "Bastard",
            "Marquess", "Marchioness", "Count", "Countess", "Viscount", "Viscountess", "Baron", "Baroness", "Baronet", "Baronetess", "Knight", "Dame",
            "Lord", "Lady", "Sir", "Squire", "Master", "Mistress", "Chancellor", "Magistrate", "Ambassador", "Bishop", "Archmage", "Paladin"
    };

    private static final String[] NAMES = {
            "George", "Michael", "Matt", "Jaren", "Catalina", "Walter", "Cullen", "Ana", "Alvin", "Carter", "Matthias", "Lee", "Kaitlin", "Morgan",
            "Conner", "Tia", "Keith", "Jett", "Shirley", "Ellie", "Danny", "Peter", "Kendall", "Garrett", "Nathan", "Dexter", "Orlando", "Randy",
            "Nilson", "Chris", "Haylee", "Reagan", "Bryce", "Jacob", "Emilee", "Danna", "Talon", "Emelia", "Hugh", "Khloe", "Ismael", "Derick",
            "Dominic", "Kassidy", "Stanley", "Isabelle", "Terramce", "Javier", "Ali", "Ray", "Elijah", "Sydnee", "Amber", "Julia", "Grace",
            "Mathew", "Aaron", "Malia", "Max", "Hector", "Rafael", "Rowan", "Jaelynn", "Talan", "Beckett", "Lindsay", "Case", "Ryan", "Gia",
            "Kassandra", "Daisy", "Kiera", "Tamia", "Anthony", "Keegan", "Van", "Abbie", "Brooke", "Kaylen", "Penelope", "Troy", "Raymond",
            "Juliana", "Linda", "Kinley", "Livia", "Angelo", "Marques", "Avery", "Braiden", "Sophie", "Alfonso", "Esmeralda", "Milo", "Helena",
            "Todd", "Sarah", "Adalyn", "Larissa", "Lance", "Melissa", "Mark", "Kermit", "Croak", "Hopper", "Freddo", "Frogger", "Pepe", "Naveen",
            "Edward", "Jeremiah", "Jelly", "Bogart", "Trevor", "April", "Athena", "Belle", "Charlotte", "Daphne", "Dixie", "Dot", "Ethyl", "Gidget",
            "Gigi", "Holly", "Honey", "Misty", "Trixie", "Xena", "Zoe", "Zookie", "Artie", "Bartles", "Bob", "Bubba", "Bumble", "Burt", "Buster",
            "Chester", "Caesar", "Dart", "Diego", "Homer", "Jasper", "Jesper", "Jojo", "Paco", "Dapylil", "Fatso", "Froggo", "Gorf", "Jafar",
            "Aatrox", "Xolaani"
    };

    public static Entity giveName(Entity finalClosestEntity, PlayerEntity player) {
        if (!(finalClosestEntity instanceof FrogEntity)) {
            player.sendMessage(Text.of("You pet " + finalClosestEntity.getName().getString() + "."));
        } else if (!Objects.equals(finalClosestEntity.getName().getString(), "Frog")) {
            player.sendMessage(Text.of("You pet " + finalClosestEntity.getName().getString() + "."));
        } else if (player.getName().getString().equals("E7Smy")) {
            String randomFrogName = getRandomFrogName(finalClosestEntity);
            player.sendMessage(Text.of("You bequeathed " + finalClosestEntity.getName().getString() + " with the name: " + randomFrogName + "."));
            finalClosestEntity.setCustomName(Text.of(randomFrogName));
        }

        return finalClosestEntity;
    }


    private static String getRandomFrogName(Entity entity) {
        String entityId = String.valueOf(entity.getUuid());
        long idAsLong = stingToHash(entityId);
        int longAsInt = (int) Math.abs(idAsLong);
        Random random = new Random(longAsInt);

        return random.nextInt(1000) == 1 ? "Bobr Kurwa" : (TITLES[(longAsInt % TITLES.length)] + " " + NAMES[(longAsInt % NAMES.length)]);
    }

    private static long stingToHash(String input) {
        return input.hashCode();
    }

}
