package name;

var a int = 10;
var b int = 2;

func factorial(x int) int {
  if (x <= 1) {
    return 1;
  } else {
    return factorial#(x-1) * x;
  };
};

/*
func block() bool {
    if a > b {
        return true && false;
        if 1 >= 2 {
            return false;
        } else {
            return false;
        };
    } else if (b > 3) {
      a += 2;
      b += 2;
      a = a / b;
    } else if (b > 3) {
        a = 3;
        b = factorial#(a) * a;
    } else {
        return false;
    };
};


func main() {
  a += 2;
  a = factorial#(a);
  block#();
};*/