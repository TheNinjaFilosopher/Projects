#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef struct Node
{
	int data;
	struct Node *next;
}ListNode;
/* Figure out how to make a new node with an int
 * Use a tail method*/
void printList(ListNode *head);
void insertNode(ListNode **head, ListNode *newNode);
void deleteList(ListNode *head);
int valueOfN(int highValue);
int specific(int num, int exp);
int length(ListNode *head);

int main(int argc, char **argv)
{
	int numVals=0, lowVal=0, highVal=0;
	int seed = 0, i = 0, a = 0;
	int randNum = 0;
	int N = 0;
	int digitNum = 0;
	ListNode *bucketArray[10];
	ListNode *headOfSort = NULL;
	ListNode *reformedList = NULL;
	ListNode *newNode = NULL;
	ListNode *temp = NULL;
	seed = atoi(argv[1]);
	numVals = atoi(argv[2]);
	lowVal = atoi(argv[3]);
	highVal = atoi(argv[4]);
	headOfSort = malloc(numVals*sizeof(ListNode));
	srandom(seed);
	printf("seed = %d\t numVals = %d\t lowVal = %d\t highVal = %d\n",seed,numVals,lowVal,highVal);
	
	for(;i<10;i++)/*Allocate memory for dummy heads of bucketArray*/
	{
		bucketArray[i] = malloc(sizeof(ListNode));
	}
	/*Loop that adds all the random numbers to the linked list*/
	for(i=0;i<numVals;i++)
	{
		randNum = (random()%(highVal+1)-lowVal)+lowVal;
		printf("Number %d: %d\n",(i+1),randNum);
		newNode = malloc(sizeof(ListNode));
		newNode->next = NULL;
		newNode->data = randNum;
		insertNode(&headOfSort,newNode);
	}

	/*printf("Value of N for looping = %d\n",valueOfN(highVal));*/
	N = valueOfN(highVal);
	printf("N = %d\n",N);
	
	for(i=0;i<N;i++)/*For loop that loops for each digit in the possible*/
	{	
		reformedList = malloc(sizeof(ListNode));/*Initializes the list the will hold the resorted list*/
		temp = headOfSort->next;
		for(a = 0;a<numVals;a++)/*Loops through each number in the sequence and adds it to the proper bucket*/
		{
			digitNum = specific(temp->data,i);
			newNode = malloc(sizeof(ListNode));
			newNode->next = NULL;
			newNode->data = temp->data;
			insertNode(&bucketArray[digitNum],newNode);
			printf("I = %d\t A = %d\t DigitNum = %d\n",i, a, digitNum);
			temp = temp->next;
		}
		for(a=0;a<10;a++)/*Print all the buckets*/
		{
			printf("Bucket %d: ",a);
			printList(bucketArray[a]->next);
		}
		for(a = 0; a < 10;a++)/*Place all numbers from the buckets into the sorted list*/
		{
			if(bucketArray[a]->next!=NULL)
			{
				temp = bucketArray[a]->next;

				while(temp!=NULL)
				{
					newNode = malloc(sizeof(ListNode));
					newNode->next = NULL;
					newNode->data = temp->data;
					insertNode(&reformedList,newNode);
					temp=temp->next;
				}
				free(bucketArray[a]->next);
			}
		}
		printList(reformedList);
		headOfSort = reformedList;
		deleteList(reformedList);
	}
	printList(headOfSort);
	deleteList(headOfSort);
	return 0;
}

ListNode *removeNode(ListNode *prev)
{
	ListNode *newNode = NULL;
	newNode = malloc(sizeof(ListNode));
	newNode = prev->next;
	free(prev->next);
	return newNode;
}

int length(ListNode *head)
{
	if(head==NULL)
	{
		return 0;
	}
	return 1 + length(head->next);
}

int specific(int num, int exp)
{
	int i = 0;
	for(;i<exp;i++)
	{
		num=num/10;
	}
	return num%10;
}

int valueOfN(int highValue)
{
	int N = 1;
	while(highValue/(int)pow(10,N)>0)
	{
		N++;
	}
	return N;
}

void printList(ListNode *head)
{
	if(head==NULL)
	{
		printf("\n");
		return;
	}
	printf("%d ",head->data);
	printList(head->next);
}

void insertNode(ListNode **head, ListNode *newNode)
{
	if(*head == NULL)
	{
		*head = newNode;
	}
	else
	{
		ListNode *temp = *head;
		while(temp->next!=NULL)
		{
			temp= temp->next;
		}
		newNode->next = temp->next;
		temp->next = newNode;
	}
}

void deleteList(ListNode *head)
{
	if(head == NULL)
	{
		printf("List is now empty\n");
		return;
	}
	deleteList(head->next);
	free(head);
}
