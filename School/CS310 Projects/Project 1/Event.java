// TO DO: add your implementation and JavaDocs.
/**
 * Class that implements an event with a starting time, an ending time, and a description.
 * @author Logan France
 *
 */
public class Event implements Comparable<Event> {

	//starting and ending time of the event
	/**
	 * Start time of the event.
	 */
	private MyTime startTime;
	/**
	 * End time of the event.
	 */
	private MyTime endTime;
		
	//description of the event
	/**
	 * Description of the event.
	 */
	private String description;
	/**
	 * The constructor for the event. Sets the description to be blank.
	 * @param startTime The starting time of the event.
	 * @param endTime The ending time of the event.
	 */
	public Event(MyTime startTime, MyTime endTime){
		// constructor with start and end times
		// set description to be empty string ""
		
		// Throw IllegalArgumentException if endTime comes before startTime
		// - Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		//        "End Time cannot come before Start Time!"
		// - Assume that the start time can be the same as the end time 
		//   (0-duration event allowed)
		
		this(startTime,endTime, "");
				
	}
	/**
	 * The constructor for the event. Includes a custom description.
	 * @param startTime The starting time of the event.
	 * @param endTime The ending time of the event.
	 * @param description The description of the event.
	 */
	public Event(MyTime startTime, MyTime endTime, String description){
		// constructor with start time, end time, and description
		
		// perform the same checking of start/end times and 
		// throw the same exception as the constructor above
		
		// if description argument is null, 
		// set description of the event to be empty string ""
		if(startTime.getDuration(endTime)<0)
			throw new IllegalArgumentException("End Time cannot come before Start Time!");
		
		this.startTime=startTime;
		this.endTime=endTime;
		if(description == null)
			this.description="";
		else
			this.description = description;
	}
	/**
	 * Returns the starting time.
	 * @return The starting time.
	 */
	public MyTime getStart(){
		// report starting time

		return startTime; //default return, remove/change as needed
	}
	/**
	 * Returns the ending time.
	 * @return The ending time.
	 */
	public MyTime getEnd(){
		// report starting time

		return endTime; //default return, remove/change as needed
	}
	/**
	 * Returns the description.
	 * @return The description.
	 */
	public String getDescription(){
		// report description
		
		return description; //default return, remove/change as needed
	}
	
	/**
	 * Compares two events to each other based on the start time of the events, then returns an integer based on the result.
	 * @return negative 1 if this event is less than the other, 0 if they are equal, and 1 if this event is greater than the other.
	 */
	@Override
	public int compareTo(Event otherEvent){
		// compare two times for ordering
		
		// The ordering of two events is the same as the ordering of their start times
		
		
		return this.getStart().compareTo(otherEvent.getStart()); //default return, remove/change as needed

	}

	/**
	 * Changes the current start time to the input start time.
	 * @param newStart The new start time.
	 * @return True if the start could be moved, False if not.
	 */
	public boolean moveStart(MyTime newStart){
		// Move the start time of this Event to be newStart but keep the same duration. 
		// - Remember to update the end time to ensure duration unchanged.
		
		// The start time can be moved forward or backward but the end time cannot 
		// go beyond 23:59 of the same day.  Do not update the event if this condition
		// cannot be satisfied and return false.  
		// Return true if the start time can be moved to newStart successfully.
		
		// Note: a false return value means the specified newStart can not be used 
		//       for the current event.  Hence if newSart is the same as the current 
		//       start, we will still return true.
		
		int duration = getStart().getDuration(getEnd());
		
		if(newStart.getEndTime(duration).compareTo(new MyTime(23,59))>0)
			return false;
		
		this.startTime = newStart;
		endTime = newStart.getEndTime(duration);
		return true; //default return, remove/change as needed
	}
	/**
	 * Changes the current duration to be the given duration in minutes.
	 * @param minute The amount of minutes the new duration is.
	 * @return True if the duration could be changed, false if not.
	 */
	public boolean changeDuration(int minute){
		// Change the duration of event to be the given number of minutes.
		// Update the end time of event based on the updated duration.	
			
		// The given minute cannot be negative; and the updated end time cannot go 
		// beyond 23:59 of the same day.  Do not update the event if these conditions
		// cannot be satisfied and return false.  
		// Return true if the duration can be changed.
		
		// Note: a false return value means the specified duration is invalid for some 
		// 		 reason.  Hence if minute argument is the same as the current duration, 
		//       we will still return true.
		
		if(minute<0||getStart().getEndTime(minute).compareTo(new MyTime(23,59))>0)
			return false;
		this.endTime = getStart().getEndTime(minute);
		return true; //default return, remove/change as needed
	
	}
	
	/**
	 * Changes the description to the given one.
	 * @param newDescription The new description.
	 */
	public void setDescription(String newDescription){
		// set the description of this event

		// if newDescription argument is null, 
		// set description of the event to be empty string ""
		if(newDescription == null)
			this.description = "";
		else
			this.description = newDescription;
	}
	
	/**
	 * Returns a string representation of the event.
	 * @return A string representation of the event.
	 */
	public String toString(){
		// return a string representation of the event in the form of
		// startTime-endTime/description
		// example: "06:30-07:00/breakfast"

		// Hint: String.format() can be helpful here...
		
		// The format of start/end times is the same as .toString() of MyTime
		String ans = String.format("%s-%s/%s", getStart().toString(), getEnd().toString(), getDescription());

		return ans; //default return, remove/change as needed
	
	}
	/**
	 * The main method where the tests are done.
	 * @param args The arguments passed in. Not used for this class.
	 */
	public static void main(String[] args){
		// creating an event
		Event breakfast = new Event(new MyTime(7), new MyTime(7,30), "breakfast");
		
		// checking start/end times
		if (breakfast.getStart()!=null && breakfast.getEnd()!=null &&
			breakfast.getStart().getHour() == 7 && breakfast.getEnd().getHour() == 7 && 
			breakfast.getStart().getMin() == 0 && breakfast.getEnd().getMin() == 30){
			System.out.println("Yay 1");			
		}		
		//System.out.println(breakfast);
		//expected output (excluding quote):
		//"07:00-07:30/breakfast"

		// moveStart
		if (breakfast.moveStart(new MyTime(6,30)) && breakfast.getStart().getHour() == 6
			&& breakfast.getStart().getMin() == 30 && breakfast.getEnd().getMin() == 0){
			System.out.println("Yay 2");					
		}
		//System.out.println(breakfast);
		
		//longer duration
		if (breakfast.changeDuration(45) && breakfast.getStart().getHour() == 6
			&& breakfast.getStart().getMin() == 30 && breakfast.getEnd().getMin() == 15
			&& breakfast.getEnd().getHour() == 7){

			System.out.println("Yay 3");					
		}
		//System.out.println(breakfast);
		
		//shorter duration
		if (!breakfast.changeDuration(-10) && breakfast.changeDuration(15) 
			&& breakfast.getStart().getHour() == 6 && breakfast.getStart().getMin() == 30 
			&& breakfast.getEnd().getMin() == 45 && breakfast.getEnd().getHour() == 6){
			System.out.println("Yay 4");					
		}
		//System.out.println(breakfast);
		
		// compareTo
		Event jogging = new Event(new MyTime(5), new MyTime(6), "jogging");
		Event morningNews = new Event(new MyTime(6, 30), new MyTime(7), "morning news");
		if (breakfast.compareTo(jogging)>0 && jogging.compareTo(breakfast)<0
			&& breakfast.compareTo(morningNews) == 0){
			System.out.println("Yay 5");								
		}
	}

}