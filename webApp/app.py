import sense
import temp_sensor as tem
from flask import *
from pymongo import MongoClient


client = MongoClient('mongodb://jason:itslit101@ds235785.mlab.com:35785/smart_tank') #conncets to database
ardi = sense.sensors() # connects to arduino
app = Flask(__name__) #flask init

db = client.smart_tank

"""
# this is an example on how to insert data into db
userTest = {
        "Name" : "Jason",
        "Email" : "jasonm718@gmail.com"
    }

db.smart_tank.insert(userTest)
"""
@app.route("/")

def index():
    
    count = ardi.counter() #gets number from arduino
    tempF = tem.read_temp_F() #get Farenheit temp through Gpio
    tempC = tem.read_temp_C() #get Celcius temp through Gpio
    
    
    
    #render index.html and sends variables 
    return render_template('index.html', tempC = tempC, tempF = tempF, count = count)


# starts the flask serverlistening on the pi port 5000
if __name__ == "__main__":
	app.run(host='0.0.0.0', debug=True)
