/* This is the only file you will be editing.
 * - Copyright of Starter Code: Prof. Kevin Andrea, George Mason University.  All Rights Reserved
 * - Copyright of Student Code: You!  
 * - Restrictions on Student Code: Do not post your code on any public site (eg. Github).
 * -- Feel free to post your code on a PRIVATE Github and give interviewers access to it.
 * -- You are liable for the protection of your code from others.
 * - Date: Jan 2023
 */

/* Fill in your Name and GNumber in the following two comment fields
 * Name: Logan France
 * GNumber: G01216330
 */

// System Includes
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <pthread.h>
#include <sched.h>
// Local Includes
#include "op_sched.h"
#include "vm_support.h"
#include "vm_process.h"

// Feel free to use these in your code, if you like! 
#define CRITICAL_FLAG   (1 << 31) 
#define LOW_FLAG        (1 << 30) 
#define READY_FLAG      (1 << 29)
#define DEFUNCT_FLAG    (1 << 28)

/* Feel free to create any helper functions you like! */
int get_bit(int value, int index);
/* Initializes the Op_schedule_s Struct and all of the Op_queue_s Structs
 * Follow the project documentation for this function.
 * Returns a pointer to the new Op_schedule_s or NULL on any error.
 */
Op_schedule_s *op_create() {
	//Allocate and Initialize the schedule
	Op_schedule_s *schedule = (Op_schedule_s *)malloc(sizeof(Op_schedule_s));
	if(schedule==NULL){
		return NULL;
	}
	//Allocate and Initialize the high queue, initialize the count, and set the head to null.
	schedule->ready_queue_high = (Op_queue_s *)malloc(sizeof(Op_queue_s));
	if(schedule->ready_queue_high==NULL){
		return NULL;
	}
	schedule->ready_queue_high->count = 0;
	schedule->ready_queue_high->head = NULL;
	
	//Allocate and Initialize the low queue, initialize the count, and set the head to null.
	schedule->ready_queue_low = (Op_queue_s *)malloc(sizeof(Op_queue_s));
	if(schedule->ready_queue_low==NULL){
		return NULL;
	}
	schedule->ready_queue_low->count = 0;
	schedule->ready_queue_low->head = NULL;
	
	//Allocate and Initialize the defunct queue, initialize the count, and set the head to null.
	schedule->defunct_queue = (Op_queue_s *)malloc(sizeof(Op_queue_s));
	if(schedule->defunct_queue==NULL){
		return NULL;
	}
	schedule->defunct_queue->count = 0;
	schedule->defunct_queue->head = NULL;
	
	return schedule; // Replace This Line with Your Code!
}

/* Create a new Op_process_s with the given information.
 * - Malloc and copy the command string, don't just assign it!
 * Follow the project documentation for this function.
 * Returns the Op_process_s on success or a NULL on any error.
 */
 /*	Initialize new process node with the following:
	o Set the state member to the Ready State.
		§ Set the Ready State bit to a 1 and Defunct State to a 0.
		§ Only one of the two State bits should be set (1) at any given time.
	o Set the Low-Priority bit of state to be 0 if is_low is false (0) or 1 if true. 
	o Set the Critical bit of state to be 0 if is_critical is false (0) or 1 if true. 
	o Initialize the lower 28-bits of state to be all 0s.
	o Initialize the age member to 0.
	o Allocate memory (malloc) for the cmd member to be big enough for command.
	o String Copy (strncpy for safety!) cmd into your struct.
	o Initialize the next member to NULL.*/
	//Continue going over errors tomorrow, need to at least compile properly for credit
