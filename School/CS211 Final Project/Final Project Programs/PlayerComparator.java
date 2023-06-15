import java.util.*;

public class PlayerComparator extends Comparator {
	  public PlayerComparator(){
		    super();
	  }
		  
		  @SuppressWarnings("unchecked")
		  
		  public String compare(String player1, String player2, String statCategory){
		    String result = "";
		    TextReader t1 = new TextReader();
		    String player1Stat = t1.getPlayerStat(player1, statCategory);
		    String player2Stat = t1.getPlayerStat(player2, statCategory);
		    double player1StatNum = Double.parseDouble(player1Stat);
		    double player2StatNum = Double.parseDouble(player2Stat);
		    
		    String statTypeWords = "";
		    
		    if (statCategory == "PPG"){
		      statTypeWords = "points per game";}
		    else if (statCategory == "APG"){
		      statTypeWords = "assists per game";}
		    else if (statCategory == "RPG"){
		      statTypeWords = "rebounds per game";}
		    else{
		      statTypeWords = statCategory;}
		      
		    
		    if (player1StatNum > player2StatNum){
		      result = player1 + " averages more " + statTypeWords + " than " + player2 + ".";}
		    else if (player2StatNum > player1StatNum){
		      result = player2 + " averages more " + statTypeWords + " than " + player1 + ".";}
		    else if (player1StatNum == player2StatNum){
		      result = player1 + " and " + player2 + " average the same " + statTypeWords + ".";}
		    System.out.println(result);
		    return result;

		    }
		    
		  
		  public String compare(String player1, String player2){
		    String pointsComparison = this.compare(player1, player2, "PPG");
		    String reboundsComparison = this.compare(player1, player2, "RPG");
		    String assistsComparison = this.compare(player1, player2, "APG");
		    String fieldGoalComparison = this.compare(player1, player2, "FGP");
		    String freeThrowComparison = this.compare(player1, player2, "FTP");
		    String pERComparison = this.compare(player1, player2, "PER");
		    //Include overall rank comparison via getRank() method
		    
		    String result = pointsComparison + "\n" + reboundsComparison + "\n" + assistsComparison + "\n" + fieldGoalComparison + "\n" + freeThrowComparison + "\n" + pERComparison;
		    System.out.print(result);
		    return result;
		  }
}
