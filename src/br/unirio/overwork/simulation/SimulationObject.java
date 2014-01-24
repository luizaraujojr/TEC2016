package br.unirio.overwork.simulation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents an abstract simulation object
 * 
 * @author Marcio Barros
 */
public abstract class SimulationObject
{
	/**
	 * Simulation interval
	 */
	protected static final double DT = 0.1;
	
	/**
	 * Object's name
	 */
	private @Getter String name;
	
	/**
	 * Current simulation time - set by the simulator
	 */
	private @Getter @Setter double currentSimulationTime;
	
	/**
	 * Initializes the simulation object
	 */
	public SimulationObject(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the objects that must be simulated before the current one
	 */
	public abstract List<SimulationObject> getDependencies();
	
	/**
	 * Prepare the simulation for the object
	 */
	public abstract void init();
	
	/**
	 * Performs a simulation step
	 */
	public abstract void step();
	
	/**
	 * Indicates whether the object has a life-cycle
	 */
	public abstract boolean isLiveObject();
	
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	public abstract boolean isStarted();

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	public abstract boolean isFinished();

	/**
	 * Starts the object's life-cycle
	 */
	public abstract void start();

	/**
	 * Finishes the object's life-cycle
	 */
	public abstract void finish();

	/**
	 * Method called before the object's life-cycle is started
	 */
	public abstract void beforeStart();

	/**
	 * Method called every simulation step that the object's life-cycle is running
	 */
	public abstract boolean liveStep();

	/**
	 * Method called before the object's life-cycle is finished
	 */
	public abstract void beforeFinish();
}