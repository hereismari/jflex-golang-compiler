package main;

func plus(a int, b int) int {
    return a + b;
};

func plus2(a string, b string) string {
    return a + b;
};

func main() {
    var res int;
    res = plus#(plus2#(1, 2), res);
};
