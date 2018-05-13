package main
import "fmt"

func main(){
	// your code goes here
	var _x = 1;
	var A = 1;
	
	fmt.Println(A + _x);
}

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
