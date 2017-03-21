// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************

import java.util.LinkedList;


public class Intersection {


  // Lists of lanes connceted to this Intersection.
  // Indexes [0, 1, 2, 3] correspond with directions [S, E, N, W] respectively
  public Lane[] incomingLanes;
  public Lane[] outgoingLanes;
  private int numIntersectionsInOneDirection;



  public Intersection(int numIntersectionsInOneDirection) {

    incomingLanes = new Lane[4];
    outgoingLanes = new Lane[4];
    this.numIntersectionsInOneDirection = numIntersectionsInOneDirection;

  } // end Intersection(int numIntersectionsInOneDirection)



  // Check whether a car has left the grid, based on the car's coordinates
  private boolean hasCarLeftGrid(Car c) {

    return(
      (c.getRow() == 0) ||
      (c.getRow() == (numIntersectionsInOneDirection + 1)) ||
      (c.getCol() == 0) ||
      (c.getCol() == (numIntersectionsInOneDirection + 1))
    );

  } // end hasCarLeftGrid



  // Move all the cars in incoming lanes at this intersection to the 
  // outgoing lanes at this intersection that they want to move to.
  public String moveCars() {
    String outputString = "";
    outputString += processIncomingLanes();
    outputString += processOutgoingLanes();

    return outputString;
  } // end moveCars



  // Process incoming lanes
  public String processIncomingLanes() {
    String outputString = "";
    // Iterate over all incoming lane. Use 2 through 5 since need the order
    // to be NWSE, which is 2, 3, 0, 1.
    for(int index = 2; index <= 5; index++) {
      int direction = index % 4;

      if(incomingLanes[direction].isEmpty()) {
        outputString += ("  incoming lane having direction "
            + TrafficTesterView.convertToLaneDirection(direction)
            + " is empty\n");
      } else {
        outputString += ("  incoming lane having direction "
          + TrafficTesterView.convertToLaneDirection(direction)
          + " is nonempty and\n");
        Car c = (Car) incomingLanes[direction].getCar(); 

        // lane dir. the car will have when it goes through the intersection
        int laneDirection = -1;
          if(c.readyToTurn()) { 
            laneDirection =
              TrafficTesterModel.updateDirection
              (c.getLaneDirection(), c.getTurnDirection()); 
          } else {
            laneDirection = c.getLaneDirection();
          }

        Lane outgoingLane = outgoingLanes[laneDirection];
        
        // if the car hasn't moved yet, and has travelled all of the lane, 
        // and its desired lane isn't full, move the car into its desired lane
        boolean laneAvailable = !outgoingLane.isFull() || !outgoingLane.isInGrid();
        if (!c.hasMoved() && c.getTimeInCurrentLane() >= 
            incomingLanes[direction].getMinTimeToTravel() && laneAvailable) {
          incomingLanes[direction].removeCar(); 
          c.move();
          outgoingLanes[c.getLaneDirection()].add(c);
          outputString += ("   car#" + c.getID() + " is removed and placed " +
            "into outgoing lane having direction " + 
            TrafficTesterView.convertToLaneDirection(laneDirection) + "\n");


          // Check if the car leaves the grid
          if(hasCarLeftGrid(c)) {
            outputString += ("   car#" + c.getID() + " leaves the grid\n");
          } // end if(hasCarLeftGrid(c))
        } else {
        outputString += ("   car#" + c.getID() + " either moves within a lane,"
          + "\n      or already moved previously in " 
          + "this time unit, or is yielding.\n");
        } // end else
      } // end if (incomingLanes[direction].isEmpty()) else ...
    }

    return outputString;
  } // end processIncomingLanes



  // Process outgoing lanes
  public String processOutgoingLanes() {
    String outputString = "";
    // Iterate over all outgoing lane. Use 2 through 5 since need the order
    // to be NWSE, which is 2, 3, 0, 1.
    for(int index = 2; index <= 5; index++) {
      int direction = index % 4;
      // If lane is empty..., else...
      if(outgoingLanes[direction].isEmpty()) {
        outputString += ("  outgoing lane having direction "
        + TrafficTesterView.convertToLaneDirection(direction) + " is empty\n");
      } // end if(outgoingLanes[direction].isEmpty())
      else {
        outputString += ("  outgoing lane having direction " + 
        TrafficTesterView.convertToLaneDirection(direction) + " is nonempty\n");
      } // end else
    } // end for
    return outputString;
  } // end processOutgoingLanes


} // end Intersection class
