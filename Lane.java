// *****************************************************************************
// *****************************************************************************
// **** Lane
// *****************************************************************************
// *****************************************************************************
import java.util.*;

public class Lane {
	private Queue<Car> carList;

	private int maxLaneSize, minTimeToTravel;
	private boolean inGrid;



	public Lane(int maxSize, int minTimeToTravel, boolean inGrid) {
		carList = new LinkedList<Car>();
		maxLaneSize = maxSize;
		this.minTimeToTravel = minTimeToTravel;
		this.inGrid = inGrid;
	} // end constructor



	// Returns if the lane is empty
	public boolean isEmpty() { return (carList.peek() == null); }



	// Returns if the lane is full
	public boolean isFull() { return (carList.size() >= maxLaneSize); }



	// Retrieve but does not remove a car from the queue of the lane
	public Car getCar() { return carList.peek(); }



	// Remove and retrieve a car from the queue of the lane
	public Car removeCar() { return carList.poll(); }


	
	// return the max lane size
	public int getMaxLaneSize() { return maxLaneSize; }



	// return the minimum number of time units it takes to traverse the lane
	public int getMinTimeToTravel() { return minTimeToTravel; }



	// returns if the lane is in the grid
	public boolean isInGrid() { return inGrid; }



	// add a car to the lane, return true if sucessful and false if not
	public boolean add(Car newCar) {
		if(carList.size() >= maxLaneSize && inGrid) {
			return false;
    } else {
			carList.add(newCar);
			return true;
		} // end if-else

	} // end add
	


} // end Lane class
