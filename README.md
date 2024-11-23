# Product and Coupon Management API

This project provides a simple API for managing products and coupons. It supports operations such as creating, reading, updating, and deleting products and coupons, as well as applying coupons to a shopping cart.

## Setup Instructions

To set up the project, ensure you have Docker installed. The project uses MongoDB running as a Docker container. To run the container, follow these steps:

1. Navigate to the project folder using the terminal.
2. Run the following command to start MongoDB in a detached mode:

```
docker-compose up -d
```

This command will start a MongoDB container on port `27017`, which the application will use to connect to the MongoDB server running on `localhost:27017`.

---

## Product CRUD Operations

### 1. Create a Product

**POST** `/product/create`

Create a new product.

**Request Body:**
```json
{
   "product_id": 3,
   "price": 20,
   "quantity": 10
}
```

**Note:** If you create a product after already creating a relative coupon, the new product will not be linked to the coupon. Linking products to coupons is a pending feature.

### 2. Delete a Product

**DELETE** `/product/delete/{product_id}`

Delete the product with the specified `product_id`.

**Note:** Associated coupons will not be deleted if it's a BXGY coupon. However, product-specific coupons will be deleted. Cart-wise coupons remain independent of products.

### 3. Get All Products

**GET** `/product/all`

Retrieve all products with their associated coupons.

**Response:**
```json
[
   {
       "product_id": 1,
       "price": 20.0,
       "quantity": 10,
       "coupon_ids": [
           "6741e5abfc56bc15bd038337"
       ]
   }
]
```

---

## Coupon CRUD Operations

### 1. Get All Coupons

**GET** `/coupons/all`

Retrieve all the coupons that have been created.

**Response:**
```json
[
   {
      "coupon_id": "6741e6a80e221c1e928a2cf4",
      "type": "cart-wise",
      "details": {
         "cart_wise_coupon_detail_id": "6741e6a80e221c1e928a2cf3",
         "threshold": 100.0,
         "discount": 10.0
      }
   },
   {
      "coupon_id": "6741e66efc56bc15bd038339",
      "type": "product-wise",
      "details": {
         "product_wise_coupon_detail_id": "6741e66efc56bc15bd038338",
         "product_id": 1,
         "discount": 20.0
      }
   },
   {
      "coupon_id": "6741e67bfc56bc15bd03833b",
      "type": "product-wise",
      "details": {
         "product_wise_coupon_detail_id": "6741e67bfc56bc15bd03833a",
         "product_id": 1,
         "discount": 20.0
      }
   },
   {
      "coupon_id": "6741e68f0e221c1e928a2cf2",
      "type": "product-wise",
      "details": {
         "product_wise_coupon_detail_id": "6741e68f0e221c1e928a2cf1",
         "product_id": 1,
         "discount": 20.0
      }
   },
   {
      "coupon_id": "6741e6b40e221c1e928a2cf6",
      "type": "bxgy",
      "details": {
         "bxgy_coupon_detail_id": "6741e6b40e221c1e928a2cf5",
         "buy_products": [
            {
               "product_id": 2,
               "quantity": 3
            },
            {
               "product_id": 1,
               "quantity": 3
            }
         ],
         "get_products": [
            {
               "product_id": 3,
               "quantity": 1
            }
         ],
         "repition_limit": 2
      }
   }
]
```

### 2. Create a Coupon

**POST** `/coupons/create`

Create a new coupon.

**Request Body:**

- **For Cart-wise Coupon:**
  ```json
  {
    "type": "cart-wise",
    "details": {
      "threshold": 100,
      "discount": 10
    }
  }
  ```

- **For Product-wise Coupon:**
  ```json
  {
    "type": "product-wise",
    "details": {
      "product_id": 1,
      "discount": 20
    }
  }
  ```

- **For BXGY Coupon:**
  ```json
  {
    "type": "bxgy",
    "details": {
      "buy_products": [
        { "product_id": 1, "quantity": 3 },
        { "product_id": 2, "quantity": 3 }
      ],
      "get_products": [
        { "product_id": 3, "quantity": 1 }
      ],
      "repition_limit": 2
    }
  }
  ```

**Note:** Product-wise coupons can only be created if the product already exists. There are some bugs to fix related to the creation of coupons for non-existent products (both product-wise and BXGY).

### 3. Delete a Coupon

**DELETE** `/coupons/delete/{coupon_id}`

Delete a coupon by its `coupon_id`.
Deleting will give other coupon which still exists like below response.

**Response:**
```json
[
   {
       "coupon_id": "6741e6a80e221c1e928a2cf4",
       "type": "cart-wise",
       "details": { ... }
   },
   {
      // other coupons
   }
]
```

### 4. Find a Coupon by ID

**GET** `/coupons/find/{coupon_id}`

Retrieve the coupon with the specified `coupon_id`.

---

## Cart and Coupon Application

### 1. Get Applicable Coupons

**POST** `/applicable-coupons`

Check which coupons are applicable to a given cart.

**Request Body:**
```json
{
   "cart": {
      "items": [
         { "product_id": 1, "quantity": 6, "price": 50 },
         { "product_id": 2, "quantity": 3, "price": 30 },
         { "product_id": 3, "quantity": 2, "price": 25 }
      ]
   }
}
```

**Response:**
```json
{
   "applicable_coupons": [
       {
           "coupon_id": "6741e8960e221c1e928a2cfa",
           "type": "product-wise",
           "discount": 60.0
       },
       {
           "coupon_id": "6741e8a10e221c1e928a2cfc",
           "type": "cart-wise",
           "discount": 44.0
       },
       {
           "coupon_id": "6741e88c0e221c1e928a2cf8",
           "type": "bxgy",
           "discount": 25.0
       }
   ]
}
```

### 2. Apply a Coupon to the Cart

**POST** `/apply-coupon/{coupon_id}`

Apply a coupon to the cart using the `coupon_id` and return the updated cart with the discount applied.
Here, BxGy coupon is applied, 1 product with product id 3 is free therefore
discount is 1 * 25 = 25
**Response:**
```json
{
   "updated_cart": {
       "items": [
           {
               "product_id": 3,
               "quantity": 2,
               "price": 25.0,
               "total_discount": 25.0
           },
           {
               "product_id": 1,
               "quantity": 6,
               "price": 50.0,
               "total_discount": 0.0
           },
           {
               "product_id": 2,
               "quantity": 3,
               "price": 30.0,
               "total_discount": 0.0
           }
       ],
       "total_price": 440.0,
       "total_discount": 25.0,
       "final_price": 415.0
   }
}
```

**Note:** Each time a BXGY coupon is applied, its repetition limit decreases. When the repetition limit reaches 0, the coupon cannot be applied again.

---

## Troubleshooting

- Ensure MongoDB is running properly by verifying that the Docker container is active.
- Check logs for any errors related to coupon creation or application, especially for non-existent products.
