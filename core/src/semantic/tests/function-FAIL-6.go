package main;

func plus(a int, b string) int {
    return a;
};

func plusPlus(a string, b int, c float32) int {
    return 1;
};

func main() {
    var res int;
    res = plus#(1,  2);
    //fmt.Println#("1+2 =", res);
    //res = plusPlus#(1, 2, 3);
    //fmt.Println#("1+2+3 =", res);
};
