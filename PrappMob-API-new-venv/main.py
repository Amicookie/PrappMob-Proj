from app import app, os, prappmob_db
from Database.db_model import Workstation, Sensor, Sample
from Database.db_generator import insert_data
from Database.db_generator import get_all_workstations
from app import socketio

if __name__ == '__main__':
    if not os.path.exists(app.config['DATABASE']):
        prappmob_db.connect()
        prappmob_db.create_tables([Workstation, Sensor, Sample])
        insert_data()
        get_all_workstations()
    #app.run(host="192.168.0.23", port=5000, debug=True)
    socketio.run(app, host="192.168.43.160", port=5000, debug=True)
    # socketio.run(app, port=5000, debug=True)