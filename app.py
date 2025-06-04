# app.py

from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)

# Load environment variables from .env
from dotenv import load_dotenv
load_dotenv()

# Example: postgresql://user:password@localhost:5432/erwdb
app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("DATABASE_URL")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db = SQLAlchemy(app)

@app.route("/")
def hello():
    return "Hello, ERW MVP!"

if __name__ == "__main__":
    app.run(debug=True)

