# from peewee import *
from .db_model import Workstation, Sensor, Sample, _get_date
from playhouse.shortcuts import model_to_dict


def get_workstation(station_id):
    return Workstation.get_by_id(station_id).to_json()


def get_sensor(sensor_id):
    return Sensor.get_by_id(sensor_id).to_json()


def get_sample(sample_id):
    return Sample.get_by_id(sample_id).to_json()

def get_all_workstations():
    workstations = []
    for workstation in Workstation.select():
        workstations.append(workstation.to_json())

    return workstations


def get_all_sensors():
    sensors = []
    for sensor in Sensor.select():
        sensors.append(model_to_dict(sensor))

    return sensors


def get_all_samples():
    samples = []
    for sample in Sample.select():
        samples.append(model_to_dict(sample))

    return samples

def create_sample(svalue, sensor_id):
    Sample.create(value=svalue, sensor_id=sensor_id)

