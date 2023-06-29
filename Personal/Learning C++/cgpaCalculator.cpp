#include <iostream>
//Program must show student's overall course grade, individual grades of each course, total credits and grade points achi

int main(){
    std::string className = "";
    double GPA = 0.0;
    while(true){
        std::cout << "Enter the name of your class, or press enter without typing in anything to see the results of your GPA:";
        std::cin >> className;
        if(className.length()>0 && (GPA>0&&GPA<4)){
            //
        }
        else{
            break;
        }
    }
    std::cin >> className;
}