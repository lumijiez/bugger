# Behavioral Design Patterns


## Author: Schipschi Daniil / FAF-223

----

## Objectives:

* Learn about Behavioral Design Patterns
* Pick a domain to implement them in
* Implement at least 1 pattern in my code


## Used Design Pattern:

* Strategy


## Implementation

* **Strategy**
  > I implemented the ___Strategy___ pattern to define a family of interchangeable enemy behaviors. This pattern allows for dynamic switching of enemy movement and attack algorithms during runtime.
  >
  > The strategy is implemented through these components:
  >
  > - `EnemyBehavior` (Strategy interface)
  > - `DefensiveBehavior` (Concrete strategy)
  > - `FollowBehavior` (Concrete strategy)
  > - `EnemyEntity` (Context)
  >
  > Here's the core implementation:
  > ```java
  > // Strategy Interface
  > public interface EnemyBehavior {
  >     void update(EnemyEntity enemy);
  >     void init(EnemyEntity enemy);
  > }
  > 
  > // Context class that uses the strategy
  > public class EnemyEntity extends Entity {
  >     protected EnemyBehavior behavior;
  >     protected EnemyType type;
  >
  >     // ... constructor and other code ...
  > 
  >     public void update() {
  >         if (behavior != null) behavior.update(this);
  >     }
  > 
  >     public void setBehavior(Behaviors behavior, float param1, float param2, float param3) {
  >         this.behavior = behavior.createBehavior(param1, param2, param3);
  >     }
  > }
  > 
  > // Concrete Strategy implementation
  > public class DefensiveBehavior implements EnemyBehavior {
  >     private final float preferredDistance;
  >     private final float moveSpeed;
  >     private final float shootCooldown;
  > 
  >     @Override
  >     public void update(EnemyEntity enemy) {
  >         Vector2 playerPos; // ... getter
  >         Vector2 enemyPos = // ... getter
  >         Vector2 direction = // ... getter
  >         float currentDistance = // ... getter
  > 
  >         // Maintain preferred distance and shoot when possible
  >         float distanceDiff = currentDistance - preferredDistance;
  >         direction.nor();
  > 
  >         // Math, just example of implementation
  >         if (Math.abs(distanceDiff) > 1.0f) {
  >             Vector2 movement = direction.scl(Math.min(moveSpeed, 
  >                               Math.abs(distanceDiff) * 0.5f));
  >             enemy.getBody().setLinearVelocity(
  >                 movement.cpy().scl(Math.signum(distanceDiff)));
  >         }
  >         // ... rest of the behavior logic
  >     }
  > }
  > ```
  >
  > The pattern is used in the game to dynamically switch enemy behaviors:
  > ```java
  > // In InputHandler.java
  > if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
  >     EnemyHandler.getInstance().overrideBehaviorForExisting(Behaviors.DEFENSIVE);
  > }
  > 
  > if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
  >     EnemyHandler.getInstance().overrideBehaviorForExisting(Behaviors.FOLLOW);
  > }
  > ```
  >
  > The benefits of this pattern include:
  > - Runtime behavior switching
  > - Easy addition of new behaviors
  > - Clean separation of different algorithms
  > - Elimination of complex conditional statements


## Conclusions

The implementation of the Strategy pattern significantly improved the flexibility and maintainability of my game's enemy behavior system:

The Strategy pattern allowed for clean separation between different enemy behaviors while maintaining a consistent interface. This made it possible to switch behaviors dynamically during gameplay, enhancing the game's variety and unpredictability.

The pattern proved particularly useful for implementing different enemy AI behaviors, as it allowed me to encapsulate each algorithm in its own class. This encapsulation made the code more organized and easier to modify or extend.

The implementation demonstrates how behavioral patterns can solve complex programming challenges in game development, particularly when dealing with dynamic algorithm switching and AI behavior management. The ability to change enemy behaviors at runtime adds significant value to the gameplay experience while maintaining clean and maintainable code.
