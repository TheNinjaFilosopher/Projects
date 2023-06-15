import java.util.ArrayList;

public class TeamEvaluator extends Evaluator {
   
   //Default constructor
   public TeamEvaluator() {}
   
   //Given team name return all players on NBA Top 100 and their statistics 
   @Override
   public ArrayList evaluate(String team) {
      //What is being returned
      ArrayList ans = new ArrayList();
      
      //Access data from text document using TextReader class
      TextReader reader = new TextReader();
      ArrayList<String> players = new ArrayList<String>();
      players = reader.getPlayersByTeam(team);  //Get all players on given team
      
      //Loop through each player on team in the NBA Top 100
      for(String player : players) {
         //Create temp ArrayList to be added to answer array
         ArrayList temp = new ArrayList();   //Temp array returns [Player Name,PPG, RPG, APG, FGP, FTP, PER]
         temp.add(player);
         
         //Add each statistic type to temp array
         for(Object stat : reader.getAllPlayerStats(player)) {
            temp.add(stat);
         }
         ans.add(temp);
      }
      return ans;
   }
   
   public static void main(String [] args) {
      TeamEvaluator te = new TeamEvaluator();
      System.out.print(te.evaluate("Los Angeles Lakers"));
   }

}