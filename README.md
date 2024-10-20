# Creational Design Patterns


## Author: Schipschi Daniil / FAF-223

----

## Objectives:

* Get familiar with the Creational DPs;
* Choose a specific domain;
* Implement at least 3 CDPs for the specific domain;


## Used Design Patterns:

* Singleton
* Factory
* Object Pool


## Implementation

* **Singleton**
  > In this project, I use the ___Singleton___ design pattern for classes and their instances that are responsible for managing
  > the logic, rendering and behavior of their subordinates, such as a `EnemyHandler`, which is responsible for managing `EnemyEntity` objects throughout the application.
  >  
  > Here is a comprehensive list of all the classes that were implemented using the ___Singleton___ pattern:
  > 
  > - `CameraHandler`
  > - `CleanupHandler`
  > - `EnemyHandler`
  > - `EnemyProjectileHandler`
  > - `InputHandler`
  > - `InterfaceHandler`
  > - `ParticleHandler`
  > - `ProjectileHandler`
  > - `SpaceVFXHandler`
  > - `SpawnerHandler`
  > - `Player`
  > 
  > The pattern is implemented by blocking direct instantiation of the constructor by making it `private`;
  > ```java
  > private EnemyHandler() {}
  > ```
  > Then, we first need to save it in a `private static` variable.
  > ```java
  > private static EnemyHandler instance;
  > ```
  > To access the instance, we implement the `public static` method `#getInstance()` that returns the instance from the stored variable, if it is not instantiated, we instantiate before returning.
  > ```java
  > public static EnemyHandler getInstance() {
  >     if (instance == null) {
  >         instance = new EnemyHandler();
  >     }
  >     return instance;
  > }
  > ```
  > By using Singletons, we can guarantee global state, centralized access, and unique instances of objects throughout the application, which often helps with performance, memory management, and codebase complexity.
  > 
  > ```java
  > public class EnemyHandler {
  >     // ... code ...
  >  
  >     private static EnemyHandler instance;
  >
  >     private EnemyHandler() {
  >     }
  >
  >     public static EnemyHandler getInstance() {
  >         if (instance == null) {
  >             instance = new EnemyHandler();
  >         }
  >         return instance;
  >     }
  >   
  >     // ... code ...
  > }
  > ```

* **Factory Method**
  > In this project, I use the ___Factory Method___ design pattern to handle the instantiation of `EnemyEntity` objects. This pattern allows for creating objects without specifying the exact class of the object that will be created, providing flexibility and encapsulation in the object creation process.
  >
  > The `EnemyFactory` class is responsible for generating various types of enemies in the game. By using the Factory Method, the game can easily instantiate different enemy types based on the requirements, which promotes the Open/Closed Principle.
  >
  > Here is a comprehensive list of the key methods in the `EnemyFactory` class that demonstrate the Factory Method pattern:
  >
  > - `createRandomEnemy(World world, Vector2 position)`: Creates a random enemy type at the specified position.
  > - `createEnemy(Enemies enemyType, World world, Vector2 position)`: Creates a specific type of enemy based on the provided enum.
  >
  > The factory method is implemented through a private static method that encapsulates the logic for creating the specific enemy types:
  > ```java
  > private static EnemyEntity getEnemyEntity(Enemies enemy, World world, Vector2 position) {
  >     return switch (enemy) {
  >         case STALKER -> new Stalker(world, position);
  >         case WASP -> new Wasp(world, position);
  >         case ULTRON -> new Ultron(world, position);
  >         case GOLEM -> new Golem(world, position);
  >         case STELLAR -> new Stellar(world, position);
  >     };
  > }
  > ```
  > This approach allows for centralized control of the object creation process, making it easier to manage and extend in the future without modifying the code that uses the factory.
  >
  > ```java
  > public class EnemyFactory {
  >     private static final Random random = new Random();
  > 
  >     public static EnemyEntity createRandomEnemy(World world, Vector2 position) {
  >         int enemyType = random.nextInt(Enemies.values().length);
  > 
  >         return getEnemyEntity(Enemies.values()[enemyType], world, position);
  >     }
  > 
  >     public static EnemyEntity createEnemy(Enemies enemyType, World world, Vector2 position) {
  >         return getEnemyEntity(enemyType, world, position);
  >     }
  > 
  >     private static EnemyEntity getEnemyEntity(Enemies enemy, World world, Vector2 position) {
  >         return switch (enemy) {
  >             case STALKER -> new Stalker(world, position);
  >             case WASP -> new Wasp(world, position);
  >             case ULTRON -> new Ultron(world, position);
  >             case GOLEM -> new Golem(world, position);
  >             case STELLAR -> new Stellar(world, position);
  >         };
  >     }
  > }
  > ```

