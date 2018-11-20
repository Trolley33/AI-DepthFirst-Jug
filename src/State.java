public class State
{
    private int[] jugs;
    // Used to prevent this states' information being influenced
    // when a new state is created (cloned from main when used).
    private int[] tempJugs;
    State parent;

    /**
     * Constructor for initial state.
     * @param A current capacity of A.
     * @param B current capacity of B.
     * @param C current capacity of C.
     */
    State(int A, int B, int C)
    {
        jugs = new int[3];
        jugs[0] = A;
        jugs[1] = B;
        jugs[2] = C;
    }

    /**
     * Constructor for a state, which is spawned from another state.
     * @param A current capacity of A.
     * @param B current capacity of B.
     * @param C current capacity of C.
     * @param parent state which created this.
     */
    private State(int A, int B, int C, State parent)
    {
        jugs = new int[3];
        jugs[0] = A;
        jugs[1] = B;
        jugs[2] = C;

        this.parent = parent;
    }

    /**
     * Produces all possible states from the current one.
     * @param maxA maximum capacity of jug A.
     * @param maxB maximum capacity of jug B.
     * @param maxC maximum capacity of jug C.
     * @return array of states, including duplicates.
     */
    State[] possibleNextStates (int maxA, int maxB, int maxC)
    {
        // Each node has 12 children, done here manually.
        State[] result = new State[12];

        // Only 3 ways to fill jugs.
        result[0] = fill (0, maxA);
        result[1] = fill (1, maxB);
        result[2] = fill (2, maxC);

        // (3 choose 2) * 2 == 6 different ways to pour between jugs.
        result[3] = pour (0, 1, maxB);
        result[4] = pour (0, 2, maxC);
        result[5] = pour (1, 2, maxC);
        result[6] = pour (1, 0, maxA);
        result[7] = pour (2, 1, maxB);
        result[8] = pour (2, 0, maxA);

        // 3 different ways to empty the jugs.
        result[9] = empty (0);
        result[10] = empty (1);
        result[11] = empty (2);

        return result;
    }

    /**
     * Fills jug to maximum.
     * @param index jug to fill.
     * @param max maximum capacity of jug.
     * @return the state produced from this action.
     */
    private State fill (int index, int max)
    {
        tempJugs = jugs.clone();
        tempJugs[index] = max;
        return new State(tempJugs[0], tempJugs[1], tempJugs[2], this);
    }

    /**
     * Move as much water as possible from one jug to another.
     * @param i1 jug providing water.
     * @param i2 jug receiving water.
     * @param max2 maximum capacity of receiving jug.
     * @return the state produced from this action.
     */
    private State pour (int i1, int i2, int max2)
    {
        tempJugs = jugs.clone();

        int A = tempJugs[i1];
        int B = tempJugs[i2];

        int fillable = max2 - B;

        if (fillable > 0)
        {
            tempJugs[i2] = B + A;

            int remainder = 0;


            if (tempJugs[i2] > max2)
            {
                tempJugs[i2] = max2;
                remainder = (A+B) - max2;
            }

            tempJugs[i1] = remainder;
        }

        return new State(tempJugs[0], tempJugs[1], tempJugs[2], this);
    }

    /**
     * Sets a jugs capacity to 0.
     * @param index jug to empty.
     * @return the state produced from this action.
     */
    private State empty (int index)
    {
        tempJugs = jugs.clone();

        tempJugs[index] = 0;
        return new State(tempJugs[0], tempJugs[1], tempJugs[2], this);
    }

    /**
     * Gives ability to compare states using jug logic,
     * rather than in built Java comparison which might not work.
     * @param o other object.
     * @return if the two objects are semantically equivalent.
     */
    @Override
    public boolean equals(Object o)
    {
        // If they are actually the same, true.
        if (o == this)
        {
            return true;
        }
        // If it isn't a State, false.
        if (!(o instanceof State))
        {
            return false;
        }
        State s = (State) o;
        // If the jugs are the same capacities, true; otherwise, false.
        return jugs[0] == s.jugs[0] && jugs[1] == s.jugs[1] && jugs[2] == s.jugs[2];

    }

    /**
     * Safety method, complementary to {@link #equals(Object)} giving
     * more reliable jug comparisons when using in-built functions.
     * (i.e. .indexOf())
     * @return a unique number identifying this State.
     */
    @Override
    public int hashCode()
    {
        // Simplistic hash method, using prime number to generate
        // unique integer output based on capacity of jugs.
        int result = 1;
        result = 31 * result + jugs[0];
        result = 31 * result + jugs[1];
        result = 31 * result + jugs[2];
        return result;
    }

    /**
     * Helper function to output the current state as a tuple.
     * @return String in format (A, B, C).
     */
    @Override
    public String toString ()
    {
        return String.format ("(%d, %d, %d)", jugs[0], jugs[1], jugs[2]);
    }
}
