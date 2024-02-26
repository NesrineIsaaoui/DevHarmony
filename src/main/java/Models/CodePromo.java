package Models;
public class CodePromo {

    private int id;
    private int id_user;
    private int id_categorieCodePromo;

    public CodePromo(int id_promo, int id_user, int id_code) {
        this.id = id_promo;
        this.id_user = id_user;
        this.id_categorieCodePromo = id_code;
    }

    public CodePromo(int id_user, int id_code) {
        this.id_user = id_user;
        this.id_categorieCodePromo = id_code;
    }

    public int getId_promo() {
        return id;
    }

    public void setId_promo(int id_promo) {
        this.id= id_promo;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_code() {
        return id_categorieCodePromo;
    }

    public void setId_code(int id_code) {
        this.id_categorieCodePromo = id_code;
    }

    @Override
    public String toString() {
        return "promotion{" +
                "id_promo=" + id +
                ", id_user=" + id_user +
                ", id_code=" + id_categorieCodePromo +
                '}';
    }
}
