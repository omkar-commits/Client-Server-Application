package omkar_18210473;

import java.io.*;
import java.util.Date;

public class ReadTemp implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object Temp = null;
	private static int temp_sample_number=0;
	private float temp;
	private Date dateTime;

	public ReadTemp(float Temp) {
		temp_sample_number++; //associate a temp and a sample number
		this.temp = Temp; 
	}
	
	public float Temp() //return the temperature
	{
		return this.temp;
	}

	public void set_Temp(float temp) //set the temperature
	{
		this.temp = temp;
	}
	public Date timeDate() //return the date and time
	{
		return this.dateTime;
	}
	
	
	public void set_time_Date(Date date) //set the date and time
	{
		this.dateTime = date;
	}
	
		
	
	public static void main(String [] args) {

        String fileName = " /sys/class/thermal/thermal_zone0/temp"; // The path to read temperature from Rpi
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                float tempC = (Integer.parseInt(line) / 1000);
                float tempF = ((tempC / 5) * 9) + 32;
                System.out.println("Temp °C: " + tempC + " Temp °F: " + tempF);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
	}}

	
	



	