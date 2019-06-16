import datetime
from app import prappmob_db
from peewee import *


def _get_date():
    return datetime.datetime.now().strftime("%Y-%m-%dT%H:%M")


class base_model(Model):
    class Meta:
        database = prappmob_db


class Workstation(base_model):
    station_id = AutoField(primary_key=True)
    station_name = CharField(null=False, unique=True)
    station_description = CharField(null=True)

    def to_json(self):
        return dict(station_id=self.station_id,
                    station_name=self.station_name,
                    station_description=self.station_description)


class Sensor(base_model):
    sensor_id = AutoField(primary_key=True)
    sensor_name = CharField(null=False, unique=True)
    sensor_description = TextField(null=True)
    station_id = SmallIntegerField(null=False)
        #ForeignKeyField(Workstation, field='station_id', null=False)  #sensor musi byc przypisany do station -- mozna to zmienic!
    # #

    def to_json(self):
        return dict(sensor_id=self.sensor_id,
                    sensor_name=self.sensor_name,
                    sensor_description=self.sensor_description,
                    station_id=self.station_id)


class Sample(base_model):
    sample_id = AutoField(primary_key=True)
    value = FloatField(null=False, default=0.0)
    timestamp = DateTimeField(null=False, default=_get_date())
    sensor_id = SmallIntegerField(null=False)
    #ForeignKeyField(Sensor, field='sensor_id', null=False)  #sample musi byc przypisany do sensor -- mozna to zmienic!
    #

    def to_json(self):
        return dict(sample_id=self.sample_id,
                    value=self.value,
                    timestamp=self.timestamp,
                    sensor_id=self.sensor_id)

# ti_db.connect()
# ti_db.create_tables([User, File])
