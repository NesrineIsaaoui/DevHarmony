package service;

import model.CoursCategory;

import java.util.List;

public interface IServiceCategory {
    public String ajouterCoursCategory(CoursCategory c);

    public List<CoursCategory> afficherCoursCategory();

    public void modifierCoursCategory(CoursCategory c);

    public void supprimerCoursCategory(int id);
}
