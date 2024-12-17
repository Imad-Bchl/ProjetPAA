package ProjetPAA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileWriter;


/**
 * Represents a colony of individuals on Mars.
 *
 * A Colonie manages a collection of Colon objects, maintains an incidence matrix for relationships,
 * and provides functionalities for reading data from files, assigning resources, and calculating jealousy rate.
 */
public class Colonie {
    // Collection of Colon objects representing the individuals in the colony
    private ArrayList<Colon> colonie;
    // Solution object representing the resource allocation (optional)
    private Solution solution;
    // Incidence matrix representing relationships between individuals (1: hate, 0: no hate)s
    private int[][] matriceIncidence;


/**
 * Constructs a Colonie object with a specified number of individuals.
 *
 * @param n The number of individuals in the colony.
 */
    public Colonie(int n) {
        this.matriceIncidence = new int[n][n];
        //this.solution = new Solution();
        this.colonie = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.colonie.add(new Colon ( Character.toString((char) 65+i) ,i));
        }
    }

/**
 * Constructs a Colonie object by reading data from a file.
 *
 * @param path The path to the file containing colony data.
 * @throws IOException If an error occurs while reading the file.
 */
    public Colonie(String path) {
        try{
            lireDepuisFichier(path);
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
    }

/**
 * Reads colony data from a specified file.
 *
 * @param cheminFichier The path to the file.
 * @throws IOException If an error occurs while reading the file.
 */
    public void lireDepuisFichier(String cheminFichier) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
        String ligne;

        while ((ligne = reader.readLine()) != null) {
            ligne = ligne.trim();
            System.out.println(ligne);

            if (ligne.startsWith("colon(")) {
                // Ajouter un colon à la colonie
                String nomColon = ligne.substring(6, ligne.length() - 1);
                colonie.add(new Colon(nomColon, colonie.size()));
            } else if (ligne.startsWith("ressource(")) {
                // Une ressource est identifiée par son numéro
                String nomRessource = ligne.substring(10, ligne.length() - 1);
                int ressource = Integer.parseInt(nomRessource);
                // Ajouter chaque ressource dans les préférences des colons
                for (Colon colon : colonie) {
                    colon.getPreferances().add(ressource);
                }
            } else if (ligne.startsWith("deteste(")) {
                // Ajouter une relation de haine
                String[] noms = ligne.substring(8, ligne.length() - 1).split(",");
                DefineRelation(noms[0], noms[1]);
            } else if (ligne.startsWith("preferences(")) {
                // Ajouter les préférences pour un colon
                String[] parties = ligne.substring(12, ligne.length() - 1).split(",");
                String nomColon = parties[0];
                ArrayList<Integer> preferences = new ArrayList<>();
                for (int i = 1; i < parties.length; i++) {
                    preferences.add(Integer.parseInt(parties[i]));
                }
                Colon colon = colonie.get(SearchColon(nomColon));
                colon.setPreferances(preferences);
            }
        }
        reader.close();

        // Initialiser la matrice d'incidence
        matriceIncidence = new int[colonie.size()][colonie.size()];
    }


