package org.bingo4.Persistence;

import com.google.gson.JsonObject;

public interface Writeable {
    JsonObject toJson();
}
