package entities;

public class Produit {
	
	private int Id;
	private String code;
	private String designation;
	private int remise;
	private float prix;
	private int quantite ;
	
	public Produit(int id, String code, String designation,int remise, float prix,
			int quantite) {
	
		Id = id;
		this.code = code;
		this.designation = designation;
		
		this.remise = remise;
		this.prix = prix;
		this.quantite = quantite;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		code = code;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

        
	public int getRemise() {
		return remise;
	}
	public void setRemise(int remise) {
		this.remise = remise;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

    @Override
    public String toString() {
        return "Produit{" + "Id=" + Id + ", code_Produit=" + code + ", designation=" + designation + ", remise=" + remise + ", prix=" + prix + ", quantite=" + quantite + '}';
    }
	
}


