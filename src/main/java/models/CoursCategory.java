package models;

public class CoursCategory {
    private int id;
    private String categoryName;

    public CoursCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public CoursCategory(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CoursCategory() {
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CoursCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
