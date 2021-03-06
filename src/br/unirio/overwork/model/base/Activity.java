package br.unirio.overwork.model.base;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.simulation.SimulationObject;

/**
 * Class that represents an activity
 * 
 * @author Marcio Barros
 */
public abstract class Activity extends SimulationObject
{
	static final String TAB = "\t";
	
	/**
	 * Number of connected activities that occur before a activity  
	 */
	private @Getter @Setter int predecessorCount;
	
	/**
	 * Number of connected activities that occur after a activity 
	 */
	private @Getter @Setter int sucessorCount;
    	
	/**
	 * Project holding the activity
	 */
	private @Getter Project project;
	
	/**
	 * List of activities that precede the current activity
	 */
	private List<Activity> precedences;
	
	/**
	 * Developer assigned to the activity
	 */
	private @Getter Developer developer;
	
	/**
	 * Current cost of the activity
	 */
	private @Getter double cost;
	
	/**
	 * Current overworking hours spent in the activity
	 */
	private @Getter double overworkHours;
	
	/**
	 * Number of errors remaining when the activity is finished
	 */
	private @Getter @Setter double errors;
	
	/**
	 * Base developer's productivity for the activity
	 */
	private @Getter @Setter double productivity;
	
	/**
	 * Base developer's productivity for the activity
	 */
	private @Getter @Setter double effortMultiplier;
	
	/**
	 * Base developer's error generation rate for the activity
	 */
	private @Getter @Setter double errorGenerationRate;
	
	/**
	 * Simulation time when the activity was first executed
	 */
	private @Getter double startExecutionTime = -1.0;

	/**
	 * Indicates whether the activity has consumed effort on the current step
	 */
	private boolean consumedEffort;

	/**
	 * Initializes an activity
	 */
	public Activity(Project project, String name)
	{
		super(name);
		this.project = project;
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
	 * Indicates whether the activity has consumed some effort after a simulation step
	 */
	public boolean hasConsumedEffort()
	{
		return consumedEffort;
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
		return result;
	}

	/**
	 * Initializes the activity for another simulation round
	 */
	@Override
	public void init() 
	{
		this.startExecutionTime = -1.0;
	}
	
	/**
	 * Collects errors from former activities before when the activity's life-cycle starts
	 */
	@Override
	public void beforeStart()
	{
		this.cost = 0.0;
		this.overworkHours = 0.0;
		this.consumedEffort = false;
		this.errors = countPrecedentErrors();
	}
	
	/**
	 * Fetches developer characteristics before simulating
	 */
	@Override
	public void beforeStep()
	{
		this.productivity = developer.getProductivity();
		this.effortMultiplier = 1.0;
		this.errorGenerationRate = developer.getErrorGenerationRate();
	}
	
	/**
	 * Method executed when the activity's life-cycle is running
	 */
	@Override
	public boolean step()
	{
		double remainingWork = getRemainingWork();

		if (remainingWork < 0.001)
			return false;
		
		double effortAvailable = developer.getEffort().available();
		
		if (effortAvailable <= 0.0)
			return true;

		double effortAdjustment = this.productivity * this.effortMultiplier;
		double effortUsed = Math.min(effortAvailable, remainingWork / effortAdjustment);
		
		if (startExecutionTime < 0.0) 
			startExecutionTime = getCurrentSimulationTime();
		
		developer.getEffort().consume(effortUsed);
		consumeEffort(effortUsed * effortAdjustment);

		this.consumedEffort = true;
		this.cost += effortUsed * 8.0 * developer.getHourlyCost(); 

		if (this.effortMultiplier > 1.0)
		{
			this.overworkHours += effortUsed * (effortMultiplier - 1.0)/* * 8.0*/;
			
			if (this.effortMultiplier > 1.25)
		 
				this.cost += effortUsed * 8.0 * ((this.effortMultiplier - 1.25) * 1.25 + 0.25 * 1.20) * developer.getHourlyCost(); 
			
			else if (this.effortMultiplier > 1.0) 
				this.cost += effortUsed * 8.0 * ((this.effortMultiplier - 1.0) * 1.20) * developer.getHourlyCost(); 
		}
				
		remainingWork = getRemainingWork();
		return remainingWork >= 0.001;
	}
	
	/**
	 * Returns the amount of work to be performed
	 */
	public abstract double getRemainingWork();
	
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
		return getName() + "\t" + nf2.format(startExecutionTime) + "\t" + nf2.format(getFinishingTime()) + "\t" + nf2.format(errors) + "\t" + nf2.format(overworkHours) + "\t" + nf2.format(cost);
	}
}