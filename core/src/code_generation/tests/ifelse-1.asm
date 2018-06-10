100: LD SP, #4000
108: LD R1, #10
116: ST a, R1
124: LD R2, #2
132: ST b, R2

function block
1000: SUB R3, R1, R2
1008: BLEZ R3, #1048
1016: AND R4, #true, #false
1024: LD R0, R4
1032: BR *0(SP)
1040: BR #1064
1048: LD R0, #false
1056: BR *0(SP)

function main
