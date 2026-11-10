package org.bingo4.camps.api;

public record LocalPos(int x, int y, int z) {
    public String configKey() {
        return x + "," + y + "," + z;
    }
}
