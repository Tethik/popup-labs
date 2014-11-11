#!/usr/bin/python
import sys
import operator
import itertools

# From wikipedia: http://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
def extended_gcd(a, b):
	s, old_s = 0, 1
	t, old_t = 1, 0
	r, old_r = b, a    
	while r != 0:
		quotient = old_r // r
		old_r, r = r, old_r - (quotient * r)
		old_s, s = s, old_s - (quotient * s)
		old_t, t = t, old_t - (quotient * t)       

	return old_s, old_t
	
def crt(mod_res, mod_primes):
	# calculate N as product of primes
	N = 1
	for p in mod_primes:
		N *= p
	#~ N = reduce(operator.mul, mod_primes, 1)
	
	#~ x = reduce(operator.mul, 
	# We know the following gcd to be 1, we're interested in the coefficients.
	# 
	sum = 0
	for i in range(len(mod_primes)):
		prime = mod_primes[i]
		a = mod_res[i]	
		rest = N//prime
		r, s = extended_gcd(prime, rest)
		e = s*rest
		sum += a*e

	return sum % N, N
	
	
if __name__ == '__main__':
	n = int(input())
	for _ in xrange(n):
		line = sys.stdin.readline().strip().split()
		mod_res = [int(i) for i in line[::2]]
		mod_primes = [int(i) for i in line[1::2]]
		ans = crt(mod_res, mod_primes)
		print (str(ans[0]) + " " + str(ans[1]))
	
		
	
	
