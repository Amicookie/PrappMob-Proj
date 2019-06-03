# from peewee import *
from Database.db_model import Workstation, Sensor, Sample, _get_date
from playhouse.shortcuts import model_to_dict


def get_workstation(station_id):
    return Workstation.get_by_id(station_id).to_json()


def get_sensor(sensor_id):
    sensor = Sensor.get_by_id(sensor_id)
    return model_to_dict(sensor)


def get_sensor_by_station(station_id):
    sensors = []
    for sensor in Sensor.select().where(Sensor.station_id==station_id):
        sensors.append(model_to_dict(sensor))

    return sensors


def get_sample(sample_id):
    return model_to_dict(Sample.get_by_id(sample_id))


def put_sample(sample_id, s_value, s_date=_get_date()):     # method for editing
    sample_to_update = Sample.select().where(Sample.sample_id == sample_id).get()
    sample_to_update.value = s_value
    sample_to_update.timestamp = s_date
    sample_to_update.save()


def create_sample(s_value, sensor_id, s_date=_get_date()):
    Sample.create(value=s_value, sensor_id=sensor_id, timestamp=s_date)


def get_sample_by_sensor(sensor_id):
    samples = []
    for sample in Sample.select().where(Sample.sensor_id == sensor_id):
        samples.append(model_to_dict(sample))

    return samples




