/* This is the only file you should update and submit. */

/* Fill in your Name and GNumber in the following two comment fields
 * Name: Logan France
 * GNumber: G01216330
 */

//To-do List
//Task Management Capability - Done
//Process Execution - In Progress
//Signal Handling - To Do
//Refined Process Control - To Do
//Swap all references of strcat to strncat
//Make reaper function as well as other signal functions.

#include <sys/wait.h>
#include "logging.h"
#include "anav.h"
#include "parse.h"
#include "util.h"

/* Constants */
#define DEBUG 1 /* You can set this to 0 to turn off the debug parse information */
#define STOP_SHELL  0
#define RUN_SHELL   1

#define PATH_ONE "./"
#define PATH_TWO "/usr/bin/"

//LOG_STATE_READY     0
//LOG_STATE_RUNNING   1
//LOG_STATE_SUSPENDED 2
//LOG_STATE_FINISHED  3
//LOG_STATE_KILLED    4

//Might also need Instruction struct in here
typedef struct task_struct{
  int task_num; //needs a task number
  int status; //Status of the task
  int exit_code;//Exit Code of the task
  int pid;//PID of the task
  char *cmd;//CMD of the task
  char *argv[MAXARGS+1];//arguments of the task
  Instruction inst;
  struct task_struct *next;//pointer to the next tasknode in a linked list
} taskNode;

typedef struct task_head{
  int count; //The amount of items in the list.
  taskNode *head; //Points to the first node of the linked list.
} taskHead;

/*Global Variables*/
//The highest task number, make sure to reset to 0 when schedule's become empty
int highestTaskNum = 0;
pid_t global_pid = 0;
int global_status = 0;
taskHead *taskList = {0};
//Keeps track of the type of process to make sure output is accurate.
int type = LOG_FG;

//Call the log_anav_status_change in here
//Make a function that can read a task based off the pid?
//Handler should be called upon getting to signal
//Should probably have different handlers for SIGCHLD and the rest

taskNode *get_task_pid(taskHead *head, int pid);
void task_info(taskNode *task);
//The reason it's not working is because I'm not blocking SIGCHILD I believe, if I implement blocking and unblocking, it might work properly
void handler_test(int sig){
  //printf("Signal was %d\n",sig);
  //pid_t pid = 0;
  //int status = 0;
  taskNode *task = malloc(sizeof(taskNode));
  task = get_task_pid(taskList, global_pid);
  task_info(task);
  wait(&global_status);
  if(sig==SIGINT){//kill for bg
    printf("That's a SIGINT\n");
    printf("1 if killed by a signal: %d\n",WIFSIGNALED(global_status));
    if(WIFEXITED(global_status)){
      printf("Child ended normally.");
    }
    else if(WIFSIGNALED(global_status)){
      printf("Child ended because of a signal.");
    }
  }
  if(sig==SIGCONT){//resume for bg
    printf("That's a SIGCONT!\n");
    //log_anav_status_change(,,,,);
  }
  if(sig==SIGTSTP){//suspend for bg
    printf("That's a SIGTSTP!\n");
  }
  if(sig==SIGTERM){
    printf("That's a SIGTERM!\n");
  }
}

/*SIGCHLD is sent under four conditions
  1. A Child Exits Normally (Finishes)
  2. A Child is Terminated by Signal (Termination)
  3. A Child is Suspended by Signal (Stopped)
  4. A Child is Continued by Signal (Resumed)
  Parent process receives a SIGCHLD for any of these four
  By default, waitpid() blocks the process and returns only when 1 or 2 happen.
  The way the project is set up, default is needed for exec, but WNOHANG would
  be better for bg. With this in mind, waitpid() needs to be called elsewhere in the code*/
/*
  When using log_anav_status_change, suspended by signal and continued by signal should be background processes, so that is easy
  */
