100: LD SP, #4000
108: LD R5, #0
116: ST y, R5

function x
1000: MUL R1, #2, #3
1008: ADD R2, #1, R1
1016: MUL R3, #4, #5
1024: ADD R4, R2, R3
1032: ST y, R4
1040: BR *0(SP)

function main
1348: ADD SP, SP, #xsize
1356: ST *SP, #1372
1364: BR #1000
1372: SUB SP, SP, #xsize
1380: LD R6, #1
1388: ST y, R6
