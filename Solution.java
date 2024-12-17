import java.util.ArrayList;

/**
 * Represents a solution to the Mars colony problem.
 *
 * A solution consists of an assignment of resources to colonists and the associated jealousy rate.
 */
public class Solution {
    private ArrayList<Integer> solution;
    /**
     * The assignment of resources to colonists. Each index in the list corresponds to a colonist,
     * and the value at that index represents the assigned resource.
     */
    private int tauxDeJalousie;
    /**
     * The overall jealousy rate of the solution.
     */

    /**
     * Constructs a default Solution object with an empty solution and a jealousy rate of 0.
     */
    public Solution() {
        this.solution = new ArrayList<>();
        this.tauxDeJalousie = 0;
    }

    /**
     * Constructs a Solution object with the specified solution and jealousy rate.
     *
     * @param solution The assignment of resources to colonists.
     * @param tauxDeJalousie The overall jealousy rate of the solution.
     */
    public Solution(ArrayList<Integer> solution, int tauxDeJalousie) {
        this.solution = solution;
        this.tauxDeJalousie = tauxDeJalousie;
    }

    /**
     * Gets the assignment of resources to colonists.
     *
     * @return The list of assigned resources.
     */
    public ArrayList<Integer> getSolution() {
        return solution;
    }

    /**
     * Gets the overall jealousy rate of the solution.
     *
     * @return The jealousy rate.
     */
    public int getTauxDeJalousie() {
        return tauxDeJalousie;
    }

    /**
     * Sets the assignment of resources to colonists.
     *
     * @param solution The new assignment of resources.
     */
    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    /**
     * Sets the overall jealousy rate of the solution.
     *
     * @param tauxDeJalousie The new jealousy rate.
     */
    public void setTauxDeJalousie(int tauxDeJalousie) {
        this.tauxDeJalousie = tauxDeJalousie;
    }
}