#!/usr/bin/python
import sys
import operator
from fractions import gcd
import itertools

# From wikipedia: http://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
def extended_gcd(a, b):
	s, old_s = 0, 1
	t, old_t = 1, 0
	r, old_r = b, a    
	while r != 0:
		quotient = old_r // r
		assert(r != 0)
		old_r, r = r, old_r - (quotient * r)
		old_s, s = s, old_s - (quotient * s)
		old_t, t = t, old_t - (quotient * t)       

	return old_s, old_t, old_r
	
def format(mod_res, mod_primes):
	#~ print(mod_primes)
	#~ print(mod_res)
	for i in range(len(mod_primes)):
		for j in range(len(mod_primes)):
			if i == j:
				continue
			
			p1 = mod_primes[i]
			p2 = mod_primes[j]
			r1 = mod_res[i]
			r2 = mod_res[j]
			
			if p1 == p2:
				if r1 != r2:
					return (True, True)
				else:
					del mod_primes[max(i,j)]
					del mod_res[max(i,j)]
					return (True, False)
						
			t = gcd(p1, p2)			
			if t == 1:
				continue # all is well.
			
			if t == p1:
				if r2 % p1 == r1:
					p3 = t * t
					r3 = r2 % p3
					p2 = p2 // t
					r2 = r2 % p2
					del mod_primes[j]
					del mod_res[j]
					mod_primes.append(p2)
					mod_res.append(r2)
					#~ mod_primes.append(p3)
					#~ mod_res.append(r3)
					return (True, False)
				else:
					return (True, True)
			elif t == p2:
				if r1 % p2 == r2:
					p3 = t * t
					r3 = r1 % p3
					p1 = p1 // t
					r1 = r1 % p1
					del mod_primes[i]
					del mod_res[i]
					#~ mod_primes.append(p3)
					#~ mod_res.append(r3)
					mod_primes.append(p1)
					mod_res.append(r1)
					return (True, False)
				else:
					return (True, True)
				
			# All other t...
			p3 = t
			if r1 % p3 != r2 % p3:
				return (True, True)
			r3 = r1 % p3
			p1 = p1 // p3
			p2 = p2 // p3
			r2 = r2 % p2
			r1 = r1 % p1
			del mod_primes[max(i,j)]
			del mod_res[max(i,j)]
			del mod_primes[min(i,j)]
			del mod_res[min(i,j)]			
			mod_primes.append(p1)
			mod_primes.append(p2)
			mod_primes.append(p3)
			mod_res.append(r1)
			mod_res.append(r2)
			mod_res.append(r3)			
			return (True, False)
			
	return (False, False)
	
def lcm(a,b):
	return a * b // gcd(a, b)		
	
def lcmm(args):
	"""Return lcm of args."""   
	l = lcm(args[0], args[1])
	for i in range(2,len(args)):
		l = lcm(l, args[i])
	return l 
	
def ext_crt(resi, primes):
	# x \cong a mod n => x \cong a mod p1, x \cong a mod p2
	# p1 * p2 = n
	# eg. 5 mod 6 = 5 mod 3, 5 mod 2. 1 mod 2, 2 mod 3
	# 5 mod 9, 2 mod 3
	mod_res = resi[:]
	mod_primes = primes[:]
	
	if len(mod_primes) == 1:
		return (mod_res[0], mod_primes[0])
	
	#~ if mod_primes[0] == mod_primes[1]:
		#~ if mod_res[0] == mod_res[1]:
			#~ return mod_res[0], mod_primes[0]
		#~ return None
	
	#~ t = gcd(mod_primes[0], mod_primes[1])
	
	_lcm = lcmm(mod_primes) #abs(mod_primes[0] * mod_primes[1]) // t
	
	f = format(mod_res, mod_primes)
	while f[0]:
		if f[1]:
			return None
		f = format(mod_res, mod_primes)
		
	#~ print(mod_primes)
	#~ print(mod_res)
	m = 1
	for p in primes:
		m *= p
	#~ print(m)
	res, prime = crt(mod_res, mod_primes)
	#~ print(res, prime, m)
	
	while res <= m:
		derp = True
		#~ print(res)
		for i in range(len(primes)):			
			derp = derp and res % primes[i] == resi[i]
				
		if derp:		
			break
		res += prime
		
	return (res, _lcm)
			
		
				 
			
	
def crt(mod_res, mod_primes):
	# calculate N as product of primes
	N = 1
	for p in mod_primes:
		N *= p

	# We know the following gcd to be 1, we're interested in the coefficients.
	sum = 0
	for i in range(len(mod_primes)):
		prime = mod_primes[i]
		a = mod_res[i]	
		rest = N//prime
		r, s, _ = extended_gcd(prime, rest)
		e = s*rest
		#~ print(s,rest,e)
		sum += a*e
	
	return sum % N, N
	
	
if __name__ == '__main__':
	n = int(input())
	for _ in range(n):
		line = sys.stdin.readline().strip().split()
		mod_res = [int(i) for i in line[::2]]
		mod_primes = [int(i) for i in line[1::2]]
		ans = ext_crt(mod_res, mod_primes)
		if not ans is None:
			print (str(ans[0]) + " " + str(ans[1]))
		else:
			print ("no solution")
		

					
			
	
		
	
	
