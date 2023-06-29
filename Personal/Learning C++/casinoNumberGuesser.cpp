#include <iostream>
#include <random>

int main(){
    int chances = 5;
    int number = 0;
    int guess = 0;
    number = rand()%100+1;
    
    while(guess!=number && chances>0){
        std::cout <<"Try and guess the number between 1 and 100 You currently have "<< chances<<" tries left: ";
        std::cin >> guess;
        if(guess>number){
            std::cout <<"Too big!\n";
        }
        else if(guess<number){
            std::cout <<"Too small!\n";
        }
        else{
            std::cout <<"Right on the money!\n";
        }
        chances--;
    }
    if(chances>0){
        std::cout<<"Congrats on guessing the number!";
    }
    else{
        std::cout<<"Better luck next time.";

    }

}