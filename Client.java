package omkar_18210473;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class Client
{
	private static int portNumber = 5050;
 private Socket socket = null;
 private ObjectOutputStream os = null;
 private ObjectInputStream is = null;
 private Connection Client_action;
 private TempRequest client_loop =null;
 private ServerClientGUI User;
 private ReadTemp readings;
 private boolean Connected = false;
 
 // the constructor expects the IP address of the server - the port is fixed at 5050
 public Client(String serverIP,ServerClientGUI User) {
 	this(serverIP);
 	this.User = User;
 }
 public Client(String serverIP)
 {   
	// this.User = User;
	 this.Client_action = new Connection();
 this.readings = new ReadTemp(0);
 
 
if (!connectToServer(serverIP))
 {
System.out.println("Can't open socket connection. to serverIP..");
 }
 }
 private boolean connectToServer(String serverIP)
 {
try // open a new socket to port: 5050 and create streams
 {
 this.socket = new Socket(serverIP,portNumber);
 this.os = new ObjectOutputStream(this.socket.getOutputStream());
 this.is = new ObjectInputStream(this.socket.getInputStream());
 System.out.print("Connected to Server\n");
 }
 catch (Exception ex)
 {
 System.out.print("Failed to Connect to Server\n" + ex.toString() + portNumber);
 System.out.println(ex.toString());
 return false;
}
return true;
 }


//Send a request to the server
public synchronized Connection Client_req(String sentence, Connection req) {
	Connection ask;
	if (req == null)
		ask = new Connection(sentence);
	else {
		ask = req;
		ask.setrequest(sentence);
	}
	
	System.out.println("01. -> Sending request (" + ask + ") to the server...");
	
	this.send(ask);
	try {
		ask = (Connection)receive();
		
		System.out.println("05. <- The Server responded with: ");
		System.out.println("    <- " + ask);
	} catch (Exception e) {
		System.out.println("XX. There was an invalid object sent back from the server");
	}	

	return ask;
}
 private void getData()
 {
 this.send(this.readings);
 int response = (int) receive();
 System.out.println("The Server Response is " + response);
 }
 // method to send a generic object.
 private void send(Object o)
 {
try
 {
 System.out.println("Sending " + o);
 os.writeObject(o);
 os.flush();
}
 catch (Exception ex)
 {
 System.out.println(ex.toString());
}
 }
 
//Client_req when there is only a string in the request
 public Connection Client_req(String str) {
 	return Client_req(str,null);
 }
 
 // method to receive a generic object.

 private Object receive()
 {
Object o = null;
try
 {
 o = is.readObject();
}
 catch (Exception ex)
 {
 System.out.println(ex.toString());
}
return o;
 }
 public static void main(String args[])
 {
	 System.out.println("**. Java Client Application - EE402 OOP Module, DCU");
if(args.length>0)
{
 Client theApp = new Client(args[0]);
 try
 {
 theApp.getData();
 }
 catch (Exception ex)
 {
 System.out.println(ex.toString());
 }
}
else
{
 System.out.println("Error: you must provide the IP of the server");
 System.exit(1);
}
System.out.println("**. End of Application.");
System.exit(0);
 }
 
 public ServerClientGUI recover_ServerClientGUI() //recover the client
 {
 	return this.User;
 }
public int Sizerecordedtable() {
	// TODO Auto-generated method stub
	return 0;
}
public ArrayList<ReadTemp> recordedtable() {
	// TODO Auto-generated method stub
	return null;
}

public void set_Client_on_loop(TempRequest client_loop) //set the client in the loop
{
	this.client_loop = client_loop;
}

public TempRequest Client_loop() //get the client
{
	return this.client_loop;
}

//Client is connected
public boolean Client_Connected()
{
	return Connected;
}

public Connection Client_request()
{
	return this.Client_action;
}

public void set_Client_request(Connection command)
{
	this.Client_action = command;
}
}
