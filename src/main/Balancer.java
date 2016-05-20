package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JTextField;
import javax.swing.JTextArea;


public class Balancer {

	private JFrame frmPortfolioBalancer;
	private JTextField symbolTextField;
	private JTextField unitsTextField;
	private JTextField marketPriceTextField;
	private JTextField purchasePriceTextField;
	private Portfolio portfolio = new Portfolio();
	private HashMap<String, Double> marketPrices = new HashMap<String, Double>();
	private HashMap<String, Integer> unitsToBalance = new HashMap<String, Integer>();
	private JTextArea currentPortfolioTextArea;
	private JTextField amountToContributeTextField;
	private JTextField targetAllocationTextField;
	private JTextArea purchasesToBalanceTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Balancer window = new Balancer();
					window.frmPortfolioBalancer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Balancer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPortfolioBalancer = new JFrame();
		frmPortfolioBalancer.setTitle("Portfolio Balancer");
		frmPortfolioBalancer.setBounds(100, 100, 696, 590);
		frmPortfolioBalancer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPortfolioBalancer.getContentPane().setLayout(null);
		
		JButton btnAddFund = new JButton("Add fund");
		btnAddFund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String symbol = symbolTextField.getText();
				final int units = Integer.parseInt(unitsTextField.getText());
				final double marketPrice = Double.parseDouble(marketPriceTextField.getText());
				final double purchasePrice = Double.parseDouble(purchasePriceTextField.getText());
				final double targetAllocation = Double.parseDouble(targetAllocationTextField.getText());
				final Fund fund = new Fund(symbol, marketPrice);
				portfolio.addFund(fund, units, targetAllocation);
				marketPrices.put(symbol, purchasePrice);
				updateCurrentPortfolioTextArea();
			}
		});
		btnAddFund.setBounds(552, 38, 118, 23);
		frmPortfolioBalancer.getContentPane().add(btnAddFund);
		
		JButton btnClearFunds = new JButton("Clear portfolio");
		btnClearFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				updateCurrentPortfolioTextArea();
			}
		});
		btnClearFunds.setBounds(552, 63, 118, 23);
		frmPortfolioBalancer.getContentPane().add(btnClearFunds);
		
		JLabel lblCurrentPortfolio = new JLabel("Current Portfolio");
		lblCurrentPortfolio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrentPortfolio.setBounds(10, 68, 122, 28);
		frmPortfolioBalancer.getContentPane().add(lblCurrentPortfolio);
		
		JLabel lblPurchasesToBalance = new JLabel("Purchases to balance");
		lblPurchasesToBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPurchasesToBalance.setBounds(10, 325, 163, 28);
		frmPortfolioBalancer.getContentPane().add(lblPurchasesToBalance);
		
		JButton btnBalance = new JButton("Balance");
		btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculateUnitsToBalance();
				updatePurchasesToBalanceTextArea();
			}
		});
		btnBalance.setBounds(267, 310, 89, 23);
		frmPortfolioBalancer.getContentPane().add(btnBalance);
		
		JLabel lblSymbol = new JLabel("Symbol");
		lblSymbol.setBounds(10, 24, 73, 14);
		frmPortfolioBalancer.getContentPane().add(lblSymbol);
		
		JLabel lblUnits = new JLabel("Units");
		lblUnits.setBounds(115, 24, 58, 14);
		frmPortfolioBalancer.getContentPane().add(lblUnits);
		
		JLabel lblMarketPrice = new JLabel("Market price");
		lblMarketPrice.setBounds(211, 24, 122, 14);
		frmPortfolioBalancer.getContentPane().add(lblMarketPrice);
		
		JLabel lblPurchasePrice = new JLabel("Purchase price");
		lblPurchasePrice.setBounds(314, 24, 89, 14);
		frmPortfolioBalancer.getContentPane().add(lblPurchasePrice);
		
		JLabel lblAddFundsIn = new JLabel("Add funds in portfolio");
		lblAddFundsIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddFundsIn.setBounds(10, 11, 214, 14);
		frmPortfolioBalancer.getContentPane().add(lblAddFundsIn);
		
		symbolTextField = new JTextField();
		symbolTextField.setToolTipText("Symbol of the fund");
		symbolTextField.setBounds(10, 39, 73, 20);
		frmPortfolioBalancer.getContentPane().add(symbolTextField);
		symbolTextField.setColumns(10);
		
		unitsTextField = new JTextField();
		unitsTextField.setToolTipText("Units of the fund owned");
		unitsTextField.setBounds(115, 39, 58, 20);
		frmPortfolioBalancer.getContentPane().add(unitsTextField);
		unitsTextField.setColumns(10);
		
		marketPriceTextField = new JTextField();
		marketPriceTextField.setToolTipText("Current market price of fund");
		marketPriceTextField.setBounds(211, 39, 86, 20);
		frmPortfolioBalancer.getContentPane().add(marketPriceTextField);
		marketPriceTextField.setColumns(10);
		
		purchasePriceTextField = new JTextField();
		purchasePriceTextField.setToolTipText("Purchase price of fund per unit");
		purchasePriceTextField.setBounds(314, 39, 89, 20);
		frmPortfolioBalancer.getContentPane().add(purchasePriceTextField);
		purchasePriceTextField.setColumns(10);
		
		currentPortfolioTextArea = new JTextArea();
		currentPortfolioTextArea.setEditable(false);
		currentPortfolioTextArea.setBounds(10, 96, 660, 169);
		frmPortfolioBalancer.getContentPane().add(currentPortfolioTextArea);
		
		purchasesToBalanceTextArea = new JTextArea();
		purchasesToBalanceTextArea.setEditable(false);
		purchasesToBalanceTextArea.setBounds(10, 359, 660, 169);
		frmPortfolioBalancer.getContentPane().add(purchasesToBalanceTextArea);
		
		JLabel lblAmountToContribute = new JLabel("Amount to contribute:");
		lblAmountToContribute.setBounds(10, 276, 136, 14);
		frmPortfolioBalancer.getContentPane().add(lblAmountToContribute);
		
		amountToContributeTextField = new JTextField();
		amountToContributeTextField.setBounds(136, 273, 195, 20);
		frmPortfolioBalancer.getContentPane().add(amountToContributeTextField);
		amountToContributeTextField.setColumns(10);
		
		JLabel lblTargetAllocation = new JLabel("Target allocation %");
		lblTargetAllocation.setBounds(429, 24, 116, 14);
		frmPortfolioBalancer.getContentPane().add(lblTargetAllocation);
		
		targetAllocationTextField = new JTextField();
		targetAllocationTextField.setBounds(429, 39, 86, 20);
		frmPortfolioBalancer.getContentPane().add(targetAllocationTextField);
		targetAllocationTextField.setColumns(10);
	}
	
	private void updateCurrentPortfolioTextArea()
	{
		currentPortfolioTextArea.setText(portfolio.toString());
	}
	
	private void calculateUnitsToBalance()
	{
		final double contribution = Double.parseDouble(amountToContributeTextField.getText());
		unitsToBalance = PortfolioBalancer.getUnitsToBalancePortfolio(portfolio, contribution, marketPrices);
	}
	
	private void updatePurchasesToBalanceTextArea()
	{
		purchasesToBalanceTextArea.setText(unitsToBalanceToString());
	}
	
	private String unitsToBalanceToString()
	{
		StringBuilder sb = new StringBuilder("Purchases to balance: \n");
		for (Entry<String, Integer> fund: unitsToBalance.entrySet())
		{
			final String symbol = fund.getKey();
			final int units = fund.getValue();
			sb.append(Integer.toString(units)).append(" units of ").append(symbol).append("\n");
		}
		return sb.toString();
	}
	
	private void clear()
	{
		portfolio.clear();
		marketPrices.clear();
		unitsToBalance.clear();
	}
}
