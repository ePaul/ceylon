package ceylon.language;

import com.redhat.ceylon.compiler.metadata.java.SatisfiedTypes;

@SatisfiedTypes({
    "ceylon.language.Castable<ceylon.language.Integer|ceylon.language.Float>",
    "ceylon.language.Integral<ceylon.language.Integer>",
    "ceylon.language.Invertable<ceylon.language.Integer>"
})
public final class Integer
    extends Object
    implements Castable<Numeric>, Integral<Integer>, Invertable<Integer> {

    private final long value;
    private Integer(long l) {
        value = l;
    }

    public static ceylon.language.Integer instance(long l) {
        return new ceylon.language.Integer(l);
    }

    public ceylon.language.Integer plus(ceylon.language.Integer op) {
        return instance(value + op.value);
    }

    public ceylon.language.Integer minus(ceylon.language.Integer op) {
        return instance(value - op.value);
    }

    public ceylon.language.Integer times(ceylon.language.Integer op) {
        return instance(value * op.value);
    }

    public ceylon.language.Integer divided(ceylon.language.Integer op) {
        return instance(value / op.value);
    }

    public ceylon.language.Integer power(ceylon.language.Integer op) {
        return instance((long) Math.pow(value, op.value)); // FIXME: ugly
    }

    public ceylon.language.Integer remainder(ceylon.language.Integer op) {
        return instance(value % op.value);
    }

    @Override
    public ceylon.language.Integer getPositiveValue() {
        return this;
    }

    @Override
    public ceylon.language.Integer getNegativeValue() {
        return instance(-value);
    }

    public boolean test(ceylon.language.Integer op) {
        return value == op.value;
    }

    public ceylon.language.Comparison compare(ceylon.language.Integer op) {
        long x = value;
        long y = op.value;
        return (x < y) ? Comparison.SMALLER :
            ((x == y) ? Comparison.EQUAL : Comparison.LARGER);
    }

    public java.lang.String toString() {
        return java.lang.Long.toString(value);
    }

    // Conversions between numeric types

    public ceylon.language.Natural natural() {
        return ceylon.language.Natural.instance(value);
    }

    public ceylon.language.Integer integer() {
        return this;
    }

    public ceylon.language.Float toFloat() {
        return ceylon.language.Float.instance(value);
    }

    // Just a kludge til we have full autoboxing
    public long longValue() {
        return value;
    }

    public int intValue() {
        return (int)value;
    }

    public ceylon.language.Integer getPredecessor() {
        return Integer.instance(value - 1);
    }

    public ceylon.language.Integer getSuccessor() {
        return Integer.instance(value + 1);
    }

    // Probably not spec-conformant
    public ceylon.language.Integer complement() {
        return instance(~value);
    }

    @Override
    public boolean equals(java.lang.Object s) {
        if (s instanceof Integer) {
            return value == ((Integer)s).value;
        } else {
            return false;
        }
    }

    @Override
    public java.lang.String getFormatted() {
        return java.lang.Long.toString(value);
    }

    @Override
    public boolean largerThan(Integer other) {
        return value > other.value;
    }

    @Override
    public boolean smallerThan(Integer other) {
        return value < other.value;
    }

    @Override
    public boolean asLargeAs(Integer other) {
        return value >= other.value;
    }

    @Override
    public boolean asSmallAs(Integer other) {
        return value <= other.value;
    }

    @Override
    public <CastValue extends Numeric> CastValue as() {
        return (CastValue)this;
    }
}
