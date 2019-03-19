from flask import Flask, request
from flask_restful import Resource, Api
from sqlalchemy import create_engine
from json import dumps

e = create_engine('sqlite:///samples.db')

app = Flask(__name__)
api = Api(app)

@app.route("/")
def hello():
    return ("General Canoli")

class Workstations(Resource):
    def get(self):
        conn = e.connect()
        query = conn.execute("select * from workstation")
        return {'workstations': [i for i in query.cursor.fetchall()]}

class Workstation(Resource):
    def get(self, workstation):
        conn = e.connect()
        query = conn.execute("select * from workstation where station_id='%s'"%workstation)
        return {'workstation': [i for i in query.cursor.fetchall()]}

class Sensors(Resource):
    def get(self):
        conn = e.connect()
        query = conn.execute("select * from sensor;")
        return {'sensors': [i for i in query.cursor.fetchall()]}

class Sensor(Resource):
    def get(self, sensor):
        conn = e.connect()
        query = conn.execute("select * from sensor where sensor_id='%s'"%sensor)
        return {'sensor': [i for i in query.cursor.fetchall()]}

class Samples(Resource):
    def get(self, sample):
        conn = e.connect()
        query = conn.execute("select * from sensor")
        return {'samples': [i for i in query.cursor.fetchall()]}

class Sample(Resource):
    def get(self, sample):
        conn = e.connect()
        query = conn.execute("select * from sample where sample_id='%s'"%sample)
        return {'sample': [i for i in query.cursor.fetchall()]}

api.add_resource(Workstations, '/workstations')
api.add_resource(Workstation, '/workstation/<string:workstation>')
api.add_resource(Sensors, "/sensors")
api.add_resource(Sensor, "/sensor/<string:sensor>")
api.add_resource(Samples, "/samples")
api.add_resource(Sample, "/sample/<string:sample>")

if __name__ == '__main__':
    app.run()