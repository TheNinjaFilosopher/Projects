// AUTHOR: KUNAL SINGH KHURANA

/** 
 * class TeamComparator 
  * Author: Kunal Singh Khurana
  * Concrete subclass of the abstract class Comparator.
  * It implements the abstract method compare() */

import java.util.*;

public class TeamComparator extends Comparator{
  
  // Default constructor
  public TeamComparator(){}
  
  /** The following method is an implementation of the abstract method inherited from the Comparator class.
    * @param homeTeam is the String name of the home team
    * @param awayTeam is the String name of the away team
    * @return result, which is a String stating which team is favored to win along with the calcualted odds. 
    * Each player's talent score is 101 minus their rank. 
    * Sum the talent scores, determine which team has a higher overall talent score, and use proportions to find odds.
    */
  
  @SuppressWarnings("unchecked")
  
  public String compare(String homeTeam, String awayTeam){
    int homeTeamTotalTalentScore = 0;
    int awayTeamTotalTalentScore = 0;
    TextReader t = new TextReader();
    ArrayList<String> homeTeamPlayers = t.getPlayersByTeam(homeTeam);
    ArrayList<String> awayTeamPlayers = t.getPlayersByTeam(awayTeam);
    
    for (String player: homeTeamPlayers){
      //ArrayList<String> playerStatArray = t.getPlayerStat(player, "RANK");
      String stringRank = t.getPlayerStat(player, "RANK"); //= playerStatArray.get(0);
      int rank = Integer.parseInt(stringRank);
      int playerTalentScore = 1 - rank;
      homeTeamTotalTalentScore += playerTalentScore;}
    
    for (String player: awayTeamPlayers){
      ArrayList<String> playerStatArray = t.getPlayerStat(player, "RANK");
      String stringRank = playerStatArray.get(0);
      int rank = Integer.parseInt(stringRank);
      int playerTalentScore = 1 - rank;
      awayTeamTotalTalentScore += playerTalentScore;}
    
    int sum = homeTeamTotalTalentScore + awayTeamTotalTalentScore;
    int homeTeamOdds = (int) (100 * homeTeamTotalTalentScore/sum);
    homeTeamOdds += 5;
    
    
    // In the real world, the odds of winning are never truly 100%. 
    // If 100% is the calculated result, 99% will be returned instead. 
    
    if (homeTeamOdds >= 100){
     homeTeamOdds = 99; 
    }
    
    int awayTeamOdds = 100 - homeTeamOdds;
    
    String favorite = "";
    int oddsDifference = homeTeamOdds - awayTeamOdds;
    if (oddsDifference < 0){
      oddsDifference = awayTeamOdds - homeTeamOdds;
      favorite = awayTeam;
    }
    else{
      favorite = homeTeam;}
    
    String result = "Likelihood of Winning" + "\n";
    result += "-----------------------------" + "\n";
    result += homeTeam + ": " + homeTeamOdds + " %" + "\n";
    result += awayTeam + ": " + awayTeamOdds + " %" + "\n"; 
    result += "The " + favorite + " are favored to win by " + oddsDifference + " %.";
    
    System.out.print(result);
    return result;
    
  }
  
  
}