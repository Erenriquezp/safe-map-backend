# Safe Map Backend – Microservices Architecture

This repository contains the backend implementation of **SafeMap**, a system designed to identify, register, and monitor safe places within a city through a scalable microservices architecture.

> 🛡️ SafeMap is designed for urban areas with high crime rates. It allows users to view safe zones, optimal visiting times, and events that occurred in specific locations.

---

## 📦 Tech Stack

- **Java 17** + **Spring Boot 3.2+**
- **MongoDB** (document-based database)
- **Spring Cloud** (Eureka, Config Server, Gateway)
- **Docker & Docker Compose**
- **Spring Web / Spring Data MongoDB**
- **Spring Security (JWT)**
- **Swagger/OpenAPI** for documentation
- **Maven** (multi-module project)

---

## 🧱 Microservices Structure

safe-map-backend/

├── config-server/ # Centralized configuration

├── eureka-server/ # Service discovery (registry)

├── gateway/ # API Gateway

├── user-service/ # Users and authentication

├── safeplace-service/ # Places marked as safe

├── schedule-service/ # Safe time ranges per place

├── event-service/ # Events logged per location

├── docker/ # Docker Compose + environment

└── pom.xml # Parent POM
