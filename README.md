# Client-Server-Application
client/server application for temperature sensing. The temperature sensor used in this application is the built in CPU temperature my raspberry pi. The path to read the CPU temperature by embedded SBC is (/ sys /class /thermal/ thermal_zone0/temp) by embedded SBC. The data of temperature is measured and displayed in the form of graph. Once the temperature is sensed the client device connects to server and pass the data to store. The stored temperature can be checked in the history of data, at the max 20 inputs from the client can be viewed in history of temperature data. Initially this process was tested using single client.   The application is designed to handle multiple clients in threaded format. The server should be able to process and handle multiple clients. Along with temperature current date and time data is also shared by the client to server. 

Following are the classes used to run the application to read the data from raspberry pi and sent data from multiple clients to server. 
1. Client
2. ThreadedServer (To get data from multiple clients)
3. ThreadedConnectionHandler (To maintain connection)
4. DateTimeService  (To get Date and time)
5. ServerClientGUI  (GUI for application)
6. ReadTemp (To read the data from Rpi)
7. TempRequest  (To initialize Client to temperature step and start)
8. Graphdisp  (To draw the graph of temperature and date-time) 

To start at this stage the design of object class “ReadTemp” is used to sense the temperature data from the CPU of raspberry pi 3 which can be achieved using path (/ sys /class /thermal/ thermal_zone0/temp). The temperature sensed is displayed in Celsius and Fahrenheit if the application is unable to read the temperature it must throw an error that unable to open temperature file or error while reading the file. The Data for Date and time is set and sent using class “DateTimeService”. The data that displays are Temperature, Data and time using the Graph class. The Graph is plot in the form temperature against time. 

The next Class to design is “TempRequest” and it is initializing the Client and time step. The loop is used to record the value of temperature and all the values are stacked in table. Once the temperature is recorded loop can be stopped using Boolean start. 

The next part after the “TempRequest” class is to design the “Client” class. The client class is responsible to connect to the server with the help of IP address. The IP address is specified by command line argument which is used to start the program. The Client class expects the IP address of the server and the port is fixed at -> 5050. The data of temperature to the server is sent in the form of object. At a time one object can be passed to server, the client must wait for specified time to send the next data. 

The “ThreadedServer” class is designed after the Client class. The class is designed to listen the data from multiple clients. If the client is no longer sharing data, the server is automatically goes to shut down state. The server starts listening to fixed post number that is “5050”. When the server can connect the clients at once it uses “ThreadedConnectionHandler” to use multithreading. Once the Connection is established between the client and server the ThreadedConnectionHandler class will perform all operations. 

To design “ThreadedConnectionHandler” class we must understand its main role i.e to interact with the client objects once the connection between client and server is established. This class must be able to store the last 20 values passed from the client. This recoded data should be able to be updated by ClientGUI. 
The next class is the final step to design the the graphical user interface class “ServerClientGUI” for the application. The GUI is designed using the Java built in library Swing. The Class should be able to display the graphed data of temperature along with Date and Time. The Class extends Jframe to take data (IP address) from user. The GUI class will be initialized as soon as the object is sent from client to server .i.e. as soon as the server program is started.

The ServerClientGUI class allow to display an interface helping the client to communicate with the server. The GUI is built using Swing and AWT.