void handler_child(int sig){
  //printf("This is the handler for children.\n");
  //SIGCHLD will be called if the bg process ends normally, when that happens
  //Do the normal logging requirements that were done for exec
  //Most should be done, this is just status change
  
  taskNode *task = malloc(sizeof(taskNode));
  task = get_task_pid(taskList,global_pid);
  //task_info(task);
  /*pid_t pid = */
  waitpid(task->pid,&global_pid, WNOHANG);
  //printf("%d\n",pid);
  //printf("%d\n",global_pid);
  //If terminates normally, go to WIFEXITED
  //If Terminates through a signal, go to WIFSIGNALED
  //If suspended, call kill and send it to the other handler
  //If continued, call kill and send it to the other handler
  if(WIFEXITED(global_status)){
    int exitStatus = WEXITSTATUS(global_status);
    task->status=LOG_STATE_FINISHED;
    task->exit_code=exitStatus;
    log_anav_status_change(task->task_num,task->pid, type, task->cmd, LOG_TERM);
    return;
  }
  else if(WIFSIGNALED(global_status)){
    int exitStatus = WEXITSTATUS(global_status);
    task->status=LOG_STATE_KILLED;
    task->exit_code=exitStatus;
    log_anav_status_change(task->task_num,task->pid, type, task->cmd, LOG_TERM_SIG);
    return;
  }
  else if(WIFSTOPPED(global_status)){
    //
    kill(global_pid,SIGTSTP);
  }
  else if(WIFCONTINUED(global_status)){
    //
    kill(global_pid,SIGCONT);
  }
}

/* Functions*/
//void task_info(taskNode *task);
int add_node(taskHead *head, taskNode *task);
void remove_node(taskHead *head, int task_num);
int get_count(taskHead *head);
int has_task_number(taskHead *head, int task_num);
taskNode *get_task(taskHead *head, int task_num);
//taskNode *get_task_pid(taskHead *head, int pid);
taskNode *createNode(char *cmd_line, Instruction inst, char **argv);

void list_function(taskHead *head);
void purge_function(taskHead *head, int task_num);

void exec_function(taskNode *task, Instruction inst);
void exec_standard(taskNode *task);
void exec_infile_function(taskNode *task, Instruction inst);
void exec_outfile_function(taskNode *task, Instruction inst);

void bg_function(taskNode *task, Instruction inst);

void pipe_function(taskNode *task1, taskNode *task2,Instruction inst);

void block_signal();
void unblock_signal();

/* The entry of your text processor program */

