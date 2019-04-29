import datetime
from app import prappmob_db
from peewee import Model, AutoField, CharField, TextField, SmallIntegerField, FloatField, DateTimeField


def _get_date():
    return datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")


class BaseModel(Model):
    class Meta:
        database = prappmob_db


class Workstation(BaseModel):
    station_id = AutoField(primary_key=True)
    station_name = CharField(null=False, unique=True)
    station_description = CharField(null=True)

    def to_json(self):
        return dict(station_id=self.station_id,
                    station_name=self.station_name,
                    station_description=self.station_description)


class Sensor(BaseModel):
    sensor_id = AutoField(primary_key=True)
    sensor_name = CharField(null=False, unique=True)
    sensor_description = TextField(null=True)
    station_id = SmallIntegerField(null=False)

    def to_json(self):
        return dict(sensor_id=self.sensor_id,
                    sensor_name=self.sensor_name,
                    sensor_description=self.sensor_description,
                    station_id=self.station_id)


class Sample(BaseModel):
    sample_id = AutoField(primary_key=True)
    value = FloatField(null=False, default=0.0)
    timestamp = DateTimeField(null=False, default=_get_date())
    sensor_id = SmallIntegerField(null=False)

    def to_json(self):
        return dict(sample_id=self.sample_id,
                    value=self.value,
                    timestamp=self.timestamp,
                    sensor_id=self.sensor_id)
