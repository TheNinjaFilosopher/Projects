// TO DO: add your implementation and JavaDocs.
/**
 * The class representing a time with two integer components: an hour within [0,23] and a minute within [0,59].
 * @author Logan France
 *
 */
public class MyTime implements Comparable<MyTime> {

	// Hour and minute of a time.
	/**
	 * The hour of the time.
	 */
	private int hour;
	/**
	 * The minutes of the time.
	 */
	private int min;
	
	/**
	 * A constructor that initializes time to be 00:00.
	 */
	public MyTime(){
		// Constructor
		// initialize time to be 00:00
		this(0,0);
	}
	/**
	 * A constructor that takes an hour and initializes the time to be the given hour and 0 for the minutes.
	 * @param hour The hour of the time.
	 */
	public MyTime(int hour){
		// Constructor with hour specified
		// initialize time to be hour:00
		
		// A valid hour can only be within [0, 23].
		// For an invalid hour, throw IllegalArgumentException.
		// Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		// "Hour must be within [0, 23]!"
		this(hour, 0);

	}
	/**
	 * The constructor takes an hour and minutes, then initializes the time with those values.
	 * @param hour The hour of the time.
	 * @param min The minutes of the time.
	 */
	public MyTime(int hour, int min){
		// Constructor with hour and minutes specified
		// initialize time to be hour:minute

		// A valid hour can only be within [0, 23].
		// A valid minute can only be within [0, 59].

		// For an invalid hour / minute, throw IllegalArgumentException.
		// Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		// "Hour must be within [0, 23]; Minute must be within [0, 59]!");
		if(hour<0||hour>23||min<0||min>59)
			throw new IllegalArgumentException("Hour must be within [0, 23]; Minute must be within [0, 59]!");
		this.hour = hour;
		this.min = min;
	}
	/**
	 * Returns the hour.
	 * @return The hour.
	 */
	public int getHour(){
		// report hour
		
		return hour; //default return, remove/change as needed
	}
	/**
	 * Returns the minutes.
	 * @return The minutes.
	 */
	public int getMin(){
		// report minute
		
		return min; //default return, remove/change as needed
	}
	
	/**
	 * Compares two times to each other based on the hours and minutes of the times, then returns an integer based on the result.
	 * @param otherTime The other time being compared to this time.
	 * @return negative 1 if this time is less than the other, 0 if they are equal, and 1 if this time is greater than the other.
	 */
	@Override 
	public int compareTo(MyTime otherTime){
		// compare two times for ordering
		// return the value 0 if the argument Time has the same hour and minute of this Time;
		// return a value less than 0 if this Time is before the Time argument; 
		// return a value greater than 0 if this Time is after the Time argument.
		if(getHour() == otherTime.getHour()) {
			if(getMin()==otherTime.getMin())
				return 0;
			else if (getMin()<otherTime.getMin())
				return -1;
			else
				return 1;
		}
		else if(getHour()<otherTime.getHour())
			return -1;
		else
			return 1;
	}
	
	/**
	 * Returns a duration based on an inputted end time.
	 * @param endTime The inputted end time.
	 * @return The duration in minutes.
	 */
	public int getDuration(MyTime endTime){
		// return the number of minutes starting from this Time and ending at endTime
		// return -1 if endTime is before this Time
		
		if(this.compareTo(endTime)==0)
			return 0;
		else if (this.compareTo(endTime)>0)
			return -1;
		int hour = endTime.getHour()-getHour();
		int minutes = endTime.getMin()-getMin();
		int ans = (hour*60)+minutes;
		return ans; //default return, remove/change as needed		
	}
	
	/**
	 * Returns an end time based on the duration.
	 * @param duration The inputted duration.
	 * @return An end time based on the duration.
	 */
	public MyTime getEndTime(int duration){
		// return a Time object that is duration minute from this Time
		
		// Throw IllegalArgumentException if duration is negative. 
		// Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		// "Duration must be non-negative!"			
		
		// return null if endTime passes 23:59 given this Time and duration argument
		if(duration<0)
			throw new IllegalArgumentException("Duration must be non-negative!");
		int ans = (getHour()*60)+getMin();
		ans+=duration;
		int newHour = (int) Math.floor(ans/60);
		if(newHour>23)
			return null;
		int newMin = ans%60;
		return new MyTime(newHour, newMin); //default return, remove/change as needed	
	}
	
	/**
	 * Returns a String representation of the object in the form of hh:mm.
	 * @return A String representation of the object in the form of hh:mm.
	 */
	public String toString() {
		// return a String representation of this object in the form of hh:mm
		// hh is the hour of the time (00 through 23), as two decimal digits
		// mm is the minute of the time (00 through 59), as two decimal digits
		
		// Hint: String.format() can be helpful here...
		String ans = String.format("%02d:%02d", getHour(),getMin());
		return ans; //default return, remove/change as needed		
	}
	/**
	 * The main method where the tests are done.
	 * @param args The arguments passed in. Not used for this class.
	 */
	public static void main(String[] args){
		//This method is provided for testing 
		//(use/modify as much as you'd like)

		//time objects
		MyTime time1 = new MyTime(7);
		MyTime time2 = new MyTime(9,30);
		
		//checking hour/minute
		if (time1.getHour() == 7 && time1.getMin() == 0 && time2.getHour() == 9
			&& time2.getMin() == 30){
			System.out.println("Yay 1");			
		}		
	
		//compareTo, duration
		if (time1.compareTo(time2) < 0 && time1.compareTo(new MyTime(7,0)) == 0
			&& time2.compareTo(time1) > 0 && time1.getDuration(time2) == 150){
			System.out.println("Yay 2");						
		}
		
		//getEndTime
		MyTime time3 = time1.getEndTime(500);
		if (time3!=null && time3.getHour() == 15 && time3.getMin() == 20 
			&& time2.getEndTime(870) == null){
			System.out.println("Yay 3");								
		}
		
	}
}