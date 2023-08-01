/*
Based on the one page RPG by Oliver Darkshine
Needs to include a random to roll the dice, as well as an option to spend potatoes
to get rid of orcs.
The first d6 will go to the grassAndMud function, which will then decide whether or
not to go to the inTheGarden function, the aKnockAtTheDoorFunction, or to increase
the potatoes to orc count.
*/
#include <iostream>
#include <random>

void printResults();
void inTheGarden();
void aKnockAtTheDoor();
void removeOrcs();
void grassAndMud(int dieRoll);

// Game ends when any of these counters reach 10
int destinyCounter {};//You get whisked off on an adventure
int potatoesCounter {};//You hunker down and eat potatoes
int orcsCounter {};//You get eaten by orcs
int potatoesToOrcs {1}; //This counter represents the number of potatoes you need to expend to get rid of 1 orc

int main(){
    int dieRoll = rand()%6+1; //The number that represents the die roll. Will always be six sided.
    char input = ' ';//The player input.
    while(input!='q'&&destinyCounter<10&&potatoesCounter<10&&orcsCounter<10){
        printResults();
        std::cout<<"Input p to spend potatoes to get rid of orcs, q to quit, or any other letter to continue: ";
        std::cin>>input;
        switch (tolower(input))
        {
        case 'p':
            removeOrcs();
            continue;
        case 'q':
            std::cout<<"Thanks for playing, hope you can finish another time!\n";
            exit(1);
        default:
            grassAndMud(dieRoll);
            break;
        }
        dieRoll = rand()%6+1;
    }
    if(orcsCounter>=10){
        std::cout<<"The orcs finally find your potato farm. Alas, orcs are not so interestd in potatoes as they are in eating you, and you end up in a cookpot.\n";
    }
    if(destinyCounter>=10){
        std::cout<<"An interfering bard or wizard turns up at your doorstep with a quest, and you are whisked away against your will on an adventure (unless you've already been eaten by orcs).\n";
    }
    if(potatoesCounter>=10){
        std::cout<<"You have enough potatoes that you can go underground and not return to the surface until the danger is past. You nestle down into your burrow and enjoy your well earned rest.\n";
    }
}

void removeOrcs(){
    if(orcsCounter<1){
        std::cout<<"There are no orcs to get rid of. Lucky you!\n";
        return;
    }
    if(potatoesCounter>=potatoesToOrcs){
        potatoesCounter-=potatoesToOrcs;
        orcsCounter--;
    }
    else{
        std::cout<<"You don't have enough potatoes to save yourself at the moment. Sorry.\n";
    }
}

void grassAndMud(int dieRoll){
    switch (dieRoll)
    {
    case 1:
    case 2:
        //Go to the inTheGarden function
        inTheGarden();
        break;
    case 3:
    case 4:
        //Go to the aKnockAtTheDoor function
        aKnockAtTheDoor();
        break;
    case 5:
    case 6:
        //increment the potatoesToOrcs counter
        std::cout<<"The world becomes a darker, more dangerous place. From now on, removing an orc costs an additional potato (This is cumulative).\n";
        potatoesToOrcs++;
        break;
    }
}

void inTheGarden(){
    int dieRoll = rand()%6+1;
    switch (dieRoll)
    {
    case 1:
        //Increment potatoes
        std::cout<<"You happily root about all day in your garden, gaining 1 potato.\n";
        potatoesCounter++;
        break;
    case 2:
        //Increment potatoes and destiny
        std::cout<<"You narrowly avoid a visitor by hiding in a potato sack, gaining 1 potato and 1 destiny.\n";
        potatoesCounter++;
        destinyCounter++;
        break;
    case 3:
        //increment destiny and orcs
        std::cout<<"A hooded stranger lingers outside your farm. You gain 1 destiny and 1 orc.\n";
        destinyCounter++;
        orcsCounter++;
        break;
    case 4:
        //increment orcs and decrement potatoes
        std::cout<<"Your field is ravaged inthe night by unseen enemies. You gain 1 orc and lose 1 potato.\n";
        orcsCounter++;
        potatoesCounter--;
        break;
    case 5:
        //decrement potatoes
        std::cout<<"You trae potatoes for other delicious foodstuffs, gaining 1 potato.\n";
        potatoesCounter--;
        break;
    case 6:
        //Increment potatoes twice
        std::cout<<"You burrow into a bumper crop of potatoes. Do you cry with joy? Possibly. Either way, you gain 2 potatoes.\n";
        potatoesCounter+=2;
        break;
    }
}

void aKnockAtTheDoor(){
    int dieRoll = rand()%6+1;
    switch (dieRoll)
    {
    case 1:
        //Increment orcs
        std::cout<<"A distant cousin. They are after your potatoes. They may snitch on you. You gain 1 orc.\n";
        orcsCounter++;
        break;
    case 2:
        //Increment destiny
        std::cout<<"A dwarven stranger. You refuse them entry. Ghastly creatures. You gain 1 destiny.\n";
        destinyCounter++;
        break;
    case 3:
        //increment destiny and orcs
        std::cout<<"A wizard strolls by. You pointedly draw the curtains, gaining 1 orc and 1 destiny.\n";
        destinyCounter++;
        orcsCounter++;
        break;
    case 4:
        //increment orcs twice and decrement potatoes
        std::cout<<"There are rumors of war in the reaches. You eat some potatoes, losing 1 potato and gaining 2 orcs.\n";
        orcsCounter+=2;
        potatoesCounter--;
        break;
    case 5:
        //increment destiny
        std::cout<<"It's an elf. They are not serious people. You gain 1 destiny.\n";
        destinyCounter++;
        break;
    case 6:
        //Increment potatoes twice
        std::cout<<"It's a sack of potatoes from a generous neighbor. You really must remember to pay them a visit one of these years. You gain 2 potatoes.\n";
        potatoesCounter+=2;
        break;
    }
}

void printResults(){
    std::cout<<"-------------------------------------------------------------------\n";
    std::cout<<"Your destiny score is "<<destinyCounter<<".\n";
    std::cout<<"Your potatoes score is "<<potatoesCounter<<".\n";
    std::cout<<"Your orcs score is "<<orcsCounter<<".\n";
    std::cout<<"It currently costs "<<potatoesToOrcs<<" potatoes to remove 1 orc.\n";
    std::cout<<"-------------------------------------------------------------------\n";
}