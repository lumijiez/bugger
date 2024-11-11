# Structural Design Patterns


## Author: Schipschi Daniil / FAF-223

----

## Objectives:

* Learn about Structural Design Patterns
* Pick a domain to implement them in
* Implement at least 3 patterns in my code


## Used Design Patterns:

* Facade
* Bridge
* Flyweight


## Implementation

* **Facade**
  > I used the ___Facade___ pattern to simplify the management of multiple game systems. The `GameSystemsFacade` class provides a single interface to handle all subsystems, reducing complexity in the main game loop.
  >
  > The facade manages these systems:
  >
  > - Projectiles
  > - Visual effects
  > - Enemies
  > - Input
  > - Camera
  > - Particles
  > - Cleanup
  > - Interface
  > - Player
  >
  > Here's the implementation:
  > ```java
  > public class GameSystemsFacade {
  >     private static GameSystemsFacade instance;
  >     private final ProjectileHandler projectileHandler;
  >     private final SpaceVFXHandler spaceVFXHandler;
  >     // ... other handlers ...
  >
  >     public void update(float delta) {
  >         step();
  >         renderClear();
  >         spaceVFXHandler.render();
  >         projectileHandler.cycle(delta);
  >         // ... other updates ...
  >     }
  > }
  > ```
  > The facade pattern reduces the complexity of system management by providing a single update method instead of requiring multiple system calls in the main game loop.

* **Bridge**
  > I implemented the ___Bridge___ pattern in the enemy system to separate enemy types from their behaviors. This separation allows independent modification of both aspects without affecting each other.
  >
  > Here's the implementation:
  > ```java
  > public class EnemyEntity extends Entity {
  >     protected EnemyBehavior behavior;  // <--- One Bridged Attribute
  >     protected EnemyType type; // <--- Second Bridged Attribute
  >
  >     public EnemyEntity(World world, EnemyType type, Behaviors behaviorType, 
  >                        Vector2 playerPosition, TripleInt options) {
  >         super(world, type.getTexturePath(), type.getSize());
  >         this.type = type;
  >         this.behavior = behaviorType.createBehavior(options.one(), 
  >                                                    options.two(), 
  >                                                    options.thr());
  >         initializePosition(playerPosition);
  >         behavior.init(this);
  >     }
  > }
  > ```
  >
  > The benefits of this pattern include:
  > - Independent creation of enemy types and behaviors
  > - Easy addition of new enemy types or behaviors
  > - Flexible combination of types and behaviors

* **Flyweight**
  > I used the ___Flyweight___ pattern to optimize memory usage when handling multiple projectiles. The pattern shares common data between projectile instances instead of duplicating it.
  >
  > Here's the flyweight implementation:
  > ```java
  > public class ProjectileFlyweight {
  >     private static final Map<Boolean, ProjectileFlyweight> flyweights = new HashMap<>();
  >     private final Sprite sprite;
  >     private final float size;
  >     private final float defaultSpeed;
  >     private final boolean isEnemy;
  >
  >     public static ProjectileFlyweight get(boolean isEnemy) {
  >         return flyweights.computeIfAbsent(isEnemy, ProjectileFlyweight::new);
  >     }
  > }
  > ```
  >
  > And its usage in projectiles:
  > ```java
  > public class Projectile extends Entity {
  >     private final ProjectileFlyweight flyweight;
  >
  >     public Projectile(World world, boolean isEnemy) {
  >         super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
  >         this.flyweight = ProjectileFlyweight.get(isEnemy);
  >     }
  >     // ... other code
  > 
  >     public void render() {
  >         Sprite spriteToRender = flyweight.getSprite();
  >         // ... other code ...
  >     }
  > }
  > ```
  >
  > This pattern efficiently manages memory by sharing common resources between multiple projectile instances.


## Conclusions

The implementation of these structural patterns significantly improved the organization and efficiency of my game project:

The Facade pattern simplified system management by providing a unified interface for all game subsystems. This reduced complexity in the main game loop and made the codebase more maintainable.

The Bridge pattern created a flexible enemy system by separating enemy types from their behaviors. This separation allows for independent modification and extension of both aspects, making the system more modular and easier to expand.

The Flyweight pattern optimized memory usage in the projectile system by sharing common resources between instances. This optimization is particularly important when dealing with numerous projectiles simultaneously.

These patterns work together to create a more organized, efficient, and maintainable game architecture. The implementation demonstrates the practical benefits of using structural patterns to solve common game development challenges.
