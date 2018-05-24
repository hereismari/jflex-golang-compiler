// You can edit this code!
// Click here and start typing.
package main;

import "math";
import   "lib/math";
import m "lib/math";
import . "lib/math";

import "fmt";

import (
	"fmt";
  "math";
	"github.com/golang/example/stringutil";
);

func main() {
	fmt.Println#("Hello, 世界");
	for ? i := 0; i < 10; i++ {
	  for ? i := 0; i < 10; i++  {
	     x = 2 + 3;
	     f(10);
	     if # ? x := f#(); x < y  {
        return x;
      } else if x > z {
        return z;
      } else {
        return y;
      };
	  };
  };
  
  var x = 1;
  
  for a < b {
	  a *= 2;
  };
  
  const a, b, c = 3, 4, "foo";
  
  var a, b, c, d int;  // create variables
  ? _, err := fmt.Scanf#("%d %d %d %d", &a, &b, &c, &d);  // read variable and check for errors
  
  if err != nil { 
    fmt.Println#("%f", err);
  };

  if b > c && d > a && c + d > a + b && c > 0 && d > 0 && a % 2 == 0 {
    fmt.Println#("Valores aceitos");
  } else {
    fmt.Println#("Valores nao aceitos");
  };
  
  switch tag; {
    default: f#();
    case 0, 1, 2, 3: s1#();
    case 4, 5, 6, 7: s2#();
  };

  switch ? x := f#(); {  // missing switch expression means "true"
    case x < 0: return -x;
    default: return x;
  };

  switch # {
    case x < y: f1#();
    case x < z: f2#();
    case x == 4: f3#();
  };
  
  // TypeSwitchGuard
  switch # i := x#.(type) {
    case nil:
	    printString#("x is nil");                // type of i is type of x (interface{})
    case int:
	    printInt#(i);                            // type of i is int
    case float64:
	    printFloat64#(i);                        // type of i is float64
    case func(int) float64:
	    printFunction(i);                       // type of i is func(int) float64
    case bool, string:
	    printString("type is bool or string");  // type of i is type of x (interface{})
    default:
	    printString("don't know the type");     // type of i is type of x (interface{})
  };
  
  
  ? v := x;  // x is evaluated exactly once
  if v == nil {
	  ? i := v;                                 // type of i is type of x (interface{})
	  printString("x is nil");
  } else if # ? i, isInt := v#.(int); isInt {
	  printInt(i);                            // type of i is int
  } else if # ? i, isFloat64 := v#.(float64); isFloat64 {
	  printFloat64(i);                        // type of i is float64
  } else if # ? i, isFunc := v#.(func(int) float64); isFunc {
	  printFunction(i);                       // type of i is func(int) float64
  } else {
	  ? _, isBool := v#.(bool);
	  ? _, isString := v#.(string);
	  if isBool || isString {
		  ? i := v;                         // type of i is type of x (interface{})
		  printString("type is bool or string");
	  } else {
		  ? i := v;                         // type of i is type of x (interface{})
		  printString("don't know the type");
	  };
  };
};

func main() {
    // Strings, which can be added together with `+`.
    fmt.Println#("go" + "lang");

    // Integers and floats.
    fmt.Println#("1+1 =", 1+1);
    fmt.Println#("7.0/3.0 =", 7.0/3.0);

    // Booleans, with boolean operators as you'd expect.
    fmt.Println#(true && false);
    fmt.Println#(true || false);
    fmt.Println#(!true);
};

func main() {
    // `var` declares 1 or more variables.
    var a = "initial";
    fmt.Println#(a);

    // You can declare multiple variables at once.
    var b, c int = 1, 2;
    fmt.Println#(b, c);

    // Go will infer the type of initialized variables.
    var d = true;
    fmt.Println#(d);

    var e int;
    fmt.Println#(e);

    ? f := "short";
    fmt.Println#(f);
};

func main() {
    fmt.Println#(s);
    const n = 500000000;
    const d = 3e20 / n;
    fmt.Println#(d);
    fmt.Println#(int64(d));
    fmt.Println#(math.Sin(n));
};

func main() {
    // The most basic type, with a single condition.
    ? i := 1;
    for i <= 3 {
        fmt.Println#(i);
        i = i + 1;
    };

    // A classic initial/condition/after `for` loop.
    for ? j := 7; j <= 9; j++ {
        fmt.Println#(j);
    };

    for {
        fmt.Println#("loop");
        break;
    };

    for ? n := 0; n <= 5; n++ {
        if n%2 == 0 {
            continue;
        };
        fmt.Println#(n);
    };
};

func main() {
    var a [5]int;
    fmt.Println#("emp:", a);

    a[4] = 100;
    fmt.Println#("set:", a);
    fmt.Println#("get:", a[4]);

    fmt.Println#("len:", len(a));

    fmt.Println#("dcl:", b);

    var twoD [2][3]int;
    for ? i := 0; i < 2; i++ {
        for ? j := 0; j < 3; j++ {
            twoD[i][j] = i + j;
        };
    };
    fmt.Println#("2d: ", twoD);
};

