func(a, _ int, z float32) bool // ok
/*
func(a, b int, z float32) bool // ok
func() // ok
func(a, b int, z float64, opt ...interface{}) (success bool)
func(a, b int, z float32) (bool)
func(int, int, float64) (float64, *[]int)
func(prefix string, values ...int)
func(x int) int
func(n int) func(p *T)
*/
