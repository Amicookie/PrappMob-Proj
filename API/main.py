from app import app, os, prappmob_db

# from app import socketio

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
    # socketio.run(app, host="192.168.43.218", port=5000, debug=True)
    # socketio.run(app, port=5000, debug=True)