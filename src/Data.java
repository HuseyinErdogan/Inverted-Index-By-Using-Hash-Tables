public class Data {
	public String File;
	public String Txt;
	public int counter;
	
	Data(String File,String Txt){
		this.File=File;
		this.Txt=Txt;
		this.counter=1;
	}
	
	public void raiseTheCounter() {
		counter+=1;
	}
	
	public String getFile() {
		return File;
	}

	public void setFile(String file) {
		File = file;
	}

	public String getTxt() {
		return Txt;
	}

	public void setTxt(String Txt) {
		this.Txt = Txt;
	}

	public int getCounter() {
		return counter;
	}



	public void print() {
		
		System.out.println("File: "+File+" - Txt File:"+Txt+" - Counter:"+counter);
	}

}