Op_process_s *op_new_process(char *command, pid_t pid, int is_low, int is_critical) {
	Op_process_s *new_process = (Op_process_s *)malloc(sizeof(Op_process_s));
	//Setting the Ready State to 1 and Defunct state to 0
	//Ready State is bit 29, Defunct State is bit 28
	new_process->state = (1<<29);
	if(is_low){
		//Set the Low Priority Bit Flag to 1
		new_process->state |= (1<<30);
	}
	else if(is_critical){
		//Sets the critical bit to 1
		new_process->state |= (1<<31);
	}
	//Initialize the lower 28 bits to be 0
	new_process->state &= (0<<28);

	//Initialize age to be 0
	new_process->age = 0;

	//Initialize pid
	new_process->pid = pid;

	//Allocate memory for the cmd member to be big enough for command
	new_process->cmd = (char *)malloc(sizeof(command));
	if(new_process->cmd==NULL){
		return NULL;
	}
	//Use String Copy to copy command into the struct
	strncpy(new_process->cmd, command, strlen(command));
	//Initialize the next member to Null
	new_process->next = NULL;
	return new_process; 
}

/* Adds a process into the appropriate singly linked list queue.
 * Follow the project documentation for this function.
 * Returns a 0 on success or a -1 on any error.
 */
/*	• Set the state member of the Process (Op_process_s) to the Ready State.
		o Set the Ready State bit to a 1 and Defunct State to a 0.
			§ Only one of the two State bits should be set (1) at any given time.
		o Make sure to set this without changing the Critical or Low Bits.
	• Add the Process Node (Op_process_s) struct to the end of the appropriate Ready Queue
		o If the Low Priority Bit in the state is a 0, insert to end of the Ready Queue – High
		o If the Low Priority Bit in the state is a 1, insert to end of the Ready Queue – Low
	• If the head pointer is NULL, then this will be your first Process, and the appropriate 
	  Ready Queue’s head pointer will point to this process.
*/
 /*Use a get bit method to do this*/
int op_add(Op_schedule_s *schedule, Op_process_s *process) {
	//Check if process is at ready state, set to yes if so
	//Add the process to the appropriate queue, High if LPB is 0, Low otherwise
	//If head pointer is NULL, this is the first process, add accordingly

	if(!schedule||!process){
		return -1;
	}
	//Set the state member of the process to the Ready State
	//Clear both bits then set bit 29 to 1
	process->state &= ~(1<<29);
	process->state &= ~(1<<28);
	process->state |= (1<<29);
	//Check Low Priority Bit at Bit 30
	//if 0, place in high queue, if 1, place in low queue
	//In either case, if head is null, then set the head to process
	//If head is not null, iterate through the list until the final
	//node is reached, then set the process to that final node's next
	int low_bit = get_bit(process->state, 30);
	if(low_bit==0){
		if(schedule->ready_queue_high->head==NULL){
			schedule->ready_queue_high->head = process;
		}
		else{
			Op_process_s *current = schedule->ready_queue_high->head;
			while(current->next!=NULL){
				current = current->next;
			}
			current->next = process;
			schedule->ready_queue_high->count++;
		}
	}
	else{
		if(schedule->ready_queue_low->head==NULL){
			schedule->ready_queue_low->head = process;
		}
		else{
			Op_process_s *current = schedule->ready_queue_low->head;
			while(current->next!=NULL){
				current = current->next;
			}
			current->next = process;
			schedule->ready_queue_low->count++;
		}
	}
	return 0; // Replace This Line with Your Code!
}
//Helper method that retrieves a bit from a value at a specific index
int get_bit(int value, int index) {
	int mask = (1<<index);
	int bit = (value&mask);
	return bit != 0;
}

/* Returns the number of items in a given Op_queue_s
 * Follow the project documentation for this function.
 * Returns the number of processes in the list or -1 on any errors.
 */
int op_get_count(Op_queue_s *queue) {
	if(!queue){
		return -1;
	}
	return queue->count;
  /*return -1; // Replace This Line with Your Code!*/
}

/* Selects the next process to run from the High Ready Queue.
 * Follow the project documentation for this function.
 * Returns the process selected or NULL if none available or on any errors.
 */
