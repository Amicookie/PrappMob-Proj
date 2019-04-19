# from peewee import *
from Database.db_model import Workstation, Sensor, Sample, _get_date
from playhouse.shortcuts import model_to_dict


def get_workstation(station_id):
    return Workstation.get_by_id(station_id).to_json()


def get_sensor(sensor_id):
    sensor = Sensor.get_by_id(sensor_id)
    return model_to_dict(sensor)


def get_sample(sample_id):
    return model_to_dict(Sample.get_by_id(sample_id))


# def put_file(file_id, file_name, file_content, file_last_editor_id):
#     file_to_update = File.select().where(File.file_id == file_id).get()
#     file_to_update.file_name = file_name
#     file_to_update.file_content = file_content
#     file_to_update.file_last_editor_id = file_last_editor_id
#     file_to_update.file_update_date = _get_date()
#     file_to_update.save()


def create_sample(svalue, sensor_id):
    Sample.create(value=svalue, sensor_id=sensor_id)

# def put_user(user_id):
#     return 0
#
#
# def delete_file(file_id):
#     # jeśli wszyscy potwierdzili, to usuwamy
#     return File.delete_by_id(file_id)
#
#
# def delete_user(user_id):
#     return 0
#
#
# def does_user_exist(user_ilogin):
#     if User.select().where(User.user_login == user_ilogin):
#         return True
#     else:
#         return False
#
#
# def log_in_user(user_ilogin, user_ipassword):
#     user = User.select().where(User.user_login == user_ilogin, User.user_password == user_ipassword)
#     for row in user:
#         if user:
#             return row
#         else:
#             return False



