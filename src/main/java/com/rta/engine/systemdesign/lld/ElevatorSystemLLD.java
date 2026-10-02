package com.rta.engine.systemdesign.lld;

/**
 * Elevator system low level design
 *
 * Who uses and elevator system? People, goods, etc.
 * What do they do ? User should be able to kind of questions
 * a) Someone standing outside -- Presses UP or DOWN
 * b) Someone inside the elevator -- Presses which floor
 *
 * Systems job will be to determine which lift comes to the floor , assigned , in what order does it stop etc etc
 *
 * 1.The building Has N elevators , M floors ( maxFloor - minFloor)
 * 2. Person can call an elevator from the Hall ( EXTERNAL REQ)
 * 3. Person inside can choose which floor to go to ( INTERNAL REQ)
 * 4. System decides which elevator to send to the person based on the current state of elevators-- dispatching
 * 5. Each elevator decides in which order does it visit its stops
 * 6. Each elevator opens door when it reaches a floor and closes after a certain time
 * 7. Display shows an elevator cars current floor and direction of travel
 * 8. Elevator can be put down for maintenance.
 *
 * NFRs:
 * 1.Low Wait time
 * 2.No starvation - every floor must be served , no floor should be ignored because of other requests
 * 3.Safety - never move with open door
 * 4.Adding a stop must not corrupt elevators state -- Thread safety
 * 5.One broken car should not stop the whole building
 *
 *
 * OUT OF SCOPE
 * --Speed , capacity , weight sensors , door obstruction sensors ,fire alarms , emergency buttons ,floor zoning ,etc
 */


/**
 *  Entities -- I'll just highlight the nouns now and will change later if needed or i have to add something to it
 *
 *  Direction
 *  ElevatorState
 *  DoorState
 *  ExternalRequest -> FR2
 *  InternalRequest -> FR3
 *  Elevator
 *
 *  DispatchStrategy
 *      NearestElevatorStrategy   (Problem B, attempt 1 - kept only to show why it fails)
 *      DirectionAwareStrategy    (Problem B, attempt 2 - the one we use)
 *
 *  ElevatorObserver --> FR 7 tell me when a car changes
 *
 * Console Display
 * ElevatorController
 *
 *
 *     How they connect (arrow = "has / uses"):
 *
 *          Person presses button
 *                  |
 *                  v
 *       +----------------------+   uses   +--------------------+
 *       |  ElevatorController  |--------->|  DispatchStrategy  |  (Strategy pattern)
 *       +----------------------+          +--------------------+
 *                  | has 1..N
 *                  v
 *       +----------------------+ notifies +--------------------+
 *       |       Elevator       |--------->|  ElevatorObserver  |  (Observer pattern)
 *       +----------------------+          +--------------------+
 *          has: Direction, ElevatorState, DoorState, upStops, downStops
 *
 *          Why elevator does not pick itself for the hall calls ?
 *          because it does not know the state of other elevators , it can only know its own state and stops
 */


