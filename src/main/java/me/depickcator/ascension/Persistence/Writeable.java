package me.depickcator.ascension.Persistence;

import com.google.gson.JsonObject;

public interface Writeable {
    JsonObject toJson();
}