int main() {
  char *cmd = NULL;
  int do_run_shell = RUN_SHELL;

  //Testing signals below
  //Now that the signals are working
  struct sigaction sa = {0};
  sa.sa_handler = handler_test;
  sigaction(SIGINT, &sa, NULL);
  sigaction(SIGCONT, &sa, NULL);
  sigaction(SIGTSTP, &sa, NULL);
  sigaction(SIGTERM, &sa, NULL);

  sa.sa_handler = handler_child;
  sigaction(SIGCHLD, &sa, NULL);

  taskList = malloc(sizeof(taskHead));
  /* Intial Prompt and Welcome */
  log_anav_intro();
  log_anav_help();

  /* Shell looping here to accept user command and execute */
  while (do_run_shell == RUN_SHELL) {
    char *argv[MAXARGS+1] = {0};  /* Argument list */
    Instruction inst = {0};       /* Instruction structure: check parse.h */
    
    /* Print prompt */
    log_anav_prompt();

    /* Get Input - Allocates memory for the cmd copy */
    cmd = get_input(); 
    /* If the input is whitespace/invalid, get new input from the user. */
    if(cmd == NULL) {
      continue;
    }

    /* Check to see if this is the quit built-in */
    if(strncmp(cmd, "quit", 4) == 0) {
      /* This is a match, so we'll set the main loop to exit when you finish processing it */
      do_run_shell = STOP_SHELL;
      /* Note: You will need to print a message when quit is entered, 
       *       you can do it here, in your functions, or at the end of main.
       */
      log_anav_quit();
      continue;
    }

    /* Parse the Command and Populate the Instruction and Arguments */
    initialize_command(&inst, argv);    /* initialize arg lists and instruction */
    parse(cmd, &inst, argv);            /* call provided parse() */

    if (DEBUG) {  /* display parse result, redefine DEBUG to turn it off */
      debug_print_parse(cmd, &inst, argv, "main (after parse)");
    }

    /*.===============================================.
     *| Add your code below this line to continue. 
     *| - The command has been parsed and you have cmd, inst, and argv filled with data.
     *| - Very highly recommended to start calling your own functions after this point.
     *o===============================================*/
    //Description of my duties
    //Code must accept a single liine of instruction from the user and perform the instruction
      //The instruction may involve creating, deleting, or listing tasks
      //The instruction may involve reading from or writing to a file.
      //The instruction may involve loading and running a user-specified program.

    //The system must support any arbitrary number of simultaneous running processes.
      //ANAV can either wait for a process to finish or let them run in the background.

    //Perform basic management of tasks, whether they are in ready mode, running, or complete.

    //Use file redirects and pipes to read input from or send output to a file or another process.

    //Use signals to suspend/resume or terminate running processes, and track child activity.

    //Built in Help Function, displays short description of the system
    if(strncmp(cmd, "help", 4)==0){
      log_anav_help();
      continue;
    }

    //Built in List Function
      //Should be as easy as iterating through the linked list and printing results
      //As shown on page 5
      //Call log_anav_num_tasks(num) to indicate the number of tasks, then call 
      //log_anav_task_info(task_num, status, exit code, pid, cmd)
      //Make a function that will do both by passing in the linked list
    if(strncmp(cmd, "list", 4)==0){
      list_function(taskList);
      continue;
    }

    //Built in Purge Function
      //Create a purge function that accepts a task number, iterate through linked list
      //to find the task number. If the task number is found, delete and call log_anav_purge(task_num).
      // If it is not found (the list should be ordered, so a number higher than the task number would 
      //indicate that it is not in the list), then call log_anav_task_num_error(task_num) instead
      //If the task exists, but is busy (Running or Suspended), call log_anav_status_error(task_num)
      //The status will be LOG_STATE_RUNNING or LOG_STATE_SUSPENDED, should be as easy as passing
      //the variable in. Once purged, a task no longer exists and no information needs to be kept
    if(strncmp(inst.instruct, "purge", 5)==0){
      purge_function(taskList, inst.id1);
      continue;
    }

    //Built in Exec Function
      //This will be how to run a non-interactive program such as ls, cat, or slow_cooker
      //Have a function that accepts a task number and runs it as a foreground process
      //If an INFILE is specified, then the child process' input should be redirected from
      //the file, and if an OUTFILE is specified, then the child process' output should be
      //redirected to the file. The process' status should change to running as it runs and
      //eventually completes.
      //First check if the task exists in the list
      //If not, call the error, otherwise check if it is busy
      //If so, call the error
      if(strncmp(inst.instruct, "exec", 4)==0){
        taskNode *task = malloc(sizeof(taskNode));
        if(has_task_number(taskList, inst.id1)==0){
          log_anav_task_num_error(inst.id1);
          continue;
        }
        else{
          task = get_task(taskList,inst.id1);
          if(task->status==LOG_STATE_RUNNING||task->status==LOG_STATE_SUSPENDED){
            log_anav_status_error(task->task_num, task->status);
            continue;
          }
        }
        type = LOG_FG;
        exec_function(task, inst);
        continue;
      }

    //Built in BG Function
      //This built-in command is how we run a non-interactive program (eg. ls, wc, cat, cal, slow_cooker) 
      //in a way where we do not wait for the program to finish before prompting the user
      //for more input.
      //Have a function that takes in a tasknum and a file
      //Have it run as a background process, needs signal handling
      //Logging requirements are similar to exec
      //Implementation is like the exec built in, but without waiting for the process to terminate
      if(strncmp(inst.instruct, "bg", 2)==0){
        taskNode *task = malloc(sizeof(taskNode));
        if(has_task_number(taskList, inst.id1)==0){
          log_anav_task_num_error(inst.id1);
          continue;
        }
        else{
          task = get_task(taskList,inst.id1);
          if(task->status==LOG_STATE_RUNNING||task->status==LOG_STATE_SUSPENDED){
            log_anav_status_error(inst.id1, task->status);
            continue;
          }
        }
        type = LOG_BG;
        bg_function(task, inst);
        continue;
      }

    //Built in Pipe Function
      //sends data from tasknum1 to tasknum2
      //Tasknum1 is a bg while tasknum2 is an exec
      //Can call those functions for easy use in a pipe function
      //Neither process will use input or output file redirection
      //Call an error if the two task numbers are identical and don't create a pipe or run the
      //processes
      //If either task cannot be executed due to invalid tasknum or incompatible state, then 
      //don't create a pipe or run either process, also checking logging requirements
      //if pipe creation fails, call log_anav_file_error(task_num) with TaskNum1 and don't
      //run either process
    if(strncmp(inst.instruct, "pipe", 4)==0){
      if(inst.id1==inst.id2){
        log_anav_pipe_error(inst.id1);
        continue;
      }
      taskNode *task1 = malloc(sizeof(taskNode));
      taskNode *task2 = malloc(sizeof(taskNode));
      if(has_task_number(taskList, inst.id1)==0){
        log_anav_task_num_error(inst.id1);
        continue;
      }
      if(has_task_number(taskList,inst.id2)==0){
        log_anav_task_num_error(inst.id2);
        continue;
      }
      else{
        task1 = get_task(taskList,inst.id1);
        task2 = get_task(taskList,inst.id2);
        if(task1->status==LOG_STATE_RUNNING||task1->status==LOG_STATE_SUSPENDED){
          log_anav_status_error(inst.id1, task1->status);
          continue;
        }
        if(task2->status==LOG_STATE_RUNNING||task2->status==LOG_STATE_SUSPENDED){
          log_anav_status_error(inst.id2, task2->status);
          continue;
        }
      }
      pipe_function(task1,task2, inst);
      continue;
    }

    //Built in Kill Function
      //call a function and pass in the TaskNum, if it doesn't exist, call log_anav_task_num_error(task_num)
      //if currently idle(Ready, Finished, Killed), call log_anav_status_error (task_num, status)
      //Call log_anav_sig_sent(LOG_CMD_KILL, task_num, pid) when signalling the kill
      //Child may exit, which leads to additional logs
      //Running or Suspended Processes can be killed
      //Signal can be sent to a child process with the kill() function
      //The signal to send is SIGINT
      //Only needs to signal the need to kill, termination and clean up are separate
    if(strncmp(inst.instruct, "kill", 4)==0){
      if(has_task_number(taskList, inst.id1)==0){
        log_anav_task_num_error(inst.id1);
        continue;
      }
      taskNode *task = malloc(sizeof(taskNode));
      task = get_task(taskList,inst.id1);
      if(task->status==LOG_STATE_READY||task->status==LOG_STATE_FINISHED||task->status==LOG_STATE_KILLED){
        log_anav_status_error(task->task_num,task->status);
        continue;
      }
      log_anav_sig_sent(LOG_CMD_KILL, task->task_num, task->pid);
      block_signal();
      kill(task->pid,SIGINT);
      unblock_signal();
      continue;
    }

    //Built in Suspend Function
      //Suspends the process of TaskNum and takes it off ready queue into suspended queue
      //Remains in suspended queue until it is resumed
      //call log_anav_task_num_error(task_num) if the taskNum does not exist or
      //log_anav_status_error(task_num, status) if the task is idle(Ready, Finished or Killed )
      //call log_anav_sig_sent(LOG_CMD_SUSPEND, task_num, pid) when signalling
      //Child may be suspended, which can lead to additional logs
      //Running or Suspended processes can be suspended; others will produce the error log
      //Signal can be sent with kill() function
      //Signal to send is SIGTSTP
      //Only needs to signal the need to suspend the process, suspension is handled separately
    if(strncmp(inst.instruct, "suspend", 7)==0){
      if(has_task_number(taskList, inst.id1)==0){
        log_anav_task_num_error(inst.id1);
        continue;
      }
      taskNode *task = malloc(sizeof(taskNode));
      task = get_task(taskList,inst.id1);
      if(task->status==LOG_STATE_READY||task->status==LOG_STATE_FINISHED||task->status==LOG_STATE_KILLED){
        log_anav_status_error(task->task_num,task->status);
        continue;
      }
      log_anav_sig_sent(LOG_CMD_SUSPEND, task->task_num, task->pid);
      kill(task->pid,SIGSTOP);
      continue;
    }

    //Built in Resume Function
      //Takes a process that is suspended and resumes it
      //If the TaskNum does not exist, call log_anav_task_num_error(task_num)
      //If the task is idle(Ready, Finished or Killed), then call log_anav_status_error(task_num, status)
      //Call log_anav_sig_sent(LOG_CMD_RESUME, task_num, pid) when signalling
      //Child process may be resumed as a side effect, which leads to additional logs
      //Running or Suspended processes can be resumed; others will produce the error log.
      //It is ok to resume a nonsuspended process, though it likely will do nothing
      //Resumed processes should be done in the foreground when resumed
      //A signal can be sent with the kill function
      //The signal to send is SIGCONT
      //Only needs to signal the need to resume the process, the resume event is separate
    if(strncmp(inst.instruct, "resume", 6)==0){
      if(has_task_number(taskList, inst.id1)==0){
        log_anav_task_num_error(inst.id1);
        continue;
      }

      taskNode *task = malloc(sizeof(taskNode));
      task = get_task(taskList,inst.id1);
      if(task->status==LOG_STATE_READY||task->status==LOG_STATE_FINISHED||task->status==LOG_STATE_KILLED){
        log_anav_status_error(task->task_num,task->status);
        continue;
      }

      log_anav_sig_sent(LOG_CMD_RESUME, task->task_num, task->pid);
      kill(task->pid,SIGCONT);
      continue;
    }

    //Reaping child processes
      //Waitpid comes with certain logging requirements
      //when states change, call log_anav_status_change(task_num, pid, type, cmd, transition)
      //Called whenever a process' state is affected
      //pid is the Process ID of the process (not Task ID)
      //type is LOG_FG for foreground processes or LOG_BG otherwise
      //resumed process is automatically LOG_FG
      //cmd is the full user command associated with the task
      //transition indicates how the process status was affected
      //Can be: exited; terminated by signal; stopped; continued
      //Uses LOG_TERM, LOG_TERM_SIG, LOG_SUSPEND, or LOG_RESUME
      //Process state does not need to be updated when first signalled, it can wait until waitpid()
      //waitpid() can be used to detect all process state changes of interest, not just termination
      //Resumed process is automatically a foreground process
      //waitpid() blocks until a process finishes by default, can be made to poll for results instead
      //NOHANG option will cause it to exit immediately if no process has finished yet
      //Additional options can be used to check for stopped/continued processes
      //WIFEXITED, WIFSTOPPED, WIFSIGNALED, WIFCONTINUED, etc
      //Call to waitpid can be interrupted if a signal arrives, might need to restart
      //the wait if that happens, check error codes
      //Multiple process could end at the same time, wait multiple times in a row if so
      //could use a loop to wait until no more processes are returned
      //a call to waitpid() allows me to record a process' exit code
      //best way to record exit codes for a terminated process

    //Keyboard interaction functions
      //need to support ctrl-C (SIGINT, value 2) and ctrl-Z (SIGTSTP, value 20)
      //ctrl-C sends a signal to the foreground process to terminate it
      //ctrl-Z sends a signal to the foreground process to suspend it
      //If there are no foreground processes, then the calls should be ignored
      //Call log_anav_ctrl_c() to report the arrival of SIGINT triggered by ^C.
      //Call log_anav_ctrl_z() to report the arrival of SIGTSTP triggered by ^Z.
      //If there is no active process, the log calls should still be made.
      //Assume that SIGINT signals received by the shell process have only been triggered by ^C
      //Assume that SIGTSTP signals received by the shell process have only been triggered by ^Z.
      //If the Task's state is not Running, the signal should be ignored
      //keyboard-triggered signals are first sent to the shell process
      //In order for the foreground child process to receive the signal, I will need to handle the
      //signal and forward it to the child process
      //By default, keyboard-triggered signal is sent to all the processes, including children
      //Need to avoid this by putting different processes in different groups with setpgid()
      //sigaction() can be used to change the default to a particular signal
      //Kill() can be used to send or forward a signal received to another process

    //First lets make a structure for holding all the tasks.
    //A linked list can hold any number of tasks, and add/remove tasks easily
    //Have a function that can create a task, then another that can
    //Add task to a linked list, and once that is done, print
    //"Adding Task #X: ls -al (Ready)"
    //X being the Task number and ls -al representing any potential task

    //The above is mostly done, just need to ensure I can add to the task lists. The idle list should be the one that I add to in most cases
    //Then I can have another function for adding to the busy list
    taskNode *newNode = createNode(string_copy(cmd),inst,clone_argv(argv));
    add_node(taskList, newNode);
    /*.===============================================.
     *| After your code: We cleanup before Looping to the next command.
     *| free_command WILL free the cmd, inst and argv data.
     *| Make sure you've copied any needed information to your Task first.
     *| Hint: You can use the util.c functions for copying this information.
     *o===============================================*/
    free_command(cmd, &inst, argv);
    cmd = NULL;
  }
  return 0;
}

