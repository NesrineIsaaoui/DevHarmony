package models;

public class Cours {
    private int id ;
    private String coursName;
    private String coursDescription;
    private String coursImage;
    private int coursPrix;
    private int idCategory ;

    public Cours() {
    }

    public Cours( String coursName, String coursDescription, String coursImage, int coursPrix, int idCategory) {

        this.coursName = coursName;
        this.coursDescription = coursDescription;
        this.coursImage = coursImage;
        this.coursPrix = coursPrix;
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public Cours(int id, String coursName, String coursDescription, String coursImage, int coursPrix, int idCategory) {
        this.id = id;
        this.coursName = coursName;
        this.coursDescription = coursDescription;
        this.coursImage = coursImage;
        this.coursPrix = coursPrix;
        this.idCategory = idCategory;
    }

    public String getCoursName() {
        return coursName;
    }

    public String getCoursDescription() {
        return coursDescription;
    }

    public String getCoursImage() {
        return coursImage;
    }

    public int getCoursPrix() {
        return coursPrix;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoursName(String coursName) {
        this.coursName = coursName;
    }

    public void setCoursDescription(String coursDescription) {
        this.coursDescription = coursDescription;
    }

    public void setCoursImage(String coursImage) {
        this.coursImage = coursImage;
    }

    public void setCoursPrix(int coursPrix) {
        this.coursPrix = coursPrix;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", coursName='" + coursName + '\'' +
                ", coursDescription='" + coursDescription + '\'' +
                ", coursImage='" + coursImage + '\'' +
                ", coursPrix=" + coursPrix +
                ", idCategory=" + idCategory +
                '}';
    }

}
