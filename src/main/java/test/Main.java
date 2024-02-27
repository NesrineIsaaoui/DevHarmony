package test;

import models.Cours;
import models.CoursCategory;
import services.ServiceCours;
import services.ServiceCoursCategory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

   //   CoursCategory c= new CoursCategory("salut");
       ServiceCoursCategory crud = new ServiceCoursCategory();
     //  crud.ajouterCoursCategory(c);

        CoursCategory c1= new CoursCategory(15,"salut");
   crud.modifierCoursCategory(c1);
    //   crud.supprimerCoursCategory(11);
       // System.out.println( crud.afficherCoursCategory());
       // ServiceCours crudCours=new ServiceCours();
       // Cours cours =new Cours("JavaFX","description : ***","image",100,2);
      //  crudCours.ajouterCours(cours);

         //System.out.println( crudCours.afficherCours());
//Cours cours1=new Cours(2,"JavaFX","description : ***","image",500,2);
      //  crudCours.modifierCours(cours1);

     //   crudCours.supprimerCours(2);
    }
}