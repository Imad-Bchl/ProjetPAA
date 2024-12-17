package ProjetPAA;

import java.util.ArrayList;

public class Colon {
/**
 * Represents a colonist in the Mars colony.
 *
 * A colonist has a name, an index, a resource assignment, and a list of preferred resources.
 */
    private String nom;
    private ArrayList<Integer> preferances;
    private int index, ressource;

// Constructor
    public Colon(String nom, int index) {
        this.nom = nom;
        this.index = index;
        this.ressource = 0;
        preferances = new ArrayList<Integer>();
    }

// Getters and setters
    public ArrayList<Integer> getPreferances() {
        return preferances;
    }

    public void setPreferances(ArrayList<Integer> preferances) {
        this.preferances = preferances;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRessource() {
        return ressource;
    }

    public void setRessource(int ressource) {
        if(this.ressource == 0){
            this.ressource = ressource;
        }
    }

    public String getNom() {
        return nom;
    }

/**
 * Assigns a resource to the colonist, ensuring it's only assigned once.
 *
 * @param ressource The resource to assign.
 */
    public void ModifyRessource(int ressource) {
        this.ressource = ressource;
    }

}
