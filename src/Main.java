import java.io.IOException;
import java.util.Scanner;

public class Main {
		
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		do{
			int choice = displayMenu(scan);
			switch(choice){
			case 1:
				try {
					DigitalSignature.signMessage(scan);
				} catch (IOException e) {
					System.out.println("Error: "+e.getMessage());
				}
				break;
			case 2:
				try {
					DigitalSignature.authenticatMessage(scan);
				} catch (IOException e) {
					System.out.println("Error: "+e.getMessage());
				}
				break;
			case 3:
				System.exit(0);
			}
			
		}while(true);
	}
	
	public static int displayMenu(Scanner scan){
		
		int choice = -1;
		//print menu 
		System.out.print("1.)Sign File\n");
		System.out.print("2.)Read Signed File\n");
		System.out.print("3.)Exit\n");
		
		
		do{
			System.out.print("Enter choice:");
			try{
				
				choice = scan.nextInt();
				scan.nextLine();
				if(choice == 1 || choice == 2 || choice == 3)break;
				System.out.print("Invalid input. Try again\n");				
			}catch(Exception e){
				e.printStackTrace();
				break;
				//System.out.print("Invalid input. Try again\n");
			}
			
		}while(true);
		return choice;
	}
	
	
}
