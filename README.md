
# Person Service

A simple REST API built with [Dropwizard](https://www.dropwizard.io/) to manage people in a database.

## Features

✅ Create a new person  
✅ Retrieve a person by ID  
✅ Stores data in PostgreSQL (or other supported databases)  
✅ Uses JDBI for database access

---

## Requirements

- Java 8 or higher (Java 11+ recommended)  
- Maven or Gradle  
- PostgreSQL (or other configured DB)

---

## Setup

### 1️⃣ Clone the project

```bash
git clone https://github.com/yourusername/person-service.git
cd person-service
````

### 2️⃣ Configure the database

Create a database named `persondb` (or as defined in your `config.yml`):

```bash
createdb persondb
```

Optionally, run schema:

```sql
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
```

### 3️⃣ Configure application

Edit `config.yml`:

```yaml
server:
  applicationConnectors:
    - type: http
      port: 8080
  adminConnectors:
    - type: http
      port: 8081

database:
  driverClass: org.postgresql.Driver
  url: jdbc:postgresql://localhost:5432/persondb
  user: postgres
  password: your_password
```

### 4️⃣ Build the project

For Maven:

```bash
mvn package
```

For Gradle:

```bash
./gradlew build
```

### 5️⃣ Run the application

```bash
java -jar target/person-service.jar server config.yml
```

---

## API Endpoints

### Create Person

`POST /person`

**Request body:**

```json
{
  "name": "John Doe"
}
```

**Response:**

```json
{
  "id": 1
}
```

### Get Person by ID

`GET /person/{id}`

**Response:**

```json
{
  "id": 1,
  "name": "John Doe"
}
```

---

## Project Structure

```
src/main/java/com/example/person/
├── api/            // Data models (Person.java)
├── db/             // DAO interfaces (PersonDao.java)
├── resources/      // REST resources (PersonResource.java)
├── PersonApplication.java  // Main app entry point
├── PersonConfiguration.java // Configuration class
```

---

## Development Tips

✅ Use Postman or curl to test endpoints
✅ Check logs for debugging
✅ Update database config in `config.yml`

---

## License

MIT License (or your chosen license)
```
