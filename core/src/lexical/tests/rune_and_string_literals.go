'a'
'ä'
'本'
'\t'
'\000'
'\007' if break continue || <
'\377'
'\x07'
'\xff'
'\u12e4'
'\U00101234'
'\''         // rune literal containing single quote character
'aa'         // illegal: too many characters, lexical error!
'\xa'        // illegal: too few hexadecimal digits, lexical error!
'\0'         // illegal: too few octal digits, lexical error!
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

