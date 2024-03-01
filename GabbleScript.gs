a set nextint. b set nextint. nextline. ca set <nextline upper chararray>
b times <z,
	ascii set <nextline chararray>
	ca len times <i,
		c set <ca get i minus 65>
		c set ternary<<c inf 0> or <c sup 26>, 26, c>
		l for <c mul a, c add 1 mul a,
			ascii get l print
		>
	>
	println
>
if <a inf 0, inc a> else <a eq b, dec a>