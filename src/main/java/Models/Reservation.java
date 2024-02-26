package Models;

import java.time.LocalDateTime;
public class Reservation {

    private int id;

    private int id_user;

    private int id_cours;
    private boolean resStatus;
    private LocalDateTime dateReservation;
    private int id_codepromo;

    private float prixd;
    public Reservation() {
    }

    public Reservation(int id, int id_user, int id_cours, boolean resStatus, LocalDateTime dateReservation, int id_codepromo, float prixd) {
        this.id = id;
        this.id_user = id_user;
        this.id_cours = id_cours;
        this.resStatus = resStatus;
        this.dateReservation = dateReservation;
        this.id_codepromo = id_codepromo;
        this.prixd = prixd;
    }

    public Reservation(int id_user, int id_cours, boolean resStatus, LocalDateTime dateReservation, int id_codepromo, float prixd) {
        this.id_user = id_user;
        this.id_cours = id_cours;
        this.resStatus = resStatus;
        this.dateReservation = dateReservation;
        this.id_codepromo = id_codepromo;
        this.prixd = prixd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public boolean isResStatus() {
        return resStatus;
    }

    public void setResStatus(boolean resStatus) {
        this.resStatus = resStatus;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getId_codepromo() {
        return id_codepromo;
    }

    public void setId_codepromo(int id_codepromo) {
        this.id_codepromo = id_codepromo;
    }

    public float getPrixd() {
        return prixd;
    }

    public void setPrixd(float prixd) {
        this.prixd = prixd;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_cours=" + id_cours +
                ", resStatus=" + resStatus +
                ", dateReservation=" + dateReservation +
                ", id_codepromo=" + id_codepromo +
                ", prixd=" + prixd +
                '}';
    }
}
