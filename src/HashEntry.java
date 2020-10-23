import java.util.ArrayList;

public class HashEntry<V, K> {
	
    private V value;
    private K key;
    private ArrayList<Data> information=new ArrayList<Data>();
    
    

    
	HashEntry(V value, K key) {
          this.value = value;
          this.key = key;
          ArrayList<Data> information;
    }    
    public ArrayList<Data> getInformation() {
		return information;
	}

	public void setInformation(ArrayList<Data> information) {
		this.information = information;
	}
	public void addInformation(Data data) {
		
		boolean flag=false;
		for(Data d : information) {

			if (d.getFile().equals(data.getFile()) ) {
				if (d.getTxt().equals(data.getTxt())) {

					d.raiseTheCounter();
					flag=true;
					break;
				}
				
			}
		}
		if (!flag) {
			information.add(data);
		}

	}
	public void printInformation() {
		int sumOfDocument=0;
		for (Data temp : information) {
			System.out.println(temp.getCounter()+"-"+temp.getFile()+"/"+temp.getTxt());
			sumOfDocument+=1;
		}
		System.out.println(sumOfDocument+" documents found");
	}

    public int getvalue() {
    	int temp=(int)value;
          return temp;
    }

    public String getkey() {
    	String temp = (String) key;
          return temp;
    }
}