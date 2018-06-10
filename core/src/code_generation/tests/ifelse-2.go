package main;

func alwaysBe1() int {
  return 1;
};

func plusPlus(a int, b int, c float32) float32 {
    if a > b {
      a += 2 + 3;  // NOT WORKING
      b += 5;      // NOT WORKING
	    return 1.0;
    } else {
	    return 0.0;
    };
    
    var x bool = a > b;
    if 1 < alwaysBe1#() {
      a = 10;
      b = a * 3;
	    return 1.0;
    } else {
	    return 0.0;
    };
};

func main(){
	// your code goes here
};
