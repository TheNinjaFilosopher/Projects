#include <iostream>
#include <random>
/*Stone Paper Scissor or Rock Paper Scissor is a game that is played between two people, Each player in this game forms one of three shapes. 
The winner will be decided as per the given rules:
    Rock vs Scissor -> Rock wins
    Rock vs Paper -> Paper wins
    Paper vs Scissor -> Scissor wins
In this game, the user is asked to make choices based on both computer and user choices and the results are displayed showing both computer and user choices.*/

/*Have the computer roll a random number, 1-3, pick R, P, or S based on that. Allow user to do the same and then compare numbers.*/
int main(){
    int computerRoll = 0;
    int playerRoll = 0;
    int playerScore = 0;
    int computerScore = 0;
    while(true){
        computerRoll = rand()%3+1;
        std::cout << "Pick a number for Rock Paper Scissors: 1 for Rock, 2 for Paper, 3 for Scissors, or -1 to quit: ";
        std::cin >> playerRoll;
        if(playerRoll==-1){
            std::cout << "Thank you so much for playing the game! The final score was "<<playerScore<<" for you and " <<computerScore<< " for the computer!\n";
            exit(1);
        }
        else if(playerRoll==1){
            if(computerRoll == 1){
                std::cout<<"You tied! Try again!\n";
            }
            else if(computerRoll==2){
                std::cout<<"You lose! Try again!\n";
                computerScore++;
            }
            else{
                std::cout<<"You win! Try again!\n";
                playerScore++;
            }
        }
        else if(playerRoll==2){
            if(computerRoll == 1){
                std::cout<<"You win! Try again!\n";
                playerScore++;
            }
            else if(computerRoll==2){
                std::cout<<"You tied! Try again!\n";
            }
            else{
                std::cout<<"You lose! Try again!\n";
                computerScore++;
            }
        }
        else if(playerRoll==3){
            if(computerRoll == 1){
                std::cout<<"You lose! Try again!\n";
                computerScore++;
            }
            else if(computerRoll==2){
                std::cout<<"You win! Try again!\n";
                playerScore++;
            }
            else{
                std::cout<<"You tied! Try again!\n";
            }
        }
        else{
            std::cout<<"Invalid Input, Try Again.\n";
        }
    }
}