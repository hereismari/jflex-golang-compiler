"IT WORKS"
core.src
robson
(if + if * if)
42
42.0
42i
`日本語`
'\U00110000'
'a'

// Composite Literal

Point3D{}
Point3D{y: -4}
Line{origin, Point3D{y: -4, z: 12.3}}
[10]string{}
[6]int{1, 2, 3, 5}
[...]string{"Sat", "Sun"}
[...]Point{{1.5, -3.5}, {0, 0}}
[][]int{{1, 2, 3}, {4, 5}} 
map[string]Point{"orig": {0, 0}}
map[Point]string{{0, 0}: "orig"}
[2]*Point{{1.5, -3.5}, {}} 
[2]PPoint{{1.5, -3.5}, {}}
[128]bool{'a': true, 'e': true, 'i': true, 'o': true, 'u': true, 'y': true}
map[string]float32{
	"C0": 16.35, "D0": 18.35, "E0": 20.60, "F0": 21.83,
	"G0": 24.50, "A0": 27.50, "B0": 30.87,
}