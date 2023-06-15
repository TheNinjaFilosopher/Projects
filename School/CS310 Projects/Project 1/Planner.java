// TO DO: add your implementation and JavaDocs.
/**
 * The implementation of a day planner. It stores a collection of events in ascending order of their starting times. 
 * The planner supports multiple operations for maintenance, including adding a new event, deleting an event, and updating an event.
 * @author Logan France
 *
 */
public class Planner{

	// DO NOT MODIFY INSTANCE VARIABLES PROVIDED BELOW
	
	//underlying array of events  -- you MUST use this for credit!
	//Do NOT change the name or type
	/**
	 * The array of events.
	 */
	private MySortedArray<Event> events;
	
	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!
	/**
	 * A constructor that initializes the events array with the default capacity of 2.
	 */
	public Planner(){
		// Constructor with no arguments.
		
		// A list of events should be created.  The initial capacity should be 
		// DEFAULT_CAPACITY defined in our MySortedArray class. 
		// The list should be empty (with no events).
		events = new MySortedArray<>();
	}
	/**
	 * Returns the number of events in the list.
	 * @return The number of events in the list.
	 */
	public int size(){
		// return the number of events in the list.
		// O(1)
		
		return events.size();
	}
	/**
	 * Returns a string representation of the planner in a specific format.
	 * @return The string representation of the planner.
	 */
	public String toString(){
		// return the string representation of the planner with this format:
		// - include all events in the list in ascending order of the indexes;
		// - each event goes into a separate line;
		// - each line except for the last uses this format (quotes excluded): "[index]event\n"
		// - the last line does not end with a new line and uses this format: "[index]event"

		// The format of an event is the same as .toString() of Event class

		// Hint: String.format() can be helpful here...
		
		// Note: use StringBuilder instead of String concatenation for a better efficiency 
		StringBuilder ans = new StringBuilder();
		
		for(int i = 0;i<size();i++) {
			ans.append("["+i+"]"+events.get(i));
			if(i!=size()-1)
				ans.append("\n");
		}
		
		return ans.toString(); //default return, remove/change as needed
	}
	/**
	 * Adds a new event to the planner.
	 * @param event The event being added to the planner.
	 */
	public void addEvent(Event event){
		
		// Add a new event into the list
		//	- make sure events are sorted after addition
		
		// O(N) where N is the number of events in the list
		events.add(event);
	}
	/**
	 * Moves the event at the index to start at a new time.
	 * @param index The index of the event being changed.
	 * @param newStart The new start time of the event.
	 * @return True if the event can be updated, False if not.
	 */
	public boolean moveEvent(int index, MyTime newStart){
		// Move the event at index to be start at newStart.
		// Hint: we will keep the same duration but the end time may need to be changed.
		
		// Return true if event can be updated; return false otherwise.
		// - return false for an invalid index
		// - return false if event cannot be moved to newStart
		
		// If with the updated starting time, the events are still sorted in ascending 
		// order of their starting times, do not change the index of the event.
		// Otherwise, fix the ordering by first removing the updated event, 
		// then adding it back.

		// O(N) where N is the number of events currently in list
		if(index<0||index>events.size()-1)
			return false;
		
		if(!events.get(index).moveStart(newStart))
			return false;
		if(index==0 && size()>1) {
			if(events.get(0).compareTo(events.get(1))>0) {
				Event e = events.delete(index);
				events.add(e);
				return true;
			}
		}
		else if (index == size()-1) {
			if(events.get(index).compareTo(events.get(index-1))<0) {
				Event e = events.delete(index);
				events.add(e);
				return true;
			}
		}
		else {
			if(events.get(index).compareTo(events.get(index+1))>0||events.get(index).compareTo(events.get(index-1))<0) {
				Event e = events.delete(index);
				events.add(e);
				return true;
			}
		}
		
		return true;
	}
	
