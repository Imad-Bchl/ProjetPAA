package ProjetPAA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Project {
    public static void main(String[] args) throws FileNotFoundException {
        Colonie Mars = new Colonie(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(Mars.getColonie().get(i).getNom());
        }
        Mars.DefineRelation("A","D");
        Mars.DefineRelation("A","C");
        Mars.DefineRelation("C","D");
        Mars.DefineRelation("B","D");
        int[][] mat = Mars.getMatriceIncidence();
        for (int i = 0; i < mat.length; i++) { // Parcours des lignes
            for (int j = 0; j < mat[i].length; j++) { // Parcours des colonnes
                System.out.print(mat[i][j] + "\t"); // Affiche chaque élément avec un tab pour espacer
            }
            System.out.println(); // Nouvelle ligne après chaque ligne de la matrice
        }
        String text = "A 1 2 3 4 5";
        Mars.AddPreferance(text);
        Colon o = Mars.getColonie().get(Mars.SearchColon("A"));
        System.out.println(o.getPreferances());

        File file = new File("./ProjetPAA/Colonie.txt");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        System.out.println(file.getAbsolutePath());
        Colonie venus = new Colonie("./ProjetPAA/Colonie.txt");
        System.out.println(venus.getColonie());
    }
}
