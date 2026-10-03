package com.rta.engine.systemdesign.lld;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

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
 *    (simulation: doors close instantly, no timer)
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
 *  DoorState
 *  ExternalRequest -> FR2
 *  InternalRequest -> FR3
 *  Elevator
 *
 *  DispatchStrategy
 *      DirectionAwareStrategy    (Problem B, attempt 2 - the only one coded;
 *                                 nearest-car is discussed in Problem B to show why it fails)
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
 *          has: Direction, DoorState, inMaintenance, upStops, downStops
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
 *   - How the code does it (Elevator.step(), one floor per call):
 *       set for my direction empty?          -> turn around
 *       move one floor
 *       current floor in that set?           -> remove it, open doors
 *       both sets empty                      -> IDLE
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
 *     - Every car in maintenance -> keep the call in a pending queue, retry every step until a car is back.
 *     - Assigned car goes to maintenance -> its stops are cleared (passengers inside are stuck in it anyway).
 *       Full version: keep hall calls separate from cabin stops, re-dispatch only the hall calls.
 *     - Same floor pressed twice -> TreeSet keeps one stop, not two.
 *
 *   The rule may change per building (hotel vs office), so it sits behind one interface:
 *     interface DispatchStrategy { Elevator pick(List<Elevator> cars, ExternalRequest req); }
 *   ElevatorController only calls pick() - swap the rule without touching the controller.
 */

/**
 * SIMPLIFIED SCOPE - what we actually build (interview-sized, ~45 min)
 *  - Cut: capacity/load (already OUT OF SCOPE above), EMERGENCY, NearestElevatorStrategy.
 *  - Cut: ElevatorState enum. Direction IDLE already means "idle". Only extra fact needed = inMaintenance.
 *  - Time is simulated: main() calls controller.step() in a loop, 1 step = 1 floor moved.
 *    No sleep, no timers -> easy to run, easy to debug.
 *  - The demo runs on one thread. `synchronized` stays because a real building has many (NFR4).
 */
public class ElevatorSystemLLD {

    /*
     * HOW TO READ THIS FILE (top to bottom = small to big):
     *   1. Enums + records      -> plain data, no logic
     *   2. Observer             -> who gets told when a car changes (FR7)
     *   3. Elevator             -> ONE car: its stops, how it moves (LOOK, Problem A)
     *   4. DispatchStrategy     -> which car answers a hall call (Problem B)
     *   5. ElevatorController   -> the building: owns all cars, takes button presses
     *   6. main()               -> demo + self-check
     *
     * Why can classes here read each other's private fields (e.currentFloor)?
     *   - All classes are nested inside ONE top-level class. Java lets them see each other's private parts.
     *   - `static` only means "create it without an ElevatorSystemLLD object". Nothing to do with access.
     *   - Separate files -> this stops compiling -> add small read methods like stopCount().
     *
     * Style rule used here: read a field directly (car.currentFloor);
     * use a method only when it calculates something (car.stopCount()).
     */


    // ===================== 1. DATA =====================

    enum Direction {UP, DOWN, IDLE}

    enum DoorState {OPEN, CLOSED}

    /**
     * Request is just data i could have a simple internal class or have a record in java as the request is immutable
     * Java record class gives me immutable object , constructors, getters , equals and toString in one line
     */
    record ExternalRequest(int floor, Direction direction) {}   // hall button: "I'm at 5, going UP"
    record InternalRequest(int elevatorId, int floor) {}        // car button:  "car 0, take me to 8"


    // ===================== 2. OBSERVER =====================

    interface ElevatorObserver {
        void onUpdate(Elevator elevator, String event);
    }

    static class ConsoleDisplay implements ElevatorObserver {
        @Override
        public void onUpdate(Elevator e, String event) {
            System.out.printf("Car %d | floor %2d | %-4s | door %-6s | up=%s down=%s | %s%n",
                    e.id, e.currentFloor, e.direction, e.doorState, e.upStops, e.downStops, event);
        }
    }


    // ===================== 3. ELEVATOR (one car) =====================

    /**
     * Elevator -- Heart of the system
     * What does one car have to remember ?
     *  Who am i ?                 -> id
     *  Where am i ?               -> currentFloor
     *  Where must i stop ?        -> upStops, downStops
     *  Which way am i going ?     -> direction (IDLE = not moving)
     *  Are my doors open ?        -> doorState
     *  Am i broken ?              -> inMaintenance
     *
     * What can one elevator car do ?
     *  accept a stop (addStop), move one floor (step), open/close doors, go into maintenance
     *
     * Why `synchronized` ?  (NFR4 - thread safety)
     *  - Real building: hall buttons, car buttons and the motor (step) hit ONE car from different threads.
     *  - TreeSet is not thread-safe -> two threads at once can lose a stop or throw an exception.
     *  - synchronized = one thread at a time inside this car. Lock is per car, so cars never wait on each other.
     *  - Our demo is single-threaded, so it runs the same without it.
     */
    static class Elevator {
        private final int id;
        private final int minFloor, maxFloor;

