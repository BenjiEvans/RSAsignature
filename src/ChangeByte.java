import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ChangeByte {

	public static void main(String[] argss){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter file that you would like to change: ");
		String fileName = scan.nextLine();
		File file = new File(fileName);
		int size = (int) file.length();
		try {
			FileInputStream in = new FileInputStream(fileName);
			byte[] data = new byte[size];
			in.read(data);
			int choice;
			do{
				System.out.print("Enter the byte to change: ");
				choice = scan.nextInt();
				if(choice >= 1 && choice <= size )break;
				System.out.print("That is out of range\nEnter a number between 1 and "+size+"\n");
				
			}while(true);
			
			data[choice-1] = (byte) (data[choice-1] << 1);
			
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(data);
			out.close();
			in.close();
			System.out.print("Byte "+choice+" was successfully changed");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}	
}