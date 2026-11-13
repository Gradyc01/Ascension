package org.bingo4.Teams;

import org.bingo4.MainMenuUI.Map.MapItems;
import org.bingo4.Structures.AnchorPoints.AnchorPoint;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamAnchors {
    private final List<AnchorPoint> anchorPoints;
    private final Team team;
    private final MapItems mapItems;
    public TeamAnchors(Team team) {
        this.anchorPoints = new ArrayList<>();
        this.team = team;
        this.mapItems = new MapItems();
    }

    public void addAnchorPoint(AnchorPoint anchorPoint) {
        anchorPoints.add(anchorPoint);
        TextUtil.debugText(String.format("Added anchor point at %s for team: %s", TextUtil.formatLocation(anchorPoint.getLocation()), team.getLeader()));
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
