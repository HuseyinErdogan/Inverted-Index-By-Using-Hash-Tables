import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HashTable{
	
    private static int tableSize = 128;
    public static double loadFactor = 0.5;// load factor değerini giriniz.
    private static double tableCounter=0;
    private static int qPrime=7;
    public static int collisionCounter=0;
    public String CollisionHandlingType="LP";// LP veya DH yazarak collision handling türünü ayarlayınız.
    public String HashFunctionType="SSF";// SSF veya PAF yazarak hashfunctionın nasıl yapılacağını belirleyiniz.
    
	 
    HashEntry<Integer, String>[] table;

    public HashTable() {
          table = new HashEntry[tableSize];
          for (int i = 0; i < tableSize; i++)
                table[i] = null;
    }
    
  
    public int hashFunction(String string) {       
    	if (HashFunctionType=="SSF") {//  HashFunction in SSF(Simple Summation Function)
    		string = string.toLowerCase();
        	char[] ch = string.toCharArray();
        	int sum=0;
        	for (int i = 0; i < ch.length; i++) {
    			sum+=ch[i]-96;
    		}
        	return sum;
		}
    	else {// HashFunction in PAF(Polynomial Accumulation Function)
    		string = string.toLowerCase();
        	char[] ch = string.toCharArray();
        	int sum=0;
        	int prime = 33;
        	for (int i = 0; i < ch.length; i++) {
        		int temp = 0;
        		temp = ch[i]-96;
        		temp = (int) (temp *  Math.pow(prime, ch.length-(i+1)));
        		sum+=temp%tableSize;	
        		sum=sum%tableSize;
    		}
        	return sum ;
		}
    	
    }

    
    public void resize() {
    	int tempSize=tableSize*2+1;

    	// ASAL SAYI BULMA
    	while (true) {
    		boolean flag=true;
			for (int i = 2; i < tempSize; i++) {
				if (tempSize%i==0) {
					flag=false;
				}
			}
			if (!flag) {
				tempSize+=1;
			}
			else {
				break;
			}
		}
    	
    	HashEntry<Integer, String>[] temp= table;
    	
    	tableSize=tempSize;
    	
    	

    	table = new HashEntry[tableSize];
        for (int i = 0; i < tableSize; i++)
              table[i] = null;
    	tableCounter=0;
        for (int i = 0; i < temp.length; i++) {
             	
			if (temp[i]!=null && temp[i].getkey()!=" ") {				
				put(hashFunction(temp[i].getkey()),temp[i].getkey(),temp[i].getInformation());				
			}
		}        
    }
    public void remove(String key) {
    	System.out.println();
    	System.out.println("-----------------");
    	int hash=hashFunction(key)%tableSize;
    	if (table[hash]==null) {
			System.out.println("Not Found");
		}
    	else {
			if (table[hash].getkey().equals(key)) {
				HashEntry<Integer, String> temp = new HashEntry<Integer, String>(table[hash].getvalue(),"RemovedWord");
				table[hash]=null;
				table[hash]=temp;
				System.out.println("'"+key+"' is removed.");				
			}
			else {
				boolean flag=false;
				while (!table[hash].getkey().equals("RemovedWord")) {
		    		int i=1;
					if (table[hash].getkey().toLowerCase().equals(key)) {
						HashEntry<Integer, String> temp = new HashEntry<Integer, String>(table[hash].getvalue(),"RemovedWord");
						table[hash]=null;
						table[hash]=temp;
						System.out.println(key+" is removed.");
						flag=true;
						break;
					}
					else {
						if (CollisionHandlingType=="LP") {
							hash+=1;
							hash=hash%tableSize;
						}
						else {
							int dk = qPrime- (hashFunction(key)%qPrime);
							hash=hash+i*dk;
							hash=hash%tableSize;
							i+=1;
						}
					}
						
				}
				if (!flag) {
					System.out.println("Not found");
				}
			}
		}
    	System.out.println("-----------------");
    }
    public void search(String key) {
    	int hash=hashFunction(key)%tableSize;
    	key=key.toLowerCase();
    	
    	System.out.println();
    	System.out.println("-----------------");
    	System.out.println("Searching for: "+key);
    	
    	
    	boolean flag=false;
    	while (table[hash]!=null) {
    		int i=1;
			if (table[hash].getkey().toLowerCase().equals(key)) {
				table[hash].printInformation();
				flag=true;
				break;
			}
			else {
				if (CollisionHandlingType=="LP") {
					hash+=1;
					hash=hash%tableSize;
				}
				else {
					int dk = qPrime- (hashFunction(key)%qPrime);
					hash=hash+i*dk;
					hash=hash%tableSize;
					i+=1;
				}
			}
				
		}
    	if (!flag) {
			System.out.println("Not found!");
		}

    	System.out.println("-----------------");
    }


	public void fillTheHash(File folder,ReadFile f){
    	File[] listOfFiles = folder.listFiles();
    	


		for (int i = 0; i < listOfFiles.length; i++) {
			File[] txtFiles = listOfFiles[i].listFiles();
			for (int j = 0; j < txtFiles.length; j++) {

				try {
					BufferedReader br = new BufferedReader(new FileReader(txtFiles[j]));

					String st;
					while ((st=br.readLine())!=null) {
						 
						String[] stArray;
						stArray = st.split(f.DELIMITERS);

						for (int k = 0; k < stArray.length; k++) {
							stArray[k]=stArray[k].toLowerCase();
							if (f.checkStopWord(stArray[k]) && stArray[k]!=" ") {
								Data tempData = new Data(listOfFiles[i].getName(),txtFiles[j].getName());
							
								
								put(hashFunction(stArray[k]),stArray[k],tempData);
							}

						}
						st=br.readLine(); // Satırlar arasında 1 boşluk olduğu için
					}
					
					
				} catch (Exception e) {
					System.out.println("Error detected while reading file!!");
				}
				
				System.out.println(listOfFiles[i]+"/"+txtFiles[j].getName()+" is completed.");

				
			}
			
			
		}
		


    }

   

    public String get(int value) {
          
    	  int hash = value;
          
          if (table[hash] == null)
                return null;
          else
                return table[hash].getkey();
          
    }

    public void put(int value, String key,Data data) {
    	
          int hash = value%tableSize;
          boolean flag=false;
          if (table[hash]!=null && table[hash].getkey().equals(key)) {
        	  collisionCounter+=1;
		}
          while(!flag&&CollisionHandlingType=="LP")//Collision olduğu zaman hashi lineer olarak artırır (LINEAR PROBING)
          {
        	  if (table[hash]==null || table[hash].getkey().equals(key) ) {
				flag=true;
        	  }
        	  else {
        		

				hash+=1;
				hash=hash%tableSize;
        	  }
        	 
          }
          	int i=1;
          	int hk=hash%tableSize;
          while (!flag&&CollisionHandlingType=="DH") {//Collision olduğu zaman DOUBLE HASHING YAPAR(DH)
			
	   		if (table[hash]==null || table[hash].getkey().equals(key) ) {
				flag=true;
	        }
	   		else {

	    	  int dk = qPrime- (value%qPrime);
	    	  
	    	  
	    	  int temp=hk+i*dk;
	    	  hash=temp%tableSize;
	    	  i+=1;
	        }					
      
          }          
          if (table[hash]==null) {
        	 
        	  table[hash] = new HashEntry<Integer, String>(value, key);
        	  table[hash].addInformation(data);
        	  tableCounter+=1;
          }
          else if (table[hash].getkey().equals(key)) {
        	  
        	  table[hash].addInformation(data);

          }
        
          if (tableCounter/(double)tableSize>loadFactor) {       	  
        	  resize();
          }
                  
    }
    public void put(int value, String key, ArrayList<Data> info ) {
	    	int hash = value%tableSize;
	        boolean flag=false;
	        if (table[hash]!=null && table[hash].getkey().equals(key)) {
	        	  collisionCounter+=1;
			}
	        while(!flag&&CollisionHandlingType=="LP")//Collision olduğu zaman hashi lineer olarak artırır (LINEAR PROBING)
	        {
	      	  if (table[hash]==null || table[hash].getkey().equals(key) ) {
					flag=true;
	      	  }
	      	  else {

					hash+=1;
					hash=hash%tableSize;
	      	  }
	      	 
	        }
	        int i=1;
	        while (!flag&&CollisionHandlingType=="DH") {//Collision olduğu zaman DOUBLE HASHING YAPAR(DH)
				
		   		if (table[hash]==null || table[hash].getkey().equals(key) ) {
					flag=true;
		        }
		   		else {
		    	  int dk = qPrime- (value%qPrime);
		    	  hash=hash+i*dk;
		    	  hash=hash%tableSize;
		    	  i+=1;
		        }		        
	        }	        
	        if (table[hash]==null) {
	      	 
	      	  table[hash] = new HashEntry<Integer, String>(value, key);
	      	  table[hash].setInformation(info);

	      	  tableCounter+=1;
	        }
    }
}
