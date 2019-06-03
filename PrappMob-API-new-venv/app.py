import os
from flask import Flask, g, session, request, flash
from peewee import *
from flask_jsonpify import jsonify
from flask_restful import Resource, Api
from flask_cors import CORS
from threading import Lock, Thread, Event
from flask_socketio import SocketIO, emit, join_room, leave_room, close_room, rooms, disconnect, send
from ast import literal_eval

async_mode = None

app = Flask(__name__)
app.config.update(dict(
    SECRET_KEY='bardzosekretnawiadomosc',
    TYTUL='',
    DATABASE=os.path.join(app.root_path, 'prappmob-peewee.db')
))

CORS(app)

prappmob_db = SqliteDatabase(app.config['DATABASE'])

socketio = SocketIO(app)  # async_mode=async_mode)
thread = Thread()
thread_stop_event = Event()
thread_lock = Lock()

#
# # SOCKET.IO HANDLERS
#


@socketio.on('sampleSaved')
def sample_saved(sample_saved_data):
    sample_saved_data_dict = literal_eval(sample_saved_data)

    socketio.emit('sampleSaved', {'sensor_id': sample_saved_data_dict.get('sensor_id')})


@socketio.on('connect')
def test_connect():
    global thread
    print('Client connected', request.sid)


@socketio.on('disconnect')
def test_disconnect():
    return
    # print('Client disconnected', request.sid)
    # global client_type
    # for cli in client_type:
    #     if cli['client_sid'] == request.sid:
    #         for file in list_of_locked_files:
    #             if file.get('user_id') == cli['user_id']:
    #                 socketio.emit('fileUnlocked', file)
    #         client_type.remove(cli)
    #         break
    # print("Global client_type disconnected:", client_type)


# REST HANDLERS

@app.before_request
def before_request():
    g.db = prappmob_db
    g.db.connect()


@app.after_request
def after_request(response):
    g.db.close()
    return response


@app.route('/')
def hello_world():
    return 'Hello World!'


class WorkstationList(Resource):
    def get(self):
        from Database.db_generator import get_all_workstations
        print(str(get_all_workstations()))
        return jsonify(get_all_workstations())

    def post(self):
        return 0


class OneWorkstation(Resource):
    def get(self, station_id):  # get 1 workstation by id
        from Database.db_methods import get_workstation
        print(str((get_workstation(station_id))))
        return jsonify(get_workstation(station_id))

    def delete(self, station_d):  # delete workstation by id
        return

    def put(self, station_id):  # insert new workstation
        return


class SensorList(Resource):
    def get(self):
        from Database.db_generator import get_all_sensors
        print(str(get_all_sensors()))
        return jsonify(get_all_sensors())

    def post(self):
        return 0


class OneSensor(Resource):
    def get(self, sensor_id):  # get 1 sensor by id
        from Database.db_methods import get_sensor
        print(str((get_sensor(sensor_id))))
        return jsonify(get_sensor(sensor_id))

    def delete(self, sensor_id):  # delete sensor by id
        return

    def put(self, sensor_id):  # insert new sensor
        return


class SampleList(Resource):
    def get(self):
        from Database.db_generator import get_all_samples
        print(str(get_all_samples()))
        return jsonify(get_all_samples())

    def post(self):
        from Database.db_methods import create_sample
        from Database.db_generator import get_all_samples
        create_sample(request.json['value'], request.json['sensor_id'], request.json['timestamp'])
        return jsonify(get_all_samples()[-1])


class OneSample(Resource):
    def get(self, sample_id):  # get 1 sample by id
        from Database.db_methods import get_sample
        print(str((get_sample(sample_id))))
        return jsonify(get_sample(sample_id))

    def delete(self, sample_id):  # delete sample by id
        return

    def put(self, sample_id):  # edit sample
        return


class SensorsByStations (Resource):
    def get(self, station_id):
        from Database.db_methods import get_sensor_by_station
        return jsonify(get_sensor_by_station(station_id))


class SamplesBySensors (Resource):
    def get(self, sensor_id):
        from Database.db_methods import get_sample_by_sensor
        return jsonify(get_sample_by_sensor(sensor_id))


Api(app).add_resource(WorkstationList, '/workstations')  # Route_1
Api(app).add_resource(SensorList, '/sensors')  # Route_2
Api(app).add_resource(SampleList, '/samples')  # Route_3
Api(app).add_resource(OneWorkstation, '/workstations/<station_id>')  # Route_4
# Api(app).add_resource(OneSensor, '/sensors/<sensor_id>')  # Route_5
# Api(app).add_resource(OneSample, '/samples/<sample_id>')  # Route_6
Api(app).add_resource(SensorsByStations, '/sensors/<station_id>')  # Route_6
Api(app).add_resource(SamplesBySensors, '/samples/<sensor_id>')  # Route_6


