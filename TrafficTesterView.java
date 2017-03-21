// *****************************************************************************
// *****************************************************************************
// **** TrafficTesterView 
// *****************************************************************************
// *****************************************************************************


// NOTE: A lane has the same coordinates as the intersection the lane aims at.
//   Throughout the rest of the semester, make sure that 
//   intersections of a grid are processed in exactly the following order:
//     row 1 intersections, from left to right
//     row 2 intersections, from left to right, etc, for all rows.
//   In an actual simulation, that process should be repeated (in that specific
//   order) as many times as necessary for the desired length of the simulation.
//   But, for simplicity, the initial tester software students build should 
//   only show the first iteration of the above process, that is, only one 
//   processing of each of the intersections.
// 
//   The format of the data to be used with the program in this file
//   is show, via an example, at the end of this file.

import java.util.*;

public class TrafficTesterView {

// NOTE: Just to keep the current tester program really simple, 
// the following constants are not encapsulated within two classes, Direction 
// and Turn, according to appropriate Information Hiding:
  static final int SOUTHWARD = 0;
  static final int EASTWARD = 1;
  static final int NORTHWARD = 2;
  static final int WESTWARD = 3;
  static final int NEVER_TURN = 0;
  static final int TURN_RIGHTWARD = 1;
  static final int TURN_LEFTWARD = -1;



  public static void main(String[] args) {
    String inputString = "";
// *****************************************************************************
    // Get input, and print out formatted input
// *****************************************************************************
    // String storing the info about the input
    inputString += ("*****************************************************\n");
    inputString += ("*****************************************************\n");
    inputString += ("*** Receive Input\n");
    inputString += ("*****************************************************\n");
    Scanner console = new Scanner(System.in);
    console.nextLine();
    int numIntersectionsInOneDirection = console.nextInt();
    inputString += ("The number of intersections in one direction ");
    inputString += ("is: " + numIntersectionsInOneDirection + "\n");
    console.nextLine();
    console.nextLine();
    int lengthOfSimulation = console.nextInt();
    inputString += ("The length of simulation ");
    inputString += ("is: " + lengthOfSimulation + "\n");
    console.nextLine();
    console.nextLine();
    int maxLaneCapacity = console.nextInt();
    inputString += ("The max lane capacity ");
    inputString += ("is: " + maxLaneCapacity + "\n");
    console.nextLine();
    console.nextLine();
    int minTimeToTravelLane = console.nextInt();
    inputString += ("The min time to travel lane ");
    inputString += ("is: " + minTimeToTravelLane + "\n");
    console.nextLine();
    console.nextLine();
    int maxQuarterRoundaboutCapacity = console.nextInt();
    inputString += ("The max quarter roundabout capacity ");
    inputString += ("is: " + maxQuarterRoundaboutCapacity + "\n");
    console.nextLine();
    console.nextLine();
    int minTimeToTravelQuarterRoundabout = console.nextInt();
    inputString += ("The min time to travel quarter roundabout ");
    inputString += ("is: " + minTimeToTravelQuarterRoundabout + "\n");
    console.nextLine();
    console.nextLine();
    int numberOfCars = console.nextInt();
    inputString += ("The number of cars is: " + numberOfCars + "\n");
    int carID;
    int row;
    int col;
    int laneDirectionCode;
    int numBlocksBeforeTurning;
    int turnDirectionCode;

    Car[] carArray = new Car[numberOfCars]; 
    for (int i = 1; i <= numberOfCars; i++) {
       console.nextLine();
       console.nextLine();
       carID = console.nextInt();
       inputString += ("Car #" + carID + "\n");
       console.nextLine();
       console.nextLine();
       col = console.nextInt();
       console.nextLine();
       console.nextLine();
       row = console.nextInt();
       console.nextLine();
       console.nextLine();
       laneDirectionCode = console.nextInt();
       console.nextLine();
       console.nextLine();
       numBlocksBeforeTurning = console.nextInt();
       console.nextLine();
       console.nextLine();
       turnDirectionCode = console.nextInt();
       inputString += ("  is born in the lane located at col " + col +
                          " and row " + row + ", that aims " +
                          convertToLaneDirection(laneDirectionCode) + ",\n");
       inputString += ("  and has " + numBlocksBeforeTurning +
                          " block(s) to go before turning\n");
       inputString += ("  and plans to " + 
                          convertToTurnDirection(turnDirectionCode) + "\n");
       Car newCar = new Car(carID, row, col, laneDirectionCode, 
                            numBlocksBeforeTurning, turnDirectionCode, 
                            minTimeToTravelLane);
       carArray[i-1] = newCar;
    } // end for

    // Print out the input information
    //System.out.println(inputString);
   
// *****************************************************************************
    // Get model to do work 
// *****************************************************************************
    TrafficTesterModel model = 
      new TrafficTesterModel(numIntersectionsInOneDirection, carArray, 
        maxLaneCapacity, minTimeToTravelLane);
    String outputInfo = model.simulate(lengthOfSimulation);
   
// *****************************************************************************
    // Format and print output 
// *****************************************************************************
    System.out.println(outputInfo); 
  } // main



  public static String convertToLaneDirection(int laneDirectionCode) {
    if (laneDirectionCode == SOUTHWARD)      return "SOUTHWARD";
    if (laneDirectionCode == EASTWARD)       return "EASTWARD";
    if (laneDirectionCode == NORTHWARD)      return "NORTHWARD";
    if (laneDirectionCode == WESTWARD)       return "WESTWARD";
    return "ILLEGAL laneDirectionCode!!!";
  } // convertToLaneDirection



  public static String convertToTurnDirection(int turnDirectionCode) {
    if (turnDirectionCode == NEVER_TURN)     return "NEVER_TURN";
    if (turnDirectionCode == TURN_RIGHTWARD) return "TURN_RIGHTWARD";
    if (turnDirectionCode == TURN_LEFTWARD)  return "TURN_LEFTWARD";
    return "ILLEGAL turnDirectionCode!!!";
  } // convertToTurnDirection

} // TrafficTesterView
