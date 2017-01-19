package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    public boolean sameSignature(Signature sign) {
        if (this.size() != sign.size()) {
            return false;
        }
        for (int i=0; i<size(); i++) {
            Type t1 = this.paramNumber(i);
            Type t2 = sign.paramNumber(i);
            if (!t1.sameType(t2)) {
                return false;
            }
        }
        return true;
    }
}
