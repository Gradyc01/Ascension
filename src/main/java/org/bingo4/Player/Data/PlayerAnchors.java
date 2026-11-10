package org.bingo4.Player.Data;

import org.bingo4.MainMenuUI.Command.AnchorPoints.AnchorPoint;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerAnchors {
    private final List<AnchorPoint> anchorPoints;
    private final int COST = 500;
    public PlayerAnchors() {
        this.anchorPoints = new ArrayList<>();
    }

    /*Creates a new Anchor Point
    * Returns true if successfully created
    * False Otherwise*/
    public boolean createAnchorPoint(PlayerData playerData) {
        Player p = playerData.getPlayer();
        Location location = p.getLocation();
        if (anchorPoints.size() > 4) {
            TextUtil.errorMessage(p, "You already have the maximum number of anchor points!");
            return false;
        }
//        if (playerData.getPlayerUnlocks().getUnlockTokens() < COST) {
//            TextUtil.errorMessage(p, "You don't have the funds to create an anchor point!");
//            return false;
//        }
//        playerData.getPlayerUnlocks().addUnlockTokens(-COST);
        AnchorPoint anchorPoint = new AnchorPoint(location);

        anchorPoints.add(anchorPoint);
        p.sendMessage(TextUtil.makeText("[Anchor Point] ", TextUtil.BLUE)
                .append(TextUtil.makeText("Anchor ", TextUtil.DARK_GREEN))
                .append(TextUtil.makeText(anchorPoint.getName(), TextUtil.GOLD))
                .append(TextUtil.makeText(" Creation Successful! Located at ", TextUtil.DARK_GREEN))
                .append(TextUtil.makeText(anchorPoint.getCoordinateString(), TextUtil.AQUA))
        );
        SoundUtil.playHighPitchPling(p);
        return true;
    }

    /*Creates an old Anchor Point
     * Returns true if successfully removed
     * False Otherwise*/
    public boolean removeAnchorPoint(AnchorPoint anchorPoint) {
        if (anchorPoints.contains(anchorPoint)) {
            anchorPoints.remove(anchorPoint);

            return true;
        }
        return false;
    }

    public List<AnchorPoint> getAnchorPoints() {
        return Collections.unmodifiableList(anchorPoints);
    }
}
