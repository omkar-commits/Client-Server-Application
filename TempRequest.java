package omkar_18210473;

public class TempRequest extends Thread
{
	private Client aclient;
	private int time_Step;
	private boolean start;
	

	public TempRequest(int time_step, Client Aclient) //constructor: initialize the client, time step and start
	{
		this.start = true;
		this.time_Step = time_step;
		this.aclient = Aclient;
	}
	
	
	public void start_loop() //start the loop and record the value of the temperature
							//This value is stocked in the table
	{
		Connection request;
		while (start)
		{
			try {
			request = aclient.Client_req("Temperature_read");	
			aclient.recover_ServerClientGUI().display_temperature("temperature", request);
				
			
				if (aclient.Sizerecordedtable() == 20)
					this.aclient.recordedtable().remove(0);	
				
				this.aclient.recordedtable().add(request.receive_sample());
				Thread.sleep(this.time_Step);
				
			} catch (Exception e) {
				this.start = false;
			}
		}
	}
	
	
	public void end_of_Loop() //stop the loop just by changing the boolean start
	{
		this.start = false;
	}
}