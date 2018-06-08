100: LD SP, #4000
108: LD R4, 0
116: ST y, R4

function x
600: MUL R0, 2, 3
608: ADD R1, 1, R0
616: MUL R2, 4, 5
624: ADD R3, R1, R2
632: ST y, R3
640: BR *0(SP)

function main
948: ADD SP, SP, #xsize
956: ST *SP, #972
964: BR #600
972: SUB SP, SP, #xsize
980: LD R5, 1
988: ST y, R5
