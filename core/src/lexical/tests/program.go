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
