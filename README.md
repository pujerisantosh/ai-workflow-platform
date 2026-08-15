# AI Workflow Platform

An AI-focused backend platform built with Java and Spring Boot for creating, executing, and scaling workflow-driven applications.

The project is being developed incrementally with production-oriented backend practices including secure APIs, PostgreSQL persistence, stateless authentication, role-based authorization, asynchronous processing, caching, resilience, and AI capabilities.

## 🎯 Project Goals

- Build a maintainable Spring Boot backend for AI-powered workflows
- Execute workflows reliably and asynchronously
- Secure APIs using JWT authentication and role-based authorization
- Introduce Kafka for event-driven processing
- Use Redis for caching and distributed coordination
- Integrate LLMs and AI services
- Build embeddings and vector search capabilities
- Implement a RAG pipeline
- Apply scalable system-design patterns

## 🏗️ Architecture Roadmap

```text
                    AI Workflow Platform
                            |
              +-------------+-------------+
              |                           |
          REST APIs                Workflow Engine
              |                           |
       Spring Security             Async Execution
              |                           |
        JWT + RBAC                     Kafka
              |                           |
         PostgreSQL                  Workers
              |
            Redis
              |
       +------+------+
       |             |
   AI / LLM      Knowledge Layer
                     |
                Documents
                     |
                  Chunking
                     |
                Embeddings
                     |
                Vector DB
                     |
                RAG Search
                     |
                    LLM
> The architecture is being implemented incrementally. The status below distinguishes implemented functionality from planned capabilities.

## ✅ Implemented

### Authentication

- User registration
- BCrypt password hashing
- Login authentication using Spring Security
- JWT generation and validation
- Stateless Spring Security configuration
- JWT authentication filter
- Protected `GET /api/users/me` endpoint

### Authorization

- `USER` and `ADMIN` roles
- Role included in JWT
- Spring Security authorities
- Method-level authorization using `@PreAuthorize`
- ADMIN-only endpoint
- USER → `403 Forbidden`
- ADMIN → `200 OK`

### API & Error Handling

- REST APIs using Spring Boot
- Request validation
- Global exception handling
- Structured error responses
- Authentication failure handling
- Swagger/OpenAPI documentation

## 🔄 In Progress

- Workflow domain
- Workflow CRUD APIs
- Workflow execution engine
- Asynchronous workflow execution
- Kafka-based event processing

## 🤖 AI Roadmap

### 1. LLM Integration

- LLM provider integration
- Prompt construction
- Model request/response handling
- Error and timeout handling

### 2. Embeddings

```text
Text
  ↓
Embedding Model
  ↓
Vector Representation

Embeddings will be used to represent documents and user queries for semantic search.

3. Vector Database

Planned capabilities:

Store embeddings
Similarity search
Top-K retrieval
Metadata filtering

4. RAG
User Question
      ↓
Question Embedding
      ↓
Vector Search
      ↓
Relevant Chunks
      ↓
Prompt + Context
      ↓
LLM
      ↓
Grounded Response

5. AI Workflow Orchestration

The final workflow engine will combine normal backend steps with AI steps, retrieval, external services, and asynchronous processing.

🛠️ Tech Stack
Area	Technology
Language	Java 21
Framework	Spring Boot 3.5.x
Security	Spring Security, JWT, BCrypt
API	REST, Swagger / OpenAPI
Persistence	Spring Data JPA / Hibernate
Database	PostgreSQL
Database Migration	Flyway
Cache	Redis
Messaging	Apache Kafka
AI	LLMs, Embeddings, Vector Search, RAG
Build	Maven
Testing	Spring Boot Test, Spring Security Test
Deployment	Docker / Cloud - planned

📁 Project Structure
src/main/java/com/santosh/aiworkflowplatform
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── repository
├── security
└── service
    └── impl
Layered Architecture

Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL

🔐 Security

The application uses stateless JWT authentication.

Public authentication endpoints are separated from protected application resources.

Role-based authorization is enforced using Spring Security.

New users receive the USER role by default. Administrative privileges are not exposed through the public registration API.

📖 API Documentation

When the application is running locally:

http://localhost:8080/swagger-ui/index.html

OpenAPI specification:

http://localhost:8080/v3/api-docs
🚀 Run Locally
Prerequisites
Java 21
PostgreSQL
Maven Wrapper
Build
.\mvnw.cmd clean compile
Run tests
.\mvnw.cmd test
Start application
.\mvnw.cmd spring-boot:run

Configure the local PostgreSQL database and JWT settings before starting the application.

🧪 Development Workflow
Ticket
  ↓
Feature Branch
  ↓
Implementation
  ↓
Testing
  ↓
Commit
  ↓
Push
  ↓
Pull Request
  ↓
Code Review
  ↓
Merge

🗺️ Roadmap
 Authentication foundation
 JWT authentication
 Global exception handling
 Role-based authorization
 Workflow CRUD
 Workflow execution engine
 Kafka asynchronous execution
 LLM integration
 Document ingestion
 Embeddings
 Vector database
 RAG pipeline
 AI workflow orchestration
 Redis caching
 Rate limiting
 Resilience and retries
 Observability
 Automated integration testing
 Docker / cloud deployment
📌 Engineering Focus

This project is being built as a backend engineering system rather than only an AI demo.

The focus is on:

Clean API design
Security
PostgreSQL and data modeling
Asynchronous processing
Distributed systems
Reliability and resilience
AI application architecture
RAG and vector search
Scalable system design


### Then save it.

After saving, run:

```powershell
git status