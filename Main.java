package project;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in), scanner = new Scanner(System.in);
        int nColon, choix, nPrefAjouter = 0, resInter, JalousieRate;
        String col1, col2, pref, aff;
        System.out.println("\t **** Bonjour Astronaut XX **** \n De combien de personnes se constituent votre colonie ?");
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
                    if(Mars.AddPreferance(pref)){
                        nPrefAjouter++;
                    };
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
                    " \t 2) Calculer le nombre de colons jaloux\n" +
                    " \t 3) Afficher les affectations actuelle \n" +
                    " \t 4)Fin. \n");
            choix = sc.nextInt();
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
}
