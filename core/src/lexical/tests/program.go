package main
import "fmt"

func main(){
	// your code goes here
	var _x = 1;
	var A = 1;
	
	fmt.Println(A + _x);
}

var numbers = [1, 2, 3, 4]
var thing = {name: "Raspberry Pi", generation: 2, model: "B"


elements := []int{1, 2, 3, 4}
type Thing struct {
    name       string
    generation int
    model      string
}
thing := Thing{"Raspberry Pi", 2, "B"}
// or using explicit field names
thing = Thing{name: "Raspberry Pi", generation: 2, model: "B"}

f := func() int { return 1 }
elements := []string{
    0:     "zero",
    1:     "one",
    4 / 2: "two",
    2:     "also two"
}

numbers := []string{"a", "b", 2 << 1: "c", "d"}
fmt.Printf("%#v\n", numbers)
[]string{"a", "b", "", "", "c", "d"}


constants := map[string]float64{"euler": 2.71828, "pi": .1415926535}

"日本語"                                 /* UTF-8 input text*/
`日本語`                                 // UTF-8 input text as a raw literal
"\u65e5\u672c\u8a9e"                    // the explicit Unicode code points
"\U000065e5\U0000672c\U00008a9e"        // the explicit Unicode code points
"\xe6\x97\xa5\xe6\x9c\xac\xe8\xaa\x9e"  // the explicit UTF-8 bytes

"teste\"teste"
`test`


1231

123

teste
//Lorem // Ipsum if
teste2
// Lorem ipsum teste
teste3
/***/
/*Lorem*/ /*ipsum*/
teste4
/*Lorem*/ //ips***um*/
teste5
/*
  Comentario longo :)
  
  
  asdasd
  asdasd
  asdsad
*/
teste6
if asdasd

teste7
//Lorem // Ipsum if

// Lorem ipsum teste

fmt.Println("\"")
fmt.Println("\x22")
fmt.Println("\u0022")
fmt.Println("\042")
fmt.Println(`"`)
fmt.Println(string('"'))
fmt.Println(string([]byte{'"'}))
fmt.Printf("%c\n", '"')
fmt.Printf("%s\n", []byte{'"'})

// Seriously, this one is just for demonstration not production :)
fmt.Println(xml.Header[14:15])
fmt.Println(strconv.Quote("")[:1])

0.
72.40
072.40  // == 72.40
2.71828
1.e+0
6.67428e-11
1E6
.25
.12345E+5


42
0600
0xBadFace
170141183460469231731687303715884105727


0i
011i  // == 11i
0.i
2.71828i
1.e+0i
6.67428e-11i
1E6i
.25i
.12345E+5i

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
	  if b > c && d > a && c + d > 1a + b && c > 0 && d > 0 && a % 2 == 0 ?? {
      fmt.Println#("Valores aceitos");
    } else {
      fmt.Println#("Valores nao aceitos");
    };
  };
  
  const a, b, c = 3, 4, "foo";
  
  var a, b, c, d int;  // create variables
  ? _, err := fmt.Scanf#("%d %d %d %d", &a, &b, &c, &d);  // read variable and check for errors
  
  if err != nil ?? { 
    fmt.Println#("%f", err);
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

