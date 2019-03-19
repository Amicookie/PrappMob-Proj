DROP TABLE IF EXISTS sample;
DROP TABLE IF EXISTS sensor;
DROP TABLE IF EXISTS workstation;

CREATE TABLE workstation (
    station_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name CHAR(32) NOT NULL,
    description TEXT
);
CREATE TABLE sensor (
    sensor_id INTEGER PRIMARY KEY AUTOINCREMENT,
    station_id INTEGER, 
    name CHAR(32) NOT NULL,
    description TEXT,
    CONSTRAINT fk_station_id
        FOREIGN KEY (station_id)
        REFERENCES workstation(station_id)
);
CREATE TABLE sample (
    sample_id INTEGER PRIMARY KEY AUTOINCREMENT,
    sensor_id INTEGER,
    value REAL NOT NULL,
    CONSTRAINT fk_sensor_id
        FOREIGN KEY (sensor_id)
        REFERENCES sensor(sensor_id)
);