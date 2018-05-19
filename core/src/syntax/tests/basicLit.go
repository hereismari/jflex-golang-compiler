0.
72.40
072.40  // == 72.40
2.71828
1.e+0
6.67428e-11
1E6
.25
.12345E+5


42
0600
0xBadFace
170141183460469231731687303715884105727


0i
011i  // == 11i
0.i
2.71828i
1.e+0i
6.67428e-11i
1E6i
.25i
.12345E+5i

'a'
'ä'
'本'
'\t'
'\000'
'\007'
'\377'
'\x07'
'\xff'
'\u12e4'
'\U00101234'
'\''         // rune literal containing single quote character
'\uDFFF'     // illegal: surrogate half, should work!
'\U00110000' // illegal: invalid Unicode code point, should work!

"abc""abc"

`\n
\n`

`abc`                // same as "abc"
`\n
\n`                  // same as "\\n\n\\n"
"\n"
"\""                 // same as `"`
"Hello, world!\n"
"日本語"
"\u65e5本\U00008a9e"
"\xff\u00FF"


"日本語"                                 /* UTF-8 input text*/
`日本語`                                 // UTF-8 input text as a raw literal
"\u65e5\u672c\u8a9e"                    // the explicit Unicode code points
"\U000065e5\U0000672c\U00008a9e"        // the explicit Unicode code points
"\xe6\x97\xa5\xe6\x9c\xac\xe8\xaa\x9e"  // the explicit UTF-8 bytes

"teste\"teste"
`test`


1231

123

"\""
"\x22"
"\u0022"
"\042"
`"`
'"'
"%c\n" '"' "%s\n"
// Seriously, this one is just for demonstration not production :)

