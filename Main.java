import javax.imageio.spi.ImageReaderSpi;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

/**
 * Starts the program without a file input.
 * Interactively prompts the user to create a colony, define relationships,
 * and preferences, and calculates the jealousy rate.
 */
    public static void MainWithoutFile(){
        Scanner sc = new Scanner(System.in), scanner = new Scanner(System.in);
        int nColon = 0, choix = 0, resInter, JalousieRate;
        ArrayList<String> PreferanceAdded = new ArrayList<>();
        String col1, col2, pref, aff;
        System.out.println("\t **** Bonjour Astronaut **** \n De combien de personnes se constituent votre colonie ?");
        do {            do{
                try{
                    nColon = sc.nextInt();
                }catch (Exception e){
                    System.out.println("Saisie invalide: saisissez une nombre entier de 1 à 4");
                    sc.next();
                }
            }while(nColon == 0);

            if (nColon < 1 || nColon > 26) {
                System.out.println("Le nombre de colon doit etre compris entre 1 et 26");
            }
        }while (nColon >26 || nColon<1);

        Colonie Mars = new Colonie(nColon);
        System.out.println("Tres bien votre colonie est creer");
        do {
            System.out.println("Que voulez faire maintenant, vous pouvez: \n" +
                    " \t 1) Ajouter une relation entre deux colons\n" +
                    " \t 2) Ajouter les préférences d’un colon\n" +
                    " \t 3) Fin.");
            do{
                try{
                    choix = sc.nextInt();
                }catch (Exception e){
                    System.out.println("Saisie invalide: saisissez une nombre entier de 1 à 4");
                    sc.next();
                }
            }while(choix == 0);

            switch (choix) {
                case 1:
                    System.out.println("le premier colon est:");
                    col1 = sc.next();
                    System.out.println("le deuxieme colon est:");
                    col2 = sc.next();
                    if(Mars.SearchColon(col1) != -1 && Mars.SearchColon(col2) != -1 ) {
                        Mars.DefineRelation(col1, col2);
                        System.out.println("Relation ajouter avec success");
                    }
                    else{
                        System.out.println("L'un ou les deux colon n'existe pas dans la colonie");
                    }
                    break;

                case 2:
                    System.out.println("Tapez les preferance de l'un de vos colon sous la forme: NomDuColon Pref1 Pref2 Pref3 .... PrefN" );
                    pref = scanner.nextLine();
                    String[] prefSplitted = pref.split(" ");
                    if(prefSplitted.length == nColon+1){
                        if(PreferanceAdded.contains(pref.split(" ")[0])){
                            System.out.println("Preferance de colon déja ajouter, voulez vous les modifier ? \n" +
                                    "1- Oui \n" +
                                    "2- Non \n");
                            int c = scanner.nextInt();
                            switch (c){
                                case 1:
                                    Mars.AddPreferance(pref);
                                    System.out.println("Preferance ajouter avec success");
                                    break;
                                case 2:
                                    System.out.println("Preferance non modifier");
                                    break;
                                default:
                                    System.out.println("Saisie Invalide, Preferance non modifier");
                            }
                        }
                        else{
                            if(Mars.SearchColon(pref.split(" ")[0]) != -1){
                                Mars.AddPreferance(pref);
                                System.out.println("Preferance ajouter avec success");
                                PreferanceAdded.add(pref.split(" ")[0]);
                            }
                            else{
                                System.out.println("Le nom de colon saisie est introuvable, reesayer en respectant la syantaxe indiquer");
                            }
                        }
                    }
                    else {
                        System.out.println("Saisie invalide: Veuillez Suivre le format 'NomDuColon Pref1 Pref2 Pref3 .... PrefN' avec autant de preferances que de ressources disponible !");
                    }
                    break;

                default:
                    if(PreferanceAdded.size() == nColon){
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
        choix = 0;
        do {
            System.out.println("Que voulez faire maintenant, vous pouvez: \n" +
                    " \t 1) Echanger les ressources de deux colons\n" +
                    " \t 2) Calculer le nombre de colons jaloux\n" +
                    " \t 3) Afficher les affectations actuelle \n" +
                    " \t 4)Fin. \n");
            do{
                try{
                    choix = sc.nextInt();
                }catch (Exception e){
                    System.out.println("Saisie invalide: saisissez une nombre entier de 1 à 4");
                    sc.next();
                }
            }while(choix == 0);

            switch (choix) {
                case 1:
                    System.out.println("le premier colon est:");
                    col1 = sc.next();
                    System.out.println("le deuxieme colon est:");
                    col2 = sc.next();
                    int rescol1 = Mars.getColonie().get(Mars.SearchColon(col1)).getRessource();
                    int rescol2 = Mars.getColonie().get(Mars.SearchColon(col2)).getRessource();
                    Mars.getColonie().get(Mars.SearchColon(col1)).ModifyRessource(rescol2);
                    Mars.getColonie().get(Mars.SearchColon(col2)).ModifyRessource(rescol1);
                    System.out.println("Echange fait avec success");
                    break;

                case 2:
                    JalousieRate = Mars.JalousyRateCalculator();
                    System.out.println("La solution actuel fait " + JalousieRate + " jaloux");
                    break;
                case 3:
                    for (int i = 0; i < Mars.getColonie().size(); i++) {
                        aff = Mars.getColonie().get(i).getNom() + " " + Mars.getColonie().get(i).getRessource();
                        System.out.println(aff);
                    }
                    break;
                default:
                    System.out.println("Bye Astronaut ");
            }
        }while (choix > 0 && choix < 5);
    }

/**
 * Starts the program with a file input.
 * Reads colony data from the specified file and provides options for
 * automatic resolution, saving solutions, and calculating jealousy.
 *
 * @param path The path to the input file containing colony data.
 * @throws InputNonValideException If the provided path is invalid.
 */
    public static void MainWithFile(String path){
        Colonie Mars = new Colonie(path);
        System.out.println(" \t\t ****** Bonjour Astraunote ***** \n Votre colonie est creer Avec success");
        Boolean resolu = false;
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        System.out.println("Que souhaitez vous faure ? , vous pouvez: \n" +
                " \t 1) Resolution automatique du problème \n" +
                " \t 2) Sauvgarder la solution dans un fichier\n" +
                " \t 3) Voir le taux de jalousie" +
                " \t 4) Fin.");
        do{
            try{
                choix = sc.nextInt();
            }catch (Exception e){
                System.out.println("Saisie invalide: saisissez une nombre entier de 1 à 4");
                sc.next();
            }
        }while(choix == 0);

        switch (choix) {
            case 1:
                //reolution auto algo
                resolu = true;
                break;
            case 2:
                if(resolu){
                    System.out.println("ou souhaiter vous enregistrer la solution ?");
                    String pathSolution = sc.nextLine();
                    Mars.EnregistrerSolutionFile(pathSolution);
                    System.out.println("Solution enregistrer avec success dans le fichier Solution.txt");
                }else{
                    System.out.println("Vous n'avez pas encore lancer la résolution automatique");
                }
                break;
            case 3:
                if(resolu){
                    System.out.println("Le taux de jalousie de la solution est: " + Mars.JalousyRateCalculator());
                }else{
                    System.out.println("Vous n'avez pas encore lancer la résolution automatique");
                }
                break;
            default:
                System.out.println("Heureux de vous avoir rencontrer Astraunote, A une rochaine fois! ");
                choix = 77;
        }while (choix != 77);
    }


/**
 * The main entry point of the application.
 *
 * @param args Command-line arguments. The first argument can be a file path.
 * @throws InputNonValideException If the provided file path is invalid.
 */
    public static void main(String[] args) {
        if(args.length == 0){
            MainWithoutFile();
        }
        else{
            if(args.length == 1){
                MainWithFile(args[0]);
            }else{
                throw new InputNonValideException("Input Invalide: veuillez saisir un chemin valide vers le fichier");
            }
        }

    }

}