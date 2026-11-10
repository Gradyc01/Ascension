package org.bingo4.camps.api;

public enum CampRotation {
    NONE(0), CLOCKWISE_90(90), CLOCKWISE_180(180), COUNTERCLOCKWISE_90(270);

    private final int degrees;

    CampRotation(int degrees) {
        this.degrees = degrees;
    }

    public int degrees() {
        return degrees;
    }
}
