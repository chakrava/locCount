package locCount;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class locCount {
	
	
	public static void main (String args[]) throws IOException
	{
		//initialize some lists
		ArrayList<File> files=new ArrayList<>();//all the files we find
		ArrayList<File> scannedFiles = new ArrayList<>();//the files we actually counted lines of
		ArrayList<Integer> scannedFilesCount = new ArrayList<>();//the number of lines in each scanned file
		int locCount=0;
		int totalLocCount=0;
		
		File toFile = new File (System.getProperty("user.dir"));//get the directory containing this file as starting point
		System.out.println("Starting directory: "+toFile+"\n");
		
		files = findFiles(files,toFile);//recursively find all files
		
		BufferedReader in = null;
		
		for(File toOpenFile:files){
			try{
				String toOpen = toOpenFile.toString();
				locCount=0;
				
				System.out.println("Checking file: "+toOpen);
				
				if(toOpen.matches(".*.java")){//only scan .java files
					scannedFiles.add(toOpenFile);
					
					in = new BufferedReader(new FileReader(toOpen));
					String temp;
					while((temp=in.readLine()) !=null){//go through the whole file, one-line at a time
						temp=temp.replaceAll("\\s","");//get rid of all white-space, \t, " ", etc.
						
						//only care about counting lines that are longer than 2, aren't comments, and don't start with "import" or "packag"
						if(temp.length()>2 &&
								!temp.substring(0,2).matches("//") &&
								(temp.length()>=6 && !temp.substring(0,6).matches("import") && !temp.substring(0,6).matches("packag")) ){
							locCount++;	
							totalLocCount++;
						}
					}
					scannedFilesCount.add(locCount);
					System.out.println("\tLines counted: "+locCount);
				}
			}catch(Error e){
				System.out.println("Someone messed something up\n\n"+e.getMessage());
			}catch(Exception e){
				System.out.println("Someone messed something up\n\n"+e.getMessage());
			}
			
		}
		
		//Final output is here
		System.out.println("\nJava files scanned:\n");
		for(int i=0;i<scannedFiles.size();i++){
			System.out.println(scannedFiles.get(i)+"\t"+scannedFilesCount.get(i)+ " lines");
		}
		System.out.println("\nTotal lines counted: "+totalLocCount);
	}
	
	
	//Recursive method to get all the files in the directory/file passed to it
	public static ArrayList<File> findFiles(ArrayList<File> list, File toFind){
		ArrayList<File> temp = new ArrayList<>();
		temp.addAll(Arrays.asList(toFind.listFiles()));
		
		for(File xFile : temp){
			if(xFile.isDirectory()){
				findFiles(list,xFile);
			}
		}
		list.addAll(temp);
		return list;
	}
}