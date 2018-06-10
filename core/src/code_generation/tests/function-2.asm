100: LD SP, #4000
108: LD R4, #0
116: ST y, R4

function x
1000: LD R3, #1
1008: ST y, R3
1016: LD R0, y
1024: BR *0(SP)

function main
1332: ADD SP, SP, #xsize
1340: ST *SP, #1356
1348: BR #1000
1356: SUB SP, SP, #xsize
1364: ST y, R0
1372: BR *0(SP)
