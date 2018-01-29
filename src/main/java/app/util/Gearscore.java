package app.util;

import app.model.Character;
import app.model.Item;

import java.util.List;

/**
 * Created by adamz on 28.01.2018.
 */
public class Gearscore {

    public static double getItemScore(Item item, String slotMod) {
        if (item == null || item.getSlot().equals("Tabard") || item.getSlot().equals("Shirt")) return 0;
        float qualityScale = 1;
        int rarity = item.getQuality();
        float itemLevel = item.getItemLevel();
        double gearScore;
        float scale = 1.8616f;
        if (item.getQuality() == 5) {
            qualityScale = 1.3f;
            rarity = 4;
        } else if (item.getQuality() == 1) {
            qualityScale = 0.005f;
            rarity = 2;
        } else if (item.getQuality() == 0) {
            qualityScale = 0.005f;
            rarity = 2;
        }
        if (item.getQuality() == 7) {
            rarity = 3;
            itemLevel = 187.05f;
        }
        float multiplierA = itemLevel > 120 ? FormulaA.multiplierA[rarity] : FormulaB.multiplierA[rarity];
        float multiplierB = itemLevel > 120 ? FormulaA.multiplierB[rarity] : FormulaB.multiplierB[rarity];
        float slotMultiplier = Slot.getMultiplier(slotMod != null ? slotMod : item.getSlot());
        gearScore = Math.floor((itemLevel - multiplierA) / multiplierB * slotMultiplier * scale * qualityScale);

        return gearScore;
    }

    public static double getCharacterScore(Character c) {
        float gearscore = 0;
        List<Item> items = c.getItems();
        boolean isFuryWarrior = false;
        if (c.getInfo().contains("Warrior")) {
            int twoHandCount = 0;
            for (Item item : items) {
                if (item.getSlot().equals("Two-Hand")) twoHandCount++;
            }
            isFuryWarrior = twoHandCount > 1;
        }
        double itemGs = 0;
        for (Item item : items) {
            if (c.getInfo().contains("Hunter")) {
                if (item.getSlot().equals("Ranged"))
                    itemGs = Gearscore.getItemScore(item, "Two-Hand");
                else if (item.getSlot().endsWith("Hand"))
                    itemGs = Gearscore.getItemScore(item, "Ranged");
                else
                    itemGs = Gearscore.getItemScore(item, null);
            } else if (isFuryWarrior && item.getSlot().equals("Two-Hand")) {
                itemGs = Gearscore.getItemScore(item, "One-Hand");
            } else
                itemGs = Gearscore.getItemScore(item, null);
            gearscore += itemGs;
        }
        return gearscore;
    }

    static class FormulaA {
        static float[] multiplierA = {0, 0, 73, 81.375f, 91.45f};
        static float[] multiplierB = {0, 0, 1, 0.8125f, 0.65f};

    }

    static class FormulaB {
        static float[] multiplierA = {0, 0, 8, 0.75f, 26};
        static float[] multiplierB = {0, 2.25f, 2, 1.8f, 1.2f};
    }

    static class Slot {
        static float RELIC = 0.3164f;
        static float TRINKET = 0.5625f;
        static float TWOHWEAPON = 2;
        static float WEAPONMAINHAND = 1.0000f;
        static float WEAPONOFFHAND = 1.0000f;
        static float RANGED = 0.3164f;
        static float THROWN = 0.3164f;
        static float WEAPON = 1.0000f;
        static float HOLDABLE = 1.0000f;
        static float HEAD = 1.0000f;
        static float NECK = 0.5625f;
        static float SHOULDER = 0.7500f;
        static float CHEST = 1.0000f;
        static float WAIST = 0.7500f;
        static float LEGS = 1.0000f;
        static float FEET = 0.75f;
        static float WRIST = 0.5625f;
        static float HAND = 0.7500f;
        static float FINGER = 0.5625f;
        static float CLOAK = 0.5625f;

        static float getMultiplier(String slotName) {
            switch (slotName) {
                case "Head":
                    return HEAD;
                case "Neck":
                    return NECK;
                case "Shoulder":
                    return SHOULDER;
                case "Back":
                    return CLOAK;
                case "Chest":
                    return CHEST;
                case "Wrist":
                    return WRIST;
                case "Hands":
                    return HAND;
                case "Waist":
                    return WAIST;
                case "Legs":
                    return LEGS;
                case "Feet":
                    return FEET;
                case "Finger":
                    return FINGER;
                case "Trinket":
                    return TRINKET;
                case "Two-Hand":
                    return TWOHWEAPON;
                case "Held In Off-Hand":
                    return HOLDABLE;
                case "Ranged":
                    return RANGED;
                case "Thrown":
                    return THROWN;
                case "Libram":
                    return RELIC;
                case "Seal":
                    return RELIC;
                case "Relic":
                    return RELIC;
                case "Idol":
                    return RELIC;
                case "Totem":
                    return RELIC;
                case "One-Hand":
                    return WEAPON;
                case "Main Hand":
                    return WEAPON;
                case "Off Hand":
                    return WEAPONOFFHAND;
                default:
                    System.out.println("Slot '" + slotName + "' not found on list, returning 0");
                    return 0;
            }
        }
    }
}