/**
 * Saves the solution (resource allocation) to a file.
 *
 * @param path The path to the output file.
 * @throws IOException If an error occurs while writing to the file.
 */
    public void EnregistrerSolutionFile(String path){
        try (FileWriter writer = new FileWriter(path)) {
            String ligne;
            for (int i = 1; i <= 10; i++) {
                ligne = colonie.get(i).getNom() + ":" + colonie.get(i).getRessource() + "\n";
                writer.write(ligne);
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture : " + e.getMessage());
        }
    }

    // Function to search for a Colon object by name
    public int SearchColon(String colon){
        //il faut verifier que le colon exist
        int i = -1;
        for (Colon col : this.colonie) {
            if (col.getNom().equals(colon)) { // Comparaison du nom
                i = col.getIndex();
            }
        }
        return i;
    }

/**
 * Defines a "hate" relationship between two individuals in the colony.
 *
 * @param ColonA The name of the first colonist.
 * @param ColonB The name of the second colonist.
 */
    public void DefineRelation(String ColonA, String ColonB) {
        // verifie l'existance des colons
        Colon C1 = this.colonie.get(SearchColon(ColonA)),
                C2 = this.colonie.get(SearchColon(ColonB));
        if (C1.getIndex() > C2.getIndex()){
            this.matriceIncidence[C2.getIndex()][C1.getIndex()] = 1;
        }
        else{
            this.matriceIncidence[C1.getIndex()][C2.getIndex()] = 1;
        }
        int res;
        for (int i = 0; i < this.matriceIncidence.length; i++) {
            res = 0;
            for (int j = 0; j < this.matriceIncidence[i].length; j++) {
                if(i!=j){
                    res += this.matriceIncidence[i][j];
                    res += this.matriceIncidence[j][i];
                }
            }
            matriceIncidence[i][i] = res;
        }
    }

/**
 * Adds preferences for a colonist, specifying the order of desired resources.
 *
 * @param Str A string containing the colonist's name followed by preferred resources separated by spaces.
 */
    public void AddPreferance(String Str){
        //verifier qu'on a N preferance DISTINCTE
        StringTokenizer tokenizer = new StringTokenizer(Str, " ");
        if(tokenizer.countTokens() == this.colonie.size()+1){
            Colon Colon1 = this.colonie.get(SearchColon(tokenizer.nextToken()));
            ArrayList<Integer> col = Colon1.getPreferances();
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                col.add(i, Integer.parseInt(tokenizer.nextToken()));
                i++;
            }
            Colon1.setPreferances(col);
        }
        else{
            System.out.println("ERREUR: chaque colon doit donner un ordre a tt les objets");
        }
    }

    public ArrayList<Colon> getColonie() {
        return colonie;
    }

    public void setColonie(ArrayList<Colon> colonie) {
        this.colonie = colonie;
    }

    //public Solution getSolution() {
    //    return solution;
    //}

    //public void setSolution(Solution solution) {
    //    this.solution = solution;
    //}

    public int[][] getMatriceIncidence() {
        return matriceIncidence;
    }

    public void setMatriceIncidence(int[][] matriceIncidence) {
        this.matriceIncidence = matriceIncidence;
    }

// Function to perform a naive resource allocation
    public void Affectation(){
        //initialisation d'une array de ressources
        ArrayList<Integer> resource = new ArrayList<>();
        for (int i = 1; i <= this.colonie.size(); i++) {
            resource.add(i);
        }


        //Attribution du premier disponible
        for (Colon colon : this.colonie) {
            for (int j = 0; j < colon.getPreferances().size(); j++) {
                for (int i = 0; i < resource.size(); i++) {
                    if ( resource.get(i) == colon.getPreferances().get(j)){
                        colon.setRessource(resource.get(i));
                        resource.remove(resource.get(i));
                        j = colon.getPreferances().size() ;
                        break;
                    }
                }
            }
        }
    };


/**
 * Calculates the jealousy rate of the colony based on resource allocation and relationships.
 *
 * @return The total number of "jealous" individuals.
 */
   public int JalousyRateCalculator() {
    int tauxDeJalousie = 0;

    // Parcourir tous les colons
    for (Colon colon1 : colonie) {
        int index1 = colon1.getIndex();

        // Parcourir tous les autres colons
        for (Colon colon2 : colonie) {
            int index2 = colon2.getIndex();

            // Vérifier s'il y a une relation de "haine" entre colon1 et colon2
            if (matriceIncidence[index1][index2] == 1 || matriceIncidence[index2][index1] == 1) {

                // Récupérer les préférences de colon1
                ArrayList<Integer> preferences1 = colon1.getPreferances();

                // Si colon1 préfère la ressource de colon2 à la sienne, il est "jaloux"
                if (preferences1.indexOf(colon2.getRessource()) < preferences1.indexOf(colon1.getRessource())) {
                    tauxDeJalousie++;
                    break;  // Un colon peut être jaloux une seule fois, donc on passe au suivant
                }
            }
        }
    }

    return tauxDeJalousie;
}
}