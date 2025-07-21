# ✨ Person Service

A simple **REST API** built with [Dropwizard](https://www.dropwizard.io/) to manage people in a database — now secured with **Keycloak OAuth 2.0** for authentication and authorization.

---

## 🚀 Features

✅ Create a new person
✅ Retrieve a person by ID
✅ Stores data in PostgreSQL
✅ Uses JDBI for database access
✅ **Secure endpoints with OAuth 2.0 (Keycloak)**
✅ Role-based access control (RBAC) — users can access endpoints based on roles

---

## 🛠 Requirements

* Java 11+ (Java 8 minimum)
* Maven or Gradle
* PostgreSQL (or compatible DB)
* [Keycloak](https://www.keycloak.org/) server (or compatible OAuth 2.0 provider)

---

## ⚙️ Setup

### 1️⃣ Clone the project

```bash
git clone https://github.com/yourusername/person-service.git
cd person-service
```

---

### 2️⃣ Configure the database

Create the database:

```bash
createdb persondb
```

Optional: create the `person` table:

```sql
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
```

---

### 3️⃣ Configure the application

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

auth:
  oauth:
    issuerUri: http://localhost:8080/realms/your-realm
    clientId: person-service-client
```

---

### 4️⃣ Set up Keycloak

* Install and run Keycloak.
* Create:

  * A **realm** → `your-realm`
  * A **client** → `person-service-client`
  * **Roles** → e.g., `USER`, `ADMIN`
* Assign roles to users in the realm.
* Configure client settings:

  * Enable **Service Accounts** (if needed).
  * Set **access type** to `confidential`.
  * Provide correct **redirect URIs** and **scopes**.

---

### 5️⃣ Build the project

For Maven:

```bash
mvn package
```

For Gradle:

```bash
./gradlew build
```

---

### 6️⃣ Run the application

```bash
java -jar target/person-service.jar server config.yml
```

---

## 🔐 Authentication & Authorization

* Uses **OAuth 2.0 Bearer tokens** (via Keycloak).
* Include the access token in each request:

```
Authorization: Bearer <access_token>
```

* Endpoint access by role:

| Endpoint       | Method | Required Role |
| -------------- | ------ | ------------- |
| `/person`      | POST   | ADMIN         |
| `/person/{id}` | GET    | USER or ADMIN |

* Unauthorized requests return:

  * `401 Unauthorized` → no/invalid token
  * `403 Forbidden` → insufficient permissions

---

## 🧪 Example curl Requests

### 🔸 Create Person (ADMIN)

```bash
curl -X POST http://localhost:8080/person \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <access_token>" \
  -d '{"name": "John Doe"}'
```

---

### 🔸 Get Person by ID (USER or ADMIN)

```bash
curl -X GET http://localhost:8080/person/1 \
  -H "Authorization: Bearer <access_token>"
```

---

### 🔸 Get Access Token (example via Keycloak)

```bash
curl -X POST http://localhost:8080/realms/your-realm/protocol/openid-connect/token \
  -d "client_id=person-service-client" \
  -d "username=myuser" \
  -d "password=mypassword" \
  -d "grant_type=password"
```

---

## 📁 Project Structure

```
src/main/java/com/example/person/
├── api/                  // Data models (Person.java)
├── auth/                 // OAuth and security setup
├── db/                   // DAO interfaces (PersonDao.java)
├── resources/            // REST resources (PersonResource.java)
├── PersonApplication.java        // Main app entry point
├── PersonConfiguration.java      // Configuration class
```

---

## 💡 Development Tips

✅ Use Postman or curl to test endpoints
✅ Check logs for debugging (`target/logs/`)
✅ Update database and auth configs in `config.yml`
✅ Confirm Keycloak user roles when debugging permissions
