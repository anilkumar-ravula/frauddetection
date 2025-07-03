# notifier.py
import requests

def send_notification(channel, recipient, message, incident_id, tenant_id="clienta", base_url="http://localhost:8081"):
    url = f"{base_url}/api/notify"
    headers = {
        "X-Tenant-ID": tenant_id,
        "Content-Type": "application/json"
    }
    payload = {
        "channel": channel,
        "recipient": recipient,
        "message": message,
        "incidentId": incident_id
    }

    try:
        response = requests.post(url, json=payload, headers=headers)
        if response.status_code == 200:
            print("✅ Alert sent successfully.")
            return True
        else:
            print(f"❌ Failed with status {response.status_code}: {response.text}")
            return False
    except requests.exceptions.RequestException as e:
        print("🚫 Request failed:", e)
        return False