Op_process_s *op_select_high(Op_schedule_s *schedule) {
	//If schedule is NULL, return NULL
	if(schedule==NULL){
		return NULL;
	}
	//If the list is empty, return NULL
	if(schedule->ready_queue_high->head==NULL){
		return NULL;
	}
	//Choose the front process and then iterate through the list
	//If a process is critical (bit 31 is 1), then choose that instead
	//And stop iterating
	Op_process_s *current = schedule->ready_queue_high->head;
	Op_process_s *process = NULL;
	//Still need a way to remove the method after selecting
	//Don't forget to decrement count
	//Start current on head.next? If that exists, then even if there is no
	//critical process, it'll just end up going to head anyway
	//If head is critical, skip the while loop.
	//Otherwise, do the loop
	if(get_bit(schedule->ready_queue_high->head->state, 31)!=1){
		while(current->next!=NULL){
			if(get_bit(current->next->state,31)==1){
				process = current->next;
				current->next = current->next->next;
				break;
			}
		current = current->next;
		}
	}

	//If process == NULL, then there were no critical bits, and the head must be removed
	if(process==NULL){
		process = schedule->ready_queue_high->head;
		schedule->ready_queue_high->head = schedule->ready_queue_high->head->next;
	}
	process->age = 0;
	process->next = NULL;
	schedule->ready_queue_high->count--;
  	return process; // Replace This Line with Your Code!
}

/* Schedule the next process to run from the Low Ready Queue.
 * Follow the project documentation for this function.
 * Returns the process selected or NULL if none available or on any errors.
 */
Op_process_s *op_select_low(Op_schedule_s *schedule) {
	//If schedule is NULL, return NULL
	if(schedule==NULL){
		return NULL;
	}
	//If the list is empty, return NULL
	if(schedule->ready_queue_low->head==NULL){
		return NULL;
	}
	//Choose the front process and remove it from the list.
	Op_process_s *process = schedule->ready_queue_low->head;
	//Don't forget to decrement count
	schedule->ready_queue_low->head=schedule->ready_queue_low->head->next;
	process->age = 0;
	process->next = NULL;
	schedule->ready_queue_low->count--;
	return process;
}

/* Add age to all processes in the Ready - Low Priority Queue, then
 * promote all processes that are >= MAX_AGE.
 * Follow the project documentation for this function.
 * Returns a 0 on success or -1 on any errors.
 */
int op_promote_processes(Op_schedule_s *schedule) {
	if(!schedule){
		return -1;
	}
	if(schedule->ready_queue_low->head==NULL){
		return -1;
	}
  	Op_process_s *current = schedule->ready_queue_low->head;

  	while(current!=NULL){
	  	current->age++;
	  	current=current->next;
  	}
  	current = schedule->ready_queue_low->head;
  /*Move all processes with an age of five to high list
	Try a while loop, same as before, stop when current is null
	Check if the age of each is above or equal to MAX_AGE, if it is, add it to high list at the end
	Then it goes back to Lower*/
	//To get the next and remove it from the linked list, perhaps check current->next?
	//And have different code for when head needs to be removed?

	//Check if head needs to be removed AFTER removing everything after?
	//Won't work, might need to care about order
	//Instead remove head, and keep checking head until it needs to be passed
	//Then move on to the current.next checking
	//This will either remove everything, or give a jumping off point of which to
	//Remove the ones that come after
	while(current!=NULL && current->age>=5){
		//Remove it from the linked list
		schedule->ready_queue_low->head = schedule->ready_queue_low->head->next;
		//set the age to 0
		current->age=0;
		//set the next to NULL
		current->next=NULL;
		//use OP add to add it to the High Queue
		//Make sure to change the low bit to high first
		current->state &= ~(1<<30);
		op_add(schedule, current);
		//Set current equal to current->next
		current=current->next;
	}
	if(current!=NULL){
		Op_process_s *process_to_pass = (Op_process_s *)malloc(sizeof(Op_process_s));
		while(current->next!=NULL){
			if(current->next->age>=5){
				/*Run Op_add to add to High*/
				process_to_pass = current->next;
				current->next=current->next->next;

				process_to_pass->age=0;

				process_to_pass->next = NULL;

				process_to_pass->state &= ~(1<<30);

				op_add(schedule, process_to_pass);
				//To ensure nothing is skipped, do continue
				//This will keep current the same and funnel processes
				//towards it, until current->next does not have an age
				//5 or higher. It will then move on from the if statement.
				continue;
			}
			current = current->next;
	}
	}

	//Return -1 on any errors
	return 0;
}

