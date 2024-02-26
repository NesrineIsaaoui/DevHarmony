package org.example;

import Models.CategorieCodePromo;
import Models.CodePromo;
import Services.CategorieCodePromoService;
import Services.CodePromoService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        CategorieCodePromo cd=new CategorieCodePromo("1AzF", 0.20F,100);
        CategorieCodePromoService cs=new CategorieCodePromoService();
        cs.addCategorieCodePromo(cd);



    }
}