import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Random;
public class KeyGen {
	
	public static void main(String[] args){
		
		Random r = new Random();
		BigInteger p = new BigInteger(512, 100, r);
		BigInteger q = new BigInteger(512, 100, r);
		BigInteger n = p.multiply(q);
		System.out.println("n:"+n);
		BigInteger negativeOne = new BigInteger("-1");

		BigInteger phi = (p.add(negativeOne)).multiply(q.add(negativeOne));
		BigInteger e = getKey(phi);
		System.out.println("e:"+e);
		BigInteger d = e.modInverse(phi);	
		System.out.println("d:"+d);
		//write key files 
		try {
			FileOutputStream out = new FileOutputStream("pubkey.rsa");
			ObjectOutputStream oout = new ObjectOutputStream(out);
			
			FileOutputStream out2 = new FileOutputStream("privkey.rsa");
			ObjectOutputStream oout2 = new ObjectOutputStream(out2);
			
			//write public 
			oout.writeObject(e);
			oout.writeObject(n);
			oout.close();
			
			//write private 
			oout2.writeObject(d);
			oout2.writeObject(n);
			oout2.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}	
	
	public static BigInteger getKey(BigInteger phi){
		Random r = new Random();
		BigInteger pub = new BigInteger(512, r);
		while(pub.gcd(phi).intValue() != 1)
		{
			pub = new BigInteger(512, r);
		}
		return pub;		
	}
}