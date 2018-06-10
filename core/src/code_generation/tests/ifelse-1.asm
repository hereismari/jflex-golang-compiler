100: LD SP, #4000
108: LD R1, #10
116: ST a, R1
124: LD R2, #2
132: ST b, R2

function block
1000: LD R0, R4
1008: BR *0(SP)
1016: LD R0, #false
1024: BR *0(SP)

function main
