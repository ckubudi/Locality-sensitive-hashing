package lsh.test.methods;

import java.util.Random;
import java.util.Set;

public class SignatureMaker {
	private int p, N;
	private int[] a,b;
	
	public SignatureMaker(int n_hash, int N) {
		Random random = new Random();
		this.a = new int[n_hash];
		this.b = new int[n_hash];
		this.p = 2147483647;
		this.N = N;
		//((a x + b)%p)%N
		for(int i = 0; i < n_hash ; i++) {
			a[i] = random.nextInt();
			b[i] = random.nextInt();
		}
	}
	
	public int[] make(Set<Integer> shingleCodes) {
		int[] signature = new int[a.length];
		for(int i = 0; i < signature.length; i++) {
			signature[i] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < signature.length; i++) {
			for(Integer code : shingleCodes) {
				int currentHash = ((a[i]*code + b[i])%p)%N;
				signature[i] = Math.min(signature[i], currentHash);
			}
		}
		return signature;
	}
}
