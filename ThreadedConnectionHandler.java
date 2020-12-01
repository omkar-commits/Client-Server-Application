package omkar_18210473;


import java.net.*;
import java.util.Date;
import java.io.*;

public class ThreadedConnectionHandler extends Thread
{
    private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;	// Output stream
    private Connection connect;
    private DateTimeService theDateService;
    private ReadTemp theTemperatureService;
      
    
	// The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket,DateTimeService theDateService,ReadTemp theTemperatureService) {
    	this.clientSocket = clientSocket;
        this.theDateService = theDateService;
        this.theTemperatureService = theTemperatureService;  
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
        theTemperatureService=new ReadTemp(0);
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void init() 
    {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {}
         }
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() 
    {
    	try {
            this.connect = (Connection)is.readObject();
        } 
        catch (Exception e) {    
        	this.closeSocket();
            return false;
        }
    	
        System.out.println("01. <- Received a String object from"
        					+ " the client (" + connect + ").");

        switch (this.connect.receive_request())
        {
        	case "Temperature_read": this.Temperature_read();
        					send(this.connect);
        					break;
        	
        	default: this.sendError("Error command: " + connect);
	        		break;
        }
      
        return true;
    }
    
    //Read the temperature and the date/time
    private void Temperature_read() {
    	this.connect.receive_sample().set_Temp(theTemperatureService.Temp());
    	this.connect.receive_sample().set_time_Date(this.theDateService.getDateAndTime());
    	
    	
    }

    // Use our custom DateTimeService Class to get the date and time
    private void getDate() {	// use the date service to get the date
        Date currentDateTimeText = theDateService.getDateAndTime();
        this.send(currentDateTimeText);
    }

    // Send a generic object back to the client 
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
    
    // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
        this.send("Error:" + message);	//remember a String IS-A Object!
    }
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
}