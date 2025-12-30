# ZipURL – Scalable URL Shortening Service

ZipURL is a scalable URL shortening service built to demonstrate system design and backend architecture concepts such as caching, distributed systems, and resilience patterns. The project focuses on handling high read throughput, fault tolerance, and horizontal scalability.

---

## 🚀 Features

- Generate short URLs and redirect to original URLs
- Cache-first architecture for low-latency reads
- Rate limiting to prevent abuse under high traffic
- Circuit breaker to handle downstream failures gracefully
- Designed for horizontal scalability

---

## 🏗 System Architecture

- Stateless REST APIs built using Spring Boot  
- Redis used as an in-memory cache to serve high-frequency read requests  
- MySQL used as the persistent data store  
- Requests follow a cache-first access pattern (Redis → MySQL fallback)  
- Resilience patterns ensure system stability under load and failure scenarios  

---

## 🛠 Technology Stack

- Java
- Spring Boot
- Redis
- MySQL
- REST APIs
- Rate Limiting
- Circuit Breaker

---

## 🔄 High-Level Flow

1. Client requests short URL creation
2. Original URL is stored in MySQL
3. Short URL mapping is cached in Redis
4. On redirect:
   - Check Redis first
   - Fallback to MySQL on cache miss
5. Rate limiting controls request bursts
6. Circuit breaker prevents cascading failures during outages

---

## ⚙️ Scalability & Reliability Considerations

- Stateless services enable horizontal scaling
- Redis reduces database load for read-heavy traffic
- Rate limiting ensures fair usage and protects the system
- Circuit breaker enables graceful degradation under failure
- Designed to scale to millions of requests per day

---

## 📌 Purpose of This Project

This project was built to demonstrate:
- System design thinking
- Scalable backend architecture
- Distributed caching strategies
- Resilience patterns in real-world systems

---

## 🔮 Possible Enhancements

- Distributed ID generation (Base62 / Snowflake)
- URL expiration with TTL-based cleanup
- Analytics (click counts, usage metrics)
- Dockerized deployment
- Load testing and benchmarking

---

## 📄 License

This project is for learning and demonstration purposes.
