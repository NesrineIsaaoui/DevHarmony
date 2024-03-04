package model;

import java.util.Objects;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String image;
    private String role;
    private int num_tel;
    private String email;
    private String pwd;
    private String status = "Active" ;
    private int resetCode;
    private String confirmcode;
    private int statuscode;

    public int getResetCode() {
        return resetCode;
    }

    public void setResetCode(int resetCode) {
        this.resetCode = resetCode;
    }

    public String getConfirmCode() {
        return confirmcode;
    }

    public void setConfirmCode(String confirmcode) {
        this.confirmcode = confirmcode;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public User(int id, String role, String text, String tfmdpText, String tfnomText, String pwd) {
        this.id = this.id;
        this.nom = nom;
        this.prenom = prenom;
        setRole(this.role);
        this.age=age;
        this.image=image;
        this.num_tel = num_tel;
        this.email = email;
        this.pwd = this.pwd;
    }

    public User(String nom, String prenom, String role, String email, String pwd) {
        this.nom = nom;
        this.prenom = prenom;
        setRole(role);
        this.email = email;
        this.pwd = pwd;
    }
    public User(String tfnom, String tfprenom, String role, String tfemailText, String tfmdpText, String pwd){}

    public User() {

    }

    public User(int id, String nom, String prenom, int age, String image, String role, int num_tel, String email, String pwd) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.image = image;
        this.role = role;
        this.num_tel = num_tel;
        this.email = email;
        this.pwd = pwd;
    }

    public static boolean isValidRole(String input) {
        for (Role role : Role.values()) {
            if (role.name().equals(input)) {
                return true;
            }
        }
        return false;
    }
    public  String getRole() {
        return role;
    }
    public void setRole(String role) {
        if(isValidRole(role)){
            this.role = role;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }







    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", image='" + image + '\'' +
                ", role=" + role +
                ", num_tel=" + num_tel +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && num_tel == user.num_tel && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(image, user.image) && role == user.role && Objects.equals(email, user.email) && Objects.equals(pwd, user.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, age, image, role, num_tel, email, pwd);
    }
}
