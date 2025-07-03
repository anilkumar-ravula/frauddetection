from kafka import KafkaConsumer
import json
import joblib
import numpy as np
import base64
from decimal import Decimal
from notifier import send_notification

# import psycopg2  # Uncomment if using DB

# Load pre-trained model
model = joblib.load('fraud_model.pkl')

# Optional: PostgreSQL DB connection
# conn = psycopg2.connect("dbname=fraud user=postgres password=postgres")
# cursor = conn.cursor()

# Kafka Consumer Setup
consumer = KafkaConsumer(
    'cdc-events',
    bootstrap_servers='localhost:9092',
    group_id='cdc-events-python',
    auto_offset_reset='earliest',
    enable_auto_commit=True,
    value_deserializer=lambda m: m.decode('utf-8')
)

print("🔁 Listening for messages...")
def decode_debezium_decimal(base64_str, scale=2):
    try:
        raw_bytes = base64.b64decode(base64_str)
        int_val = int.from_bytes(raw_bytes, byteorder='big', signed=True)
        return Decimal(int_val) / (10 ** scale)
    except Exception as e:
        print(f"❌ Error decoding decimal: {e}")
        return None

for msg in consumer:
    try:
        txn = json.loads(msg.value)
        if not isinstance(txn, dict):
            print(f"⚠️ Skipping non-dict message: {txn}")
            continue

        # Ensure required keys exist
        required_keys = ['transactionamount', 'customerage', 'accountbalance']
        if not all(key in txn for key in required_keys):
            print(f"⚠️ Missing required keys in message: {txn}")
            continue

        # Prepare features for model
        features = np.array([[decode_debezium_decimal(txn['transactionamount'],2), txn['customerage'],decode_debezium_decimal(txn['accountbalance'],2)]])
        prediction = model.predict(features)
        fraud_ml = prediction[0] == -1

        # Apply rule-based logic
        fraud_rule = txn['customerage'] > 150
        is_fraud = fraud_ml or fraud_rule

        print("\n✅ Prediction Result:")
        print(f"TransactionID: {txn['transactionid']}")
        print(f"Amount: {decode_debezium_decimal(txn['transactionamount'],2)}")
        print(f"Age: {txn['customerage']}")

        print(f"Fraud Prediction: {'🚨 FRAUD' if is_fraud else '✔️ Legit'}")

        # Optional: Store in DB
        # cursor.execute(
        #     "INSERT INTO transactions (transaction_id, amount, fraud) VALUES (%s, %s, %s)",
        #     (txn['TransactionID'], txn['amount'], is_fraud)
        # )
        # conn.commit()
        if is_fraud:
            print("ℹ️ Sending Notification ")
            channel = "EMAIL"
            recipient = "Anil.Ravula@Geminisolutions.com"
            message = f"TransactionID: {txn['transactionid']} | Amount: {decode_debezium_decimal(txn['transactionamount'],2)} |  {txn['location']}"
            incident_id = txn['transactionid']

            # Trigger only if fraud_detected is True
            send_notification(
                channel=channel,
                recipient=recipient,
                message=message,
                incident_id=incident_id
            )

    except json.JSONDecodeError:
            print(f"❌ Invalid JSON: {msg.value}")
    except Exception as e:
            print(f"❌ Error processing message: {e}")