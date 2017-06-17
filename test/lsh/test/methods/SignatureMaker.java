package lsh.test.methods;

import java.util.Random;
import java.util.Set;

public class SignatureMaker {
	private int p;
	private int[] a,b;
	
	public SignatureMaker(int n_hash) {
		Random random = new Random();
		this.a = new int[n_hash];
		this.b = new int[n_hash];
		this.p = 2147483647;
		//((a x + b)%p)%N
		for(int i = 0; i < n_hash ; i++) {
			a[i] = random.nextInt()%p;
			b[i] = random.nextInt()%p;
		}
	}
	
	public int[] make(Set<Integer> shingleCodes) {
		int[] signature = new int[a.length];
		for(int i = 0; i < signature.length; i++) {
			signature[i] = Integer.MAX_VALUE;
		}
		//min-hashing sensitivity (d1,d2,1-d1,1-d2)
		for(int i = 0; i < signature.length; i++) {
			for(Integer code : shingleCodes) {
				int currentHash = ((a[i]*code + b[i])%p);
				signature[i] = Math.min(signature[i], currentHash);
			}
			if(signature[i] == 2147483647) {
				System.out.println(shingleCodes.size());
				System.exit(0);
			}
			
		}
		return signature;
	}
}
