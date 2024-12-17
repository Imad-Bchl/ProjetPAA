import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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

    public static Colonie instancierColonie(int n){
        return new Colonie(n);
    }
    public static class Relation {
        private String colon1;
        private String colon2;
        public Relation(String colon1, String colon2){
            this.colon1 = colon1;
            this.colon2 = colon2;
        }
        public String getColon1(){
            return colon1;
        }
        public String getColon2(){
            return colon2;
        }
    }
    public static class Preference {
        private String colonName;
        private StringTokenizer tokenizer;
        public Preference(String colonName, StringTokenizer tokenizer){
            this.colonName = colonName;
            this.tokenizer = tokenizer;
        }
        public String getColonName(){
            return this.colonName;
        }
        public StringTokenizer getTokenizer(){
            return tokenizer;
        }
    }
    public void ajouterRelationsDeteste(ArrayList<String[]> relationsDeteste) {
        // Assurez-vous que la matrice d'incidence est vide avant d'ajouter de nouvelles relations
        for (int i = 0; i < matriceIncidence.length; i++) {
            for (int j = 0; j < matriceIncidence[i].length; j++) {
                matriceIncidence[i][j] = 0;
            }
        }
    
        // Ajouter les relations 'deteste' dans la matrice d'incidence
        for (String[] relation : relationsDeteste) {
            String colonA = relation[0];
            String colonB = relation[1];
    
            // Trouver les indices des colons
            int indexA = getIndexByColonName(colonA);
            int indexB = getIndexByColonName(colonB);
    
            if (indexA != -1 && indexB != -1) {
                // Marquer la relation 'deteste' (relation bidirectionnelle)
                matriceIncidence[indexA][indexB] = 1;
                matriceIncidence[indexB][indexA] = 1;
            } else {
                System.out.println("Erreur : Les colons " + colonA + " ou " + colonB + " n'existent pas.");
            }
        }
    }
    
    // Fonction pour obtenir l'index du colon à partir de son nom
    private int getIndexByColonName(String colonName) {
        for (int i = 0; i < colonie.size(); i++) {
            if (colonie.get(i).getNom().equals(colonName)) {
                return i;
            }
        }
        return -1; // Si le colon n'existe pas, retourner -1
    }



    public static Colonie lireFichier(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        boolean readingColons = false;
        boolean readingRessources = false;
        boolean readingDeteste = false;
        boolean readingPreferences = false;
        List<Colon> listeColons = new ArrayList<Colon>();
        List<Relation> listeRelations = new ArrayList<Relation>();
        List<Preference> listePreferences = new ArrayList<Preference>();
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Ignore les lignes vides
            if (line.isEmpty()) {
                continue;
            }

            // Détecter et traiter les différentes sections
            if (line.startsWith("colon")) {
                readingColons = true;
                readingRessources = false;
                readingDeteste = false;
                readingPreferences = false;
                // Extraire et ajouter un colon
                String colonName = line.substring(6, line.length() - 1).trim();
                listeColons.add(new Colon(colonName, index));
                index++;
            } else if (line.startsWith("deteste")) {
                readingColons = false;
                readingRessources = false;
                readingDeteste = true;
                readingPreferences = false;
                // Extraire et ajouter une relation
                String[] parts = line.substring(8, line.length() - 1).split(",");
                String colon1 = parts[0].trim();
                String colon2 = parts[1].trim();
                listeRelations.add(new Relation(colon1,colon2));
                // DefineRelation(colon1, colon2);
            } else if (line.startsWith("preferences")) {
                readingColons = false;
                readingRessources = false;
                readingDeteste = false;
                readingPreferences = true;
                // Extraire et ajouter les préférences
                StringTokenizer tokenizer = new StringTokenizer(line.substring(12).trim(), " ");
                if (tokenizer.hasMoreTokens()) {
                    String colonName = tokenizer.nextToken();
                    listePreferences.add(new Preference(colonName, tokenizer));
                    //AddPreferance(colonName, tokenizer);
                }
            }
        }
        Colonie nouvelleColonie = new Colonie(index);
        for (Preference preference : listePreferences) {
           nouvelleColonie.AddPreferance(preference.getColonName(),preference.getTokenizer());
        }
        for (Colon colon : listeColons) {
            nouvelleColonie.getColonie().add(colon);
        }
        for (Relation relation : listeRelations) {
            nouvelleColonie.DefineRelation(relation.getColon1(), relation.getColon2());
        }
        scanner.close();
        return nouvelleColonie;
    }


    // Une fonction qui retourne l'index du clon rechercher par son nom dans la colonie
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
        // Nettoyage des noms des colons
        ColonA = ColonA.trim().replaceAll("[^A-Za-z0-9]", "");
        ColonB = ColonB.trim().replaceAll("[^A-Za-z0-9]", "");
    
        // Vérifier l'existence des colons dans la colonie
        int indexC1 = SearchColon(ColonA);
        int indexC2 = SearchColon(ColonB);
    
        if (indexC1 == -1 || indexC2 == -1) {
            System.out.println("Erreur : un ou plusieurs colons n'existent pas dans la colonie.");
            return;
        }
    
        // Vérifier que les indices sont dans les limites de la matrice
        if (indexC1 >= matriceIncidence.length || indexC2 >= matriceIncidence.length) {
            System.out.println("Erreur : un index dépasse la taille de la matrice.");
            return;
        }
    
        Colon C1 = this.colonie.get(indexC1);
        Colon C2 = this.colonie.get(indexC2);
    
        // Mettre à jour la matrice d'incidence
        if (C1.getIndex() > C2.getIndex()) {
            this.matriceIncidence[C2.getIndex()][C1.getIndex()] = 1;
        } else {
            this.matriceIncidence[C1.getIndex()][C2.getIndex()] = 1;
        }
    
        // Calculer la somme des relations de haine
        int res;
        for (int i = 0; i < this.matriceIncidence.length; i++) {
            res = 0;
            for (int j = 0; j < this.matriceIncidence[i].length; j++) {
                if (i != j) {
                    res += this.matriceIncidence[i][j];
                    res += this.matriceIncidence[j][i];
                }
            }
            matriceIncidence[i][i] = res;
        }
    }
    
    public void AddPreferance(String colonName, StringTokenizer tokenizer) {
        int colonIndex = SearchColon(colonName);
        if (colonIndex != -1) {
            Colon colon = colonie.get(colonIndex);
            ArrayList<Integer> preferences = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                preferences.add(Integer.parseInt(tokenizer.nextToken()));
            }
            colon.setPreferances(preferences);
        }
    }

    public void afficherMatriceIncidence() {
        System.out.println("Matrice d'incidence :");
        for (int i = 0; i < matriceIncidence.length; i++) {
            for (int j = 0; j < matriceIncidence[i].length; j++) {
                System.out.print(matriceIncidence[i][j] + " ");
            }
            System.out.println(); // Pour passer à la ligne suivante après chaque ligne de la matrice
        }
    }
    

    public void validerFichier(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String line;
            ArrayList<String> colons = new ArrayList<>();
            ArrayList<String> ressources = new ArrayList<>();
            ArrayList<String[]> relationsDeteste = new ArrayList<>();
            ArrayList<String[]> preferences = new ArrayList<>();
            
            // Première passe pour lire les lignes du fichier
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
    
                // Vérification du format des lignes
                if (line.startsWith("colon(")) {
                    String colonName = line.substring(6, line.length() - 2);
                    colons.add(colonName);
                } else if (line.startsWith("ressource(")) {
                    String ressourceName = line.substring(10, line.length() - 2);
                    ressources.add(ressourceName);
                } else if (line.startsWith("deteste(")) {
                    String[] parts = line.substring(8, line.length() - 2).split(",");
                    relationsDeteste.add(parts);
                } else if (line.startsWith("preferences(")) {
                    String[] parts = line.substring(12, line.length() - 2).split(",");
                    preferences.add(parts);
                } else {
                    System.out.println("Erreur: ligne non reconnue - " + line);
                }
            }
            ajouterRelationsDeteste(relationsDeteste);
            afficherMatriceIncidence();
            // Debug : Vérifier les tailles des listes
            System.out.println("Nombre de colons : " + colons.size());
            System.out.println("Nombre de ressources : " + ressources.size());
            System.out.println("Nombre de relations 'deteste' : " + relationsDeteste.size());
            System.out.println("Nombre de préférences : " + preferences.size());
    
            // Affichage colons relations préférences
            System.out.println("\nListe des colons : ");
            for (String colon : colons) {
                System.out.println("- " + colon);
            }
    
            System.out.println("\nRelations 'deteste' : ");
            for (String[] relation : relationsDeteste) {
                System.out.println("- " + Arrays.toString(relation));
            }
    
            System.out.println("\nPréférences : ");
            for (String[] preference : preferences) {
                System.out.println("- " + Arrays.toString(preference));
            }
    
            // Validation nombre colons et ressources
            if (colons.size() != ressources.size()) {
                throw new IllegalArgumentException("Le nombre de colons doit être égal au nombre de ressources.");
            }
    
            // Validation relations "deteste"
            for (String[] relation : relationsDeteste) {
                if (relation.length != 2) {
                    throw new IllegalArgumentException("Les relations 'deteste' doivent être constituées de 2 colons.");
                }
                // Vérification que les colons existent
                if (!colons.contains(relation[0]) || !colons.contains(relation[1])) {
                    throw new IllegalArgumentException("Un ou plusieurs colons dans 'deteste' n'existent pas : " + Arrays.toString(relation));
                }
            }
    
            // Validation des préférences
            for (String[] pref : preferences) {
                if (pref.length != 1 + ressources.size()) {
                    throw new IllegalArgumentException("Les préférences doivent contenir " + (1 + ressources.size()) + " éléments.");
                }
                
                // Vérification que le colon et les ressources existent
                if (!colons.contains(pref[0])) {
                    throw new IllegalArgumentException("Le colon dans 'preferences' n'existe pas : " + pref[0]);
                }
                for (int i = 1; i < pref.length; i++) {
                    if (!ressources.contains(pref[i])) {
                        throw new IllegalArgumentException("La ressource dans 'preferences' n'existe pas : " + pref[i]);
                    }
                }
            }
    
            // Si tout est validé, afficher un message
            System.out.println("Fichier validé avec succès.");
            
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur illegal argument : " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    //Methode qui prend un estring avec le nom du colon puis la liste des preference separer par un espace
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
    
                // Vérifier les indices avant d'accéder à la matrice
                if (index1 < matriceIncidence.length && index2 < matriceIncidence.length) {
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
        }
    
        return tauxDeJalousie;
    }
    
    }
