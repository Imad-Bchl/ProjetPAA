import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        if(args.length == 0){
        //Test predifini :
        PredefinedTest.test();

        Scanner sc = new Scanner(System.in), scanner = new Scanner(System.in);
        int nColon, choix, nPrefAjouter = 0, resInter, JalousieRate;
        String col1, col2, pref, aff;
        System.out.println("\t **** Bonjour Astronaut **** \n De combien de personnes se constituent votre colonie ?");
        do {
            nColon = sc.nextInt();
            if (nColon < 1 || nColon > 26) {
                System.out.println("Le nombre de colon doit etre compris entre 1 et 26");
            }
        }while (nColon >26 || nColon<1);

        Colonie Mars = new Colonie(nColon);
        System.out.println("Tres bien votre colonie est creer");
        System.out.println(Mars.getColonie().size());
        do {
            System.out.println("Que voulez faire maintenant, vous pouvez: \n" +
                    " \t 1) Ajouter une relation entre deux colons\n" +
                    " \t 2) Ajouter les préférences d’un colon\n" +
                    " \t 3) Fin.");
            choix = sc.nextInt();
            switch (choix) {
                case 1:
                    System.out.println("le premier colon est:");
                    col1 = sc.next();
                    System.out.println("le deuxieme colon est:");
                    col2 = sc.next();
                    Mars.DefineRelation(col1, col2);
                    System.out.println("Relation ajouter avec success");
                    break;

                case 2:
                    System.out.println("Tapez les preferance de l'un de vos colon sous la forme: NomDuColon Pref1 Pref2 Pref3 .... PrefN" );
                    pref = scanner.nextLine();
                    Mars.AddPreferance(pref);
                    System.out.println("Preference ajouter avec success");
                    nPrefAjouter++;
                    break;

                default:
                    if(nPrefAjouter == nColon){
                        choix = 77;
                    }
                    else{
                        System.out.println("Vous n'avez pas attribuer des preferances a tout vos colons");
                    }
            }
        }while (choix != 77);

        Mars.Affectation();

        System.out.println("Nous vous proposons cette solution naive: ");
        for (int i = 0; i < Mars.getColonie().size(); i++) {
            aff = Mars.getColonie().get(i).getNom() + " " + Mars.getColonie().get(i).getRessource();
            System.out.println(aff);
        }

        do {
            System.out.println("Que voulez faire maintenant, vous pouvez: \n" +
                    " \t 1) Echanger les ressources de deux colons\n" +
                    " \t 2) Ajouter le nombre de colons jaloux\n" +
                    " \t 3) Fin.\n");
            choix = sc.nextInt();
            switch (choix) {
                case 1:
                    System.out.println("le premier colon est:");
                    col1 = sc.next();
                    System.out.println("le deuxieme colon est:");
                    col2 = sc.next();
                    resInter = Mars.getColonie().get(Mars.SearchColon(col1)).getRessource();
                    Mars.getColonie().get(Mars.SearchColon(col1)).ModifyRessource(Mars.getColonie().get(Mars.SearchColon(col2)).getRessource());
                    Mars.getColonie().get(Mars.SearchColon(col2)).ModifyRessource(resInter);
                    System.out.println("Echange fait avec success");
                    break;

                case 2:
                    JalousieRate = Mars.JalousyRateCalculator();
                    System.out.println("La solution actuel fait " + JalousieRate + "jaloux");
                    break;
                
            }
        }while (choix < 1 || choix > 2);

    }
    else{
        File file = new File(args[0]);
            if (file.exists()) {
                // Créer la colonie à partir du fichier
                try {
                    Colonie colonie = new Colonie(3);  // 5 est un exemple, à adapter selon le fichier
                    colonie.lireFichier(file);
                    colonie.validerFichier(file.getAbsolutePath());
                    afficherMenu(colonie);
                } catch (FileNotFoundException e) {
                    System.out.println("Le fichier spécifié n'existe pas.");
                } catch (Exception e) {
                     e.printStackTrace();
                }
            } else {
                System.out.println("Le fichier " + args[0] + " est introuvable.");
            }
        }
    }

    // Fonction pour lancer le menu et interagir avec l'utilisateur
    private static void afficherMenu(Colonie colonie) {
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            System.out.println("\nChoisissez une option:");
            System.out.println("1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin");
            
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1:
                    // Échanger les ressources entre deux colons
                    System.out.print("Nom du premier colon : ");
                    String colon1 = scanner.nextLine();
                    System.out.print("Nom du deuxième colon : ");
                    String colon2 = scanner.nextLine();
                    echangerRessources(colonie, colon1, colon2);
                    break;
                case 2:
                    // Afficher le nombre de colons jaloux
                    int jalousie = colonie.JalousyRateCalculator();
                    System.out.println("Nombre de colons jaloux : " + jalousie);
                    break;
                case 3:
                    // Quitter le programme
                    quitter = true;
                    System.out.println("Fin du programme");
                    break;
                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
            }
        }
        scanner.close();
    }

    // Fonction pour échanger les ressources de deux colons
    private static void echangerRessources(Colonie colonie, String colon1, String colon2) {
        int index1 = colonie.SearchColon(colon1);
        int index2 = colonie.SearchColon(colon2);

        if (index1 != -1 && index2 != -1) {
            Colon c1 = colonie.getColonie().get(index1);
            Colon c2 = colonie.getColonie().get(index2);

            // Échanger les ressources
            int temp = c1.getRessource();
            c1.setRessource(c2.getRessource());
            c2.setRessource(temp);

            System.out.println("Les ressources des colons " + colon1 + " et " + colon2 + " ont été échangées.");
        } else {
            System.out.println("Un ou les deux colons n'ont pas été trouvés.");
        }
    }

   
}
