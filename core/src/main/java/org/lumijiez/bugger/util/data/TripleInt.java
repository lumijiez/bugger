package org.lumijiez.bugger.util.data;

public class TripleInt extends Triple<Integer,Integer,Integer> {

    public TripleInt(int first, int second, int third) {
        super(first, second, third);
    }

    public int one() {
        return super.first();
    }

    public int two() {
        return super.second();
    }

    public int thr() {
        return super.third();
    }

    @Override
    public String toString() {
        return "IntTriple{" +
            "first=" + one() +
            ", second=" + two() +
            ", third=" + thr() +
            '}';
    }
}
