#!/usr/bin/python
import sys
import functools

__author__ = "Joakim Uddholm, Per Classon"


def extended_gcd(a, b):
    """
    From Wikipedia:
    http://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
    """
    s, old_s = 0, 1
    t, old_t = 1, 0
    r, old_r = b, a
    while r != 0:
        quotient = old_r // r
        old_r, r = r, old_r - (quotient * r)
        old_s, s = s, old_s - (quotient * s)
        old_t, t = t, old_t - (quotient * t)

    return old_s, old_t


def chinese_remainder_theorem(mod_res, mod_primes):
    """
    Returns N as a product of given primes.
    """
    N = functools.reduce(lambda x, y: x*y, mod_primes, 1)

    res = 0
    for i in range(len(mod_primes)):
        prime = mod_primes[i]
        a = mod_res[i]
        rest = N // prime
        r, s = extended_gcd(prime, rest)
        e = s * rest
        res += a * e

    return res % N, N


if __name__ == '__main__':
    n = int(input())
    for _ in xrange(n):
        line = sys.stdin.readline().strip().split()
        mod_res = [int(i) for i in line[::2]]
        mod_primes = [int(i) for i in line[1::2]]
        ans = chinese_remainder_theorem(mod_res, mod_primes)
        print "{} {}".format(*ans)