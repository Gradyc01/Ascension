package org.bingo4.Teams;


import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import static org.bingo4.Player.Interfaces.WeaponInfo.MELEE;
import static org.bingo4.Player.Interfaces.WeaponInfo.PROJECTILE;
import static org.bukkit.attribute.Attribute.ARMOR;
import static org.bukkit.attribute.AttributeModifier.Operation.ADD_NUMBER;

public class TeamComeback {
    public static final NamespacedKey COMEBACK_TIER = new NamespacedKey(Bingo4.getInstance(), "player_comeback_tier");

    private final Team team;
    private final Bingo4 plugin = Bingo4.getInstance();
    private double comebackStrength;


    public TeamComeback(Team team) {
        this.team = team;
        comebackStrength = 0.0;
    }

    public void recalculateComebackAdvantages(int gameScoreDifferential) {
        comebackStrength = getComebackStrength(gameScoreDifferential);
        int comebackTier = (int) Math.clamp(Math.ceil(comebackStrength * 10), 0, 5);
        TextUtil.debugText(String.format("[Team %s's Comeback] comeback strength is calculated to be %s. Comeback Tier: '%s'", team.getLeader().getName(), comebackStrength, comebackTier));

        for (PlayerData playerData : team.getTeamMembersData()) {
            playerData.getPlayerItems().setPlayerWeaponTier(comebackTier, MELEE, COMEBACK_TIER);
            playerData.getPlayerItems().setPlayerWeaponTier(comebackTier, PROJECTILE, COMEBACK_TIER);
            reconcileArmorAttribute(playerData, Math.clamp(comebackStrength * 15, 0, 10));
        }
    }

    public int calculateCaptureTime(int startingCaptureTime) {
        double captureTime = Math.clamp(startingCaptureTime + (comebackStrength * -60), 10, 60);
        TextUtil.debugText(String.format("[Team %s's Comeback] capture time is calculated to be %s", team.getLeader().getName(), captureTime));
        return (int) captureTime;
    }

    public int calculateBingoLockdownGameScoreAmount() {
        int gameScore = team.getTeamStats().getGameScore();
        double scoreAmount;
        if (comebackStrength < 0) {
            scoreAmount = (gameScore + (gameScore * Math.clamp(comebackStrength - 0.1, -1, 0)));
        } else {
            scoreAmount = Math.max(gameScore, Math.max(team.getTeamStats().getGameScoreDifferential() - 10, gameScore + (gameScore * comebackStrength)));
        }
        TextUtil.debugText(String.format("[Team %s's Comeback] From this bingo lockdown earns %s points", team.getLeader().getName(), scoreAmount));
        return (int) scoreAmount;
    }

    private void reconcileArmorAttribute(PlayerData playerData, double levels) {
        Player player = playerData.getPlayer();

        AttributeInstance armor = player.getAttribute(ARMOR);
        if (armor == null) return;
        armor.removeModifier(COMEBACK_TIER);
        AttributeModifier modifier = new AttributeModifier(COMEBACK_TIER, levels, ADD_NUMBER);
        armor.addModifier(modifier);
    }

    private double getComebackStrength(double gameScoreDifferential) {
        // How far behind/ahead a team must be before scaling begins
        final double START_THRESHOLD = 6.0;

        // Deficit at which maximum strength is reached
        final double MAX_DEFICIT = 50.0;

        final double EXPONENT = 1.4;

        if (Math.abs(gameScoreDifferential) <= START_THRESHOLD) {
            return 0.0;
        }

        double differential = Math.abs(gameScoreDifferential);

        double normalized = Math.clamp(
                (differential - START_THRESHOLD) / (MAX_DEFICIT - START_THRESHOLD),
                0.0,
                1.0
        );

        double strength = Math.pow(normalized, EXPONENT);

        return Math.copySign(strength, -gameScoreDifferential);
    }


}
