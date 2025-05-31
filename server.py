from flask import Flask, request, jsonify
import openai
import os

app = Flask(_name_)

openai.api_key = os.getenv("OPENAI_API_KEY")  # Secure API key storage

# Improved FAQ with more flexible matching
FAQ_KNOWLEDGE_BASE = {
    "book flight": "You can book a flight by visiting our website, selecting your destination, choosing travel dates, and making a payment.",
    "how to book a flight": "You can book a flight by visiting our website and selecting your travel options.",
    "hold reservation": "Some airlines allow you to hold a reservation for a limited time before making a payment.",
    "payment methods": "We accept credit cards, debit cards, PayPal, and other major payment gateways.",
    "cancel ticket": "You can cancel your ticket through 'Manage Booking' on our website. Refund policies depend on the ticket type.",
    "refund policy": "Refund eligibility depends on the ticket type. Fully flexible tickets are refundable, while basic fares may not be.",
    "airport check-in time": "Check-in closes 60 minutes before domestic flights and 2 hours before international flights.",
    "extra baggage": "You can purchase extra baggage allowance online or at the airport. Charges vary by airline and route.",
    "visa requirements": "Visa requirements vary by destination. Check with the embassy or airline before traveling.",
    "student discount": "Some airlines offer student discounts. Check the airline's website or contact support for details."
}

def find_best_match(user_input):
    user_input = user_input.lower()
    for keyword, response in FAQ_KNOWLEDGE_BASE.items():
        if keyword in user_input:  # Partial match check
            return response
    return None

@app.route("/chat", methods=["POST"])
def chat():
    data = request.get_json()
    user_input = data.get("message", "").strip().lower()

    if not user_input:
        return jsonify({"response": "Please provide a message."})

    # Check for FAQ match
    faq_response = find_best_match(user_input)
    if faq_response:
        return jsonify({"response": faq_response})

    # No FAQ match, call OpenAI
    try:
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are an AI assistant specializing in airline reservation systems."},
                {"role": "user", "content": user_input}
            ],
            max_tokens=150
        )
        bot_reply = response["choices"][0]["message"]["content"].strip()
        return jsonify({"response": bot_reply})

    except Exception as e:
        print("OpenAI API Error:", e)
        return jsonify({"response": "I'm sorry, but I couldn't process your request at the moment. Please try again later."})

if _name_ == "_main_":
    app.run(host="0.0.0.0", port=5000,debug=True)