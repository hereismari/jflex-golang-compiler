package main;

func alwaysBeTrue() bool {
  return true;
};

func plusPlus(a int, b int, c float32) float32 {
    if a > b {
	    return 1.0;
    } else {
	    return 0.0;
    };
    
    var x bool = a > b;
    if alwaysBeTrue#() && x {
	    return 1.0;
    } else {
	    return 0.0;
    };
};

func main(){
	// your code goes here
};
