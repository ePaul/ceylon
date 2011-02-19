public class Literals() {
    
    @type["String"] "Hello";
    @type["Natural"] 1;
    @type["Float"] 1.0;
    @type["Character"] `x`;
    @type["Sequence<String>"] { "hello", "world" };
    @type["Sequence<Natural>"] { 1, 2, 3, 4 };
    @type["String"] "pi = " 3.1415 " approx";
    @type["Quoted"] 'hibernate.org';
    
    Boolean b = true;
    
    String[] strings = { "Hello", "World" };
    
    String? string = null.nothing<String>();
    
    Iterable<String> istrings = strings;
    
}