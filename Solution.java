import java.util.ArrayList;

public class Solution {
    private ArrayList<Integer> solution;
    private int tauxDeJalousie;

    public Solution() {
        this.solution = new ArrayList<>();
        this.tauxDeJalousie = 0;
    }

    public Solution(ArrayList<Integer> solution, int tauxDeJalousie) {
        this.solution = solution;
        this.tauxDeJalousie = tauxDeJalousie;
    }

    public ArrayList<Integer> getSolution() {
        return solution;
    }

    public int getTauxDeJalousie() {
        return tauxDeJalousie;
    }

    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    public void setTauxDeJalousie(int tauxDeJalousie) {
        this.tauxDeJalousie = tauxDeJalousie;
    }
}
