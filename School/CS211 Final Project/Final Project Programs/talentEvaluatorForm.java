import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class talentEvaluatorForm {

	private JFrame frmNbaTextEvaluator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					talentEvaluatorForm window = new talentEvaluatorForm();
					window.frmNbaTextEvaluator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public talentEvaluatorForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNbaTextEvaluator = new JFrame();
		frmNbaTextEvaluator.setTitle("NBA Text Evaluator");
		frmNbaTextEvaluator.getContentPane().setName("");
		frmNbaTextEvaluator.setPreferredSize(new Dimension(816, 489));
		frmNbaTextEvaluator.setSize(new Dimension(816, 489));
		frmNbaTextEvaluator.setBounds(100, 100, 555, 380);
		frmNbaTextEvaluator.setSize(816, 489);
		frmNbaTextEvaluator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNbaTextEvaluator.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("NBA Talent Evaluator");
		panel.setName("nbaTitleText1");
		panel.setSize(new Dimension(800, 66));
		panel.setLocation(new Point(0, 0));
		panel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 48));
		
		frmNbaTextEvaluator.getContentPane().add(panel);
		
		JLabel lblNbaTextEvaluator = new JLabel("NBA TEXT EVALUATOR");
		lblNbaTextEvaluator.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 48));
		panel.add(lblNbaTextEvaluator);
		
		JButton btnAnalyzeTeam = new JButton("Analyze Team");
		btnAnalyzeTeam.setName("btnAnalyzeTeam");
		btnAnalyzeTeam.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 31));
		btnAnalyzeTeam.setSize(new Dimension(231, 76));
		btnAnalyzeTeam.setLocation(new Point(81, 159));
		btnAnalyzeTeam.setBounds(75, 137, 231, 76);
		frmNbaTextEvaluator.getContentPane().add(btnAnalyzeTeam);
		
		JButton btnAnalyzePlayer = new JButton("Analyze Player");
		btnAnalyzePlayer.setSize(new Dimension(231, 76));
		btnAnalyzePlayer.setName("btnAnalyzeTeam");
		btnAnalyzePlayer.setLocation(new Point(81, 159));
		btnAnalyzePlayer.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 29));
		btnAnalyzePlayer.setBounds(413, 137, 231, 76);
		frmNbaTextEvaluator.getContentPane().add(btnAnalyzePlayer);
		
		JButton btnCompareTeam = new JButton("Compare Team");
		btnCompareTeam.setSize(new Dimension(231, 76));
		btnCompareTeam.setName("btnAnalyzeTeam");
		btnCompareTeam.setLocation(new Point(81, 159));
		btnCompareTeam.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 28));
		btnCompareTeam.setBounds(75, 278, 231, 76);
		frmNbaTextEvaluator.getContentPane().add(btnCompareTeam);
		
		JButton btnComparePlayer = new JButton("Compare Player");
		btnComparePlayer.setSize(new Dimension(231, 76));
		btnComparePlayer.setName("btnAnalyzeTeam");
		btnComparePlayer.setLocation(new Point(81, 159));
		btnComparePlayer.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 27));
		btnComparePlayer.setBounds(413, 278, 231, 76);
		frmNbaTextEvaluator.getContentPane().add(btnComparePlayer);
	}
}
