# MealPlanner

A **Meal Planner + Grocery List** backend for families. Plan meals, manage recipes and ingredients, and generate grocery lists—all organized by household.

---

## Tech Stack

| Layer        | Technology              |
|-------------|-------------------------|
| Framework   | Spring Boot 4.x         |
| Java        | 17                      |
| Data        | Spring Data JPA         |
| Database    | H2 (in-memory, dev)     |
| Build       | Maven                   |
| Utilities   | Lombok                  |

---

## Prerequisites

- **Java 17** or higher  
- **Maven** (or use the IDE’s built-in support)

---

## How to Run

1. **Clone and open** the project in your IDE or terminal.

2. **Build and run** (from project root):
   ```bash
   mvn spring-boot:run
   ```
   Or run the main class: `com.healthyme.MealPlanner.MealPlannerApplication`

3. **Base URL:** `http://localhost:8080`  
   - **H2 Console:** `http://localhost:8080/h2-console`  
     - JDBC URL: `jdbc:h2:mem:mealplanner`  
     - Username: `sa`  
     - Password: (leave empty)

4. **API docs:** See **[API-README.md](./API-README.md)** for all GET endpoints (table), POST sample request/response for every resource, and PUT/DELETE reference.

---

## Project Structure

```
src/main/java/com/healthyme/MealPlanner/
├── MealPlannerApplication.java     # Entry point
├── config/                         # JPA, Web (CORS), Security (placeholder)
├── common/                         # Shared code
│   ├── exception/                  # ApiException, ResourceNotFoundException, GlobalExceptionHandler
│   ├── model/                      # BaseEntity (id, createdAt, updatedAt)
│   ├── response/                   # ApiResponse<T>
│   └── util/                       # DateUtils
├── enums/                          # Unit, MealType, GroceryItemSource
├── household/                      # Households (e.g. “Smith Family”)
├── user/                           # Users (linked to a household)
├── ingredient/                     # Ingredients (name, default unit)
├── recipe/                         # Recipes + RecipeIngredient (links to ingredients)
├── mealplan/                       # Meal plans (household + date + meal type + recipe)
└── grocery/                        # Grocery lists + items (per household)
```

Each feature module follows: **controller → service → repository → model** (and **dto** where used).

---

## Domain Overview

| Concept        | Description |
|----------------|-------------|
| **Household**  | A family/group. Users, meal plans, and grocery lists belong to a household. |
| **User**       | A person (name, email) linked to one household. |
| **Ingredient** | A food item with an optional default unit (e.g. gram, cup). |
| **Recipe**     | Name, instructions, servings, and a list of ingredients with quantity and unit. |
| **Meal plan**  | A slot: household + date + meal type (breakfast/lunch/dinner/snack) + recipe. |
| **Grocery list** | Named list for a household; contains **grocery items** (name, quantity, unit, checked, source). |

---

## API Endpoints

**Base URL:** `http://localhost:8080`  
**Response wrapper:** `{ "success": boolean, "message": "...", "data": ... }`

Full request/response samples and all GET endpoints are in **[API-README.md](./API-README.md)**.

### Summary

| Resource       | Base path             | GET list / by id | POST | PUT /{id} | DELETE /{id} |
|----------------|------------------------|-------------------|------|-----------|---------------|
| Households     | `/api/households`     | ✓                 | ✓    | ✓         | ✓             |
| Users          | `/api/users`          | ✓                 | ✓    | ✓         | ✓             |
| Ingredients    | `/api/ingredients`    | ✓                 | ✓    | ✓         | ✓             |
| Recipes        | `/api/recipes`        | ✓                 | ✓    | ✓         | ✓             |
| Meal plans     | `/api/meal-plans`     | ✓ (optional: `householdId`, `start`, `end`) | ✓ | ✓ | ✓ |
| Grocery lists  | `/api/grocery-lists`  | ✓ (optional: `householdId`) | ✓ | ✓ | ✓ |

- **GET** – List (e.g. `GET /api/households`) or by id (e.g. `GET /api/households/1`).
- **POST** – Create; request body is JSON (see API-README.md for sample input/output per resource). **Ingredients** POST accepts an array and returns an array (bulk create).
- **PUT** – Update; same body shape as POST for that resource.
- **DELETE** – Returns 204 No Content.

**CORS:** Configured in `WebConfig` for `/api/**` (adjust origins/methods in production).

---

## Configuration

- **Application:** `src/main/resources/application.properties`
- **Database:** H2 in-memory, URL `jdbc:h2:mem:mealplanner`, user `sa`, no password.
- **JPA:** `ddl-auto=update`, H2 dialect, SQL logging enabled for dev.
- **Security:** `SecurityConfig` is a placeholder; add Spring Security when needed.

---

## Enums Reference

- **Unit:** PIECE, CUP, TABLESPOON, TEASPOON, GRAM, KILOGRAM, OUNCE, POUND, MILLILITER, LITER, CLOVE, BUNCH, SLICE, CAN, BAG, PACKAGE, PINCH, TO_TASTE  
- **MealType:** BREAKFAST, LUNCH, DINNER, SNACK  
- **GroceryItemSource:** RECIPE, MANUAL  

---

## Next Steps (Optional)

- Add **Spring Security** (auth, roles) and finish `SecurityConfig`.
- Add **validation** (e.g. Bean Validation) on request DTOs.
- Add **tests** for controllers and services.
- Switch to a **persistent DB** (e.g. PostgreSQL) for production and set `spring.datasource.*` and `spring.jpa.database-platform` accordingly.