/* This is called when a process exits normally.
 * Put the given node into the Defunct Queue and set the Exit Code 
 * Follow the project documentation for this function.
 * Returns a 0 on success or a -1 on any error.
 */
int op_exited(Op_schedule_s *schedule, Op_process_s *process, int exit_code) {
	if(schedule==NULL||process==NULL){
		return -1;
	}
	//Set the process to the defunct state by clearing both bits
	//Then setting bit 28 to 1
	process->state &= ~(1<<29);
	process->state &= ~(1<<28);
	process->state |= (1<<28);

	//Set 0-27 to the input exit code
	process->state |= exit_code;

	//Add the process to the defunct queue
	Op_process_s *current = schedule->defunct_queue->head;
	//If current == NULL, then the defunct queue is empty.
	if(current==NULL){
		schedule->defunct_queue->head = process;
		return 0;
	}
	while(current->next!=NULL){
		current=current->next;
	}
	current->next = process;
  	return 0; // Replace This Line with Your Code!
}

/* This is called when the OS terminates a process early.
 * Remove the process with matching pid from Ready High 
 * or Ready Low and add the Exit Code to it.
 * Follow the project documentation for this function.
 * Returns a 0 on success or a -1 on any error.
 */
int op_terminated(Op_schedule_s *schedule, pid_t pid, int exit_code) {
	//Find the process with matching pid in either high queue or low queue
	Op_process_s *current = schedule->ready_queue_high->head;
	Op_process_s *matching_pid_process = NULL;
	if(current==NULL){
		return -1;
	}
	//Need to check head seperately and check the next of current
	while(current!=NULL){
		if(current->pid == pid){
			matching_pid_process = current;
			matching_pid_process->next=NULL;
			break;
		}
		current = current->next;
	}
	//If it still equals NULL, then it wasn't found in High Queue
	if(matching_pid_process==NULL){
		current = schedule->ready_queue_low->head;
		while(current!=NULL){
			if(current->pid == pid){
				matching_pid_process = current;
				break;
			}
			current = current->next;
		}
	}
	//This means it wasn't in either list.
	if(matching_pid_process==NULL){
		return -1;
	}
	//Set process to the defunct state
	matching_pid_process->state &= ~(1<<29);
	matching_pid_process->state &= ~(1<<28);
	matching_pid_process->state |= (1<<28);

	matching_pid_process->state |= exit_code;

	current = schedule->defunct_queue->head;
	if(current==NULL){
		schedule->defunct_queue->head = matching_pid_process;
	}
	else{
		while(current->next!=NULL){
			current=current->next;
		}
		current->next = matching_pid_process;
	}
  	return 0; // Replace This Line with Your Code!
}

/* Frees all allocated memory in the Op_schedule_s, all of the Queues, and all of their Nodes.
 * Follow the project documentation for this function.
 */
void op_deallocate(Op_schedule_s *schedule) {
	//Free all Nodes from each Queue in the OP Schedule.
	//	o Remember to free the cmd String!
	//Free all Queues from the OP Schedule
	//Free the OP Schedule

	//Have loops for each queue, have a current variable and a next variable
	//Move through while current does not equal null, have a check for if next equals null
	Op_process_s *current = schedule->defunct_queue->head;
	Op_process_s *next_node = (Op_process_s *) malloc(sizeof(Op_process_s));
	while(current!=NULL){
		if(current->next!=NULL){
			next_node = current->next;
		}
		free(current->cmd);
		free(current);
		current = next_node;
	}

	current = schedule->ready_queue_high->head;
	while(current!=NULL){
		if(current->next!=NULL){
			next_node = current->next;
		}
		free(current->cmd);
		free(current);
		current = next_node;
	}

	current = schedule->ready_queue_low->head;
	while(current!=NULL){
		if(current->next!=NULL){
			next_node = current->next;
		}
		free(current->cmd);
		free(current);
		current = next_node;
	}

	//free all the queues
	free(schedule->defunct_queue);
	free(schedule->ready_queue_high);
	free(schedule->ready_queue_low);

	//Free schedule
	free(schedule);
  return;
}