//Specify which list is being added to, and then which node is being added
int add_node(taskHead *head, taskNode *task){
  if(head->head==NULL){
    head->head = task;
    head->count = 1;
    return 1;
  }
  taskNode *current = head->head;
  while(current->next!=NULL){
    current=current->next;
  }
  current->next=task;
  head->count++;
  return 1;
}

taskNode *createNode(char *cmd_line, Instruction inst, char **argv){
  taskNode *newNode = malloc(sizeof(taskNode));

  //newNode.argv = clone_argv(argv);
  newNode->cmd = cmd_line;
  newNode->task_num = ++highestTaskNum;
  newNode->status = LOG_STATE_READY;
  newNode->exit_code = 0;
  newNode->pid = 0;
  newNode->inst = inst;
  newNode->next = NULL;
  int i = 0;
  while(argv[i]){
    newNode->argv[i] = argv[i];
    i++;
  }
  log_anav_task_init(newNode->task_num,newNode->cmd);
  return newNode;
}

//Specify which list is being removed from, and which node is being removed
void remove_node(taskHead *head, int task_num){
  if(head->head==NULL){
    return;
  }
  if(head->head->task_num==task_num){
    head->head=head->head->next;
    head->count--;
    if(head->count==0){
      highestTaskNum = 0;
    }
    return;
  }
  taskNode *current = head->head;
  while(current->next!=NULL){
    if(current->next->task_num==task_num){
      current->next=current->next->next;
      head->count--;
      if(head->count==0){
        highestTaskNum = 0;
      }
      return;
    }
    current = current->next;
  }
}

