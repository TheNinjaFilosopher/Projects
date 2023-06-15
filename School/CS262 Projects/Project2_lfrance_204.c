#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "GMUsms.h"

#define UNPACKED_MESSAGE_CONST 160
#define PACKED_MESSAGE_CONST 120
#define BYTETOBINARYPATTERN "%d%d%d%d%d%d%d%d"
#define BYTETOBINARY(byte)  \
	(byte & 0x80 ? 1 : 0), \
	(byte & 0x40 ? 1 : 0), \
	(byte & 0x20 ? 1 : 0), \
	(byte & 0x10 ? 1 : 0), \
	(byte & 0x08 ? 1 : 0), \
	(byte & 0x04 ? 1 : 0), \
	(byte & 0x02 ? 1 : 0), \
	(byte & 0x01 ? 1 : 0)
#define PRINTBIN(x) printf(BYTETOBINARYPATTERN, BYTETOBINARY(x));

void packer();
void unpacker();
void appendString(char *original, unsigned char byte);
void itoa(int integer, char *string);
int main()
{
	char option = NULL;
	char buffer[300];
	
	while(1)
	{
		printf("Enter in an option\n");
		printf("[P]ack and save a line of text  (options are 'P' or 'p')\n");
		printf("[U]npack and print a line of text (options are 'U' or 'u')\n");
		printf("[Q]uit  (options are 'Q' or 'q')\n");
		printf("Input Option: ");
		fgets(buffer,300,stdin);
		sscanf(buffer,"%c",&option);/*Sets the option variable based on user input*/
		switch(option)/*Does a different method or ends the program, based on user input*/
		{
		case 'P':
		case 'p': packer();
			break;
		case 'U':
		case 'u': printf("Going to unpacker function\n");
			unpacker();
			break;
		case 'Q':
		case 'q': printf("Exiting the Program\n");
			return 1;
		}
	}

	return 0;
}

void packer()
{
	FILE *file = NULL;
	char filename[100];
	char message[UNPACKED_MESSAGE_CONST];
	unsigned char packedArray[PACKED_MESSAGE_CONST];
	unsigned char messageSMS[UNPACKED_MESSAGE_CONST];
	int messageCount = 0, i = 0, j = 0, a = 0, b = 0;
	int mask = 0x00;
	printf("Print the name of the file you are packing into: ");
	fgets(filename,300,stdin);
	filename[strlen(filename)-1] = '\0';/*Asks the user to input a file name and places that input in the filename variable*/

	file = fopen(filename,"wb");/*Opens file for writing*/
	if(file == NULL)/*Checks if file exists*/
	{
		printf("File does not exist.\n");
		return;
	}
	printf("Now print the message you want to pack. Keep in mind messages over 160 characters will be reduced to 159 and the 160th character will be replaced with a NULL terminator: ");
	fgets(message,300,stdin);/*Asks the user to input a message and places the input in the message variable*/
	
	if(message[strlen(message)-1]!='\n')/*Flushes the input buffer*/
	{
		char buffer[300] = {'A',0};
		while(buffer[strlen(buffer)-1]!= '\n')
		{
			fgets(buffer,100,stdin);
		}
	}

	while(message[messageCount]!='\0')/*Sets the variable for the size of the message*/
	{
		messageCount++;
	}
	if(messageCount>160)/*Reduces the size of the message if it is too large*/
	{
		printf("Too many characters. Must be reduced.\n");
		printf("First Message: %s\n",message);
		message[159] = '\0';
		printf("Packable Message: %s\n",message);
	}
	
	for(i=0;i<PACKED_MESSAGE_CONST;i++)/*Initializes the packedArray*/
	{
		packedArray[i] = 0x00;
	}
	i=0;
	for(;i<messageCount;i++)/*Converts ASCII to GMUSCII*/
	{
		messageSMS[i] = CharToSMS(message[i]);
	}
	i = 0;
	for(;a<messageCount;i++,j++)/*Packs the message into the packedArray by incrementing a and b when i=6 and j=8 respectively*/
	{
		if(i==6)
		{
			i = 0;
			a++;
		}
		if(j==8)
		{
			j = 0;
			b++;
		}
		switch(j)
		{
		case 0: mask = 0x01;
			break;
		case 1: mask = 0x02;
			break;
		case 2: mask = 0x04;
			break;
		case 3: mask = 0x08;
			break;
		case 4: mask = 0x10;
			break;
		case 5: mask = 0x20;
			break;
		case 6: mask = 0x40;
			break;
		case 7: mask = 0x80;
	        	break;
		}
		if(messageSMS[a] & 0x01 ? 1 : 0)
		{
			packedArray[b] = packedArray[b] ^ mask;
			
		}
		messageSMS[a] = messageSMS[a]>>1;
	}
	
	fwrite(packedArray, sizeof(size_t),UNPACKED_MESSAGE_CONST,file);/*Writes packedArray to the file in binary format*/
	fclose(file);
}

