package ro.ase.restaurante;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String nume;
    private String bucatarie;
    private String telefon;
    private float rating;
    private boolean livrare;

    public Restaurant(String nume, String bucatarie, String telefon, float rating, boolean livrare) {
        this.nume = nume;
        this.bucatarie = bucatarie;
        this.telefon = telefon;
        this.rating = rating;
        this.livrare = livrare;
    }

    public String getNume() {
        return nume;
    }

    public String getBucatarie() {
        return bucatarie;
    }

    public String getTelefon() {
        return telefon;
    }

    public double getRating() {
        return rating;
    }

    public boolean isLivrare() {
        return livrare;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "nume='" + nume + '\'' +
                ", bucatarie='" + bucatarie + '\'' +
                ", telefon='" + telefon + '\'' +
                ", rating=" + rating +
                ", livrare=" + livrare +
                '}';
    }
}
