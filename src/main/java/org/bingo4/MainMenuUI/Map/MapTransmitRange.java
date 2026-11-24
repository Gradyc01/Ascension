package org.bingo4.MainMenuUI.Map;

public enum MapTransmitRange {
    FARTHEST(1024),
    FAR(512),
    MIDDLE(256),
    CLOSE(128),
    CLOSEST(64);

    private final int transmitRange;
    MapTransmitRange(int transmitRange) {
        this.transmitRange = transmitRange;
    }

    public int getTransmitRange() {
        return transmitRange;
    }
}
