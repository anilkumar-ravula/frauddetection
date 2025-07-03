# fraud_stream_processor.py

import pandas as pd
import numpy as np
import joblib
# Load the same dataset (acts like stream input)
df = pd.read_csv('bank_transactions_data_3.csv')

# Select same features as in training
features = [
    'TransactionAmount',
    'CustomerAge',
    'TransactionDuration',
    'LoginAttempts',
    'AccountBalance'
]

# Drop rows with missing feature data
df = df.dropna(subset=features)

# Load trained model
model = joblib.load('fraud_model.pkl')

# Prepare data for prediction
X = df[features]
predictions = model.predict(X)  # -1 = fraud

# Add predictions to the DataFrame
df['FraudPrediction'] = np.where(predictions == -1, 'Fraud', 'Normal')

# Print sample results
print("\n🔍 Sample Predictions:")
print(df[['TransactionID', 'TransactionAmount', 'FraudPrediction']].head())

# Save to output file
df.to_csv('transactions_with_predictions.csv', index=False)
print("📄 Saved predictions to 'transactions_with_predictions.csv'")
