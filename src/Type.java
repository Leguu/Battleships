//
// Assignment 4
// Written by: Asil Erturan, 40164714
// For COMP 248 Section EC – Fall 2020
//

import java.util.Random;

// The type of a tile.
// This isn't the tile itself, as it doesn't have a revealed field.
// Types prefixed with E_ are enemy tiles.
// These contains a list of strings of what to say when a player or enemy hits them,
// Just for fun.
public enum Type {
    EMPTY("_", "empty",
            new String[]{"You missed!",
                    "You missed, too bad!",
                    "Torpedo missed, sir!",
                    "*You hear the torpedo whizz past enemy forces*",
                    "No enemies in this sector.",
                    "No fish here, sir.",
                    "Report: Torpedo did not hit anything.",
                    "Captain, our torpedo missed.",
                    "Waste of a torpedo."},
            new String[]{"The enemy's torpedo hit nothing!",
                    "The enemy missed!",
                    "Report: Radar detects unexploded enemy ordinance.",
                    "*You hear an enemy torpedo whizz past you*",
                    "The enemy torpedo missed us, sir!",
                    "Captain, the enemy torpedo missed.",
                    "The enemy just wasted a torpedo!",
                    "Report: Enemy projectile in unoccupied sector."},
            "Sir, we know this sector is empty. Why are you shooting at it?",
            "The enemy shot an empty sector. Again."),

    // We don't actually use this enum, we're just keeping it around for the representation.
    // In reality a "MISS" is just a revealed EMPTY tile.
    MISS("*", "miss", new String[0], new String[0], "ERROR", "ERROR"),

    GRENADE("g", "grenade",
            new String[]{"Catastrophic failure! We've hit our own grenade!",
                    "Red alert! We just shot our own grenade!",
                    "\"HQ to Submarine 21b, you just hit your own grenade, over.\"",
                    "WARNING: Friendly grenade hit!",
                    "WARNING: You hit your own grenade!",
                    "Sir, we just shot our own grenade!"},
            new String[]{"Fantastic! The enemy just hit our grenade!",
                    "Report: Enemy projectile hit friendly grenade.",
                    "The enemy captain just shot our grenade!",
                    "The enemy torpedo hit our grenade, sir!",
                    "They fell right into our trap! Enemy hit our grenade!",
                    "*You hear a loud explosion as an enemy torpedo strikes a friendly grenade*"},
            "Sir, why are we shooting at our own demolished grenades?",
            "The enemy shot one of our grenades! Too bad it had already detonated..."),
    SHIP("s", "ship",
            new String[]{"Catastrophic failure! We've hit our own ship!",
                    "You hit your own ship!",
                    "Men overboard! Enemy torpedo hit one of our ships.",
                    "Red alert! We shot our own ship!",
                    "\"HQ to Submarine 21b, cease and desist friendly fire, over.\"",
                    "That was a friendly ship, captain!"},
            new String[]{"Warning: Enemy torpedo hit friendly ship!",
                    "Sir, one of our ships was destroyed by the enemy forces!",
                    "The enemy hit our ship!",
                    "WARNING: Friendly ship sustained damage.",
                    "WARNING: Friendly ship sunk.",
                    "Report: Enemy projectile destroyed friendly ship."},
            "Sir, why are you shooting our own ship wrecks?",
            "Our enemy is wasting torpedoes on our ship wrecks!"),
    E_GRENADE("G", "enemy grenade",
            new String[]{"Catastrophic failure! We just hit an enemy grenade!",
                    "Sir, that was an enemy grenade!",
                    "WARNING: Enemy grenade hit!",
                    "*The entire submarine rumbles as your torpedo strikes an enemy grenade*",
                    "WARNING: Internal damage sustained from enemy grenade!"},
            new String[]{"Hah, the enemy just shot their own grenade!",
                    "Good news! The enemy hit its own grenade in confusion!",
                    "*You hear a loud bang as an enemy torpedo hits their own grenade*",
                    "Report: Enemy forces have destroyed their own grenade."},
            "Sir, that enemy grenade is already detonated... It's no danger to us.",
            "Our enemy is wasting torpedoes on their own destroyed grenades!"),
    E_SHIP("S", "enemy ship",
            new String[]{"Enemy ship hit in sector 4.",
                    "*You hear your torpedo whizz by... And hit an enemy ship!*",
                    "Report: Enemy ship offline.",
                    "Success! Enemy ship sunk!",
                    "We got 'em! One of their ships is down.",
                    "Excellent shot, captain! Enemy ship down.",
                    "Enemy ships have sustained heavy damage!"},
            new String[]{"Huzzah! Enemy forces are attacking their owns ships!",
                    "Report: Enemies have attacked their own ships.",
                    "*You hear an enemy torpedo whizz past... Right into their own ships!",
                    "The enemy captain must be blind; he just shot his own ship!",
                    "Enemy ship down! They've destroyed themselves!"},
            "Sir, that enemy ship is already sunk. Why are we still shooting at it?",
            "The enemy are wasting torpedoes on their own ship wrecks!");

    // What symbol will we use on the grid for the tile?
    public final String representation;
    public final String name;
    // What happens when you hit this?
    public final String[] friendlyRemarks;
    // What happens when the enemy hits this?
    public final String[] enemyRemarks;
    // What happens when you hit a revealed target?
    public final String friendlyRevealedRemark;
    // What happens when the enemy hits a revealed target?
    public final String enemyRevealedRemark;

    Type(String representation,
         String name,
         String[] friendlyRemarks,
         String[] enemyRemarks,
         String friendlyRevealedRemark,
         String enemyRevealedRemark) {
        this.representation = representation;
        this.name = name;
        this.friendlyRemarks = friendlyRemarks;
        this.enemyRemarks = enemyRemarks;
        this.friendlyRevealedRemark = friendlyRevealedRemark;
        this.enemyRevealedRemark = enemyRevealedRemark;
    }

    boolean isGrenade() {
        return this == Type.GRENADE || this == Type.E_GRENADE;
    }

    String randomFriendlyRemark() {
        Random r = new Random();
        return friendlyRemarks[r.nextInt(friendlyRemarks.length)];
    }

    String randomEnemyRemark() {
        Random r = new Random();
        return enemyRemarks[r.nextInt(enemyRemarks.length)];
    }

    @Override
    public String toString() {
        return representation;
    }
}
