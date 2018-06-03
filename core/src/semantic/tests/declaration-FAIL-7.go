package main;

var i, j, k, w string;

var x6, x7 int = 1, 2;   // valid

var i, j int = 1, 2; // error i was already declared in this scope

func main() {
  i, j, k, k2 = 2, 3, "teste", 3.4;
};
