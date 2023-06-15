import java.util.Collections;
import java.util.ArrayList;

public class PlayerEvaluator extends Evaluator {
	   public PlayerEvaluator() {}
	   
	   //Return a players rank in each stat type given player name
	   @Override
	   public ArrayList evaluate(String player) {
	      ArrayList ans = new ArrayList();
	      TextReader tr = new TextReader();
	      
	      String stats[] = {"PPG", "RPG", "APG", "FGP", "FTP", "PER"};
	      
	      for(String s : stats) {
	         String temp = s + " Ranking: " + statRanking(player, s);
	         ans.add(temp);
	      }
	      
	      return ans;
	   }
	   
	   //Return a players rank in each stat type given player rank
	   public ArrayList evaluate(int rank) {
	      ArrayList ans = new ArrayList();
	      
	      TextReader tr = new TextReader();
	      ArrayList players = tr.getStat("PLAYER");
	      
	      String player = players.get(rank).toString();
	      
	      ans.add(evaluate(player));
	      return ans;
	   }
	   
	   //Find ranking in certain stat type
	   public int statRanking(String player, String statType) {
	      TextReader tr = new TextReader();
	      
	      int ans = 0;
	      
	      //Formatting
	      String alps = tr.getPlayerStat(player, statType);
	      ArrayList allStat = tr.getStat(statType);
	      ArrayList allStats = new ArrayList();
	      
	      String sps = alps.toString();
	      //Remove brackets from array inputs
	      //sps = sps.substring(1, sps.length() - 1);
	      
	      //Remove percentage symbol for doubles
	      if(sps.contains("%")) {
	          sps = sps.substring(0, sps.length() - 1);
	       }
	      
	      double playerStat = Double.parseDouble(sps);
	            
	      for(Object stat : allStat) {
	         String temp = stat.toString();
	         if(temp.contains("%")) {
	            temp = temp.substring(0, temp.length() - 1);
	         }
	      
	         double d = Double.parseDouble(temp);
	         allStats.add(d);
	      }
	      
	      //Sort array
	      Collections.sort(allStats);
	            
	      for(int i = 0; i <= allStats.size() - 1; i++) {
	         if(allStats.get(i).equals(playerStat)) {
	            ans = i;
	         }
	      }
	      return 100 - ans;
	   }
	   
	   public static void main(String [] args) {
	      TextReader tr = new TextReader();
	      
	      PlayerEvaluator pe = new PlayerEvaluator();
	      System.out.println(pe.evaluate(1));
	      System.out.println(pe.evaluate("Lebron James"));
	      
	   }

}
