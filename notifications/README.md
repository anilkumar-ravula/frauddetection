# Notification Service

A Spring Boot-based microservice for sending notifications via Email, Slack, SMS, and Calls. Designed for integration with systems like Alertmanager or internal services via REST.

## 🔧 Key Capabilities

Multi-Channel Support:

Email (SMTP / external service like SendGrid)

Slack (via Webhook)

SMS (e.g., Twilio)

Mobile Call (e.g., Twilio voice call API)

REST Endpoints for Ingestion:

/api/notify — Accepts generic POST requests (compatible with Alertmanager & other services)

Retry & Failure Handling:

Configurable retry mechanism using Spring Retry or custom logic with a message queue.

Notification Tracking:

Persisted in a relational DB (e.g., PostgreSQL) with status: PENDING, SENT, FAILED, RETRYING, etc.

Data Encrptions:



## High Level Architecture

```
[Alertmanager / Other Services]
         │
[NotificationController]
         │
[NotificationService]
         │
┌────────┼──────────────┐
│ EmailSenderService   │
│ SlackSenderService   │
│ SMSSenderService     │
│ CallSenderService    │
└────────┴──────────────┘
        │
[NotificationRepository]
        │
[PostgreSQL / JPA]
```
## ✨ Features

- Multi-channel notifications: Email, Slack, SMS, Call
- REST API to send notifications
- Retry and failure handling
- Notification tracking and persistence
- Dockerized with Helm chart for Kubernetes deployment

## 🚀 Tech Stack

- Java 8+
`- Spring Boot
- Maven
- PostgreSQL (for tracking notifications)
- Docker
- Helm (Kubernetes)
`
## 🧪 REST API

### POST `/api/notify`

**Request Body:**

```
{
  "channel": "EMAIL",
  "recipient": "user@example.com",
  "message": "Server down!",
  "incidentId": "1234"
}
```

## 🐳 Docker

Build and run:

```bash
docker build -t de/notification-service .
docker run -p 8080:8080 imr/notification-service
```

Build and run compose:
```bash
docker build -t de/notification-service .
docker-compose up -d
```

##  API Validation:

# Setup smtp password for gmail :
```
Here's how to find your Gmail SMTP settings:
Access your Google Account: Go to [Link: myaccount.google.com/apppasswords https://myaccount.google.com/apppasswords].
Generate an App Password:
Select "Mail" as the app and "Other (Custom name)" as the device.
Give the app a descriptive name to help you remember it.
Copy the App Password: This password will be your SMTP password.

configure these values in .env file for dcker compose or for help 

SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=youremail
SMTP_PASSWORD=passwordfromabovestep


```

```
curl --location 'http://localhost:8080/api/notify' \
--header 'X-Tenant-ID: clienta' \
--header 'Content-Type: application/json' \
--data-raw '{
"channel": "EMAIL",
"recipient": "user@example.com",
"message": "Server down!",
"incidentId": "1234"
}'

curl --location 'http://localhost:8080/api/notify'


Ideal Response if it is working : Welcome to notifications
```
## ☸️ Kubernetes (via Helm)

Build and push your Docker image:

```bash
docker build -t imr/notification-service:latest .
docker push imr/notification-service:latest
```

Deploy using Helm:

```bash
helm install notification-service ./helm/notification-service
```

## 🛠 Configuration

Edit `src/main/resources/application.yml` to configure database, mail server, and other settings.

## 📂 Project Structure

```
.
├── src
│   └── main
│       ├── java/com/example/notification
│       ├── resources
├── Dockerfile
├── helm
│   └── notification-service
├── pom.xml
```

## 📃 License

MIT
