/**
 * Created by benjellt on 1/18/17.
 */
public class GrandFloat {
    float f;
    float erreur;
    public GrandFloat(float f,float e){
        this.f=f;
        this.erreur=e;
    }
    public float getErreur(){
        return this.erreur;
    }
    public float getFLoatt(){
        return this.f;
    }
    @Override
    public String toString(){
        return " grand float =" + this.f +" + "+ this.erreur ;
    }



    // Somme deux float et garde l'erreur dans notre structure
    public static GrandFloat additionSimple(float f, float g){
        float somme = f+g;
        // on récupère le f erroné
        float fprime = somme -g;
        // on récupère le g erroné
        float gprime = somme -fprime;
        //on calcul l'erreur sur f
        float erreur_f = f- fprime;
        // on calcul l'erreur sur g
        float erreur_g = g-gprime;
        //on somme les erreurs
        float erreur = erreur_f + erreur_g;
        GrandFloat r= new GrandFloat(somme,erreur);
        return r;
    }








    //public static GrandFloat additionPrecise(float f, float g){
      //  float somme= f+g;
        //float tmp = somme-f;
        //float erreur= (f-(somme-tmp)) + (g-tmp);
        //GrandFloat r= new GrandFloat(somme,erreur);
        //return r;

    //}








    //Addition de deux grand float
    public static GrandFloat additionGrandFloat(GrandFloat ff , GrandFloat gg){
	  GrandFloat tmp1tmp1,tmp2tmp2 ;
	  tmp1tmp1 = additionSimple(ff.f,gg.f);
	  tmp2tmp2 = additionSimple(ff.erreur,gg.erreur);
      // on ajoute la somme des erreurs à l'erreur de la somme
	  tmp1tmp1.erreur+=tmp2tmp2.f;
	  // on affine
	  tmp1tmp1= additionSimple(tmp1tmp1.f,tmp1tmp1.erreur);
	  // on ajoute la plus petite des erreurs
	  tmp1tmp1.erreur+=tmp2tmp2.erreur;
	  tmp1tmp1= additionSimple(tmp1tmp1.f,tmp1tmp1.erreur);
	  return tmp1tmp1 ;
    }

    public static GrandFloat monAddition(GrandFloat ff, GrandFloat gg){
        GrandFloat resultat = new GrandFloat(ff.f+gg.f, ff.erreur + gg.erreur);

        return  resultat;


    }


    public static float pow(float f, int n) {
        if (n >= 0) {
            if (n==0) return (float)1;
            float tmp = pow(f,n/2);
            if ( n%2 == 0 ) {
                return tmp*tmp;
            }
            return f*tmp*tmp;
        }
        else {
            if (n == -1){
                return (float)1/f;
            }
            float tmp = pow(f,n/2);
            if ( n%2 == 0 ) {
                return tmp*tmp;
            }
            return tmp*tmp/f;
        }

    }


    // transforme un float en Grand float, sans gain de précision( fonction donc auxiliaire ) mais aussi sans perte
    public static GrandFloat split(float g, int n){
        if ( n>22 || n<12){
            throw new IllegalArgumentException(" le split doit être entre 12 et 22");
        }
        float split = ( pow(2,n)+1) * g;
        float tmp = split - g;
        float f = split - tmp;
        float erreur = g-f;
        return new GrandFloat(f,erreur);


    }


    // multiplie deux float et garde l'erreur dans notre structure
    public static GrandFloat multiplicationSimple(float f, float g){
        float produit = f*g;
        GrandFloat tmp = split(f,12);
        GrandFloat tmp1 = split(g,12);
        // première erreur la différence
        float erreur1 = produit - (tmp.f * tmp1.f);
        // deuxième erreur : le produit de la partie haute de tmp par l'erreur de tmp1 et ainsi de suite
        float erreur2 = erreur1 - (tmp.erreur * tmp1.f);
        float erreur3 = erreur2 - (tmp.f *tmp1.erreur);
        float erreur = tmp.erreur * tmp1.erreur - erreur3;
        return new GrandFloat(produit,erreur);

    }

    // multiplication de grands float
    public static GrandFloat multiplicationGrandFloat(GrandFloat ff, GrandFloat gg){
        GrandFloat Produit = multiplicationSimple(ff.f,gg.f);
        // on ajoute les erreurs de multiplication par les erreurs
        Produit.erreur += ff.f* gg.erreur;
        Produit.erreur += ff.erreur + gg.f;
        // on récupère les erreurs significatifs
        Produit= additionSimple(Produit.f,Produit.erreur);
        return Produit;
    }

    public boolean estPlusGrandque(GrandFloat ff){
        return ( this.f+this.erreur > ff.f+this.erreur );
    }

    public static GrandFloat GrandPi(){
        GrandFloat pi = new GrandFloat((float)3.141592,(float)0.000000653589793);
        return pi;
    }
    
    public static GrandFloat GrandPiSur2(){
        GrandFloat pisur2 = new GrandFloat((float)1.570796,(float)0.00000032679489651);
        return pisur2;
    }

    public void ajouter_erreur(float f){
        this.erreur+=f;

    }
    public void ajouter_float(float f){
        this.f+=f;
    }
    public static GrandFloat moinsGrandpi(){
        GrandFloat moinspi= new GrandFloat((float)-3.141592,(float)-0.000000653589793);
        return moinspi;
    }



    public static float adaptGranfloat( float f){

        if (f > (float) 3.14159265359){
            GrandFloat MoinsGrand2pi= additionSimple((float)-6.283185,(float)-0.00000030718);
            GrandFloat resultat = additionSimple(f,-(float)6.28318530718);
            System.out.println(" t'étais pas censé etre la retourne chez toi lucas le zeb");
            while (resultat.estPlusGrandque(GrandPi())){

                resultat =additionGrandFloat(resultat,MoinsGrand2pi);

            }
            return resultat.f + resultat.erreur;
        }
        if (f < (float)-3.1415926539){
            GrandFloat Grand2pi=additionSimple((float)6.253185,(float)0.00000030718);
            GrandFloat resultat = additionSimple(f,(float)6.28318530718);
            while (moinsGrandpi().estPlusGrandque(resultat)){
                resultat =additionGrandFloat(resultat,Grand2pi);
            }
            return resultat.f + resultat.erreur;
        }
        System.out.println(" tla normalement lucas de zeb");
        return f;
    }

    public static float pimoinsf( float f){
       GrandFloat r = additionSimple(-f,0);
       r= additionGrandFloat(r,GrandPi());
       return r.f+ r.erreur;
    }
    
    public static float pisur2moinsf( float f){
       GrandFloat r = additionSimple(-f,0);
       r= additionGrandFloat(r,GrandPiSur2());
       return r.f+ r.erreur;
    }




}
