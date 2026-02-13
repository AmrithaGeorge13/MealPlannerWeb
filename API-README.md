# MealPlanner API Reference

Base URL: `http://localhost:8080`

All responses use the wrapper:

```json
{
  "success": true | false,
  "message": "string or null",
  "data": { ... } | [ ... ] | null
}
```

---

## GET APIs (summary)

| Method | Endpoint | Query parameters | Description |
|--------|----------|------------------|-------------|
| GET | `/api/households` | — | List all households |
| GET | `/api/households/{id}` | — | Get household by id |
| GET | `/api/users` | — | List all users |
| GET | `/api/users/{id}` | — | Get user by id |
| GET | `/api/ingredients` | — | List all ingredients |
| GET | `/api/ingredients/{id}` | — | Get ingredient by id |
| GET | `/api/recipes` | — | List all recipes |
| GET | `/api/recipes/{id}` | — | Get recipe by id |
| GET | `/api/meal-plans` | `householdId`, `start` (ISO date), `end` (ISO date) | List meal plans (optionally filtered) |
| GET | `/api/meal-plans/{id}` | — | Get meal plan by id |
| GET | `/api/grocery-lists` | `householdId` (optional) | List grocery lists (optionally by household) |
| GET | `/api/grocery-lists/{id}` | — | Get grocery list by id |

---

## POST APIs – sample request & response

### 1. Create household

**Request**

`POST /api/households`  
`Content-Type: application/json`

```json
{
  "name": "Smith Family"
}
```

**Sample response** (201 Created)

```json
{
  "success": true,
  "message": null,
  "data": {
    "id": 1,
    "name": "Smith Family",
    "createdAt": "2026-02-07T10:00:00Z",
    "updatedAt": "2026-02-07T10:00:00Z"
  }
}
```

---

### 2. Create user

**Request**

`POST /api/users`  
`Content-Type: application/json`

```json
{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "household": { "id": 1 }
}
```

**Sample response** (201 Created)

```json
{
  "success": true,
  "message": null,
  "data": {
    "id": 1,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "household": {
      "id": 1,
      "name": "Smith Family",
      "createdAt": "2026-02-07T10:00:00Z",
      "updatedAt": "2026-02-07T10:00:00Z"
    },
    "createdAt": "2026-02-07T10:00:00Z",
    "updatedAt": "2026-02-07T10:00:00Z"
  }
}
```

---

### 3. Create ingredients (bulk)

**Request**

`POST /api/ingredients`  
`Content-Type: application/json`

Body is an **array** of ingredient objects. One or more ingredients can be created in a single request.

```json
[
  { "name": "Tomato", "defaultUnit": "PIECE" },
  { "name": "Onion", "defaultUnit": "PIECE" },
  { "name": "Pasta", "defaultUnit": "GRAM" }
]
```

**Sample response** (201 Created)

`data` is an array of created ingredients (same order as request).

```json
{
  "success": true,
  "message": null,
  "data": [
    { "id": 1, "name": "Tomato", "defaultUnit": "PIECE" },
    { "id": 2, "name": "Onion", "defaultUnit": "PIECE" },
    { "id": 3, "name": "Pasta", "defaultUnit": "GRAM" }
  ]
}
```

**`defaultUnit`** (optional per item) – one of:  
`PIECE`, `CUP`, `TABLESPOON`, `TEASPOON`, `GRAM`, `KILOGRAM`, `OUNCE`, `POUND`, `MILLILITER`, `LITER`, `CLOVE`, `BUNCH`, `SLICE`, `CAN`, `BAG`, `PACKAGE`, `PINCH`, `TO_TASTE`

---

### 4. Create recipe

**Request**

`POST /api/recipes`  
`Content-Type: application/json`

```json
{
  "name": "Tomato Pasta",
  "instructions": "Boil pasta. Cook tomatoes. Mix and serve.",
  "servings": 4,
  "ingredients": [
    { "ingredientId": 1, "quantity": 4, "unit": "PIECE" },
    { "ingredientId": 2, "quantity": 2, "unit": "CUP" }
  ]
}
```

**Sample response** (201 Created)

```json
{
  "success": true,
  "message": null,
  "data": {
    "id": 1,
    "name": "Tomato Pasta",
    "instructions": "Boil pasta. Cook tomatoes. Mix and serve.",
    "servings": 4,
    "ingredients": [
      {
        "ingredientId": 1,
        "ingredientName": "Tomato",
        "quantity": 4,
        "unit": "PIECE"
      },
      {
        "ingredientId": 2,
        "ingredientName": "Pasta",
        "quantity": 2,
        "unit": "CUP"
      }
    ]
  }
}
```

---

### 5. Create meal plan

**Request**

`POST /api/meal-plans`  
`Content-Type: application/json`

```json
{
  "householdId": 1,
  "planDate": "2026-02-10",
  "mealType": "DINNER",
  "recipeId": 1
}
```

**Sample response** (201 Created)

```json
{
  "success": true,
  "message": null,
  "data": {
    "id": 1,
    "householdId": 1,
    "planDate": "2026-02-10",
    "mealType": "DINNER",
    "recipeId": 1,
    "recipeName": "Tomato Pasta"
  }
}
```

**`mealType`** – one of: `BREAKFAST`, `LUNCH`, `DINNER`, `SNACK`  
**`planDate`** – ISO date string: `YYYY-MM-DD`

---

### 6. Create grocery list

**Request**

`POST /api/grocery-lists`  
`Content-Type: application/json`

```json
{
  "householdId": 1,
  "name": "Weekly groceries"
}
```

**Sample response** (201 Created)

```json
{
  "success": true,
  "message": null,
  "data": {
    "id": 1,
    "householdId": 1,
    "name": "Weekly groceries",
    "items": []
  }
}
```

---

## PUT APIs (same body shape as POST, with `id` in path)

| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT | `/api/households/{id}` | Update household (body: `{ "name": "..." }`) |
| PUT | `/api/users/{id}` | Update user (body: `{ "name", "email", "household": { "id" } }`) |
| PUT | `/api/ingredients/{id}` | Update ingredient (body: same as POST ingredient) |
| PUT | `/api/recipes/{id}` | Update recipe (body: same as POST recipe) |
| PUT | `/api/meal-plans/{id}` | Update meal plan (body: same as POST meal plan) |
| PUT | `/api/grocery-lists/{id}` | Update grocery list (body: same as POST grocery list) |

---

## DELETE APIs

| Method | Endpoint | Response |
|--------|----------|----------|
| DELETE | `/api/households/{id}` | 204 No Content |
| DELETE | `/api/users/{id}` | 204 No Content |
| DELETE | `/api/ingredients/{id}` | 204 No Content |
| DELETE | `/api/recipes/{id}` | 204 No Content |
| DELETE | `/api/meal-plans/{id}` | 204 No Content |
| DELETE | `/api/grocery-lists/{id}` | 204 No Content |

---

## Enums reference

- **Unit:** `PIECE`, `CUP`, `TABLESPOON`, `TEASPOON`, `GRAM`, `KILOGRAM`, `OUNCE`, `POUND`, `MILLILITER`, `LITER`, `CLOVE`, `BUNCH`, `SLICE`, `CAN`, `BAG`, `PACKAGE`, `PINCH`, `TO_TASTE`
- **MealType:** `BREAKFAST`, `LUNCH`, `DINNER`, `SNACK`
- **GroceryItemSource:** `RECIPE`, `MANUAL`
