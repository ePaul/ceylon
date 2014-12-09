package com.redhat.ceylon.compiler.js;

import java.util.HashSet;
import java.util.Set;

import com.redhat.ceylon.compiler.typechecker.model.Declaration;
import com.redhat.ceylon.compiler.typechecker.tree.Tree;
import com.redhat.ceylon.compiler.typechecker.tree.Visitor;

/** Recursively generates code for a destructuring pattern that may contain other
 * patterns.
 * 
 * @author Enrique Zamudio
 */
public class Destructurer extends Visitor {

    private final GenerateJsVisitor gen;
    private final JsIdentifierNames names;
    private final String expvar;
    private final Set<Declaration> directAccess;
    private boolean first;
    private final Set<Declaration> added = new HashSet<>();

    /** Generate the code for the specified pattern. If null is passed instead of a
     * generator, no code is output but the patterns are still visited and their
     * declarations gathered.
     * @param that The pattern to visit.
     * @param gen The generator to output the code.
     * @param directAccess the set in which to store the declarations for direct access.
     * @param expvar the name of the variable storing the expression for destructuring.
     * @param first If false, a comma is output before the very first declaration is output. */
    public Destructurer(final Tree.Pattern that, final GenerateJsVisitor gen,
            final Set<Declaration> directAccess, final String expvar, boolean first) {
        this.gen = gen;
        names = gen == null ? null : gen.getNames();
        this.directAccess = directAccess;
        this.expvar = expvar;
        this.first=first;
        that.visit(this);
    }

    public void visit(final Tree.TuplePattern that) {
        int idx=0;
        for (Tree.Pattern p : that.getPatterns()) {
            if (p instanceof Tree.VariablePattern) {
                p.visit(this);
                if (gen != null) {
                    if (((Tree.VariablePattern)p).getVariable().getType() instanceof Tree.SequencedType) {
                        gen.out(".spanFrom(");
                    } else {
                        gen.out(".$_get(");
                    }
                    gen.out(Integer.toString(idx++), ")");
                }
            } else {
                added.addAll(new Destructurer(p, gen, directAccess, expvar+".$_get("+(idx++)+")",
                        first && idx==0).getDeclarations());
            }
        }
    }

    public void visit(final Tree.KeyValuePattern that) {
        if (that.getKey() instanceof Tree.VariablePattern) {
            that.getKey().visit(this);
            if (gen != null) {
                gen.out(".key");
            }
        } else {
            added.addAll(new Destructurer(that.getKey(), gen, directAccess, expvar+".item", first).getDeclarations());
        }
        if (that.getValue() instanceof Tree.VariablePattern) {
            that.getValue().visit(this);
            if (gen != null) {
                gen.out(".item");
            }
        } else {
            added.addAll(new Destructurer(that.getValue(), gen, directAccess, expvar+".item", false).getDeclarations());
        }
    }

    public void visit(final Tree.VariablePattern that) {
        directAccess.add(that.getVariable().getDeclarationModel());
        added.add(that.getVariable().getDeclarationModel());
        if (first) {
            first=false;
        } else if (gen != null) {
            gen.out(",");
        }
        if (gen != null) {
            gen.out(names.name(that.getVariable().getDeclarationModel()), "=",expvar);
        }
    }

    /** Returns the declarations gathered by this Destructurer. */
    public Set<Declaration> getDeclarations() {
        return added;
    }

}
