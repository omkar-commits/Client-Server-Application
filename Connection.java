package omkar_18210473;

import java.io.Serializable;



public class Connection implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	private String user_ask;
	private ReadTemp asample;
	
	
	//get and return the request of the user
	public String receive_request() 
	{
		return this.user_ask;
	}
	
	//get and return the sample
	public ReadTemp receive_sample() 
	{
		return this.asample;
	}

	
	public void setrequest(String request) //set the request
	{
		this.user_ask = request;
	}
	
	public Connection() //constructor
	{
		this.asample = new ReadTemp(0.0f);
		
	}
	
	public Connection(String request) 
	{
		this();
		this.user_ask = request;
	}
	
	
}