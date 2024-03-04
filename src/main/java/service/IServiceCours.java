package service;

import model.Cours;

import java.util.List;

public interface IServiceCours {
    public void ajouterCours(Cours c);

    public List<Cours> afficherCours();

    public void modifierCours(Cours c);

    public void supprimerCours(int id);

  //  public int getNombreAvisByRating(int idCours, int rating);
}
