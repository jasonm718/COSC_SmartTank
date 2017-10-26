import sense
import temp_sensor as tem
from flask import *
from pymongo import MongoClient

db = MongoClient('mongodb://jason:itslit101@ds235785.mlab.com:35785/smart_tank')

ardi = sense.sensors()

app = Flask(__name__)

@app.route("/")

def index():
    count = ardi.counter()
    tempF = tem.read_temp_F()
    tempC = tem.read_temp_C()
    return render_template('index.html', tempC = tempC, tempF = tempF, count = count)


# starts the flask serverlistening on the pi port 5000
if __name__ == "__main__":
	app.run(host='0.0.0.0', debug=True)
