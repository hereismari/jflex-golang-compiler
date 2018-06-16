100: LD SP, #4000
108: LD R2, #0
116: ST y, R2

function x
1000: LD R1, #1
1008: ST y, R1
1016: LD R0, #y
1024: BR *0(SP)

function main
4032: ADD SP, SP, #xsize
4040: ST *SP, #4056
4048: BR #1000
4056: SUB SP, SP, #xsize
4064: ST y, R0
4072: BR *0(SP)
