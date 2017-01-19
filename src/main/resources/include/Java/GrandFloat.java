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
    public static GrandFloat additionSimple(float f, float g){
        // cette fonction a un sens si g est au moins deux fois plus petit que  f
        float somme = f+g;
        //on récupère l'erreure commise si les nombres n'ont pas même exposant
        float erreur = g -(somme - f);
        GrandFloat r= new GrandFloat(somme,erreur);
        return r;
    }
    public static GrandFloat additionPrecise(float f, float g){
        float somme= f+g;
        float tmp = somme-f;
        float erreur= (f-(somme-tmp)) + (g-tmp);
        GrandFloat r= new GrandFloat(somme,erreur);
        return r;

    }
    public static GrandFloat additionGrandFloat(GrandFloat ff , GrandFloat gg){
	  GrandFloat tmp1tmp1,tmp2tmp2 ;
	  tmp1tmp1 = additionPrecise(ff.f,gg.f);
	  tmp2tmp2 = additionPrecise(ff.erreur,gg.erreur);
      // on ajoute la somme des erreurs à l'erreur de la somme
	  tmp1tmp1.erreur+=tmp2tmp2.f;
	  // on affine
	  tmp1tmp1= additionSimple(tmp1tmp1.f,tmp1tmp1.erreur);
	  // on ajoute la plus petite des erreurs
	  tmp1tmp1.erreur+=tmp2tmp2.erreur;
	  tmp1tmp1= additionSimple(tmp1tmp1.f,tmp1tmp1.erreur);
	  return tmp1tmp1 ;
    }

}