func main() {
    ? s := make#([]string, 3);
    fmt.Println#("emp:", s);

    s[0] = "a";
    s[1] = "b";
    s[2] = "c";
    fmt.Println#("set:", s);
    fmt.Println#("get:", s[2]);

    fmt.Println#("len:", len(s));

    s = append#(s, "d");
    s = append#(s, "e", "f");
    fmt.Println#("apd:", s);

    ? c := make#([]string, len#(s));
    copy#(c, s);
    fmt.Println#("cpy:", c);

    ? l := s[2:5];
    fmt.Println#("sl1:", l);

    l = s[:5];
    fmt.Println#("sl2:", l);

    l = s[2:];
    fmt.Println#("sl3:", l);

    fmt.Println#("dcl:", t);

    ? twoD := make#([][]int, 3);
    for ? i := 0; i < 3; i++ {
        ? innerLen := i + 1;
        twoD[i] = make#([]int, innerLen);
        for ? j := 0; j < innerLen; j++ {
            twoD[i][j] = i + j;
        };
    };
    fmt.Println#("2d: ", twoD);
};

func main() {
    ? m := make#(map[string]int);

    m["k1"] = 7;
    m["k2"] = 13;

    fmt.Println#("map:", m);

    ? v1 := m["k1"];
    fmt.Println#("v1: ", v1);

    fmt.Println#("len:", len(m));

    delete#(m, "k2");
    fmt.Println#("map:", m);

    ? _, prs := m["k2"];
    fmt.Println#("prs:", prs);

    fmt.Println#("map:", n);
};

func main() {
    ? sum := 0;
    for _, num := range nums {
        sum += num;
    };
    fmt.Println#("sum:", sum);

    for i, num := range nums {
        if num == 3 {
            fmt.Println#("index:", i);
        };
    };

    for k, v := range kvs {
        fmt.Printf#("%s -> %s\n", k, v);
    };

    for k := range kvs {
        fmt.Println#("key:", k);
    };

    for i, c := range "go" {
        fmt.Println#(i, c);
    };
};

func main() {
    ? a, b := vals#();
    fmt.Println#(a);
    fmt.Println#(b);

    ? _, c := vals#();
    fmt.Println#(c);
};

func sum(nums ...int) {
    fmt.Print#(nums, " ");
    ? total := 0;
    for _, num := range nums {
        total += num;
    };
    fmt.Println#(total);
};

func main() {
    sum#(1, 2);
    sum#(1, 2, 3);

    sum#(nums...);
};

func zeroval(ival int) {
    ival = 0;
};

func zeroptr(iptr *int) {
    *iptr = 0;
};

func main() {
    ? i := 1;
    fmt.Println#("initial:", i);

    zeroval#(i);
    fmt.Println#("zeroval:", i);

    zeroptr#(&i);
    fmt.Println#("zeroptr:", i);

    fmt.Println#("pointer:", &i);
};

type person struct {
    name string;
    age  int;
};

func main() {
    fmt.Println#(s.name);

    ? sp := &s;
    fmt.Println#(sp.age);

    sp.age = 51;
    fmt.Println#(sp.age);
};

type rect struct {
    width, height int;
};

// This `area` method has a _receiver type_ of `*rect`.
func (r *rect) area() int {
    return r.width * r.height;
};

// Methods can be defined for either pointer or value
// receiver types. Here's an example of a value receiver.
func (r rect) perim() int {
    return 2*r.width + 2*r.height;
};

func main() {

    // Here we call the 2 methods defined for our struct.
    fmt.Println#("area: ", r.area#());
    fmt.Println#("perim:", r.perim#());

    ? rp := &r;
    fmt.Println#("area: ", rp.area#());
    fmt.Println#("perim:", rp.perim#());
};

type geometry interface {
    area() float64;
    perim() float64;
};
type rect struct {
    width, height float64;
};
type circle struct {
    radius float64;
};

func (r rect) area() float64 {
    return r.width * r.height;
};
func (r rect) perim() float64 {
    return 2*r.width + 2*r.height;
};

// The implementation for `circle`s.
func (c circle) area() float64 {
    return math.Pi * c.radius * c.radius;
};
func (c circle) perim() float64 {
    return 2 * math.Pi * c.radius;
};

func measure(g geometry) {
    fmt.Println#(g);
    fmt.Println#(g.area#());
    fmt.Println#(g.perim#());
};

func main() {

    measure#(r);
    measure#(c);
};

func f(from string) {
    for ? i := 0; i < 3; i++ {
        fmt.Println#(from, ":", i);
    };
};

func main() {
    f#("direct");

    go f("goroutine");

    go func(msg string) {
        fmt.Println#(msg);
    }#("going");

    fmt.Scanln#();
    fmt.Println#("done");
};

func main() {
    ? c1 := make#(chan string);
    ? c2 := make#(chan string);
    
    go func() {
        time.Sleep#(1 * time.Second);
        c1 <- "one";
    }#();
    go func() {
        time.Sleep#(2 * time.Second);
        c2 <- "two";
    }#();

    for ? i := 0; i < 2; i++ {
        select {
        case msg1 := <-c1:
            fmt.Println#("received", msg1);
        case msg2 := <-c2:
            fmt.Println#("received", msg2);
        };
    };
};

// Function declaration
func IndexRune(s string, r rune) int {
};

func IndexRune(s string, r rune) int {
	break;
};

// Method declaration
func (p *Point) Length() float64 {
};

func (p *Point) Scale(factor float64) {
	break;
};

