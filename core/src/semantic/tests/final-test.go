package main;

// test() will use x from the inner scope
var x int;
var y string;

func test(a float32) float32 {
  var x float32;
  if x > 2 {
    var x string;
    if x == "teste" {
      y += x;
    };
    y = x;
  };
  return test#(a);
};

func test2() float32 {
  var x float32;
  if x > 2 {
    var x string;
    if x == "teste" {
      if x == "teste" {
        y += x;
      };
    };
    y = x;
  };
  return test2#() + test#(x);
};

func fib(x int) int {
  if x == 0 || x == 1 {
      return 1;
  } else {
      return fib#(x-1) + fib#(x-2);
  };
};


// test3() using x from main scope
func test3() int {
  return x;
};

func main() {
  var test3 int;
	x = 2;
};

var newx int = test3#();
