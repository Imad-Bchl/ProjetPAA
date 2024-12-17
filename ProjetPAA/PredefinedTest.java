package ProjetPAA;

public class PredefinedTest {
    public static void test() {
        Colonie Mars = new Colonie(5);
        System.out.println("Création de la colonie avec 5 colons : " + Mars.getColonie().size());

        // Définir les relations de haine
        Mars.DefineRelation("A", "B");
        Mars.DefineRelation("A", "C");
        Mars.DefineRelation("C", "D");
        Mars.DefineRelation("B", "E");
        System.out.println("Relations de haine ajoutées.");

        // Ajouter les préférences pour chaque colon
        Mars.AddPreferance("A 1 2 3 4 5");
        Mars.AddPreferance("B 1 3 2 5 4");
        Mars.AddPreferance("C 3 1 2 4 5");
        Mars.AddPreferance("D 4 3 5 1 2");
        Mars.AddPreferance("E 5 2 3 1 4");
        System.out.println("Préférences ajoutées.");

        // Effectuer l'affectation naïve
        Mars.Affectation();
        System.out.println("Affectation naïve terminée.");

        // Afficher la répartition des ressources
        System.out.println("Répartition des ressources :");
        for (Colon colon : Mars.getColonie()) {
            System.out.println("Colon " + colon.getNom() + " a reçu la ressource " + colon.getRessource());
        }

        // Calculer et afficher le taux de jalousie
        int tauxDeJalousie = Mars.JalousyRateCalculator();
        System.out.println("Taux de jalousie de la solution : " + tauxDeJalousie);
    }
}
