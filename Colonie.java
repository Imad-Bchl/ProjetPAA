import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Colonie {
    private ArrayList<Colon> colonie;
    private Solution solution;
    private int[][] matriceIncidence;

    public Colonie(int n) {
        this.matriceIncidence = new int[n][n];
        this.solution = new Solution();
        this.colonie = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.colonie.add(new Colon ( Character.toString((char) 65+i) ,i));
        }
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

    //Methode qui prend un estring avec le nom du colon puis la liste des preference separer par un espace
    public void AddPreferance(String Str){
        //verifier qu'on a N preferance DISTINCTE
        StringTokenizer tokenizer = new StringTokenizer(Str, " ");
        if(tokenizer.countTokens() == this.colonie.size()){
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

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public int[][] getMatriceIncidence() {
        return matriceIncidence;
    }

    public void setMatriceIncidence(int[][] matriceIncidence) {
        this.matriceIncidence = matriceIncidence;
    }

    //appelle les algo d'affectation et enregistre le meilleur dans le sol de l'objet
    public void Affectation(){};

    // 3 ou 4 algo d'affectation a appller la haut

    public void JalousyRateCalculator(){};
}
