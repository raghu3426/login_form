from flask import Flask, render_template, request, redirect, url_for
import mysql.connector
from werkzeug.security import generate_password_hash, check_password_hash

app = Flask(__name__)

# MySQL connection setup
conn = mysql.connector.connect(
    host='localhost',
    user='root',          # your MySQL username
    password='SQLRaghu@2005',  # your MySQL password
    database='website_db'
)
cursor = conn.cursor(dictionary=True)

@app.route('/')
def home():
    return redirect(url_for('login'))

# ---------- LOGIN PAGE ----------
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']

        cursor.execute("SELECT * FROM users WHERE username=%s", (username,))
        user = cursor.fetchone()

        if user and check_password_hash(user['password'], password):
            return f"Welcome, {username}!"
        else:
            return "Invalid username or password."

    return render_template('login.html')


# ---------- REGISTER PAGE ----------
@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        hashed_password = generate_password_hash(password)

        try:
            cursor.execute("INSERT INTO users (username, password) VALUES (%s, %s)", (username, hashed_password))
            conn.commit()
            return redirect(url_for('login'))
        except mysql.connector.IntegrityError:
            return "Username already exists. Try another one."

    return render_template('register.html')

if __name__ == '__main__':
    app.run(debug=True)
