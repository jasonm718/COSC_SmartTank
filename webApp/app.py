import sense
from flask import *

ardi = sense.sensors()

app = Flask(__name__)

@app.route("/")

def index():
    count = ardi.counter()
    return render_template('index.html', count = count)


# starts the flask serverlistening on the pi port 5000
if __name__ == "__main__":
	app.run(host='0.0.0.0', debug=True)
