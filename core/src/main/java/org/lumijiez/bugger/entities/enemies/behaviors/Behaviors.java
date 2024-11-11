package org.lumijiez.bugger.entities.enemies.behaviors;

public enum Behaviors {
    FOLLOW(FollowBehavior.class),
    DEFENSIVE(DefensiveBehavior.class);

    private final Class<? extends EnemyBehavior> behaviorClass;

    Behaviors(Class<? extends EnemyBehavior> behaviorClass) {
        this.behaviorClass = behaviorClass;
    }

    public EnemyBehavior createBehavior(float param1, float param2, float param3) {
        try {
            return behaviorClass.getDeclaredConstructor(float.class, float.class, float.class)
                .newInstance(param1, param2, param3);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create behavior: " + e.getMessage(), e);
        }
    }
}
