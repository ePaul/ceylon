"A collection of unique elements.
 
 A `Set` is a `Collection` of its elements.
 
 Sets may be the subject of the binary union, 
 intersection, and complement operators `|`, `&`, and 
 `~`."
shared interface Set<out Element>
        satisfies Collection<Element> &
                  Cloneable<Set<Element>>
        given Element satisfies Object {
    
    "Determines if this `Set` is a superset of the 
     specified Set, that is, if this `Set` contains all 
     of the elements in the specified `Set`."
    shared default Boolean superset(Set<Object> set) {
        for (element in set) {
            if (!element in this) {
                return false;
            }
        }
        else {
            return true;
        }
    }
    
    "Determines if this `Set` is a subset of the given 
     `Set`, that is, if the given set contains all of 
     the elements in this set."
    shared default Boolean subset(Set<Object> set) {
        for (element in this) {
            if (!element in set) {
                return false;
            }
        }
        else {
            return true;
        }
    }
    
    "Two `Set`s are considered equal if they have the 
     same size and if every element of the first set is
     also an element of the second set, as determined
     by `contains()`."
    shared actual default Boolean equals(Object that) {
        if (is Set<Object> that,
                that.size==size) {
            for (element in this) {
                if (!element in that) {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }
    
    shared actual default Integer hash {
        variable Integer hashCode = 1;
        for (Element elem in this){
            hashCode *= 31;
            hashCode += elem.hash;
        }
        return hashCode;
    }
    
    "Returns a new `Set` containing all the elements of 
     this `Set` and all the elements of the given `Set`."
    shared formal Set<Element|Other> union<Other>(Set<Other> set)
            given Other satisfies Object;
    
    "Returns a new `Set` containing only the elements 
     that are present in both this `Set` and the given 
     `Set`."
    shared formal Set<Element&Other> intersection<Other>(Set<Other> set)
            given Other satisfies Object;
    
    "Returns a new `Set` containing only the elements 
     contained in either this `Set` or the given `Set`, 
     but no element contained in both sets."
    shared formal Set<Element|Other> exclusiveUnion<Other>(Set<Other> set)
            given Other satisfies Object;
    
    "Returns a new `Set` containing all the elements in 
     this `Set` that are not contained in the given
     `Set`."
    shared formal Set<Element> complement<Other>(Set<Other> set)
            given Other satisfies Object;
    
}
"A [[Set]] with no elements."
shared object emptySet extends Object() satisfies Set<Nothing> {
    shared actual Set<Other> union<Other>(Set<Other> set)
            given Other satisfies Object
        => set;
    shared actual Set<Nothing> intersection<Other>(Set<Other> set)
            given Other satisfies Object
        => emptySet;
    shared actual Set<Other> exclusiveUnion<Other>(Set<Other> set)
            given Other satisfies Object
        => set;
    shared actual Set<Nothing> complement<Other>(Set<Other> set)
            given Other satisfies Object
        => emptySet;
    shared actual Set<Nothing> clone => emptySet;
    shared actual Iterator<Nothing> iterator() => emptyIterator;
    shared actual Boolean subset(Set<Object> set) => true;
    shared actual Boolean superset(Set<Object> set) => set.empty;
    shared actual Integer size = 0;
    shared actual Boolean empty = true;
    shared actual Boolean contains(Object element) => false;
    shared actual Boolean containsAny({Object*} elements) => false;
    shared actual Boolean containsEvery({Object*} elements) => false;
    
    shared actual Integer count(
            Boolean selecting(Nothing element)) => 0;
    
    shared actual Set<Nothing> map<Result>(
            Result collecting(Nothing element)) => emptySet;
    
    shared actual Set<Nothing> filter
            (Boolean selecting(Nothing element)) => emptySet;
    
    shared actual Result fold<Result>(Result initial,
            Result accumulating(Result partial, Nothing element)) => 
            initial;
    
    shared actual Null find
            (Boolean selecting(Nothing element)) => null;
    
    shared actual [] collect<Result>
            (Result collecting(Nothing element)) => [];
    
    shared actual [] select
            (Boolean selecting(Nothing element)) => [];
    
    shared actual Boolean any
            (Boolean selecting(Nothing element)) => false;
    
    shared actual Boolean every
            (Boolean selecting(Nothing element)) => false;
    
    shared actual Set<Nothing> skipping(Integer skip) => emptySet;
    
    shared actual Set<Nothing> taking(Integer take) => emptySet;
    
    shared actual Set<Nothing> by(Integer step) => emptySet;
}