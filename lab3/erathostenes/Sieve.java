package erathostenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * Erathostenes Sieve for finding primes. Uses bitset for 1 bit = 1 number memory efficient storage.
 * 
 * @author Joakim Uddholm, Per Classon
 */
public class Sieve {

	private BitSet bits;
	private int numberOfPrimes = 1;
	
	/**
	 * Construct a sieve with limit n.
	 * @param n
	 */
	public Sieve(int n) {
		bits = new BitSet(n+1);
		bits.set(0, true);
		bits.set(1, true);
		
		if(n == 1)
			return;
		
		int nsqrt = (int) Math.ceil(Math.sqrt((double) n));
		
		for(long pp = 4; pp > 0 && pp <= n; pp += 2) {
			bits.set((int) pp);
		}
		
		for(int v = 3; v <= n; v += 2) {
			if(bits.get(v)) // Composite
				continue;
			
			numberOfPrimes++;
			
			if(v <= nsqrt) {
				for(long pp = v*v; pp > 0 && pp <= n; pp += v) {
					bits.set((int) pp);
				}
			}
		}
	}
	
	public int getNumberOfPrimes() {
		return numberOfPrimes;
	}
	
	/**
	 * Check if p is a prime number. P must be less than limit
	 * @param p
	 * @return boolean
	 */
	public boolean isPrime(int p) {
		return !bits.get(p);
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String[] line = reader.readLine().split(" ");
		
		int n = Integer.parseInt(line[0]);
		int q = Integer.parseInt(line[1]);
		
		Sieve s = new Sieve(n);
		writer.write(Integer.toString(s.getNumberOfPrimes()));
		writer.write("\n");
		for(int i = 0; i < q; ++i) {	
			writer.write(s.isPrime(Integer.parseInt(reader.readLine())) ? "1" : "0");
			writer.write("\n");
		}
		writer.flush();
		writer.close();
	}

}
