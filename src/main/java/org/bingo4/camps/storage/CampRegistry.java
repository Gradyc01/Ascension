package org.bingo4.camps.storage;

import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CampRegistry {
    private final Map<String, CampInstance> camps = new LinkedHashMap<>();

    public void register(CampInstance camp) {
        camps.put(camp.instanceId(), camp);
    }

    public CampInstance get(String instanceId) {
        return camps.get(instanceId);
    }

    public Collection<CampInstance> all() {
        return Collections.unmodifiableCollection(camps.values());
    }
}