void unpacker()/*Unpacks the file*/
{
	FILE *file = NULL;
	unsigned char packedArray[PACKED_MESSAGE_CONST];
	unsigned char unpackedArray[UNPACKED_MESSAGE_CONST];
	char byteString[2000];
	char filename[100];
	int i = 0;
	int a = 0;
	int mask = 0x00;

	printf("Please enter the filename for the file that contains the packed message: ");
	fgets(filename,100,stdin);
	filename[strlen(filename)-1] = '\0';/*Prompts the user for input and places that input in the filename variable*/

	file = fopen(filename,"rb");/*Opens the file*/
	if(file == NULL)/*Checks if the file exists*/
	{
		printf("File does not exist.\n");
		return;
	}
	fread(packedArray,sizeof(char),PACKED_MESSAGE_CONST,file);/*reads the packed file into the array*/
	printf("%s\n",packedArray);
	
	for(i=0;i<UNPACKED_MESSAGE_CONST;i++)/*Initializes the unpackedArray*/
	{
		unpackedArray[i] = 0x00;
	}
	i = 119;
	for(;i>=2;i-=3)/*Appends the packedArray to the byteString in binary format*/
	{	
		if(packedArray[i]!=0)
		{
			appendString(byteString,packedArray[i]);
		}
		if(packedArray[i-1]!=0)
		{
			appendString(byteString,packedArray[i-1]);
		}
		if(packedArray[i-2]!=0)
		{
			appendString(byteString,packedArray[i-2]);
		}
	
	}
	
	for(i=119;i>=0;i--)/*Sets the GMUSCII back to ASCII*/
	{
		
		for(a=0;a<6;a++)
		{
			switch(a)
			{
			case 5: mask = 0x01;
				break;
			case 4: mask = 0x02;
				break;
			case 3: mask = 0x04;
		                break;
			case 2: mask = 0x08;
			        break;
			case 1: mask = 0x10;
			        break;
			case 0: mask = 0x20;
			        break;
			}
			
			if(byteString[(6*i)+a]=='1')
			{
				unpackedArray[i] = unpackedArray[i] ^ mask;
			}
		}
		
		unpackedArray[i] = SMSToChar(unpackedArray[i]);
	}
	i=159;
	while(i>=0)/*Prints the unpackedArray*/
	{	
		if(unpackedArray[i]!=0)
			printf("%c",unpackedArray[i]);
		i--;
		
	}
	printf("\n");
	fclose(file);
}


void appendString(char *original, unsigned char byte)/*Appends the byte variable to the original variable in the form of binary*/
{
	char *c = malloc(sizeof(char));
	int i = 7, mask = 0x00, byte0 = 0;

	for(;i>=0;i--)
	{
		switch(i)
		{
		case 0: mask = 0x01;
			break;
		case 1: mask = 0x02;
			break;
		case 2: mask = 0x04;
			break;
		case 3: mask = 0x08;
			break;
		case 4: mask = 0x10;
			break;
		case 5: mask = 0x20;
			break;
		case 6: mask = 0x40;
			break;
		case 7: mask = 0x80;
			break;
		}
		byte0 = byte & mask ? 1 : 0;
		itoa(byte0,c);
		strcat(original,c);
	}
}

void itoa(int n, char *s)/*Sets s string to 1 or 0 based on n*/
{
	if(n==0)
	{	
		s[0] = '0';
	}
	else
	{
		s[0] = '1';
	}
}
