import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {
		
		HashTable hash = new HashTable();
		ReadFile f = new ReadFile();
		
		File folder = new File("/bbc");
		
		long startIndex = System.currentTimeMillis(); //using for calculating indexing time
		hash.fillTheHash(folder,f);
     	long endIndex = System.currentTimeMillis(); //using for calculating indexing time
		 
		while(true) {
			

			System.out.println();
			
			System.out.println("-----------------");
			System.out.println("Enter your command.");
			System.out.println("Example:Search Cat");
			System.out.println("Example:Remove Cat");
			System.out.println("Example:Search 1000.txt");
			System.out.println("-----------------");
			System.out.println();
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			input=input.toLowerCase();
			String inputArray[]= new String[2];
			
			inputArray=input.split(" ");
			
			if (inputArray[0].equals("search")) {
				if (inputArray[1].equals("1000.txt")) {
					long est = endIndex - startIndex; // Geçen süreyi milisaniye cinsinden elde ediyoruz
					 System.out.println((double)est);
					 double seconds1 = (double)est/1000; // saniyeye çevirmek için 1000'e bölüyoruz.
					 System.out.println(seconds1);
					 File binTXT = new File("/1000.txt");
					 String[] binArray = new String[1000];
						try {
							BufferedReader br = new BufferedReader(new FileReader(binTXT));
							
							for (int i = 0; i < 1000; i++) {
								binArray[i]=br.readLine();

							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					 long max=Integer.MIN_VALUE;
					 long min=Integer.MAX_VALUE;
					 long sum=0;
						
						for (int i = 0; i < binArray.length; i++) {
							long startSearch = System.nanoTime();
							hash.search(binArray[i]);	//searches for each staff on 1000.txt
							
							long endSearch = System.nanoTime(); 
						    long tempTime = endSearch - startSearch; //calculates elapsed time 
						    
						    sum+=tempTime;
						    if (tempTime>max) {
								max=tempTime;
							}
						    if (tempTime<min) {
								min=tempTime;
							}

						}
					    long avarege=sum/1000;
						System.out.println();
						System.out.println("Load Factor: "+hash.loadFactor+" - Collision Handling Type: "+hash.CollisionHandlingType+" - Hash Function Type: "+hash.HashFunctionType);
						System.out.println();
						System.out.println("Indexing time: "+seconds1+" seconds ---- Avarege: "+avarege+" nanoseconds ---- Min: "+min/1000+" nanoseconds ---- Max: "+max/1000+" nanoseconds");						
						System.out.println("Collision Count: "+hash.collisionCounter);
				}
				else
					hash.search(inputArray[1]);
			}
			else if (inputArray[0].equals("remove")) {
				hash.remove(inputArray[1]);
			}
			else
				System.out.println("Wrong command!!");
			
		}
		
		
		
	

	}

}
