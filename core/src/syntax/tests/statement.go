const a = 10;
const Pi float64 = 3.14159265358979323846;
const zero = 0.0;         // untyped floating-point constant
const (
	size int64 = 1024;
	eof        = -1;  // untyped integer constant
);
const a, b, c = 3, 4, "foo";  // a = 3, b = 4, c = "foo", untyped integer and string constants
const u, v float32 = 0, 3;    // u = 0.0, v = 3.0

type lol = (abc.def);
var lol = 10;
x = x;
f(10) <- f(10);
abc : const a = 1;
go 1.2 * 22;
return x;
break op;
continue op;
goto op;
{goto op;};
select {default : goto op;};
defer f(10) * abc.def;
if # ? x := f#(); x < y ? {
	return x;
} else if x > z ? {
	return z;
} else {
	return y;
};
for a < b _ {
	a *= 2;
};
for # ? i := 0; i < 10; i++ _ {
	for # ? i := 0; i < 10; i++ _ {
	   x = 2 + 3;
	   f(10);
	   if # ? x := f#(); x < y ? {
      return x;
    } else if x > z ? {
      return z;
    } else {
      return y;
    };
	};
};
