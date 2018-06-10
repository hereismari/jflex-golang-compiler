100: LD SP, #4000
108: LD R7, #0
116: ST y, R7

function x
1000: MUL R3, #2, #3
1008: ADD R4, #1, R3
1016: MUL R5, #4, #5
1024: ADD R6, R4, R5
1032: ST y, R6
1040: BR *0(SP)

function main
1348: ADD SP, SP, #xsize
1356: ST *SP, #1372
1364: BR #1000
1372: SUB SP, SP, #xsize
1380: LD R8, #1
1388: ST y, R8
