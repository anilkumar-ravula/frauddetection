# VeriFlux - Real-Time Fraud Detection System

**VeriFlux** is a real-time fraud detection system designed for detecting fraudulent banking transactions using streaming data, Kafka, machine learning, and notifications. This project combines Python, Java Spring Boot, Kafka Streams, Docker, and PostgreSQL into a cohesive architecture.

---

## 📁 Project Structure

```
DE_TECH_EXPO_VeriFlux/
│
├── connector/                      # Kafka connectors
├── kafka-docker/                  # Kafka & Zookeeper Docker configs
├── notification-service/          # Spring Boot microservice for sending alerts
├── stream_processor/              # Python scripts for ML model and Kafka stream processing
├── infra/                         # Infrastructure as code (Helm, Kubernetes, etc.)
├── postgres/                      # PostgreSQL setup and initialization
│
├── docker-compose.yml             # Main Docker Compose file
├── .env                           # Environment variables
├── pom.xml                        # Parent Maven build configuration
├── mvnw / mvnw.cmd                # Maven wrapper
└── README.md                      # This file
```

---

## 🔧 Technologies Used

- **Apache Kafka** for event streaming
- **Spring Boot** for Notification Service (Java)
- **Python** for machine learning and stream processing
- **Docker & Docker Compose** for containerization
- **PostgreSQL** for data persistence
- **Helm/Kubernetes** for deployment orchestration
- **Kafka Connect** for integration pipelines

---

## 🚀 How to Run the Project

### Prerequisites

- Docker & Docker Compose
- Java 17+
- Python 3.9+
- Maven

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/DE_TECH_EXPO_VeriFlux.git
cd DE_TECH_EXPO_VeriFlux
```

### 2. Start Kafka, PostgreSQL, and Dependencies

```bash
docker-compose up -d
```

### 3. Train Fraud Detection Model

```bash
cd stream_processor
python train_fraud_model.py
```

### 4. Start Stream Processor

```bash
python fraud_stream_processor.py
```

### 5. Start Notification Service (Spring Boot)

```bash
cd notification-service
./mvnw spring-boot:run
```

---

## 📡 System Architecture

```text
+---------------------+       Kafka Topic       +-----------------------+
|  Transaction Stream |  -------------------->  | Stream Processor (Py) |
+---------------------+                         +-----------+-----------+
                                                           |
                                               Fraud Detected Event
                                                           |
                                              +------------v-------------+
                                              |   Notification Service   |
                                              |    (Spring Boot App)     |
                                              +------------+-------------+
                                                           |
                                                  Sends Alerts (Email/SMS/Slack)
```

---

## 📊 Sample Datasets

- `bank_transactions_data_2.csv`
- `bank_transactions_data_3.csv`
- Output: `transactions_with_predictions.csv`

---

## 📦 Notification Channels Supported

- Email
- Slack
- SMS (extendable)
- Voice Call (mock/demo only)

---

## 🧪 Testing

1. Trigger sample transactions to Kafka topic.
2. Monitor for flagged frauds.
3. Confirm notifications are received.

---

## 🛠️ Folder Highlights

- `notification-service`: Java Spring Boot microservice to deliver fraud alerts
- `stream_processor`: Python-based fraud model + Kafka stream handler
- `postgres`: DB setup with user/schema/table initialization
- `kafka-docker`: Custom Kafka stack for local dev
- `infra`: Contains Helm charts and Kubernetes manifests

---

## 📃 License

This project is licensed under the MIT License.

---

## 🙌 Acknowledgements

- Inspired by real-world FinTech fraud detection use cases.
- Uses open-source technologies and tools.