	/**
	 * Changes the duration of the event at the given index to the new given duration.
	 * @param index The index of the event being changed.
	 * @param minute The new duration in minutes.
	 * @return True if the duration can be changed, false if not.
	 */
	public boolean changeDuration (int index, int minute){
		// Change the duration of event at index to be the given number of minutes.
		
		// Return true if the duration can be changed.
		// Return false if:
		// - index is invalid; or
		// - minute is negative; or
		// - the duration of event at index can not be updated with the specified minute

		// O(1)
		if(index<0||index>events.size()-1)//the changeDuration() method in Event should handle the rest.
			return false;
		
		return events.get(index).changeDuration(minute);
		
		//return false; //default return, remove/change as needed
	
	}

	/**
	 * Changes the description of the event at the given index.
	 * @param index The index of the event whose description is being changed.
	 * @param description The new description.
	 * @return True if the description can be changed, false if not.
	 */
	public boolean changeDescription(int index, String description){
		// Change the description of event at index.
		
		// Return true if the event can be changed.
		// Return false for an invalid index.
		
		// O(1)
		
		if(index<0||index>events.size()-1)
			return false;
		
		events.get(index).setDescription(description);
		return true; //default return, remove/change as needed
	}
	
	/**
	 * Removes the event at the given index.
	 * @param index The given index.
	 * @return True if the event can be removed, False if not.
	 */
	public boolean removeEvent(int index){
		// Remove the event at index.
		
		// Return true if the event can be removed
		// Return false for an invalid index.
		
		// O(N) where N is the number of elements currently in the storage
		if(index<0||index>events.size()-1)
			return false;
		events.delete(index);
		return true; //default return, remove/change as needed
	}
	
	/**
	 * Returns the event at the given index.
	 * @param index The given index.
	 * @return The event at the given index.
	 */
	public Event getEvent(int index){
		// Return the event at index
		
		// Return null for an invalid index.
		
		//O(1)
		if(index<0||index>events.size()-1)
			return null;
		return events.get(index); //default return, remove/change as needed
	}
	/**
	 * The main method where the tests are done.
	 * @param args The arguments passed in. Not used for this class.
	 */
	public static void main(String[] args){
	
		// creating a planner
		Planner day1 = new Planner();

		// adding two events		
		Event breakfast = new Event(new MyTime(7), new MyTime(7,30), "breakfast");
		Event jogging = new Event(new MyTime(5), new MyTime(6), "jogging");
		day1.addEvent(breakfast);
		day1.addEvent(jogging);
		
		if (day1.size()==2 && day1.getEvent(0)==jogging && day1.getEvent(1)==breakfast ){
			System.out.println("Yay 1");					
		}
		
		//toString
		if (day1.toString().equals("[0]05:00-06:00/jogging\n[1]07:00-07:30/breakfast")){
			System.out.println("Yay 2");							
		}
		//System.out.println(day1);

		// move start of breakfast		
		MyTime newBFTime = new MyTime(6,30);
		
		if (day1.moveEvent(1, newBFTime) && day1.getEvent(1).getStart().getHour() == 6
			&& day1.getEvent(1).getStart().getMin() == 30){
			System.out.println("Yay 3");								
		}
		//System.out.println(day1);
		
		// change duration
		if (day1.changeDuration(0, 45) && day1.getEvent(0).getEnd().getHour() == 5 
			&& day1.getEvent(0).getEnd().getMin() == 45 && day1.changeDuration(1, 60)
			&& day1.getEvent(1).getEnd().getHour() == 7 
			&& day1.getEvent(1).getEnd().getMin() == 30){
			System.out.println("Yay 4");											
		}
		//System.out.println(day1);
		
		// change description, remove
		
		if (day1.changeDescription(1, "sleeping") && !day1.removeEvent(3) 
			&& !day1.removeEvent(-2) && day1.removeEvent(0)){
			System.out.println("Yay 5");							
		}
		//System.out.println(day1);
		
	}
}