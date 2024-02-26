package Models;

public class CategorieCodePromo {

    private int id;
    private String code;
    private float value;
    private int nb_users;

    public CategorieCodePromo() {
    }

    public CategorieCodePromo(int id, String code, float value, int nb_users) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.nb_users = nb_users;
    }

    public CategorieCodePromo(String code, float value, int nb_users) {
        this.code = code;
        this.value = value;
        this.nb_users = nb_users;
    }

    public int getId() {
        return id;
    }

    public void setId_code(int id_code) {
        this.id = id_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getNb_users() {
        return nb_users;
    }

    public void setNb_users(int nb_users) {
        this.nb_users = nb_users;
    }

    @Override
    public String toString() {
        return "CategorieP{" +
                "id_code=" + id+
                ", code='" + code + '\'' +
                ", value=" + value +
                ", nb_users=" + nb_users +
                '}';
    }
}
