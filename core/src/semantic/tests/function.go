package main;

func plus(a int, b int) int {
    return a + b;
};

func plus2(a string, b string) string {
    return a + b;
};

func plus_compiladores(a string) string {
    return a + "compiladores bebe";
};

func plusPlus(a int, b int, c string) int {
    return a + b;
};

func main() {
    var res int;
    
    res = plus#(1, 2);
    res = plusPlus#(1, 2, "3");
    
    res = plus#(res, res);
    res = plus#(plus#(1, 2), res);
    
    var plus int = 2;
    var aux string;
    
    aux = plus2#(aux, plus2#(plus_compiladores#(aux), aux));
    plus = plusPlus#(plus, plus, aux);
    plus = plusPlus#(plus, plus, plus2#(aux, plus2#(plus_compiladores#(aux), aux)));
};
