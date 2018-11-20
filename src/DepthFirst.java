import java.util.ArrayList;

class DepthFirst
{
    private int[] maxes;

    // Open and closed sets of states (ready to explore, or explored).
    private ArrayList<State> explored;
    private ArrayList<State> toExplore;

    private State currentState;
    DepthFirst(int maxA, int maxB, int maxC)
    {
        this.maxes = new int[3];

        this.maxes[0] = maxA;
        this.maxes[1] = maxB;
        this.maxes[2] = maxC;

        explored = new ArrayList<> ();
        toExplore = new ArrayList<> ();

        toExplore.add(new State(0, 0, 0));

        generateNodes ();

        outputSteps ();

    }

    /**
     * Prints the path to the goal state (if it exists) in reverse.
     */
    private void outputSteps ()
    {
        State nextState = currentState;
        while (nextState != null)
        {
            System.out.println (nextState);
            nextState = nextState.parent;
        }

        System.out.println (explored.size());
    }

    /**
     * Performs Depth first search on initial state.
     * Size of {@link #explored} states shows the number of possible states.
     */
    private void generateNodes ()
    {

        // State to search for.
        State goal = null;

        // While there are possible nodes to expand.
        while (toExplore.size () > 0)
        {
            // Get last node added (as Depth First: last in, first out).
            currentState = toExplore.get (toExplore.size()-1);
            // Remove current node from unexplored nodes.
            toExplore.remove(currentState);
            // Add current node to explored nodes.
            explored.add(currentState);

            // Check if we reached the goal (if we set one).
            if (goal != null)
            {
                if (currentState.equals (goal))
                {
                    break;
                }
            }

            // Loop through each state that can be reached from this state
            // in 1 move.
            for (State s : currentState.possibleNextStates(maxes[0], maxes[1], maxes[2]))
            {
                // If the state has already been explored, ignore it.
                if (explored.indexOf (s) != -1)
                {
                    continue;
                }
                // If the node has no been explored, and is not waiting
                // to be explored, add it to the waiting nodes.
                if (toExplore.indexOf (s) == -1)
                {
                    toExplore.add(s);
                }
            }
        }
    }
}
