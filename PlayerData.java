package dev.umbra.ac.checks.xray;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    private int suspicionPoints = 0;
    private int stoneMined = 0;
    private final Map<String, Integer> oreMined = new HashMap<>();
    private final Deque<AngleEvent> angleEvents = new ArrayDeque<>();
    private float lastYaw = 0f;

    public int getSuspicionPoints() { return suspicionPoints; }
    public void addSuspicionPoints(int points) { this.suspicionPoints += points; }
    public void clearSuspicionPoints() {
        this.suspicionPoints = 0;
    }
    public int getStoneMined() { return stoneMined; }
    public void incrementStone() { this.stoneMined++; }

    public Map<String, Integer> getOreMined() { return oreMined; }
    public void incrementOre(String type) {
        oreMined.merge(type, 1, Integer::sum);
    }

    public Deque<AngleEvent> getAngleEvents() { return angleEvents; }

    public void addAngleEvent(double angle, String oreType) {
        angleEvents.addLast(new AngleEvent(System.currentTimeMillis(), angle, oreType));
        pruneOldEvents();
    }

    private void pruneOldEvents() {
        long cutoff = System.currentTimeMillis() - 10 * 60 * 1000L;
        while (!angleEvents.isEmpty() && angleEvents.peekFirst().timestamp < cutoff) {
            angleEvents.pollFirst();
        }
    }

    public float getLastYaw() { return lastYaw; }
    public void setLastYaw(float lastYaw) { this.lastYaw = lastYaw; }

    public static class AngleEvent {
        public final long timestamp;
        public final double angle;
        public final String oreType;

        public AngleEvent(long timestamp, double angle, String oreType) {
            this.timestamp = timestamp;
            this.angle = angle;
            this.oreType = oreType;
        }
    }
}