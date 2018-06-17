100: LD SP, #4000
108: LD R1, #0
116: ST y, R1

function x
1000: MUL R2, #2, #3
1008: ADD R3, #1, R2
1016: MUL R4, #4, #5
1024: MUL R5, R4, R1
1032: ADD R6, R3, R5
1040: ST y, R6
1048: BR *0(SP)

function main
4056: ADD SP, SP, #mainsize
4064: ST *SP, #4080
4072: BR #1000
4080: SUB SP, SP, #mainsize
4088: LD R7, #1
4096: ST y, R7
