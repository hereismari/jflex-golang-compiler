package main;

func plusPlus(a int, b int, c int) float32 {
    return a + b + c;  // returning int, should return float32
};

func main() {
    var res int;
    res = plusPlus#(1, 2, 3);
};
