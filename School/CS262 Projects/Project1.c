#include <stdio.h>
#include <stdlib.h>

#define NUM_GOOSE_SPACES 3
#define NUM_BRIDGE_SPACES 1
#define NUM_MAZE_SPACES 2
#define NUM_SKULL_SPACES 1

const int gooseSpaces[NUM_GOOSE_SPACES] = {7,11,15};
const int bridgeSpaces[NUM_BRIDGE_SPACES] = {6};
const int mazeSpaces[NUM_MAZE_SPACES] = {13,20};
const int skullSpaces[NUM_SKULL_SPACES] = {23};

int roll();
void print(int humanPos, int computerPos);
int changePos(int playerPos);

int main()
{
	int humanRoll = 0, computerRoll = 0, humanPos = 1, computerPos = 1;
	int seed = 0;
	char play = ' ';
	char buffer[100];
	printf("Enter a number for the random seed: ");
	fgets(buffer,100,stdin);
	sscanf(buffer,"%d",&seed);
	srandom(seed);
	do
	{
		printf("Enter P to play or Q to quit: ");
		fgets(buffer,100,stdin);
		sscanf(buffer,"%c",&play);
		switch(play)	
		{
		case 'p':
		case 'P': 
			break;
		case 'q':
		case 'Q':
			return 0;
		}
		if(play!='p'&&play!='P')
		{
			printf("Incorrect input. Please try again: ");
		}
	}
	while(play!='p'&&play!='P');
	
	printf("HUMAN PLAYER, Press <enter> to roll the dice\n");
	fgets(buffer,100,stdin);
	humanRoll = roll();
	printf("COMPUTER PLAYER, Press <enter> to roll the dice\n");
	fgets(buffer,100,stdin);
	computerRoll = roll();
	while(humanRoll == computerRoll)
	{
		printf("There was a tie. Please try again.\n");
		printf("HUMAN PLAYER, Press <enter> to roll the dice\n");
		fgets(buffer,100,stdin);
		humanRoll = roll();
		printf("COMPUTER PLAYER, Press <enter> to roll the dice\n");
		fgets(buffer,100,stdin);
		computerRoll = roll();
	}
	if(humanRoll>computerRoll)
	{
		printf("HUMAN PLAYER GOES FIRST\n");
		while(humanPos!=24&&computerPos!=24)
		{
			printf("HUMAN PLAYERS TURN, [%d]... Press <enter> to roll the dice\n",humanPos);
			fgets(buffer,100,stdin);
			humanPos = changePos(humanPos);
			print(humanPos,computerPos);
			if(humanPos==24)
			{
				printf("Human Player Wins");
				continue;
			}
			printf("COMPUTER PLAYERS TURN, [%d]... Press <enter> to roll the dice\n",computerPos);
			fgets(buffer,100,stdin);
			computerPos = changePos(computerPos);
			print(humanPos,computerPos);
		}
			if(computerPos == 24)
				printf("Computer Player Wins\n");
	}
	else
	{
		printf("COMPUTER PLAYER GOES FIRST\n");
                while(humanPos!=24&&computerPos!=24)
                {
                        printf("COMPUTER PLAYERS TURN, [%d]... Press <enter> to roll the dice\n",computerPos);
			fgets(buffer,100,stdin);
                        computerPos = changePos(computerPos);
                        print(humanPos,computerPos);
                        if(computerPos==24)
                        {
                                printf("Computer Player Wins\n");
                                continue;
                        }
                        printf("HUMAN PLAYERS TURN, [%d]... Press <enter> to roll the dice\n",humanPos);
			fgets(buffer,100,stdin);
                        humanPos = changePos(humanPos);
                        print(humanPos,computerPos);
                }
                        if(humanPos == 24)
                                printf("Human Player Wins\n");
	}
	printf("Thank you for playing!\n");
	printf("Press <enter> to return to the main menu.\n");
	fgets(buffer,100,stdin);
	main();
	return 0;
}

int roll()
{
	int dice1 = 0,dice2 = 0,total = 0;
	dice1 = (random()%6)+1;
	dice2 = (random()%6)+1;
	total = dice1+dice2;
	printf("%d and %d for a %d\n", dice1,dice2,total);
	return total;
}

void print(int humanPos, int computerPos)
{
	int i = 0;
	for(;i<24;i++)
	{
		if((i+1)==24)
		{
			if(24==humanPos)
			{
				printf("<$>\n");
				continue;
			}
			if(24==computerPos)
			{
				printf("<%%>\n");
				continue;
			}
			printf("<24>\n");
			continue;
		}
		switch(i+1)
		{
		case 7:
		case 11:
		case 15: printf("+");
			break;
		case 6: printf("*");
			break;
		case 13:
		case 20: printf("-");
			break;
		case 23: printf("!");
			break;
		}
		printf("[");
		if((i+1)==humanPos)
		{
			printf("$");
			if((i+1)==computerPos)
				printf("%%");
			printf("]\t");
			if((i+1)==12)
				printf("\n");
			continue;
		}
		if((i+1)==computerPos)
		{
			printf("%%]\t");
			if((i+1)==12)
				printf("\n");
			continue;
		}
		if((i+1)==12)
		{
			printf("12]\n");
			continue;
		}
		else
		{
			printf("%d",(i+1));
		}
		printf("]\t");
	}
}

int changePos(int playerPos)
{
	int addRoll = roll();
	int isSpecial = 0;/*Boolean Statement*/
	int i = 0;
	int tempPos = playerPos;
	while(isSpecial==0)
	{
		switch(tempPos+addRoll)
		{
		case 7:
		case 11:
		case 15: printf("Landed on a %d\n",(tempPos+addRoll));
			addRoll+=addRoll;
			printf("New Position is %d\n",(tempPos+addRoll));
			break;
		case 6: printf("New Position is 12\n");
			return 12;
		case 13:
		case 20: printf("Landed on a %d\n",(tempPos+addRoll));
			printf("Going back to original space of %d\n",playerPos);
			return playerPos;
		case 23: printf("Hi Ho, to spot 1 you go!\n");
			return 1;
		default: isSpecial = 1;
		}
	}
	if(addRoll+playerPos>24)
	{
		int start = playerPos;
		for(;i<addRoll;start++,i++)
		{
			if(start==24)
			{
				addRoll-=i;
				playerPos = 24;
				playerPos-=addRoll;
				printf("New Position is %d\n",playerPos);
				switch(playerPos)
				{
				case 15: addRoll+=addRoll;
					printf("New Position is %d\n", (playerPos+addRoll));
					break;
				case 20: printf("Going back to original space of %d\n",playerPos);
					return tempPos;
				case 23: printf("Hi Ho, to spot 1 you go!\n");
					return 1;
				}
			}
			return playerPos;
		}
	}
	playerPos += addRoll;
	printf("New Position is %d\n",playerPos);
	return playerPos;
}