* **Object Pool Pattern**
  > In this project, I utilize the ___Object Pool Pattern___ to manage the instantiation and reuse of `Ray` projectiles efficiently. This pattern minimizes the overhead of frequent object creation and destruction, which can lead to performance issues, particularly in a game with a high frequency of projectile generation. The `ProjectilePool` class maintains two arrays: one for free projectiles and one for deployed projectiles. By reusing objects instead of creating new ones, the application can manage memory more effectively and reduce garbage collection overhead.
  >
  > Here are the key methods in the `ProjectilePool` class that demonstrate the Object Pool Pattern:
  >
  > - **Constructor**
  > The constructor initializes the pool with a predefined number of projectiles:
  > ```java
  > public ProjectilePool(boolean isEnemy) {
  >     for (int i = 0; i < INITIAL_PROJECTILES; i++) {
  >         freeProjectiles.add(new Ray(Bugger.getInstance().getWorld(), isEnemy));
  >     }
  > }
  > ```
  > - **`obtain()` Method**
  > This method retrieves a projectile from the pool. If no free projectiles are available, it can either reuse a deployed projectile or return `null`:
  > ```java
  > public Ray obtain() {
  >     Ray projectile;
  >     if (freeProjectiles.size > 0) {
  >         projectile = freeProjectiles.pop();
  >     } else if (deployedProjectiles.size > 0) {
  >         projectile = deployedProjectiles.first();
  >         deployedProjectiles.removeIndex(0);
  >     } else {
  >         return null;
  >     }
  >     deployedProjectiles.add(projectile);
  >     return projectile;
  > }
  > ```
  >
  > - **`free(Ray ray)` Method**
  > This method resets and returns a projectile to the pool, marking it as available for reuse:
  > ```java
  > public void free(Ray ray) {
  >     ray.reset();
  >     deployedProjectiles.removeValue(ray, true);
  >     freeProjectiles.add(ray);
  > }
  > ```
  >
  > - **`updateAndRender(float delta)` Method**
  > This method updates and renders all deployed projectiles. It manages their lifecycle and removes those marked for destruction:
  > ```java
  > public void updateAndRender(float delta) {
  >     for (int i = 0; i < deployedProjectiles.size; i++) {
  >         Ray ray = deployedProjectiles.get(i);
  >         if (!ray.isMarkedToDestroy()) {
  >             ray.update(delta);
  >             ray.render();
  >         } else {
  >             free(ray);
  >             i--;
  >         }
  >     }
  > }
  > ```
  >
  > The pooling mechanism promotes better performance and helps in managing resources, especially in a game where objects are frequently created and destroyed. Here is how the `ProjectilePool` is utilized in the `ProjectileHandler` class:
  >
  > - **Constructor**
  > Initializes the `ProjectileHandler` with a `ProjectilePool` instance:
  > ```java
  > private ProjectileHandler() {
  >     projectilePool = new ProjectilePool(false);
  > }
  > ```
  >
  > - **`cycle(float delta)` Method**
  > This method updates and renders all active projectiles using the pool's functionality:
  > ```java
  > public void cycle(float delta) {
  >     projectilePool.updateAndRender(delta);
  > }
  > ```
  >
  > - **`shootRay()` Method**
  > Acquires a projectile from the pool and initializes it based on the player's aim:
  > ```java
  > public void shootRay() {
  >     Vector2 direction = new Vector2();
  >     float mouseX = Gdx.input.getX();
  >     float mouseY = Gdx.input.getY();
  >     Vector3 mousePosition = CameraHandler.getInstance().getCamera().unproject(new Vector3(mouseX, mouseY, 0));
  >     Vector2 playerPos = Player.getInstance().getPosition();
  >     direction.set(mousePosition.x, mousePosition.y).sub(playerPos).nor();
  >     shootRay(playerPos, direction, 20f);
  > }
  > ```
  >
  > - **`shootRay(Vector2 position, Vector2 direction, float speed)` Method**
  > Obtains a projectile from the pool and initializes it with the given position and direction:
  > ```java
  > public void shootRay(Vector2 position, Vector2 direction, float speed) {
  >     Ray projectile = projectilePool.obtain();
  >     if (projectile != null) {
  >         projectile.init(position, direction.nor().scl(speed), false);
  >     }
  > }
  > ```
  >
  > By implementing the Object Pool Pattern, we efficiently manage projectile instances, resulting in improved performance and resource utilization in the game.
  


## Conclusions / Screenshots / Results
d![image](https://github.com/user-attachments/assets/728df0c8-9245-4778-99a9-d655e5792ef0)
![image](https://github.com/user-attachments/assets/6497e630-976c-4670-a907-99c6d1360346)
You can see in the debugger, that Projectiles are capped at 100 due to object pooling
![image](https://github.com/user-attachments/assets/1ceeeb8f-a445-460a-897e-dc69e0bf8c00)


In this project, various design patterns have been implemented to enhance code organization, improve performance, and simplify maintenance. The Singleton pattern ensures that essential classes, such as `EnemyHandler`, `ParticleHandler`, and `InputHandler`, have a single, global instance that provides centralized access to crucial game components. This pattern promotes efficient resource management and minimizes potential conflicts from multiple instances.

The Factory Method pattern is employed to create instances of `EnemyEntity` objects through the `EnemyFactory`. By encapsulating the instantiation logic, this pattern allows for greater flexibility in adding new enemy types without modifying existing code, adhering to the Open/Closed Principle.

The Object Pool Pattern is utilized to manage the lifecycle of `Ray` projectiles in the `ProjectilePool`. By reusing objects instead of creating new ones each time, this pattern significantly reduces memory overhead and garbage collection frequency, resulting in smoother gameplay performance.

Overall, the application of these design patterns not only streamlines the development process but also contributes to a more robust and scalable architecture. This approach allows for easier testing, debugging, and future enhancements, ensuring the project can evolve with additional features and complexity without compromising performance or maintainability.
