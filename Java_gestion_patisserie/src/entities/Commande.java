package entities;



public class Commande {
    private int Id;
    private String nom_client ;
    private String designation ;
    private String date ;
    private double prix ;
    private double accompte ;
    private double reste ;

    public Commande(int Id, String nom_client, String designation, String date, double prix, double accompte, double reste) {
        this.Id = Id;
        this.nom_client = nom_client;
        this.designation = designation;
        this.date = date;
        this.prix = prix;
        this.accompte = accompte;
        this.reste = reste;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getDes() {
        return designation;
    }

    public void setDes(String designation) {
        this.designation = designation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getAccompte() {
        return accompte;
    }

    public void setAccompte(double accompte) {
        this.accompte = accompte;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    @Override
    public String toString() {
        return "Commande{" + "Id=" + Id + ", nom_client=" + nom_client + ", designation=" + designation + ", date=" + date + ", prix=" + prix + ", accompte=" + accompte + ", reste=" + reste + '}';
    }
    
    
    
    
}
