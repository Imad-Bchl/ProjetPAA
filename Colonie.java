import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Colonie {
    private ArrayList<Colon> colonie;
    private Solution solution;
    private int[][] matriceIncidence;

    public Colonie(int n) {
        this.matriceIncidence = new int[n][n];
        //this.solution = new Solution();
        this.colonie = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.colonie.add(new Colon ( Character.toString((char) 65+i) ,i));
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
    


    public void lireFichier(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        boolean readingColons = false;
        boolean readingRessources = false;
        boolean readingDeteste = false;
        boolean readingPreferences = false;

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
                colonie.add(new Colon(colonName, colonie.size()));
            } else if (line.startsWith("deteste")) {
                readingColons = false;
                readingRessources = false;
                readingDeteste = true;
                readingPreferences = false;
                // Extraire et ajouter une relation de détestation
                String[] parts = line.substring(8, line.length() - 1).split(",");
                String colon1 = parts[0].trim();
                String colon2 = parts[1].trim();
                DefineRelation(colon1, colon2);
            } else if (line.startsWith("preferences")) {
                readingColons = false;
                readingRessources = false;
                readingDeteste = false;
                readingPreferences = true;
                // Extraire et ajouter les préférences
                StringTokenizer tokenizer = new StringTokenizer(line.substring(12).trim(), " ");
                if (tokenizer.hasMoreTokens()) {
                    String colonName = tokenizer.nextToken();
                    AddPreferance(colonName, tokenizer);
                }
            }
        }


        scanner.close();
    }


    // Une fonction qui retourne l'index du clon rechercher par son nom dans la colonie
    public int SearchColon(String colon){
        //il faut verifier que le colon exist
        int i = -1;
        for (Colon col : colonie) {
            if (col.getNom().equals(colon)) { // Comparaison du nom
                i = col.getIndex();
            }
        }
        return i;
    }

    // function qui rajoute une relation de hate entre 2 colons
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

    //fct qui fait l'algo d'attribution naive
    public void Affectation(){
        //initialisation d'une array de ressources
        ArrayList<Integer> resource = new ArrayList<>();
        for (int i = 1; i <= this.colonie.size(); i++) {
            resource.add(i);
        }
        System.out.println(resource);

        //Attribution du premier disponible
        for (Colon colon : this.colonie) {
            for (int j = 0; j < colon.getPreferances().size(); j++) {
                for (int i = 0; i < resource.size(); i++) {
                    System.out.println(colon.getNom() + " " + resource.get(i)+ " " + colon.getPreferances().get(j));
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

    // 3 ou 4 algo d'affectation a appller la haut

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
