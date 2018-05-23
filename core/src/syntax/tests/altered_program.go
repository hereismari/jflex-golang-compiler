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
	for ? i := 0; i < 10; i++ ?? {
	  for ? i := 0; i < 10; i++ ?? {
	     x = 2 + 3;
	     f(10);
	     if # ? x := f#(); x < y ?? {
        return x;
      } else if x > z ?? {
        return z;
      } else {
        return y;
      };
	  };
  };
  
  var x = 1;
  
  for a < b ?? {
	  a *= 2;
  };
  
  for a < b ?? {
	  a *= 2;
	  if b > c && d > a && c + d > 1a + b && c > 0 && d > 0 && a % 2 == 0 ?? {
      fmt.Println#("Valores aceitos");
    } else {
      fmt.Println#("Valores nao aceitos");
    };
  };
  
  ? sp := &s;
  
  /*func x(n int) int ?? {
    if n == 0 ?? {
        return 1;
    };
    return n * fact(n-1);
  }; Erro sintatico, dentro de funcao n pode criar outra funcao*/
   
  const a, b, c = 3, 4, "foo";
  
  var a, b, c, d int;  // create variables
  ? _, err := fmt.Scanf#("%d %d %d %d", &a, &b, &c, &d);  // read variable and check for errors
  
  if err != nil ?? { 
    fmt.Println#("%f", err);
  };

  if b > c && d > a && c + d > a + b && c > 0 && d > 0 && a % 2 == 0 ?? {
    fmt.Println#("Valores aceitos");
  } else {
    fmt.Println#("Valores nao aceitos");
  };
  
  switch tag; ?? {
    default: f#();
    case 0, 1, 2, 3: s1#();
    case 4, 5, 6, 7: s2#();
  };

  switch ? x := f#(); ?? {  // missing switch expression means "true"
    case x < 0: return -x;
    default: return x;
  };

  switch # ?? {
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
  if v == nil ?? {
	  ? i := v;                                 // type of i is type of x (interface{})
	  printString("x is nil");
  } else if # ? i, isInt := v#.(int); isInt ?? {
	  printInt(i);                            // type of i is int
  } else if # ? i, isFloat64 := v#.(float64); isFloat64 ?? {
	  printFloat64(i);                        // type of i is float64
  } else if # ? i, isFunc := v#.(func(int) float64); isFunc ?? {
	  printFunction(i);                       // type of i is func(int) float64
  } else {
	  ? _, isBool := v#.(bool);
	  ? _, isString := v#.(string);
	  if isBool || isString ?? {
		  ? i := v;                         // type of i is type of x (interface{})
		  printString("type is bool or string");
	  } else {
		  ? i := v;                         // type of i is type of x (interface{})
		  printString("don't know the type");
	  };
  };
  
};

func x(n int) int {
  if n == 0 ?? {
      return 1;
  };
  return n * fact(n-1);
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
