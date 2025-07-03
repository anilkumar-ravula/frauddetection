# train_fraud_model.py

import pandas as pd
from sklearn.ensemble import IsolationForest
import joblib

# Load the dataset
df = pd.read_csv('bank_transactions_data_2.csv')

# Select features for training
features = [
    'TransactionAmount',
    'CustomerAge',
    'AccountBalance'
]

# Drop rows with missing feature data
df = df.dropna(subset=features)

# Extract training data
X = df[features]

# Train Isolation Forest model
model = IsolationForest(contamination=0.05, random_state=42)
model.fit(X)

# Save trained model
joblib.dump(model, 'fraud_model.pkl')
print("✅ Model trained and saved as 'fraud_model.pkl'")
