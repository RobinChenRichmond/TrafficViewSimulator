// *****************************************************************************
// *****************************************************************************
// **** Car
// *****************************************************************************
// *****************************************************************************
public class Car {
  // Number representing a unique identifier for the car
  private int carID;

  // Row and column of the car's location.
  private int row, col;
 
  // NORTH, SOUTH, EAST, WEST
  private int laneDirectionCode;

  // Number of blocks the car has left to travel before turning.
  private int numBlocksBeforeTurning;

  // LEFT, RIGHT, NONE
  private int turnDirectionCode;

  // the number of time units spent in the current lane
  private int timeInCurrentLane;

  // if the car has moved in this time unit
  private boolean movedThisTimeUnit;

  // Construct the car
  public Car(int carID, int row, int col, int laneDirectionCode, 
             int numBlocksBeforeTurning, int turnDirectionCode,
             int timeInCurrentLane) {
    this.carID = carID;
    this.row = row;
    this.col = col;
    this.laneDirectionCode = laneDirectionCode;
    this.numBlocksBeforeTurning = numBlocksBeforeTurning;
    this.turnDirectionCode = turnDirectionCode;
    this.timeInCurrentLane = timeInCurrentLane;
    this.movedThisTimeUnit = false;
  } // end of Car constructor



  // Move the car, updating its lane direction, turn direction,
  // col and row location, and number of blocks before turning.
  public void move() {
    // If the car plans to turn and has not yet turned, and is ready to turn,
    // have the car turn.
    if(readyToTurn()) {
      turn();
    } // end of if(readyToTurn())

    // Update the car's location
    if(laneDirectionCode == TrafficTesterView.SOUTHWARD) { row--; }
    else if(laneDirectionCode == TrafficTesterView.EASTWARD) { col++; }
    else if(laneDirectionCode == TrafficTesterView.NORTHWARD) { row++; }
    else if(laneDirectionCode == TrafficTesterView.WESTWARD) { col--; }
    // end of update of the car's location

    decrementBlocksBeforeTurn();

    // reset the car's time in current unit to 0
    timeInCurrentLane = 0;

    // set "if the car has moved in this time unit" to true
    setMoveStatus(true);
  
  } // end of move
  


  // Have the car turn, updating its lane direction and setting
  // its turn direction to NEVER_TURN, since car's turn at most once.
  public void turn() {
    laneDirectionCode = TrafficTesterModel.updateDirection(getLaneDirection(),
        getTurnDirection());
    turnDirectionCode = TrafficTesterView.NEVER_TURN;
  } // end of turn



  // Check if the car is ready to turn, if it plans to turn
  // and has not already turned.
  public boolean readyToTurn() {
    return numBlocksBeforeTurning == 0;
  } // end of readyToTurn



  // If the car has not already turned, decrement the number of 
  // blocks it must travel before it turns.
  public void decrementBlocksBeforeTurn() {
    int alreadyTurned = -1;
    if(numBlocksBeforeTurning != alreadyTurned) {
      numBlocksBeforeTurning--;
    } // end of if(numBlocksBeforeTurning != -1)
  } // end of decrementBlocksBeforeTurn



  // Get the car's time in the current lane
  public int getTimeInCurrentLane() { return timeInCurrentLane; }



  // Increment the car's time in the current lane by 1
  public void incrementTimeInCurrentLane() { timeInCurrentLane++; }



  // Check if the car has moved in the current time unit
  public boolean hasMoved() { return movedThisTimeUnit; }



  // Set the car's status of if having moved in the current time unit
  public void setMoveStatus(boolean newStatus) { 
    this.movedThisTimeUnit = newStatus; 
  } // end of setMoveStatus



  // Get the ID of the car
  public int getID() { return carID; }



  // Get the index of row the car located in the model
  public int getRow() { return this.row; }



  // Get the index of column the car located in the model
  public int getCol() { return this.col; }



  // Get the direction of the car's lane
  public int getLaneDirection() { return laneDirectionCode; }



  // Get the number of blocks the car will pass before turning
  public int getBlocksBeforeTurn() { return numBlocksBeforeTurning; }



  // Get the direction when the car needs to turn
  public int getTurnDirection() { return turnDirectionCode; }



} // end Car
