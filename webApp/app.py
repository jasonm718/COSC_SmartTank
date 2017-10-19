from flask import *

app = Flask(__name__)

@app.route("/")

def hello():
	return render_template('index.html')


# starts the flask serverlistening on the pi port 5000
if __name__ == "__main__":
	app.run(host='0.0.0.0', debug=True)
