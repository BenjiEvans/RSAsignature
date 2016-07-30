import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class DigitalSignature {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("1.)Sign Message");
		System.out.println("2.)Check Message Authenticity");
		System.out.print("Enter your choice: ");
		int choice;
		Scanner scan = new Scanner(System.in);
		choice =  scan.nextInt();
		scan.nextLine();
		//scan.close();
		if(choice == 1){
			signMessage(scan);
		}else if(choice ==2){
			authenticatMessage(scan);
		}	
		scan.close();
	}

	public static void authenticatMessage(Scanner scan) throws FileNotFoundException {
		
		try {
			File file = promptforFile("Enter file to authenticate",scan);
			ObjectInputStream signedFile  = new ObjectInputStream(new FileInputStream(file));
			//get signature and message
			BigInteger signature = (BigInteger) signedFile.readObject();
			byte[] message = (byte[]) signedFile.readObject();
			//get message digest 
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(message);
			byte [] digest1 = md.digest();// original message digest
			//decrypt message digest from signature
			//first get public key to unencrypt 
			ObjectInputStream pubKey = new ObjectInputStream(new FileInputStream("pubkey.rsa"));
			BigInteger d = (BigInteger) pubKey.readObject();
			BigInteger n = (BigInteger) pubKey.readObject();				
			BigInteger signedDigest = signature.modPow(d, n);			
			
			byte [] digest2 = signedDigest.toByteArray();// message digest from signature 
			
			if(MessageDigest.isEqual(digest1, digest2)){
				
				System.out.println("Message is authentic");
				
			}else{
				System.out.println("Message is not authentic");
			}
			
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage()+"\nMessage is likely not authentic\n");
			//System.out.println("The signed file is not authentic");
		}catch (ClassNotFoundException e) {
			System.out.println("Error: "+e.getMessage()+"\nMessage is likely not authentic\n");
			//System.out.println("The signed file is not authentic");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("Error: "+e.getMessage()+"\nMessage is likely not authentic\n");
		}
	}

	public static void signMessage(Scanner scan) throws IOException{
		File file = promptforFile("Enter file to sign",scan);
		try {
	
			Scanner fileScan = new Scanner(file);
			//read bytes; this may cause problems for really large files
			int length = (int)file.length();
			byte [] data = new byte[length];
			int i =0;
			while(fileScan.hasNextByte())
			{
				data[i] = fileScan.nextByte();
				i++;
			}
			//get md hash 
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte[] digest = md.digest();
			//encrypt with private key 
			BigInteger signedMessage = new BigInteger(digest);
			ObjectInputStream privKey = new ObjectInputStream(new FileInputStream("privkey.rsa"));
			BigInteger e = (BigInteger) privKey.readObject();
			BigInteger n = (BigInteger) privKey.readObject();
			signedMessage = signedMessage.modPow(e,n);
			privKey.close();
			//finish signing (write to file)
			String signFileName = file.getName()+".signed";
			ObjectOutputStream signedFile = new ObjectOutputStream(new FileOutputStream(signFileName));
			signedFile.writeObject(signedMessage); //append signeture 
			//append file data
			/*for(i = 0; i < length ; i++)
			{
				signedFile.writeByte(data[i]);
			}*/
			signedFile.writeObject(data);
			signedFile.close();			
			System.out.println("Message has been signed and stored in "+signFileName);
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			System.out.println("Error: "+e.getMessage()+"\nMessage is likely not authentic\n");
		} catch (IOException e1) {
			throw e1;
		}
	}

	private static File promptforFile(String prompt, Scanner scan) {
		System.out.print(prompt+":");
		String fileName = scan.nextLine();
		return new File(fileName); 
	}
}
