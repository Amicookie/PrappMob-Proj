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

# socketio = SocketIO(app)  # async_mode=async_mode)
# thread = Thread()
# thread_stop_event = Event()
# thread_lock = Lock()
#
#

#
# # SOCKET.IO HANDLERS
#
# @socketio.on('chat')
# def handle_chat(message):
#     print('Chat', message)
#     message_dict = literal_eval(message)
#     socketio.emit('chat', {'username': message_dict.get('username'), 'message': message_dict.get('message')})
#
#
# @socketio.on('fileSaved')
# def handle_file_saved(file_saved):
#     print('File Saved!', file_saved)
#     file_saved_dict = literal_eval(file_saved)
#
#     global created_file_id
#     file_saved_dict['file_id'] = created_file_id
#     print('File Saved Details', file_saved_dict)
#     socketio.emit('fileSaved', {'username': file_saved_dict.get('username'),
#                                 'file_name': file_saved_dict.get('file_name'),
#                                 'file_id': file_saved_dict.get('file_id')})
#     created_file_id = -1
#     print('Resetted created_file_id' + str(created_file_id))
#
#
# @socketio.on('fileLocked')
# def handle_file_locked(file_locked_data):
#     print('File Locked!', file_locked_data)
#     file_locked_data_dict = literal_eval(file_locked_data)
#     global count_list_of_locked_files
#     count_list_of_locked_files += 1
#     file_locked_data_dict['count'] = count_list_of_locked_files
#
#     list_of_locked_files.append(file_locked_data_dict)
#     print(list_of_locked_files)
#     socketio.emit('fileLocked', {'user_id': file_locked_data_dict.get('user_id'),
#                                  'username': file_locked_data_dict.get('username'),
#                                  'file_name': file_locked_data_dict.get('file_name'),
#                                  'file_id': file_locked_data_dict.get('file_id'),
#                                  'list_of_files': list_of_locked_files,
#                                  'count': count_list_of_locked_files})
#
#
# @socketio.on('fileUnlocked')
# def handle_file_unlocked(file_unlocked_data):
#     print('File Unlocked!', file_unlocked_data)
#     file_unlocked_data_dict = literal_eval(file_unlocked_data)
#     global count_list_of_locked_files
#     index_of_unlocked_file = -1
#
#     for locked_file in list_of_locked_files:
#         index_of_unlocked_file += 1
#         if locked_file.get('file_id') == file_unlocked_data_dict.get('file_id'):
#             list_of_locked_files.remove(locked_file)
#             count_list_of_locked_files -= 1
#             break
#     # list_of_locked_files.remove(file_unlocked_data_dict)
#
#     print(list_of_locked_files)
#     socketio.emit('fileUnlocked', {'username': file_unlocked_data_dict.get('username'),
#                                    'file_name': file_unlocked_data_dict.get('file_name'),
#                                    'file_id': file_unlocked_data_dict.get('file_id'),
#                                    'user_id': file_unlocked_data_dict.get('user_id'),
#                                    'index_of_unlocked_file': index_of_unlocked_file})
#
#
# @socketio.on('fileUpdated')
# def handle_file_updated(file_updated_data):
#     print('File Updated!', file_updated_data)
#     file_updated_data_dict = literal_eval(file_updated_data)
#     global updated_file_id
#     file_updated_data_dict['file_id'] = updated_file_id
#     print('File Updated Details', file_updated_data_dict)
#     socketio.emit('fileUpdated', {'username': file_updated_data_dict.get('username'),
#                                   'file_name': file_updated_data_dict.get('file_name'),
#                                   'file_id': file_updated_data_dict.get('file_id')})
#     updated_file_id = -1
#
#
# @socketio.on('fileDeletion')
# def handle_file_deletion(file_deletion_data):
#     print('File Deletion: ', file_deletion_data)
#     file_deletion_data_dict = literal_eval(file_deletion_data)
#     socketio.emit('fileDeletion', {'username': file_deletion_data_dict.get('username'),
#                                    'file_name': file_deletion_data_dict.get('file_name'),
#                                    'file_id': file_deletion_data_dict.get('file_id')})
#
#
# @socketio.on('connect')
# def test_connect():
#     global thread
#     print('Client connected', request.sid)
#
#
# @socketio.on('socketConnectionTypeHelper')
# def helper(user_id):
#     print('Client sid: ', request.sid, 'User_id: ', user_id)
#     global client_type
#     user_id_dict = literal_eval(user_id)
#     for cli in client_type:
#         if cli['client_sid'] == request.sid and cli['user_id'] == -1:
#             cli['user_id'] = user_id_dict.get('user_id')
#             break
#     print("Global client_type TYPE HELPER: ", client_type)
#
#
# @socketio.on('connectionType')
# def connection_type(conn_type):
#     print('Client type and ID: ', conn_type, ', Client sid: ', request.sid)
#     conn_type_dict = literal_eval(conn_type)
#
#     global client_type
#     # for cli in client_type:
#     #     if cli.sid == request.sid:
#     #         cli.client_type = connection_type
#     #         break
#     #     else
#     #
#
#     # if conn_type_dict.get('user_id') == NULL
#
#     # conn_type_dict.get('user_id', -1) --- set '-1' if user_id is null
#
#     client_type.append({"client_sid": request.sid, "client_type": conn_type_dict.get('connectionType'),
#                         "user_id": conn_type_dict.get('user_id', -1)})
#     print("Global client_type connected: ", client_type)
#
#     # with thread_lock:
#     #     if thread is None:
#     #         thread = socketio.start_background_task(background_thread)
#     # emit('my_response', {'data': 'Connected', 'count': 0})
#
#
# @socketio.on('disconnect')
# def test_disconnect():
#     return
#     # print('Client disconnected', request.sid)
#     # global client_type
#     # for cli in client_type:
#     #     if cli['client_sid'] == request.sid:
#     #         for file in list_of_locked_files:
#     #             if file.get('user_id') == cli['user_id']:
#     #                 socketio.emit('fileUnlocked', file)
#     #         client_type.remove(cli)
#     #         break
#     # print("Global client_type disconnected:", client_type)


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
            #get_all_samples()


    def post(self):
        return 0


class OneSample(Resource):
    def get(self, sample_id):  # get 1 sample by id
        from Database.db_methods import get_sample
        print(str((get_sample(sample_id))))
        return jsonify(get_sample(sample_id))

    def delete(self, sample_id):  # delete sample by id
        return

    def put(self, sample_id):  # insert new sample
        return


Api(app).add_resource(WorkstationList, '/workstations')  # Route_1
Api(app).add_resource(SensorList, '/sensors')  # Route_2
Api(app).add_resource(SampleList, '/samples')  # Route_3
Api(app).add_resource(OneWorkstation, '/workstations/<station_id>')  # Route_4
Api(app).add_resource(OneSensor, '/sensors/<sensor_id>')  # Route_5
Api(app).add_resource(OneSample, '/samples/<sample_id>')  # Route_6

