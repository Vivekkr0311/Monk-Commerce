# Monk Commerce API Documentation

## Setting Up MongoDB

1. Navigate to the project directory.
2. Open the terminal and execute:
   ```bash
   docker-compose up -d
   ```
   This command will start MongoDB locally, mapping it to your machine on port `27017`.

   The Spring Boot application will communicate with this MongoDB instance.

---

## Coupon CRUD Operations

### Create Coupon
**POST**: `localhost:8080/coupon`

Payload Examples:

1. **Cart-Wise Coupon**:
    ```json
    {
        "type": "cart-wise",
        "details": {
            "threshold": 100,
            "discount": 10
        }
    }
    ```

2. **Product-Wise Coupon**:
    ```json
    {
        "type": "product-wise",
        "details": {
            "product_id": 1,
            "discount": 20
        }
    }
    ```

3. **Buy X Get Y (BXGY) Coupon**:
    ```json
    {
        "type": "bxgy",
        "details": {
            "buy_products": [
                {"product_id": 1, "quantity": 3},
                {"product_id": 2, "quantity": 3}
            ],
            "get_products": [
                {"product_id": 3, "quantity": 1}
            ],
            "repition_limit": 2
        }
    }
    ```

> **Note**: Ensure products are created first before creating coupons.

---

### Get All Coupons
**GET**: `localhost:8080/coupon`

Returns all coupons. Coupon IDs are returned as strings.

---

### Get Coupon by ID
**GET**: `localhost:8080/coupon/{id}`

Fetch a specific coupon using its ID.

---

### Delete Coupon
**DELETE**: `localhost:8080/coupon/{id}`

Deletes a coupon by its ID.

> **Caution**: This operation is not implemented as a CASCADE, so products may still reference the deleted coupon.

---

## Product CRUD Operations

### Create Product
**POST**: `localhost:8080/products/create`

Payload Example:
```json
{
    "product_id": 3,
    "quantity": 4,
    "price": 100
}
```

---

### Get All Products
**GET**: `localhost:8080/products/all-products`

Fetches a list of all products.

---

### Get Product by ID
**GET**: `localhost:8080/products/{id}`

Fetch a specific product by its ID.

---

### Delete Product
**DELETE**: `localhost:8080/products/delete/{id}`

Deletes a product by its ID.

> **Caution**: This operation does not delete the associated coupon.

---

## Coupon Applicability and Application

### Get Applicable Coupons
**POST**: `localhost:8080/applicable-coupons`

Payload Example:
```json
{
    "cart": {
        "items": [
            {"product_id": 1, "quantity": 6, "price": 50}, 
            {"product_id": 2, "quantity": 3, "price": 30}, 
            {"product_id": 3, "quantity": 2, "price": 25}
        ]
    }
}
```

Returns a list of applicable coupon IDs for the given cart.

---

### Apply Coupon
**POST**: `localhost:8080/apply-coupon/{id}`

Payload Example:
```json
{
    "cart": {
        "items": [
            {"product_id": 1, "quantity": 6, "price": 50}, 
            {"product_id": 2, "quantity": 3, "price": 30}
        ]
    }
}
```

Returns a JSON with cart information and applied coupon details.

> **Note**: This endpoint is partially implemented as it is complex.

---

## Important Notes
- Create products before creating coupons to ensure proper functionality.
- Deleting coupons or products does not cascade to their associations.