# PrappMob-Proj
## Installation
Local API requires  [Python 3.7.](https://www.python.org/) to run.
WebApp requires [Node.js](https://nodejs.org/), [Angular CLI](https://cli.angular.io/) to run.
AndroidApp requires [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) to run.
##### AndroidApp runs on Android SDK Version 28 and above.
#

Using following IDE to develop: [PyCharm](https://www.jetbrains.com/pycharm/), [Visual Studio Code](https://code.visualstudio.com/), [Android Studio](https://developer.android.com/studio/).

### API
JSON-based requests are handled by API set on [Heroku](https://www.heroku.com/).
You can run API locally (that requires ip configuration and changing variables in the code) for debugging purposes.

Local API requires following frameworks to start: 

#### [ORM DB] peewee
```sh
$ pip install peewee
```

#### [REST API & SOCKET SERVER] Flask
```sh
$ pip install flask
$ pip install flask-cors
$ pip install flask-restful
$ pip install flask-jsonpify
$ pip install flask-socketio
```

#### Running API 
##### IP configuration - Internet connection required!
#
> On Windows
- Run Command Prompt
- Execute command:
```sh
    C:\Users\User> ipconfig
    Wireless LAN adapter Wi-Fi:
        IPv4 Address. . . . . . . . . . . : 192.168.1.105
```
- Get IPv4 Address and include it in main.py file in line 14:
```sh
    socketio.run(app, host="192.168.1.105", port=5000, debug=True)
```

##### Run API
#
> Without database included 
- Right click on app.py -> run
- Right click on main.py -> run
- Server runs @ http://{IPv4 Address}:5000
> With database included 
- Right click on main.py -> run
- Server runs @ http://{IPv4 Address}:5000

#### Running WebApp
##### IP configuration - if local API is set!
#
- In environment.ts file change ws_url variable to "http://{IPv4Address}/"

##### Run WebApp
#
> Check installed Node.js and AngularCLI version in terminal:
```sh
    C:\Users\User> node -v
    C:\Users\User> npm -v
    C:\Users\User> ng v
```
- If above-mentioned commands are not working, check PATH environment variable
> Terminal commands:
- Install dependencies
```sh
    ...\Angular-TI-v2> npm install --save rxjs-compat
    ...\Angular-TI-v2> npm install ng6-socket-io
    ...\Angular-TI-v2> npm install jquery --save-dev
```
- Run WebApp:
```sh
    ...\Angular-TI-v2> ng serve
```
- WebApp runs @ http://localhost:4200/

#### Running AndroidApp
##### IP configuration - if local API is set!
#
- In SplashActivity.java file:
- Change public static final String url variable to "http://{IPv4Address}:5000"

##### Run AndroidApp
#
> Use Android Emulator with Antroid SDK Version 28 and above to run.
- App will work only if you have valid Internet connection.

