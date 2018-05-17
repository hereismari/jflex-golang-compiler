// Stryct types
// An empty struct.
struct {}

struct {
	x, y int;
}

// A struct with 6 fields.
struct {
	x, y int;
	u float32;
	_ float32; // padding
	A *[]int;
	// F func();
}

// Embedded struct (by value).
struct {
	Discount;
}

// Embedded struct (by reference).
struct {
	*Discount;
}

// Embedded struct with blank value - pointer (syntax error).
//struct {
//	*_;
//}

// Embedded struct with blank value - value (syntax error).
//struct {
//	_;
//}

struct {
	fmt.Formatter;
}

struct {
	*fmt.Formatter;
}

struct {
	x, y, _, z int;
}

// Embedded struct shouldn't accept type. (syntax error)
//struct {
//	fmt.Formatter int;
//}

// Embedded struct shouldn't accept type. (syntax error)
//struct {
//	*fmt.Formatter int;
//}

// Array Types
[32]byte
//[2*N] struct { x, y int32 }
[1000]*float64
[3][5]int
[2][2][2]float64  // same as [2]([2]([2]float64))

// Slice Types
[]T
[][32]byte

// Pointer Types
*Point
*[4]int

// Channel Types
chan T          // can be used to send and receive values of type T
chan<- float64  // can only be used to send float64s
<-chan int      // can only be used to receive ints
chan<- chan int    // same as chan<- (chan int)
chan<- <-chan int  // same as chan<- (<-chan int)
<-chan <-chan int  // same as <-chan (<-chan int)
chan (<-chan int)

// Map Types
map[string]int
//map[*T]struct{ x, y float64 }
//map[string]interface{} */