//returns the count of a list
int get_count(taskHead *head){
  return head->count;
}

//Checks if a task number is contained in the list
//Accepts a list and a task number
int has_task_number(taskHead *head, int task_num){
  if(!task_num){
    return 0;
  }
  taskNode *current = head->head;
  while(current!=NULL){
    if(current->task_num==task_num){
      return task_num;
    }
    current = current->next;
  }
  return 0;
}

taskNode *get_task(taskHead *head, int task_num){
  taskNode *current = head->head;
  taskNode *ans = {0};
  while(current!=NULL){
    if(current->task_num==task_num){
      ans = current;
      break;
    }
    current = current->next;
  }
  return ans;
}

taskNode *get_task_pid(taskHead *head, int pid){
  taskNode *current = head->head;
  taskNode *ans = {0};
  while(current!=NULL){
    if(current->pid==pid){
      ans = current;
      break;
    }
    current = current->next;
  }
  //printf("The answer pid is %d\n", ans->pid);
  return ans;
}

void task_info(taskNode *task){
  log_anav_task_info(task->task_num,task->status,task->exit_code,task->pid,task->cmd);
}

    //Built in List Function
      //Should be as easy as iterating through the linked list and printing results
      //As shown on page 5
      //Call log_anav_num_tasks(num) to indicate the number of tasks, then call 
      //log_anav_task_info(task_num, status, exit code, pid, cmd)
      //Make a function that will do both by passing in the linked list
