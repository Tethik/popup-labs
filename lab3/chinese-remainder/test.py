import crt_ext
import random


while True:
	print()
	a = random.randint(2, 20)
	b = random.randint(0, a-1)
	c = random.randint(2, 20)
	d = random.randint(0, c-1)

	print(b,a,d,c)
	t = crt_ext.ext_crt([b, d],[a, c])
	if t is None:
		print("no solution")
		continue
	res, mod = t
	
	
	print(res,mod)
	print(b, res % a)
	print(d, res % c)
	
	assert(res % a == b)
	assert(res % c == d)
	
	