        private int currentFloor;
        private Direction direction = Direction.IDLE;
        private DoorState doorState = DoorState.CLOSED;
        private boolean inMaintenance = false;

        Elevator(int id, int minFloor, int maxFloor) {
            this.id = id;
            this.minFloor = minFloor;
            this.maxFloor = maxFloor;
            this.currentFloor = minFloor;   // every car starts at the ground floor.
        }

        /**
         * Why TreeSet, why not PriorityQueue
         *  - No duplicates: floor 7 pressed twice = one stop. PQ keeps both, car "stops" twice.
         *  - contains(7) is O(log n). PQ is O(n) - it must scan.
         *  - remove(7) (stop served / canceled) is O(log n). PQ.remove(Object) is O(n).
         *  - Both ends: first() and last() in one set. PQ gives only one end (min OR max heap).
         *  - ceiling(f) / floor(f): "next stop above/below my current floor" in O(log n).
         *    Not used here (step() checks one floor at a time), but PQ cannot answer it at all.
         *  - Iteration is sorted, so Display/debug prints stops in order. PQ iteration is not sorted.
         *  - PQ only wins on peek() O(1) vs O(log n) - does not matter for a few dozen floors.
         *  - Neither is thread-safe: guard with synchronized (NFR4),
         *    or ConcurrentSkipListSet (same API) if lock-free is needed.
         *
         * Rule that keeps LOOK simple:
         *  - upStops   only holds floors ABOVE the car when added -> served on the way up.
         *  - downStops only holds floors BELOW the car when added -> served on the way down.
         */
        private final TreeSet<Integer> upStops = new TreeSet<>();
        private final TreeSet<Integer> downStops = new TreeSet<>();


        /**
         * Observer pattern  (FR7 - display shows floor and direction)
         *
         * Problem:
         *  - Many things care when a car changes: hall display, display inside the car, logger.
         *  - Bad way: Elevator calls display.show(), logger.log() directly.
         *    Elevator must know every screen. New screen = edit Elevator.
         *
         * Idea (like YouTube subscribe):
         *  - Channel uploads -> every subscriber gets told. Channel doesn't know who they are.
         *  - Elevator changes -> every observer gets told. Elevator doesn't know who they are.
         *
         * Who is who:
         *  - Subject (the one being watched) = Elevator
         *  - Observer (the contract)         = ElevatorObserver interface, one method onUpdate()
         *  - Concrete observer               = ConsoleDisplay (add LogObserver etc. later)
         *
         * How it works:
         *  1. addObserver(display)    -> display subscribes to this car.
         *  2. Car changes something   -> calls notifyObservers("ARRIVED_AT_5").
         *  3. notifyObservers loops   -> calls onUpdate(this, event) on every observer.
         *  4. Passes `this`           -> one display can watch many cars and know which one changed.
         *
         * When to notify:
         *  - Floor changed, door opened/closed, direction changed, state changed (maintenance).
         *
         * Why it helps:
         *  - New display = new class + addObserver(). Elevator code untouched (Open/Closed principle).
         *  - Elevator and displays can be tested separately.
         *
         * Watch out:
         *  - onUpdate runs on the car's thread. Keep it fast - a slow display delays the car.
         *  - Adding an observer while notifying throws ConcurrentModificationException on ArrayList.
         *    Fix: CopyOnWriteArrayList (many reads, rare writes - fits observers).
         */
        private final List<ElevatorObserver> observers = new ArrayList<>();

        void addObserver(ElevatorObserver observer) {
            observers.add(observer);
        }

        void notifyObservers(String event) {
            for (ElevatorObserver observer : observers) {
                observer.onUpdate(this, event);
            }
        }

        /**
         * Add a floor on which this car must stop on
         * Thoughts :
         * Is this a valid floor ? if no fail loudly
         * Am I broken or under maintenance?
         * Am I already there ? Open the damn door
         * Otherwise : Floor above me ? add to upstops, else downstops
         * If I was IDLE then i have now a reason to move -> Pick a direction
         */
        synchronized void addStop(int floor) {
            if (floor < minFloor || floor > maxFloor) {
                throw new IllegalArgumentException("Floor " + floor + " is out of range [" + minFloor + ", " + maxFloor + "]");
            }
            if (inMaintenance) {
                notifyObservers("Rejected stop " + floor + " (in maintenance)");
                return;
            }
            // Between two step() calls the car is always standing AT a floor -> just open the doors.
            if (floor == currentFloor) {
                openAndCloseDoors();
                return;
            }

            if (floor > currentFloor) {
                upStops.add(floor);
            } else {
                downStops.add(floor);
            }

            if (direction == Direction.IDLE) {
                direction = floor > currentFloor ? Direction.UP : Direction.DOWN;
            }
            notifyObservers("Stop added: " + floor);
        }

