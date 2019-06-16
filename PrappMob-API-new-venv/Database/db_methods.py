# from peewee import *
from Database.db_model import Workstation, Sensor, Sample, _get_date
from playhouse.shortcuts import model_to_dict
import time, datetime


def get_workstation(station_id):
    return Workstation.get_by_id(station_id).to_json()


def get_sensor(sensor_id):
    sensor = Sensor.get_by_id(sensor_id)
    return model_to_dict(sensor)


def get_sensor_by_station(station_id):
    sensors = []
    for sensor in Sensor.select().where(Sensor.station_id == station_id):
        sensors.append(model_to_dict(sensor))

    return sensors


def get_sample(sample_id):
    return model_to_dict(Sample.get_by_id(sample_id))


def put_sample(sample_id, s_value, s_date=_get_date()):  # method for editing
    sample_to_update = Sample.select().where(Sample.sample_id == sample_id).get()
    sample_to_update.value = s_value
    sample_to_update.timestamp = s_date
    sample_to_update.save()


def create_sample(s_value, sensor_id, s_date=_get_date()):

    if s_date is None or s_date == "":
        s_date = _get_date()

    Sample.create(value=s_value, sensor_id=sensor_id, timestamp=s_date)


def get_sample_by_sensor(sensor_id):
    samples = []
    for sample in Sample.select().where(Sample.sensor_id == sensor_id):
        samples.append(model_to_dict(sample))

    return samples


def filter_samples(date_from=datetime.datetime(1970, 1, 1, 0, 0).strftime("%Y-%m-%dT%H:%M"), date_to=_get_date(), workstation_id=-1, sensor_id=-1):
    samples = []

    if date_from == "" or date_from is None:
        date_from = datetime.datetime(1970, 1, 1, 0, 0).strftime("%Y-%m-%dT%H:%M")

    if date_to == "" or date_to is None:
        date_to = _get_date()

    if sensor_id != -1:
        for sample in Sample.select().where((Sample.timestamp >= date_from) &
                                            (Sample.timestamp <= date_to) &
                                            (Sample.sensor_id == sensor_id)):
            samples.append(model_to_dict(sample))

    elif sensor_id == -1 and workstation_id != -1:

        # sq_sensors = Sensor.select(Sensor.sensor_id).where(Sensor.station_id == workstation_id)
        # (Sample.sensor_id__in == sq_sensors)

        sq = Sample.select().join(Sensor, on=(Sample.sensor_id == Sensor.sensor_id)).where((Sample.timestamp >= date_from) & (Sample.timestamp <= date_to) & (Sensor.station_id == workstation_id))

        print(sq.sql()[0])

        for sample in sq:
            samples.append(model_to_dict(sample))

    elif sensor_id == -1 and workstation_id == -1:
        for sample in Sample.select().where((Sample.timestamp >= date_from) &
                                            (Sample.timestamp <= date_to)):
            samples.append(model_to_dict(sample))

    print(str(samples))

    return samples
