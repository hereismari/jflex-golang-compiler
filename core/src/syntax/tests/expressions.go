f#(3.1415, true, id, id, id),

f # (id), f#(10),

10 + 10 * 10, sem_erro * 10 & 10 && 10 + 10 + 10 * 10 - 10 + // sem erro sintatico
f(10), f(((((((((((X))))))))))), (int),
x,
2,
(s + ".txt"),
f#(3.1415, true),
Point{1, 2},
m["foo"],
s[i : j + 1],
// s[::], // ERRO SINTATICO
s[:x:x],
s[1:],
s[1:1],
// s[:1],  // ERRO SINTATICO
obj.color,
s[f(1):10+20+30-2*1000],

// Conversions
*Point(p),        // same as *(Point(p))
?*Point?(p),      // p is converted to *Point
<-chan int(c),    // same as <-(chan int(c))
?<-chan int?(c),  // c is converted to <-chan int
func()(x),        // function signature func() x
?func()?(x),      // x is converted to func()
?func() int?(x),  // x is converted to func() int
func() int(x),
??int??(?int?(x))
//???????*Point??????? // ERRO SINTATICO



// , ERRO SINTATICO

// x is converted to func() int (unambiguous)


// 10 + 10 * 10, erro erro * 10 & 10 && 10 + 10 + 10 * 10 - 10 // ERRO SINTATICO
