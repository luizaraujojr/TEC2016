package br.unirio.overwork.instance.report;

import java.util.ArrayList;
import java.util.List;

public class Report
{
	private List<ReportDataFunction> dataFunctions;
	
	private List<ReportTransactionFunction> transactionFunctions;
	
	public Report()
	{
		this.dataFunctions = new ArrayList<ReportDataFunction>();
		this.transactionFunctions = new ArrayList<ReportTransactionFunction>();
	}
	
	public int countDataFunction()
	{
		return dataFunctions.size();
	}
	
	public ReportDataFunction getDataFunctionIndex(int index)
	{
		return dataFunctions.get(index);
	}
	
	public ReportDataFunction getDataFunctionName(String name)
	{
		for (ReportDataFunction df : dataFunctions)
			if (df.getName().compareToIgnoreCase(name) == 0)
				return df;
		
		return null;
	}
	
	public void addDataFunction(String name, int rets, int dets, int functionPoints)
	{
		dataFunctions.add(new ReportDataFunction(name, rets, dets, functionPoints));
	}
	
	public Iterable<ReportDataFunction> getDataFunctions()
	{
		return dataFunctions;
	}
	
	public int countTransactionFunction()
	{
		return transactionFunctions.size();
	}
	
	public ReportTransactionFunction getTransactionFunctionIndex(int index)
	{
		return transactionFunctions.get(index);
	}
	
	public ReportTransactionFunction getTransactionFunctionName(String name)
	{
		for (ReportTransactionFunction tf : transactionFunctions)
			if (tf.getName().compareToIgnoreCase(name) == 0)
				return tf;
		
		return null;
	}
	
	public void addTransactionFunction(String name, int ftrs, int dets, int functionPoints)
	{
		transactionFunctions.add(new ReportTransactionFunction(name, ftrs, dets, functionPoints));
	}
	
	public Iterable<ReportTransactionFunction> getTransactionFunctions()
	{
		return transactionFunctions;
	}
}