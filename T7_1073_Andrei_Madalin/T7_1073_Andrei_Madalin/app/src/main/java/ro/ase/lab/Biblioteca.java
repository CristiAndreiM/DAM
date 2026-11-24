package ro.ase.lab;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Biblioteca")
public class Biblioteca {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String denumire;
    private String adresa;
    private int capacitate;
    private int nrcarti;
    public Biblioteca(int capacitate, String adresa, String denumire, int nrcarti) {
        this.capacitate = capacitate;
        this.adresa = adresa;
        this.denumire = denumire;
        this.nrcarti = nrcarti;
    }

    public int getNrcarti() {
        return nrcarti;
    }

    public void setNrcarti(int nrcarti) {
        this.nrcarti = nrcarti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Biblioteca{");
        sb.append("id=").append(id);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", adresa='").append(adresa).append('\'');
        sb.append(", capacitate=").append(capacitate);
        sb.append(", nrcarti=").append(nrcarti);
        sb.append('}');
        return sb.toString();
    }
}