        /**
         * Move ONE floor. This is LOOK (Problem A). Called once per tick by the controller.
         * Thoughts :
         * Nothing to do (no stops or broken) ? stay put
         * Nothing left in my direction ? turn around
         * Move 1 floor in my direction
         * Is this floor a stop ? remove it, open doors (doors go IDLE if that was the last stop)
         */
        synchronized void step() {
            if (inMaintenance || !hasPendingStops()) {
                return;
            }

            // LOOK: nothing left ahead -> turn around
            if (direction == Direction.UP && upStops.isEmpty()) {
                direction = Direction.DOWN;
            } else if (direction == Direction.DOWN && downStops.isEmpty()) {
                direction = Direction.UP;
            }

            currentFloor += (direction == Direction.UP) ? 1 : -1;
            notifyObservers("Moved");

            TreeSet<Integer> ahead = (direction == Direction.UP) ? upStops : downStops;
            if (ahead.remove(currentFloor)) {      // remove() returns true if this floor was a stop
                openAndCloseDoors();
            }
        }

        /** Safety (NFR3): doors only open between moves, and close before the next step(). */
        private void openAndCloseDoors() {
            doorState = DoorState.OPEN;
            notifyObservers("Arrived, doors open");
            doorState = DoorState.CLOSED;
            if (!hasPendingStops()) {
                direction = Direction.IDLE;
                notifyObservers("Idle");
            }
        }

        /**
         * FR8: broken car drops all its stops. Passengers inside are stuck in it anyway,
         * so their floors are NOT handed to other cars. Strategy skips this car from now on (NFR5).
         */
        synchronized void goToMaintenance() {
            inMaintenance = true;
            direction = Direction.IDLE;
            upStops.clear();
            downStops.clear();
            notifyObservers("In maintenance");
        }

        synchronized void endMaintenance() {
            inMaintenance = false;
            notifyObservers("Back in service");
        }

        boolean hasPendingStops() {
            return !upStops.isEmpty() || !downStops.isEmpty();
        }

        int stopCount() {
            return upStops.size() + downStops.size();
        }
    }


    // ===================== 4. STRATEGY (which car?) =====================

    /** Problem B - Strategy pattern. Controller only knows this interface. */
    interface DispatchStrategy {
        Elevator pick(List<Elevator> cars, ExternalRequest req);
    }

    static class DirectionAwareStrategy implements DispatchStrategy {
        private static final int STOP_COST = 2;   // one stop (doors open/close) ~ 2 floors of travel

        /** Cheapest working car wins. Ties -> first in list (lowest id). null = every car broken. */
        @Override
        public Elevator pick(List<Elevator> cars, ExternalRequest req) {
            Elevator best = null;
            int bestCost = Integer.MAX_VALUE;

            for (Elevator car : cars) {
                if (car.inMaintenance) {
                    continue;
                }
                int cost = travelCost(car, req) + car.stopCount() * STOP_COST;
                if (cost < bestCost) {
                    best = car;
                    bestCost = cost;
                }
            }
            return best;
        }

        /** How many floors must this car travel before it reaches the caller? (3 cases from Problem B) */
        private int travelCost(Elevator car, ExternalRequest req) {
            int distance = Math.abs(car.currentFloor - req.floor());
            Direction d = car.direction;

            // Case 1: idle -> comes straight to me
            if (d == Direction.IDLE) {
                return distance;
            }

            // Case 2: on the way -> same direction as me AND hasn't passed me yet
            boolean onTheWay =
                    (d == Direction.UP   && req.direction() == Direction.UP   && car.currentFloor <= req.floor()) ||
                    (d == Direction.DOWN && req.direction() == Direction.DOWN && car.currentFloor >= req.floor());
            if (onTheWay) {
                return distance;
            }

            // Case 3: busy elsewhere -> finish current sweep at lastStop, then come back to me
            int lastStop = car.currentFloor;
            if (d == Direction.UP && !car.upStops.isEmpty()) {
                lastStop = car.upStops.last();      // highest floor it still goes up to
            }
            if (d == Direction.DOWN && !car.downStops.isEmpty()) {
                lastStop = car.downStops.first();   // lowest floor it still goes down to
            }
            return Math.abs(car.currentFloor - lastStop) + Math.abs(lastStop - req.floor());
        }
    }


    // ===================== 5. CONTROLLER (the building) =====================

