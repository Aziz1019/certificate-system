# Gift Certificates System

This is a RESTful web service for managing gift certificates and tags. The system allows you to perform CRUD operations on gift certificates, create, read, and delete operations on tags, and search certificates by tags, name, and description.

## Getting Started

### Prerequisites

What things you need to install

1. Intellij IDEA (more preferable) or any other IDE
2. PostgreSQL
3. Tomcat version 10
4. Postman or any similar app for working with APIs

### Installing

* `Clone` the project
* Open the `resources` in repository layer and create database and necessary tables
* Modify `application.properties` based on your database config
* Do not forget to include `-Dspring.profiles.active=dev` for dev and `-Dspring.profiles.active=prod` for different profiles in `VM OPTIONS` Tomcat Configuration

## API Endpoints

The following API endpoints are available:

### Gift Certificates

**GET `/gift-certificates`**

Retrieve all gift certificates in the system.

**GET `/gift-certificates/{id}`**

Retrieve a single gift certificate by its ID.

**POST `/gift-certificates`**

Create a new gift certificate.

##### Request Body

```json
{
  "name": "Gift Certificate",
  "description": "This is a sample gift certificate",
  "price": 100.00,
  "durationInDays": 30,
  "tags": [
    {
      "name": "Tag 1"
    },
    {
      "name": "Tag 2"
    }
  ]
}


```

Or

```json

{
  "name": "Gift Certificate",
  "description": "This is a sample gift certificate",
  "price": 100.00,
  "durationInDays": 30,
  "tags":[]
}

```
**Patch `/gift-certificates`**

Updates gift certificate with a given Request Body

```json

{
  "id": 18,
  "name": "Gift Certficate",
  "description": "This is new gift certificate",
  "price": "20.18",
  "duration": "65",
  "tags":[]
}

```
**DELETE `/gift-certificates/{id}`**

Deletes a single gift certificate by its ID.


**PUT `/gift-certificates`**

Retrieves gift certificates based on request parameters.

#### Request Parameters

- `tag`: Search gift certificates by tag name.
- `name`: Search gift certificates by name.
- `description`: Search gift certificates by description.
- `sort`: Sort gift certificates by name or date. Use name_asc or date_asc for ascending order and name_desc or date_desc for descending order.


## Tags

**GET `/api/tags`**

Retrieves all Tag resources.

**GET `/api/tags/{id}`**

Retrieves a specific Tag resource by ID.

**POST `/api/tags`**

Creates a new Tag resource.

**DELETE `/api/tags/{id}`**

Deletes a specific Tag resource by ID.
