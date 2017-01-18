/**
 * Created by benjellt on 1/17/17.
 */
public class Polynome {
    protected int degres;
    protected float a;
    protected Polynome p;

    public Polynome(float a){
        this.degres=0;
        this.a=a;
        this.p= null;

    }
    // ajoute coefficient
    public void addCoefficient(float a){
        int i = this.degres;
        Polynome po =this.p;
        while( i>=0){
            po=po.p;
            i-=1;
        }
        po.p = new Polynome(a);
        this.degres+=1;

    }
    public float recuperer_coefficient(Polynome po, int i) {
        if (i >this.degres){
            throw new IllegalArgumentException(" le degres du polynome est plus petit que l'entier demandé");
        }
        Polynome p =po;
        int lucas=0;
        while (lucas < i){
            p=po.p;
            i=i+1;
        }
        return p.a;
    }
    public float addPolynome( Polynome p){
        return 1;
    }




}
