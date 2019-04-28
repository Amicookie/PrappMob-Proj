from .db_model import Workstation, Sensor, Sample, _get_date
from playhouse.shortcuts import model_to_dict


def insert_data():
    workstations = [
        {'station_name': 'Station1',
         'station_description': 'Station1 Description'},
        {'station_name': 'Station2',
         'station_description': 'Station2 Description'},
        {'station_name': 'Station3',
         'station_description': 'Station3 Description'},
        {'station_name': 'Station4',
         'station_description': 'Station4 Description'},
        {'station_name': 'Station5',
         'station_description': 'Station5 Description'}
    ]

    Workstation.insert_many(workstations).execute()

    sensors = [
        {'sensor_name': 'Sensor1',
         'sensor_description': 'Sensor1 Description',
         'station_id': 1},
        {'sensor_name': 'Sensor2',
         'sensor_description': 'Sensor2 Description',
         'station_id': 2},
        {'sensor_name': 'Sensor3',
         'sensor_description': 'Sensor3 Description',
         'station_id': 3},
        {'sensor_name': 'Sensor4',
         'sensor_description': 'Sensor4 Description',
         'station_id': 4},
        {'sensor_name': 'Sensor5',
         'sensor_description': 'Sensor5 Description',
         'station_id': 5},
        {'sensor_name': 'Sensor6',
         'sensor_description': 'Sensor6 Description',
         'station_id': 5},
        {'sensor_name': 'Sensor7',
         'sensor_description': 'Sensor7 Description',
         'station_id': 4}
    ]

    Sensor.insert_many(sensors).execute()

    samples = [
        {'value': 0.1,
         'timestamp': _get_date(),
         'sensor_id': 7},
        {'value': 0.2,
         'timestamp': _get_date(),
         'sensor_id': 6},
        {'value': 0.3,
         'timestamp': _get_date(),
         'sensor_id': 5},
        {'value': 0.4,
         'timestamp': _get_date(),
         'sensor_id': 4},
        {'value': 0.5,
         'timestamp': _get_date(),
         'sensor_id': 3},
        {'value': 0.6,
         'timestamp': _get_date(),
         'sensor_id': 2},
        {'value': 0.7,
         'timestamp': _get_date(),
         'sensor_id': 1},
        {'value': 0.8,
         'timestamp': _get_date(),
         'sensor_id': 1},
        {'value': 0.9,
         'timestamp': _get_date(),
         'sensor_id': 2},
        {'value': 1.099999,
         'timestamp': _get_date(),
         'sensor_id': 3}
    ]

    Sample.insert_many(samples).execute()

    print("workstations, sensors, samples added!")


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


# get_all_files()
# get_all_users()
