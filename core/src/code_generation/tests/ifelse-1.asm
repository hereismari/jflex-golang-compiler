100: LD SP, #4000
108: LD R3, #10
116: ST a, R3
124: LD R4, #2
132: ST b, R4

function block
1000: > R5, R3, R4
1008: LD R0, true
1016: BR *0(SP)
1024: LD R0, false
1032: BR *0(SP)

function main
