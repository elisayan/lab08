package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    private final Map<String, Pair<String, String>> deathList = new HashMap<>();
    private String currentName;
    private long time;

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException();
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name.equals(null)){
            throw new NullPointerException();
        }
        this.currentName = name;
        deathList.put(name, new Pair<String,String>("heart attack", ""));
        this.time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {        
        if(cause.equals(null) || deathList.isEmpty()){
            throw new IllegalStateException();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.time - currentTimeMillis < 40) {
            deathList.get(this.currentName).setFirst(cause);
            this.time = currentTimeMillis;
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if(details.isEmpty() || deathList.isEmpty()){
            throw new IllegalStateException();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.time - currentTimeMillis < 6040) {
            deathList.get(this.currentName).setSecond(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if(!deathList.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return deathList.get(name) == null ? "heart attack" : deathList.get(name).getFirst();
    }

    @Override
    public String getDeathDetails(String name) {
        if(!deathList.containsKey(name)){
            throw new IllegalArgumentException();
        }
        return deathList.get(name) == null ? "" : deathList.get(name).getSecond();
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathList.containsKey(name);
    }
    
    public class Pair<T, R> {
        private T first;
        private R second;
        
        public Pair(T first, R second) {
            this.first = first;
            this.second = second;
        }
        
        public T getFirst() {
            return first;
        }
        public void setFirst(T first) {
            this.first = first;
        }
        public R getSecond() {
            return second;
        }
        public void setSecond(R second) {
            this.second = second;
        }
    }
}
