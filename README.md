# Restaurant Allergy Reviews API

## About the Project

This is a learning project built to practise Java, Spring Boot, REST APIs and database management.

The API allows users to find restaurants based on allergy information, submit reviews and rate how suitable a restaurant is for people with different food allergies. Admins can accept or reject reviews before they are used to calculate restaurant allergy scores.

## Features

- Add and search for restaurants.
- Register users.
- Submit restaurant allergy reviews.
- Allow admins to accept or reject reviews.
- Calculate average allergy scores using accepted reviews.
- Search for restaurants by postcode and allergy type.
- Validate user input and handle errors.

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Supported Allergies

- Peanut
- Egg
- Dairy

Scores range from 1 to 5. Reviews must be accepted by an admin before they contribute to a restaurant's average scores.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/restaurants` | Add a restaurant |
| GET | `/restaurants/{id}` | Get a restaurant by ID |
| GET | `/restaurants?zipCode={zipCode}&allergy={allergy}` | Search restaurants by postcode and allergy |
| POST | `/users` | Register a user |
| GET | `/users/{displayName}` | Get a user by display name |
| POST | `/reviews` | Submit a dining review |
| GET | `/reviews/{id}` | Get a review by ID |
| GET | `/admin/reviews` | View pending reviews |
| PUT | `/admin/reviews/{id}` | Accept or reject a review |

## How It Works

1. Users register with their details.
2. Restaurants are added to the system.
3. Users submit reviews with optional allergy scores.
4. New reviews are marked as **PENDING**.
5. An admin accepts or rejects each review.
6. Accepted reviews are used to calculate the restaurant's average allergy scores.
7. Users can search for restaurants by postcode and allergy type.

## Running the Project

### Requirements

- Java
- Maven
- An IDE such as IntelliJ IDEA

### Steps

1. Clone the repository:

   ```bash
   git clone <repository-url>
   ```

2. Open the project in your IDE.
3. Run the Spring Boot application.
4. Send requests using cURL or Postman.

The API will typically run at:

```text
http://localhost:8080
```

## Example Request

Search for restaurants in postcode `M1 1AE` with a peanut allergy score:

```bash
curl "http://localhost:8080/restaurants?zipCode=M1%201AE&allergy=peanut"
```

## Author

Created as a learning project to gain practical experience with Java, Spring Boot, REST API development and database management.
