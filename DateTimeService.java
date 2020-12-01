package omkar_18210473;

import java.util.Calendar;
import java.util.Date;

//get the date for samples
public class DateTimeService
{
   //return date or time
   public Date getDateAndTime()
   {
	 Date date = Calendar.getInstance().getTime();
	 return date;	
   }	
}