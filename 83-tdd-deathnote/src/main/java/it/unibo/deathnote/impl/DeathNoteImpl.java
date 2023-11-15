package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    private Map<String, String> deathList = new HashMap<>();

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber >= 1 && ruleNumber <= RULES.size()) {
            return RULES.get(ruleNumber);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        deathList.put(name, null);       
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause.isEmpty() || deathList.isEmpty()){
            throw new IllegalStateException();
        }
        try {
            wait(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = deathList.keySet().iterator().next();
        deathList.put(name, cause);
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        if(details.isEmpty() || deathList.isEmpty()){
            throw new IllegalStateException();
        }
        try {
            wait(6400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = deathList.keySet().iterator().next();
        deathList.put(name, details);
        return true;
    }

    @Override
    public String getDeathCause(String name) {
        if(!deathList.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return deathList.get(name) == null ? "heart attack" : deathList.get(name);
    }

    @Override
    public String getDeathDetails(String name) {
        if(!deathList.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return deathList.get(name) == null ? "" : deathList.get(name);
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathList.containsKey(name);
    }
    
}
