// *****************************************************************************
// *****************************************************************************
// **** TrafficTesterModel
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class TrafficTesterModel {

  // Whether a lane is an outgoing lane or an incoming lane,
  // relative to an intersection
  private static final boolean OUTGOING = true;
  private static final boolean INCOMING = false;

  private int numIntersectionsInOneDirection;
  private Car[] carArray;
  private Intersection[][] intersectionArray;
  private int maxLaneCapacity;
  private int minTimeToTravelLane;


  // Construct the model: create the intersections, create the lanes
  // and link each one to the two intersections that share it,
  // and place the cars in the lanes.
  public TrafficTesterModel(int numIntersectionsInOneDirection, 
                            Car[] carArray, int maxLaneCapacity, 
                            int minTimeToTravelLane) {
    this.numIntersectionsInOneDirection = numIntersectionsInOneDirection;
    this.carArray = carArray;
    this.intersectionArray = 
      new Intersection[numIntersectionsInOneDirection + 1]
      [numIntersectionsInOneDirection + 1];
    this.maxLaneCapacity = maxLaneCapacity;
    this.minTimeToTravelLane = minTimeToTravelLane;

    initializeIntersections();
    initializeLanes();
    placeCarsInLanes();
  } // end TrafficTesterModel constructor



  // Run the simulation for a certain number of time units
  public String simulate(int numTimeUnits) {
    // Append all simulation output to this string, to return to the 
    // Traffic Tester View.
    String outputString = "";

    for(int t = 1; t <= numTimeUnits; t++) {

      outputString += ("\n" + "Time Unit: " + t + "\n");

      // Initialize status of the cars
      for(int i = 0; i < carArray.length; i ++){
        carArray[i].setMoveStatus(false);
        carArray[i].incrementTimeInCurrentLane();
      } // end for (int i = 0; i < carArray.length; i ++)

      // Iterate over the intersections
      for(int row = 1; row <= numIntersectionsInOneDirection; row++) {
        for(int col = 1; col <= numIntersectionsInOneDirection; col++) {
          // Move all the cars that are in incoming lanes of the intersection
          // to outgoing lanes.
          outputString += ("At the intersection located at col " + col 
              + " and row " + row + "\n");
          outputString += (intersectionArray[row][col].moveCars() + "\n");
        } // end for (col)
      } // end for (row)
    } // end for (int t = 1; t <= numTimeUnits; t++)

    return outputString;

  } // end simulate



  // Fill the 2D intersection array with Intersection instances.
  private void initializeIntersections() {
    for(int row = 1; row <= numIntersectionsInOneDirection; row++) {
      for(int col = 1; col <= numIntersectionsInOneDirection; col++) {
        Intersection newIntersection = new 
          Intersection(numIntersectionsInOneDirection);
        intersectionArray[row][col] = newIntersection;
      } // end for (col)
    } // end for (row)
  } // end initializeIntersections



  private void initializeLanes() {
    // Initialize all lanes incoming to intersections
    initializeLanes(TrafficTesterView.NORTHWARD);
    initializeLanes(TrafficTesterView.WESTWARD);
    initializeLanes(TrafficTesterView.SOUTHWARD);
    initializeLanes(TrafficTesterView.EASTWARD);
  } // end initializeLanes()



  // Initialize all lanes of a given direction.
  private void initializeLanes(int direction) {
    // Iterate over each intersection's pair of coordinates.
    for(int col = 1; col <= numIntersectionsInOneDirection; col++) {
      for(int row = 1; row <= numIntersectionsInOneDirection; row++) {
        // Coordinates for the intersection the lane is incoming to
        int intersection1Col = col; int intersection1Row = row;
        // Coordinates for the intersection the lane is outgoing from
        int intersection2Col = -1; int intersection2Row = -1;

        // Set second intersection's coordinates based on lane's direction
        // and the first intersection's coordinates (which are the same as 
        // the lane's coordinates)
        if(direction == TrafficTesterView.SOUTHWARD) {
          intersection2Col = col; intersection2Row = row + 1;
        } else if(direction == TrafficTesterView.EASTWARD) {
          intersection2Col = col - 1; intersection2Row = row;
        } else if(direction == TrafficTesterView.NORTHWARD) {
          intersection2Col = col; intersection2Row = row - 1;
        } else if(direction == TrafficTesterView.WESTWARD) {
          intersection2Col = col + 1; intersection2Row = row;
        } // end if(direction == TrafficTesterView.SOUTHWARD)

        Lane newLane = new Lane(maxLaneCapacity, minTimeToTravelLane, true);

        // Get intersection 1. It definitely exists, since we are iterating 
        // over legal intersection coordinates, and since legal lane 
        // coordinates imply legal intersection coordinates, by the 
        // definition of lane coordinates.
        Intersection intersection1 = 
          intersectionArray[intersection1Row][intersection1Col];
        intersection1.incomingLanes[direction] = newLane;
        
        // Check if an intersection exists that the lane is outgoing from
        if(intersection2Row >= 1 && intersection2Row <= 
          numIntersectionsInOneDirection && intersection2Col >= 1 
          && intersection2Col <= numIntersectionsInOneDirection) {

            Intersection intersection2 = 
              intersectionArray[intersection2Row][intersection2Col];
            intersection2.outgoingLanes[direction] = newLane;
        } // end if

        // Lane initializations are based on intersection coordinates.
        // Thus lanes that have no intersection that they are incoming to
        // have not yet been initialized. Initialize them here.
        if(col == 1 || col == numIntersectionsInOneDirection ||
            row == 1 || row == numIntersectionsInOneDirection) {
          Intersection edgeIntersection = intersectionArray[row][col];
          for(int i = 0; i < 4; i ++){
            if(edgeIntersection.outgoingLanes[i] == null){
              edgeIntersection.outgoingLanes[i] = 
                new Lane(maxLaneCapacity, minTimeToTravelLane, false);
            } // if(edgeIntersection.outgoingLanes[i] == null)
          } // end for(int i = 0; i < 4; i ++)
        } // end if
        
      } // end for (row)
    } // end for (col)
  } // end initializeLanes(int direction)



  // Place initial cars in lanes
  private void placeCarsInLanes() {
    for(int i = 0; i < carArray.length; i++) {
      Car c = carArray[i];
      
      // The car's coordinates are of the intersection that it is in an
      // incoming lane of.
      Intersection incomingInt = 
        intersectionArray[c.getRow()][c.getCol()];

      incomingInt.incomingLanes[c.getLaneDirection()].add(c);
    } // end for (int i = 0; i < carArray.length; i++)
  } // end placeCarsInLanes



  // Combine a lane direction with a turn direction to get a new lane
  // direction.
  public static int updateDirection(int oldDirection, int turnDirection) {

    int n = TrafficTesterView.NORTHWARD;
    int e = TrafficTesterView.EASTWARD;
    int s = TrafficTesterView.SOUTHWARD;
    int w = TrafficTesterView.WESTWARD;

    int right = TrafficTesterView.TURN_RIGHTWARD;
    int left = TrafficTesterView.TURN_LEFTWARD;

    if(oldDirection == n && turnDirection == right) { return e; }
    if(oldDirection == n && turnDirection == left)  { return w; }
    if(oldDirection == e && turnDirection == right) { return s; }
    if(oldDirection == e && turnDirection == left)  { return n; }
    if(oldDirection == s && turnDirection == right) { return w; }
    if(oldDirection == s && turnDirection == left)  { return e; }
    if(oldDirection == w && turnDirection == right) { return n; }
    if(oldDirection == w && turnDirection == left)  { return s; }

    return oldDirection;

  } //  end updateDirection
} // end TrafficTesterModel