/**
 * Problems -- FCFS vs SSTF vs SCAN vs LOOK vs C-SCAN vs C-LOOK
 * (FR5: in what order does ONE car visit its stops)
 *
 * Same example for all of them:
 *   Building floors 0..10. Car is at floor 5, going UP.
 *   Requests arrived in this order: 8, 2, 9, 1, 6
 *
 * 1. FCFS (First Come, First Served)
 *    Go to floors in the order people pressed them. No thinking.
 *    Path: 5 -> 8 -> 2 -> 9 -> 1 -> 6          = 29 floors travelled
 *    + Simple, fair by arrival time.
 *    - Zig-zags up and down the building. Very slow. Passes floors without stopping.
 *
 * 2. SSTF (Shortest Seek Time First)
 *    Always go to the NEAREST pending floor next.
 *    Path: 5 -> 6 -> 8 -> 9 -> 2 -> 1          = 12 floors traveled
 *    + Least travel in the short run.
 *    - STARVATION: if people keep calling from 7, 8, 9 the car never goes down to 1 and 2.
 *      Breaks NFR 2.
 *
 * 3. SCAN
 *    Keep going in the current direction, stopping at every requested floor on the way,
 *    go ALL the way to the last floor (10), then turn around and do the same going down.
 *    Path: 5 -> 6 -> 8 -> 9 -> 10 -> 2 -> 1    = 14 floors traveled
 *    + No starvation: every floor is reached within one round trip.
 *    - Wasted trip to floor 10 even though nobody asked for it.
 *
 * 4. LOOK
 *    Same as SCAN, but turn around at the LAST REQUEST, not at the last floor.
 *    "Look" ahead - if nobody is above me, reverse now.
 *    Path: 5 -> 6 -> 8 -> 9 -> 2 -> 1          = 12 floors traveled
 *    + No starvation (like SCAN) and no wasted trips (like SSTF).
 *    + This is how real elevators behave.
 *
 * 5. C-SCAN (Circular SCAN)
 *    Serve requests in ONE direction only (say up). At the top floor, run empty
 *    straight back down to the bottom floor and start going up again.
 *    Path: 5 -> 6 -> 8 -> 9 -> 10 -> (empty) 0 -> 1 -> 2   = 17 floors travelled
 *    + Very even wait time for every floor.
 *    - Long empty runs. Person at 2 who wants to go DOWN waits for the full loop.
 *
 * 6. C-LOOK (Circular LOOK)
 *    Same as C-SCAN, but turn at the last request and jump back to the lowest request
 *    (not to floor 0).
 *    Path: 5 -> 6 -> 8 -> 9 -> (empty) 1 -> 2  = 13 floors travelled
 *    + Less empty travel than C-SCAN.
 *    - Still one-direction only. People going down ride the wrong way first.
 *
 * Quick compare:
 *    Algo     Travel    Starvation?   Good for
 *    FCFS     worst     no            nothing real, baseline only
 *    SSTF     best      YES           nothing safe, breaks NFR 2
 *    SCAN     ok        no            disk heads, not lifts
 *    LOOK     best      no            ELEVATORS  <-- pick this
 *    C-SCAN   bad       no            disks (one-way read), uniform wait
 *    C-LOOK   ok        no            disks; maybe a lift with one-way rush traffic
 *
 * PICK: LOOK
 *   - Meets NFR 1 (low wait): no trips to empty floors.
 *   - Meets NFR 2 (no starvation): every floor is passed within one up+down sweep.
 *   - People travel both ways, so circular versions (C-SCAN / C-LOOK) waste trips.
 *   - Fits our Elevator entity directly:
 *       upStops   = TreeSet (ascending)  -> going UP:   next stop = upStops.first()   (lowest floor above me)
 *       downStops = TreeSet (descending) -> going DOWN: next stop = downStops.first() (highest floor below me)
 *       current direction's set is empty -> switch direction; both empty -> IDLE
 *
 * Note: this is per-car order (FR5). Choosing WHICH car answers a hall call is the
 * DispatchStrategy (FR4) - different problem.
 */


/**
 * PROBLEM B - "Which elevator should answer a hall call?"  (FR4, NFR1, NFR5)
 *
 * Same example for both attempts:
 *   Someone at floor 7 presses UP.
 *   Car A: floor 6, going DOWN, last stop 0
 *   Car B: floor 2, going UP,   1 stop on the way (floor 4)
 *   Car C: floor 10, IDLE
 *   Car D: floor 7, MAINTENANCE
 *
 *   Attempt 1: nearest car  (NearestElevatorStrategy)
 *     Picks D (distance 0) - but D is broken. Request is lost.
 *     Skip D, picks A (distance 1) - but A must go 6 -> 0 -> 7 = 13 floors first. Bad.
 *     Mistake: distance alone ignores where the car is GOING.
 *
 *   Attempt 2: cost = how many floors the car must travel before it reaches me
 *              (DirectionAwareStrategy)
 *     Step 0: skip cars in MAINTENANCE (FR8, NFR5).
 *     Case 1 - car is IDLE:
 *        cost = |car - me|
 *     Case 2 - car is "on the way": moving TOWARDS me AND in MY direction
 *        (going UP and car <= me and I pressed UP, or going DOWN and car >= me and I pressed DOWN)
 *        cost = |car - me|                     it picks me up while passing
 *     Case 3 - anything else (wrong direction, or already passed me):
 *        cost = |car - lastStop| + |lastStop - me|   it must finish its trip, then come back
 *        (estimate - good enough to compare cars, no magic "big penalty" number needed)
 *     Then: cost += stopsAlreadyAssigned * STOP_COST
 *        each stop = door open + close time; STOP_COST ~ 2 floors of travel.
 *        Also spreads work so one car doesn't get every call.
 *     Tie -> lower car id (stable, easy to test).
 *
 *     Example:
 *       A: case 3 -> |6 - 0| + |0 - 7| = 13, + 0 stops  = 13
 *       B: case 2 -> |2 - 7|           = 5,  + 1 * 2    = 7
 *       C: case 1 -> |10 - 7|          = 3,  + 0 stops  = 3   <-- pick C
 *       D: skipped (maintenance)
 *
 *   Edge cases:
 *     - Every car in maintenance -> keep the call in a pending queue, retry when a car is back.
 *     - Assigned car goes to maintenance -> give its hall calls back to the controller to re-dispatch (NFR5).
 *     - Same floor + same direction pressed twice -> one request, not two.
 *
 *   The rule may change per building (hotel vs office), so it sits behind one interface:
 *     interface DispatchStrategy { Elevator pick(List<Elevator> cars, ExternalRequest req); }
 *   ElevatorController only calls pick() - swap the rule without touching the controller.
 */

public class ElevatorSystemLLD {


    enum Direction {UP,DOWN,IDLE}



}