    /**
     * The building's brain. Owns all cars, takes button presses, ticks time.
     * Hall button  -> strategy picks a car -> car.addStop(floor)
     * Car button   -> no choice needed, that car.addStop(floor)
     */
    static class ElevatorController {
        private final List<Elevator> cars = new ArrayList<>();
        private final DispatchStrategy strategy;
        private final Queue<ExternalRequest> pending = new ArrayDeque<>();   // hall calls no car could take yet

        ElevatorController(int numCars, int minFloor, int maxFloor, DispatchStrategy strategy, ElevatorObserver display) {
            this.strategy = strategy;
            for (int i = 0; i < numCars; i++) {
                Elevator car = new Elevator(i, minFloor, maxFloor);
                car.addObserver(display);
                cars.add(car);
            }
        }

        /** FR2 - someone in the hall presses UP/DOWN. */
        void handleExternal(ExternalRequest req) {
            Elevator car = strategy.pick(cars, req);
            if (car == null) {
                System.out.println("No car available for floor " + req.floor() + " - waiting");
                pending.add(req);   // retried on every step()
                return;
            }
            car.addStop(req.floor());
        }

        /** FR3 - someone inside car X presses a floor. */
        void handleInternal(InternalRequest req) {
            car(req.elevatorId()).addStop(req.floor());
        }

        /** FR8 + NFR5 - put a car down. Other cars keep working. */
        void setMaintenance(int elevatorId) {
            car(elevatorId).goToMaintenance();
        }

        void endMaintenance(int elevatorId) {
            car(elevatorId).endMaintenance();
        }

        /** One tick of time = retry waiting hall calls once, then every car moves one floor. */
        void step() {
            for (int n = pending.size(); n > 0; n--) {   // fixed count: a call that fails again waits for the next tick
                handleExternal(pending.poll());
            }
            for (Elevator car : cars) {
                car.step();
            }
        }

        /** Bad car id from a button panel -> clear error instead of IndexOutOfBoundsException. */
        private Elevator car(int elevatorId) {
            if (elevatorId < 0 || elevatorId >= cars.size()) {
                throw new IllegalArgumentException("No elevator with id " + elevatorId);
            }
            return cars.get(elevatorId);
        }
    }


    // ===================== 6. DEMO =====================

    public static void main(String[] args) {
        ElevatorController building = new ElevatorController(
                2, 0, 10, new DirectionAwareStrategy(), new ConsoleDisplay());

        building.handleExternal(new ExternalRequest(5, Direction.UP));   // someone at 5 wants to go up
        building.handleInternal(new InternalRequest(0, 8));              // someone inside car 0 picks 8
        building.handleExternal(new ExternalRequest(2, Direction.DOWN)); // someone at 2 wants to go down

        for (int tick = 0; tick < 12; tick++) {
            building.step();
        }

        // What should happen:
        //  - Car 0 gets the call at 5 (both idle at 0, tie -> car 0) and stop 8.
        //  - Call at 2 DOWN: car 1 idle at 0 -> cost 2.
        //                   car 0 going UP, not on the way -> |0-8| + |8-2| = 14, + 2 stops * 2 = 18 -> car 1 wins.
        //  - Car 0 stops at 5 then 8 then goes IDLE. Car 1 stops at 2 then goes IDLE.
        //  - Doors open only on "Arrived" lines, never on a "Moved" line.

        // Self-check: fails loudly if the logic breaks
        Elevator car0 = building.cars.get(0), car1 = building.cars.get(1);
        check(car0.currentFloor == 8 && car0.direction == Direction.IDLE, "car 0 should end idle at 8");
        check(car1.currentFloor == 2 && car1.direction == Direction.IDLE, "car 1 should end idle at 2");

        // Maintenance: car 1 breaks, a new call must go to car 0
        building.setMaintenance(1);
        building.handleExternal(new ExternalRequest(3, Direction.UP));
        check(car0.stopCount() == 1 && car1.stopCount() == 0, "call should skip broken car 1");
        for (int tick = 0; tick < 6; tick++) {
            building.step();
        }
        check(car0.currentFloor == 3, "car 0 should serve the call at 3");

        // Pending queue: both cars broken -> call waits; car 1 repaired -> it takes the call
        building.setMaintenance(0);
        building.handleExternal(new ExternalRequest(6, Direction.DOWN));
        check(building.pending.size() == 1, "call should wait in the pending queue");
        building.endMaintenance(1);
        for (int tick = 0; tick < 6; tick++) {
            building.step();
        }
        check(building.pending.isEmpty() && car1.currentFloor == 6, "repaired car 1 should serve the waiting call");

        System.out.println("All checks passed");
    }

    private static void check(boolean ok, String message) {
        if (!ok) {
            throw new IllegalStateException("FAILED: " + message);
        }
    }
}
