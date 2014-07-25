package br.unirio.optimization.analysis;

import java.util.Locale;

import unirio.experiments.multiobjective.analyzer.MultiExperimentAnalyzer;
import unirio.experiments.multiobjective.reader.ExperimentFileReader;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;

public class MainProgram
{
	public static final void main(String[] args) throws Exception
	{
		Locale.setDefault(new Locale("en", "US"));
		//analyzeStopCriteria();
		//analyzePopulationSize();
		//analyzeRandomSearch();
		analyzeOMS();
	}

	protected static void analyzeStopCriteria() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga5k", "data/result/NSGA/5K/nsga5k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga10k", "data/result/NSGA/10K/nsga10k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga20k", "data/result/NSGA/20K/nsga20k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	protected static void analyzePopulationSize() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga50k2x", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k4x", "data/result/NSGA/50K/4X/nsga50k4x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("nsga50k8x", "data/result/NSGA/50K/8X/nsga50k8x.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	protected static void analyzeRandomSearch() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("nsga50k2x", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("rs50k", "data/result/RS/50K/rs50k.txt", 6, 50, 3));
		analyzer.analyzeCycleFrontiers();
	}

	protected static void analyzeOMS() throws ExperimentFileReaderException, Exception
	{
		ExperimentFileReader reader = new ExperimentFileReader();
		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("GA", "data/result/NSGA/50K/2X/nsga50k2x.txt", 6, 50, 3));
		analyzer.addExperimentResult(reader.execute("MAR", "data/result/OMS/Margarine/Margarine.txt", 6, 1, 3));
		analyzer.addExperimentResult(reader.execute("SH", "data/result/OMS/SecondHalf/SecondHalf.txt", 6, 1, 3));
		analyzer.addExperimentResult(reader.execute("CPM", "data/result/OMS/CPM/CPM.txt", 6, 1, 3));
		analyzer.analyzeCycleFrontiers();
	}
}