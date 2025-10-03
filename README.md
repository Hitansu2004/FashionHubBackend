# Fashion Hub Backend

> **Enterprise-Grade E-commerce Microservices Platform**  
> A comprehensive, scalable backend solution for fashion retail businesses built with Spring Boot microservices architecture.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue.svg)](https://microservices.io/)
[![SQL Server](https://img.shields.io/badge/Database-SQL%20Server-red.svg)](https://www.microsoft.com/en-us/sql-server)

## 🏗️ Architecture Overview

Fashion Hub Backend is a **production-ready microservices platform** designed to handle enterprise-level fashion e-commerce operations. Built with modern Spring Boot architecture, it provides scalable, maintainable, and robust backend services.

### 🚀 Key Features

- **Complete E-commerce Suite**: From user management to order fulfillment
- **Microservices Architecture**: Independently deployable, scalable services
- **Enterprise Database Design**: Comprehensive SQL Server schemas
- **API Gateway Integration**: Centralized routing and load balancing
- **Service Discovery**: Eureka server for dynamic service registration
- **Advanced Product Management**: SKU-based inventory with attributes
- **Multi-tenant Ready**: Seller management and marketplace functionality

## 🏢 Business Capabilities

### 👥 User Management
- User registration and authentication
- Profile management
- Address management for billing/shipping

### 🛍️ Product Catalog
- Comprehensive product information management (PIM)
- Category hierarchy and organization
- Product attributes (size, color, pricing)
- Seller and brand management

### 🛒 Shopping Experience
- Smart shopping cart with save-for-later
- Real-time pricing and discount calculations
- Advanced product search and filtering

### 📦 Order Management
- Complete order lifecycle management
- Order tracking and status updates
- Invoice generation and payment processing
- Return and refund management

### 📊 Inventory Control
- Real-time inventory tracking
- Multi-location stock management
- Automated reservation and allocation
- Low stock alerts and replenishment

### 🎯 Marketing & Promotions
- Flexible promotion engine
- Discount code management
- Category-based promotional campaigns

## 🛠️ Technical Architecture

### Microservices Structure

```
Fashion Hub Backend/
├── 🌐 api-gateway/              # Spring Cloud Gateway (Port 8000)
├── 🔍 eureka-server/            # Service Discovery
├── 👤 UserService/              # User Management & Authentication
├── 📦 product-management-service/ # Unified Product & Category Service (Port 8001)
├── 🏪 catalog-service/          # Product Catalog & Promotions
├── 📋 orderservice/             # Order Management System
├── 📊 inventoryService/         # Inventory Management System
└── 🛒 cncServiece/             # Cart & Checkout Service
```

### Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.5.3 |
| **Language** | Java | 17 |
| **Database** | Microsoft SQL Server | Latest |
| **Service Discovery** | Netflix Eureka | Latest |
| **API Gateway** | Spring Cloud Gateway | Latest |
| **Build Tool** | Maven | 3.6+ |
| **ORM** | JPA/Hibernate | Latest |

### Database Architecture

The platform uses **6 specialized databases** for optimal performance:

- **`account_db`** - User accounts and authentication
- **`cart_and_checkout_db`** - Shopping cart and checkout processes
- **`pim_db`** - Product Information Management
- **`oms_db`** - Order Management System
- **`ims_db`** - Inventory Management System
- **`cms_db`** - Catalog Management System

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Microsoft SQL Server**
- **Git**

### 1. Clone the Repository

```bash
git clone https://github.com/Hitansu2004/FashionHubBackend.git
cd FashionHubBackend
```

### 2. Database Setup

Execute the provided SQL scripts to create databases:

```bash
# Run the database creation scripts
sqlcmd -S localhost -i cms_sql_data.sql
sqlcmd -S localhost -i cnc_db.sql
sqlcmd -S localhost -i oms_db.sql
sqlcmd -S localhost -i ims_master_data.sql
sqlcmd -S localhost -i pim_master_data.sql
sqlcmd -S localhost -i snb_master_data.sql
```

### 3. Start Services

#### Option 1: Quick Start (Recommended)
```bash
# Use the provided batch script for Windows
./clean-install-all.bat

# Or manually start each service
```

#### Option 2: Manual Service Startup

Start services in the following order:

```bash
# 1. Start Eureka Server (Service Discovery)
cd eureka-server
./mvnw spring-boot:run

# 2. Start API Gateway
cd ../api-gateway
./mvnw spring-boot:run

# 3. Start Core Services
cd ../UserService
./mvnw spring-boot:run

cd ../product-management-service
./mvnw spring-boot:run

cd ../catalog-service
./mvnw spring-boot:run

cd ../orderservice
./mvnw spring-boot:run

cd ../inventoryService
./mvnw spring-boot:run

cd ../cncServiece
./mvnw spring-boot:run
```

### 4. Access the Platform

- **API Gateway**: http://localhost:8000
- **Eureka Dashboard**: http://localhost:8761
- **Product Management**: http://localhost:8001

## 📡 API Documentation

### Gateway Routing

All requests are routed through the API Gateway at `localhost:8000`:

| Route | Service | Description |
|-------|---------|-------------|
| `/user/**` | UserService | User management endpoints |
| `/products/**` | Product Management | Product and category operations |
| `/catalog/**` | Catalog Service | Product catalog and promotions |
| `/orders/**` | Order Service | Order management |
| `/inventory/**` | Inventory Service | Stock management |
| `/cart/**` | Cart Service | Shopping cart operations |

### Key Endpoints

#### Product Management
```http
GET    /products              # Get products with pagination
GET    /products/{id}         # Get product by ID
POST   /products              # Create new product
PUT    /products/{id}         # Update product
DELETE /products/{id}         # Delete product
GET    /products/stats        # Get product statistics
```

#### Order Management
```http
GET    /orders                # Get user orders
POST   /orders                # Create new order
GET    /orders/{id}           # Get order details
PUT    /orders/{id}/status    # Update order status
GET    /orders/{id}/tracking  # Get shipment tracking
```

## 🏆 Enterprise Features

### Scalability
- **Horizontal Scaling**: Each microservice can be scaled independently
- **Load Balancing**: API Gateway distributes requests efficiently
- **Database Partitioning**: Separate databases for optimal performance

### Reliability
- **Service Discovery**: Automatic service registration and health checks
- **Circuit Breakers**: Fault tolerance and graceful degradation
- **Retry Mechanisms**: Automatic retry for failed requests

### Security
- **Authentication**: JWT-based user authentication
- **Authorization**: Role-based access control
- **Data Validation**: Comprehensive input validation
- **SQL Injection Protection**: JPA/Hibernate ORM security

### Monitoring & Operations
- **Health Checks**: Built-in Spring Boot Actuator endpoints
- **Service Registry**: Eureka dashboard for service monitoring
- **Logging**: Comprehensive application logging
- **Metrics**: Performance monitoring capabilities

## 🎯 Business Impact

### For Fashion Retailers
- **Reduced Time-to-Market**: Deploy new features independently
- **Scalable Growth**: Handle increasing customer loads efficiently
- **Cost Optimization**: Resource allocation based on actual usage
- **Data Insights**: Comprehensive business analytics capabilities

### For Developers
- **Modern Architecture**: Latest Spring Boot and Java technologies
- **Clean Code**: Well-structured, maintainable codebase
- **Documentation**: Comprehensive API and database documentation
- **Testing**: Unit and integration testing capabilities

## 🔮 Future Enhancements

- **Event-Driven Architecture**: Apache Kafka for real-time data streaming
- **Containerization**: Docker and Kubernetes deployment
- **Cloud Integration**: AWS/Azure cloud-native services
- **AI/ML Integration**: Recommendation engine and predictive analytics
- **Mobile APIs**: GraphQL for mobile application support

## 🤝 Contributing

This is a showcase project demonstrating enterprise-level backend development capabilities. For collaboration opportunities or technical discussions, please reach out through GitHub.

## 📄 License

This project is developed for portfolio demonstration purposes.

## 👨‍💻 Developer

**Hitansu Parichha**  
Full-Stack Developer specializing in Enterprise Java Applications

- 🌐 **Portfolio**: [View Portfolio](https://github.com/Hitansu2004)
- 💼 **LinkedIn**: Connect for business opportunities
- 📧 **Email**: Available for freelancing projects

---

> **💡 Professional Note**: This Fashion Hub Backend demonstrates production-ready microservices architecture, suitable for enterprise-level fashion and retail businesses. The platform showcases advanced Spring Boot capabilities, database design expertise, and scalable system architecture.
