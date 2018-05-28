package main;

func main() {
	var a int = 1 + 2;
	var b int = 2.0 + 2;
	var c float32 = 2.0 + 2;
	var d float32 = 2 + 2.0;
	var e float32 = 2 + 2;
	
	var f int;
	f = 1 + 1;
	f = 2.0 + 2;
	
	var g float32;
	g = 1 + f;
	
	a = 1 - 2 - 3;
	
	b = 1 | 2;
	
	c = 1 ^ 2;
	
	a = 1 % 2;
	
	b = 1 / 2;
	
	g = 1 / 2.0;
	
	a = 1 & 2 | 3 ^ 4 &^ 5;
};
