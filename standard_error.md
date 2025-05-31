Here are some **common error response formats used in the industry**, with a focus on the **RFC 7807 standard** (`application/problem+json`) and other typical formats used by major APIs:

---

## ✅ 1. **RFC 7807 – Problem Details for HTTP APIs**

**Industry Standard** (used by GitHub, Stripe, Azure, etc.)
**Content-Type**: `application/problem+json`

### 📄 Example:

```json
{
  "type": "https://example.com/probs/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "User with ID 123 does not exist.",
  "instance": "/api/users/123"
}
```

### 🔹 Field Descriptions:

* `type`: A URI identifier for the error type (can link to documentation).
* `title`: A short, human-readable title.
* `status`: HTTP status code.
* `detail`: A detailed explanation.
* `instance`: The path to the request (or request ID).

---

## ✅ 2. **Custom JSON Error Format** (Common in many REST APIs like Google, Facebook)

### 📄 Example:

```json
{
  "error": {
    "code": 404,
    "message": "The requested user was not found.",
    "errors": [
      {
        "message": "User ID 123 does not exist.",
        "domain": "global",
        "reason": "notFound"
      }
    ]
  }
}
```

Used by: Google APIs (e.g., Gmail API, Maps API)
Good for: Grouping multiple error reasons under one response.

---

## ✅ 3. **Simple REST API Error Response**

### 📄 Example:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found.",
  "path": "/api/users/123",
  "timestamp": "2025-05-31T10:15:30.000Z"
}
```

Used by: Many Java Spring Boot apps (Spring's `@ControllerAdvice`)
Good for: Human-readable debugging and frontend display.

---

## ✅ 4. **GraphQL Error Format**

(Used when API is based on GraphQL, not REST)

### 📄 Example:

```json
{
  "errors": [
    {
      "message": "User not found.",
      "locations": [{ "line": 2, "column": 3 }],
      "path": ["getUser"]
    }
  ],
  "data": null
}
```

---

## ✅ Summary Table

| Format             | Standard         | Used By              | Good For                    |
| ------------------ | ---------------- | -------------------- | --------------------------- |
| RFC 7807           | ✅ Yes            | Stripe, Azure        | Standardized, linkable docs |
| Custom JSON Format | ❌ No             | Google, Facebook     | Detailed nested errors      |
| Simple REST Format | ❌ No             | Spring Boot, Express | Easy to read & log          |
| GraphQL Format     | ✅ (GraphQL spec) | Apollo, GitHub       | GraphQL-specific APIs       |

---

### 💡 Pro Tip:

If you're building an API, **RFC 7807** is recommended for long-term interoperability and clarity.

Let me know if you want a boilerplate implementation of RFC 7807 error handling in a specific backend framework (like Flask, Express.js, or Spring Boot).