void list_function(taskHead *head){
  taskNode *current = head->head;
  log_anav_num_tasks(head->count);
  if(head->count==0){
    return;
  }
  while(current!=NULL){
    task_info(current);
    current=current->next;
  }
}
    //Built in Purge Function
      //Create a purge function that accepts a task number, iterate through linked list
      //to find the task number. If the task number is found, delete and call log_anav_purge(task_num).
      // If it is not found (the list should be ordered, so a number higher than the task number would 
      //indicate that it is not in the list), then call log_anav_task_num_error(task_num) instead
      //If the task exists, but is busy (Running or Suspended), call log_anav_status_error(task_num)
      //The status will be LOG_STATE_RUNNING or LOG_STATE_SUSPENDED, should be as easy as passing
      //the variable in. Once purged, a task no longer exists and no information needs to be kept
void purge_function(taskHead *head, int task_num){
  if(has_task_number(head,task_num)){
    taskNode *task = get_task(head, task_num);
    if(task->status==LOG_STATE_RUNNING||task->status==LOG_STATE_SUSPENDED){
      log_anav_status_error(task_num, task->status);
      return;
    }
    else{
      remove_node(head,task_num);
      log_anav_purge(task_num);
    }
  }
  else{
    log_anav_task_num_error(task_num);
  }
}

