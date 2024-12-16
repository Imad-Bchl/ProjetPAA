import java.util.ArrayList;

public class Colon {
    private String nom;
    private ArrayList<Integer> preferances;
    private int index, ressource;

    public Colon(String nom, int index) {
        this.nom = nom;
        this.index = index;
        this.ressource = 0;
        preferances = new ArrayList<Integer>();
    }
    
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

    public void ModifyRessource(int ressource) {
        this.ressource = ressource;
    }

    public String getNom() {
        return nom;
    }
}
