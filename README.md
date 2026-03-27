# 🧠 Task Manager - Fullstack Application

Aplicación fullstack para la gestión de tareas internas, desarrollada con Spring Boot (backend) y React + TypeScript (frontend).

Permite crear, listar, filtrar, actualizar estado y eliminar tareas, incluyendo paginación, ordenamiento y manejo de errores.

---

# 🚀 Tecnologías utilizadas

## 🔹 Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Swagger / OpenAPI

## 🔹 Frontend

* React
* TypeScript
* Vite
* Axios
* CSS moderno

---

# 🏗️ Arquitectura

## Backend (Arquitectura en capas)

controller → service → repository → entity
↓
dto
↓
mapper

### Capas:

* Controller → expone endpoints REST
* Service → lógica de negocio
* Repository → acceso a datos
* Entity → modelo de base de datos
* DTO → transporte de datos
* Mapper → conversión
* Exception → manejo global de errores

---

## Frontend

components/
services/
types/

* components → UI
* services → llamadas API
* types → tipado TypeScript

---

# 📁 Estructura del proyecto

task-manager/
├── backend/
│   └── task-manager-backend
└── frontend/
└── task-manager-frontend

---

# 🗄️ Base de datos

CREATE DATABASE task_manager_db;

---

# ⚙️ Configuración Backend

spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager_db
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

---

# ▶️ Ejecución Backend

cd backend/task-manager-backend

mvnw.cmd clean install
mvnw.cmd spring-boot:run

---

# 🌐 Swagger

http://localhost:8080/swagger-ui.html

---

# ▶️ Ejecución Frontend

cd frontend/task-manager-frontend

npm install
npm run dev

http://localhost:5173

---

# 📌 Endpoints

POST /api/tasks
GET /api/tasks
GET /api/tasks?page=0&size=5
GET /api/tasks?status=PENDING
GET /api/tasks/{id}
PATCH /api/tasks/{id}/status
DELETE /api/tasks/{id}

---

# ⚠️ Manejo de errores

400 → validación
404 → recurso no encontrado

---

# ⭐ Funcionalidades

## Backend

* CRUD completo
* DTOs
* Validaciones
* Manejo de errores
* Swagger
* CORS
* Paginación
* Ordenamiento
* Filtro

## Frontend

* Listado de tareas
* Crear tareas
* Cambiar estado
* Eliminar tareas
* Filtro
* Axios
* Hooks
* Manejo de errores
* UI moderna

---

# 🚀 Bonus

* Paginación backend
* Paginación frontend
* Ordenamiento
* Confirmación al eliminar
* Loader
* UI mejorada

---

# 🧪 Ejemplo JSON

{
"title": "Implementar login",
"description": "Crear autenticación básica",
"status": "PENDING",
"priority": "HIGH"
}

---

# 📌 Notas

* Arquitectura limpia
* DTOs para desacoplar
* Código modular
* Separación frontend/backend

---

# 👨‍💻 Autor

Joseph Arias