/*After checking errors in calling the task, come to this exec function
  fork the parent and call log_anav_status_change(task_num, pid, LOG_FG, cmd, LOG_START)
  inside the child, if exec fails, call log_anav_exec_error(cmd) and terminate the child with exit code 1
  If a redirection is performed, call log_anav_redir(task_num, redir_type, filename) [either  LOG_REDIR_IN or  LOG_REDIR_OUT]
  If the redir file can't be opened, call log_anav_file_error(task_num, filename). Can check if redir is needed by seeing if infile or outfile is NULL
  If both are NULL, no redirection*/
  //Split this function into three, one for infiles, one for outfiles, use this one for neither, based on inst
  //Before that, implement the version without redirection

  //Mostly done, still need to handle setpgid and signals
void exec_function(taskNode *task, Instruction inst){
  int process = fork();
  if(process==0){
    //Child Process
    setpgid(0,0);
    if(inst.infile!=NULL){
      exec_infile_function(task, inst);
      if(inst.outfile!=NULL){
        exec_outfile_function(task, inst);
      }
      exec_standard(task);
    }
    else if(inst.outfile!=NULL){
      exec_outfile_function(task, inst);
      exec_standard(task);
    }
    else{
      exec_standard(task);
    }
  }
  else{
    //Parent Process
    task->status = LOG_STATE_RUNNING;
    task->pid=process;
    log_anav_status_change(task->task_num, task->pid, LOG_FG, task->cmd, LOG_START);
    global_pid = task->pid;
    waitpid(task->pid,&global_status,0);
  }
}

//Function that runs standard execution
void exec_standard(taskNode *task){
  char pathStringOne[10] = "";
  strcat(pathStringOne, PATH_ONE);
  strcat(pathStringOne, string_copy(task->argv[0]));
      
  int checkOne = execv(pathStringOne,task->argv);

  if(checkOne==-1){
    char pathStringTwo[10] = "";
    strcat(pathStringTwo, PATH_TWO);
    strcat(pathStringTwo, string_copy(task->argv[0]));

    int checkTwo = execv(pathStringTwo,task->argv);
    if(checkTwo==-1){
      log_anav_exec_error(task->cmd);
      exit(1);
    }
  }
}
//Function that runs infile execution
//For the infile, Open the File for reading, then use dup to change the standard input
//to the file. If the file isn't valid, then call log_anav_file_error(task_num, filename)
//Possibly remove exec_standard and call it in the if statement instead
void exec_infile_function(taskNode *task, Instruction inst){
  //printf("Welcome to the infile function!\n");
  int infile = open(inst.infile,O_RDONLY);
  if(infile==-1){
    log_anav_file_error(task->task_num, inst.infile);
    exit(1);
  }
  else{
    log_anav_redir(task->task_num, LOG_REDIR_IN, inst.infile);
    dup2(infile, STDIN_FILENO);
  }
}
//Function that runs outfile execution
void exec_outfile_function(taskNode *task, Instruction inst){
  //printf("Welcome to the outfile function!\n");
  //Remember to remove the TRUNC and CREAT parts, keep only WRONLY
  int outfile = open(inst.outfile,O_WRONLY|O_TRUNC|O_CREAT, 0644);
  if(outfile==-1){
    log_anav_file_error(task->task_num, inst.outfile);
    exit(1);
  }
  else{
    log_anav_redir(task->task_num, LOG_REDIR_OUT, inst.outfile);
    dup2(outfile, STDOUT_FILENO);
  }
}
//Could maybe use an if statement inside of the infile/outfile functions to save on code space, use inst.instruct to know which to call
//Could possibly reuse exec_standard as well
//Use NOHANG in waitpid to not have the waitpid block

