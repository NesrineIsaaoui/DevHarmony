package entities;

public class Plan {
    private int idplan;
    private String nom;

    public int getIdplan() {
        return idplan;
    }

    public void setIdplan(int idplan) {
        this.idplan = idplan;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Plan() {
    }

    public Plan(int idplan, String nom) {
        this.idplan = idplan;
        this.nom = nom;
    }

    public Plan(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "idplan=" + idplan +
                ", nom='" + nom + '\'' +
                '}';
    }
}
