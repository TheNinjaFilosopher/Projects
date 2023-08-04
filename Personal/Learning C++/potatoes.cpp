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

void incrementPotatoes();
void incrementPotatoesAndDestiny();
void incrementDestinyAndOrcs();
void incrementOrcsAndDecrementPotatoes();
void decrementPotatoes();
void incrementPotatoesTwice();
void incrementOrcs();
void incrementDestiny();
void incrementOrcsTwiceAndDecrementPotatoes();

// Game ends when any of these counters reach 10
int destinyCounter {};//You get whisked off on an adventure
int potatoesCounter {};//You hunker down and eat potatoes
int orcsCounter {};//You get eaten by orcs
int potatoesToOrcs {1}; //This counter represents the number of potatoes you need to expend to get rid of 1 orc

int main(){
    srand(time(0));
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
    srand(time(0));
    int dieRoll = rand()%6+1;
    switch (dieRoll)
    {
    case 1:
        //Increment potatoes
        //std::cout<<"You happily root about all day in your garden, gaining 1 potato.\n";
        incrementPotatoes();
        potatoesCounter++;
        break;
    case 2:
        //Increment potatoes and destiny
        //std::cout<<"You narrowly avoid a visitor by hiding in a potato sack, gaining 1 potato and 1 destiny.\n";
        incrementPotatoesAndDestiny();
        potatoesCounter++;
        destinyCounter++;
        break;
    case 3:
        //increment destiny and orcs
        //std::cout<<"A hooded stranger lingers outside your farm. You gain 1 destiny and 1 orc.\n";
        incrementDestinyAndOrcs();
        destinyCounter++;
        orcsCounter++;
        break;
    case 4:
        //increment orcs and decrement potatoes
        //std::cout<<"Your field is ravaged inthe night by unseen enemies. You gain 1 orc and lose 1 potato.\n";
        incrementOrcsAndDecrementPotatoes();
        orcsCounter++;
        potatoesCounter--;
        break;
    case 5:
        //decrement potatoes
        //std::cout<<"You trade potatoes for other delicious foodstuffs, losing 1 potato.\n";
        decrementPotatoes();
        potatoesCounter--;
        break;
    case 6:
        //Increment potatoes twice
        //std::cout<<"You burrow into a bumper crop of potatoes. Do you cry with joy? Possibly. Either way, you gain 2 potatoes.\n";
        incrementPotatoesTwice();
        potatoesCounter+=2;
        break;
    }
}

void aKnockAtTheDoor(){
    srand(time(0));
    int dieRoll = rand()%6+1;
    switch (dieRoll)
    {
    case 1:
        //Increment orcs
        //std::cout<<"A distant cousin. They are after your potatoes. They may snitch on you. You gain 1 orc.\n";
        incrementOrcs();
        orcsCounter++;
        break;
    case 2:
        //Increment destiny
        //std::cout<<"A dwarven stranger. You refuse them entry. Ghastly creatures. You gain 1 destiny.\n";
        incrementDestiny();
        destinyCounter++;
        break;
    case 3:
        //increment destiny and orcs
        //std::cout<<"A wizard strolls by. You pointedly draw the curtains, gaining 1 orc and 1 destiny.\n";
        incrementDestinyAndOrcs();
        destinyCounter++;
        orcsCounter++;
        break;
    case 4:
        //increment orcs twice and decrement potatoes
        //std::cout<<"There are rumors of war in the reaches. You eat some potatoes, losing 1 potato and gaining 2 orcs.\n";
        incrementOrcsTwiceAndDecrementPotatoes();
        orcsCounter+=2;
        potatoesCounter--;
        break;
    case 5:
        //increment destiny
        //std::cout<<"It's an elf. They are not serious people. You gain 1 destiny.\n";
        incrementDestiny();
        destinyCounter++;
        break;
    case 6:
        //Increment potatoes twice
        //std::cout<<"It's a sack of potatoes from a generous neighbor. You really must remember to pay them a visit one of these years. You gain 2 potatoes.\n";
        incrementPotatoesTwice();
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


//For these, let's do a d4 die roll for four options each.
void incrementPotatoes(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"You happily root about all day in your garden, gaining 1 potato.";
            break;
        case 2:
            std::cout<<"A neighborhood boy throws a stone at you. Upon coming to, you discover that it's not a stone, but a potato!";
            break;
        case 3:
            std::cout<<"While on a visit to your distant cousin, you notice that they have a particularly large sack of potatoes just lying around. Surely they wouldn't notice if one were missing.";
            break;
        case 4:
            std::cout<<"A long distance lover of yours sends you a potato in a Dear John letter. You gain one potato.";
            break;
    }
    std::cout<<"\n";
}

void incrementPotatoesAndDestiny(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"You narrowly avoid a visitor by hiding in a potato sack, gaining 1 potato and 1 destiny.";
            break;
        case 2:
            std::cout<<"A dwarf runs up to you, shouting desperately about repaying a debt. He tosses a sack of potatoes at you before running off. You gain 1 potato and 1 destiny.";
            break;
        case 3:
            std::cout<<"A strange omen occurs, where it rains squash, berries, and potatoes. Sadly, you slept in and your neighbors get most of the bounty You gain 1 potato and 1 destiny.";
            break;
        case 4:
            std::cout<<"You find a potato with the entire manifesto of the Dark Lord written upon it. Frightening, but a tuber is a tuber. You gain 1 potato and 1 destiny.";
            break;
    }
    std::cout<<"\n";
}

