# API Response Structure

## ApiResponseDTO

Standard response format for all API endpoints.

### Response Structure Overview

The API follows a consistent three-field response structure that clearly distinguishes between successful and error responses.

---

## Success Response Format

When an API call succeeds, the response contains:

- **status**: HTTP status code (200-299 range)
- **message**: Optional descriptive message about the operation
- **data**: The actual response data object

### Success Response Examples

#### Basic Success Response
```json
{
  "status": 200,
  "message": "Order retrieved successfully",
  "data": {
    "orderId": 123,
    "customerName": "John Doe",
    "orderDate": "2026-03-30T10:30:00Z",
    "totalAmount": 99.99
  }
}
```

#### Created Resource Response (201)
```json
{
  "status": 201,
  "message": "Order created successfully",
  "data": {
    "orderId": 456,
    "customerName": "Jane Smith",
    "orderDate": "2026-03-30T11:15:00Z",
    "totalAmount": 149.99
  }
}
```

---

## Error Response Format

When an API call fails, the response contains:

- **status**: HTTP error status code (400-599 range)
- **message**: Mandatory error description explaining what went wrong
- **data**: Always null for error responses

### Error Response Examples

#### Bad Request Error (400)
```json
{
  "status": 400,
  "message": "Invalid customer ID provided. ID must be a positive integer.",
  "data": null
}
```

#### Not Found Error (404)
```json
{
  "status": 404,
  "message": "Order not found with ID: 999",
  "data": null
}
```

#### Unauthorized Error (401)
```json
{
  "status": 401,
  "message": "Authentication required to access this resource",
  "data": null
}
```

#### Internal Server Error (500)
```json
{
  "status": 500,
  "message": "Internal server error occurred while processing the request",
  "data": null
}
```

---

## Field Specifications

### status
- **Type**: Integer
- **Required**: Yes
- **Success Range**: 200-299
- **Error Range**: 400-599
- **Examples**: 200, 201, 400, 404, 500

### message
- **Type**: String
- **Success**: Optional (can be included for clarity or omitted)
- **Error**: Mandatory (must describe the error)
- **Purpose**: Provide human-readable description of the operation result

### data
- **Type**: Object or null
- **Success**: Must contain the response payload
- **Error**: Must be null
- **Purpose**: Carry the actual business data or indicate no data for errors

---

## Implementation Guidelines

### 1. Consistent Structure
Always use the same three-field format across all endpoints:
```json
{
  "status": "<integer>",
  "message": "<string|null>",
  "data": "<object|null>"
}
```

### 2. Status Code Alignment
Ensure the `status` field matches the actual HTTP response status:
- HTTP 200 response → `"status": 200`
- HTTP 404 response → `"status": 404`
- HTTP 500 response → `"status": 500`

### 3. Message Best Practices
- **Success**: Be concise and descriptive ("Order created", "Data retrieved successfully")
- **Error**: Be specific and helpful ("Customer not found with ID: 123", "Email format is invalid")
- **Validation**: Explain what's wrong and how to fix it

---

## Quick Reference

| Response Type | Status Code | Message | Data |
|---------------|--------------|----------|-------|
| Success | 200-299 | Optional | Required |
| Error | 400-599 | Required | null |

This structure ensures clients can consistently handle both successful and error responses in a predictable manner.

