package br.unirio.overwork.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.overwork.simulation.LiveSimulationObject;
import br.unirio.overwork.simulation.SimulationObject;

/**
 * Class that represents an activity
 * 
 * @author Marcio Barros
 */
public abstract class Activity extends LiveSimulationObject
{
	/**
	 * List of activities that precede the current activity
	 */
	private List<Activity> precedences;
	
	/**
	 * Develop assigned to the activity
	 */
	private @Getter Developer developer;
	
	/**
	 * Number of errors remaining when the activity is finished
	 */
	protected @Getter double errors;

	/**
	 * Initializes an activity
	 */
	public Activity(String name)
	{
		super(name);
		this.precedences = new ArrayList<Activity>();
	}

	/**
	 * Sets the developer working in the activity
	 */
	public Activity setDeveloper(Developer developer)
	{
		this.developer = developer;
		return this;
	}

	/**
	 * Adds a precedent activity to the current one
	 */
	public Activity addPrecedent(Activity activity)
	{
		this.precedences.add(activity);
		return this;
	}

	/**
	 * Returns the precedent activities of the current one
	 */
	public Iterable<Activity> getPrecedences()
	{
		return precedences;
	}
	
	/**
	 * Counts the number of errors inherited from the precedent activities
	 */
	private double countPrecedentErrors()
	{
		double sum = 0.0;
		
		for (Activity activity : getPrecedences())
			sum += activity.getErrors();
		
		return sum;
	}

	/**
	 * Returns the list of dependencies for simulation
	 */
	@Override
	public List<SimulationObject> getDependencies()
	{
		List<SimulationObject> result = new ArrayList<SimulationObject>();
		result.addAll(precedences);
		result.add(developer);
		return result;
	}

	/**
	 * Method executed before the activity's life-cycle is started
	 */
	@Override
	public void beforeStart()
	{
		errors = countPrecedentErrors();
	}
	
	/**
	 * Method executed when the activity's life-cycle is running
	 */
	@Override
	public boolean liveStep()
	{
		double remainingWork = getRemainingWork();

		if (remainingWork >= 0.001)
		{
			double effortAvailable = developer.getEffortAvailable();
			double effortUsed = Math.min(effortAvailable, remainingWork);
			developer.setEffortAvailable(effortAvailable - effortUsed);
			consumeEffort(effortUsed);
			remainingWork = getRemainingWork();
		}
		
		return remainingWork >= 0.001;
	}
	
	/**
	 * Returns the amount of work to be performed
	 */
	protected abstract double getRemainingWork();
	
	/**
	 * Consumes an amount of effort
	 */
	protected abstract void consumeEffort(double effort);

	/**
	 * Presents the activity as a string
	 */
	@Override
	public String toString()
	{
		NumberFormat nf2 = new DecimalFormat("0.00");
		return getName() + "\t" + nf2.format(getFinishingSimulationTime()) + "\t" + nf2.format(errors);
	}
}