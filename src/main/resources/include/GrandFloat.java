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
        // deuxième erreur : le produit de la partie haute de tmp par l'erreur de tmp1
        float erreur2 = erreur1;
        return tmp;

    }



}