void incrementDestinyAndOrcs(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"A wizard strolls by. You pointedly draw the curtains, gaining 1 orc and 1 destiny.";
            break;
        case 2:
            std::cout<<"A human runs through the center of town, wounded and shouting about orcs. He chokes on blood and is buried in an unmarked grave. You gain 1 orc and 1 destiny.";
            break;
        case 3:
            std::cout<<"A goblin comes through, demanding taxes. He's run out of town, but promises to return. You gain 1 orc and 1 destiny.";
            break;
        case 4:
            std::cout<<"You get a letter that an old friend of yours was kidnapped by orcs. You hide it under a pile of clothes and gain 1 orc and 1 destiny.";
            break;
    }
    std::cout<<"\n";
}

void incrementOrcsAndDecrementPotatoes(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"Your field is ravaged in the night by unseen enemies. You gain 1 orc and lose 1 potato.";
            break;
        case 2:
            std::cout<<"An orc breaks into your house and steals some of your potatoes while you hide under the bed. You gain 1 orc and lose 1 potato.";
            break;
        case 3:
            std::cout<<"You wake up in the middle of the night and throw a pot at what you think is an orc, but is actually just a sack of potatoes. The fear remains as you gain 1 orc and lose 1 potato.";
            break;
        case 4:
            std::cout<<"Your next door neighbor betrays the community and runs right into the arms of orcs for unknown reasons. Worst of all, they ran right through your garden while escaping! You gain 1 orc and lose 1 potato.";
            break;
    }
    std::cout<<"\n";
}

void decrementPotatoes(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"You trade potatoes for other delicious foodstuffs, losing 1 potato.";
            break;
        case 2:
            std::cout<<"You enter into a lottery to gain potatoes. Surely you'll hear back any day now. Any day now. You lose 1 potato.";
            break;
        case 3:
            std::cout<<"You start an all potatoes diet to lose weight, but end up congested, losing 1 potato.";
            break;
        case 4:
            std::cout<<"A raccoon gets into your potato storage and makes off with quite a few of them, causing you to lose 1 potato.";
            break;
    }
    std::cout<<"\n";
}

void incrementPotatoesTwice(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"It's a sack of potatoes from a generous neighbor. You really must remember to pay them a visit one of these years. You gain 2 potatoes.";
            break;
        case 2:
            std::cout<<"You burrow into a bumper crop of potatoes. Do you cry with joy? Possibly. Either way, you gain 2 potatoes.";
            break;
        case 3:
            std::cout<<"An old friend of yours is eaten by orcs. Luckily, all their potatoes were left alone. You gain 2 potatoes.";
            break;
        case 4:
            std::cout<<"You won a potato lottery, gaining 2 potatoes!";
            break;
    }
    std::cout<<"\n";
}

void incrementOrcs(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"A distant cousin. They are after your potatoes. They may snitch on you. You gain 1 orc.";
            break;
        case 2:
            std::cout<<"Orc pinup calendars end up on your doorstep. They make you feel strange things and cause you to gain 1 orc.";
            break;
        case 3:
            std::cout<<"A group of wizards that live near the coast have caused an orc uprising elsewhere. This does not bode well. You gain 1 orc.";
            break;
        case 4:
            std::cout<<"Your pen pal's penmanship has taken on a strange, rough nature, almost like that of an orc. Luckily, they assure you that they have not been eaten by orcs, so you have nothing to worry about. You gain 1 orc.";
            break;
    }
    std::cout<<"\n";
}

void incrementDestiny(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"A dwarven stranger. You refuse them entry. Ghastly creatures. You gain 1 destiny.";
            break;
        case 2:
            std::cout<<"It's an elf. They are not serious people. You gain 1 destiny.";
            break;
        case 3:
            std::cout<<"A ranger rides through looking for a thief. You gain 1 destiny.";
            break;
        case 4:
            std::cout<<"A long bearded man who showed up when you were young comes for a visit and talks about adventure. You gain 1 destiny.";
            break;
    }
    std::cout<<"\n";
}

void incrementOrcsTwiceAndDecrementPotatoes(){
    srand(time(0));
    int dieRoll = rand()%4+1;//rolls 1 through 4.
    switch(dieRoll){
        case 1:
            std::cout<<"There are rumors of war in the reaches. You eat some potatoes, losing 1 potato and gaining 2 orcs.";
            break;
        case 2:
            std::cout<<"You find a goblin sleeping in your bed surrounded by half eaten potatoes. It runs away, promising to bring its big brother. You lose 1 potato and gain 2 orcs.";
            break;
        case 3:
            std::cout<<"A dark ritual is performed in a faraway land, causing crops to fail and the power of orcs to rise. You lose 1 potato and gain 2 orcs.";
            break;
        case 4:
            std::cout<<"Terror. Terror. Terror. Terror. Terror. The potatoes won't be enough. You lose 1 potato and gain 2 orcs.";
            break;
    }
    std::cout<<"\n";
}