//BG is calling the terminated normally to fast, move reaper function to SIGCHLD to see if that will work
//Only issue is that it requires a global task, so first make the tasklist global
//Done
void bg_function(taskNode *task, Instruction inst){
  //printf("Welcome to the Background Function!\n");
  int process = fork();
  if(process==0){
    //Child Process
    setpgid(0,0);
    if(inst.infile!=NULL){
      exec_infile_function(task, inst);
      if(inst.outfile!=NULL){
        exec_outfile_function(task, inst);
      }
      exec_standard(task);
    }
    else if(inst.outfile!=NULL){
      exec_outfile_function(task, inst);
      exec_standard(task);
    }
    else{
      exec_standard(task);
    }
  }
  else{
    //Parent Process
    task->status = LOG_STATE_RUNNING;
    task->pid=process;
    log_anav_status_change(task->task_num, task->pid, LOG_BG, task->cmd, LOG_START);
    global_pid = task->pid;
  }
}

//Pipe Function
void pipe_function(taskNode *task1, taskNode *task2,Instruction inst){
  //Create the pipe, then call fork
  int pipe_fds[2] = {0};
  int tube = pipe(pipe_fds);
  if(tube==-1){
    log_anav_file_error(task1->task_num,LOG_FILE_PIPE);
    exit(1);
  }
  log_anav_pipe(task1->task_num, task2->task_num);
  int write_pipe = pipe_fds[1];
  int read_pipe = pipe_fds[0];

  int process = fork();
  //int process2 = 0;
  //Similar stuff with bg and exec
  //probably shouldn't call the functions since that will make more children than I need
  if(process==0){
    //Child Process - A
    //Close the read end and redirect output to the write end
    //Do stuff with task 1, same as bg
    setpgid(0,0);
    close(read_pipe);
    dup2(write_pipe, STDOUT_FILENO);
    task1->status = LOG_STATE_RUNNING;
    task1->pid=process;
    log_anav_status_change(task1->task_num, task1->pid, LOG_BG, task1->cmd, LOG_START);
    global_pid = task2->pid;
    type = LOG_BG;
    exec_standard(task1);
		exit(0);
  }
  else{
    //Parent Process - B
    //Close the write end and redirect input to the read end
    //Do stuff with task 2, same as exec
    //Close Write Pipe
    //Before doing the above, fork once more to make another child to do the foreground process in
    int process2 = fork();
    /*
    task2->status = LOG_STATE_RUNNING;
    task2->pid=process;
    log_anav_status_change(task2->task_num, task->pid, LOG_FG, task->cmd, LOG_START);
    global_pid = task2->pid;
    waitpid(task2->pid,&global_status,0);
    */
    if(process2==0){
      //child - B
      close(write_pipe);
      dup2(read_pipe, STDIN_FILENO);
      task2->status = LOG_STATE_RUNNING;
      task2->pid=process;
      log_anav_status_change(task2->task_num, task2->pid, LOG_FG, task2->cmd, LOG_START);
      global_pid = task2->pid;
      waitpid(task2->pid,&global_status,0);
      type = LOG_FG;
      exec_standard(task2);
    }
    else{
      //Parent
      close(write_pipe);
      close(read_pipe);
    }
  }
}

void block_signal(){
  sigset_t mask, prev_mask;
  sigemptyset(&mask);
  sigaddset(&mask, SIGCHLD);
  sigprocmask(SIG_BLOCK, &mask, &prev_mask);
}

void unblock_signal(){
  sigset_t mask, prev_mask;
  sigemptyset(&mask);
  sigaddset(&mask, SIGCHLD);
  sigprocmask(SIG_UNBLOCK, &mask, &prev_mask);
}