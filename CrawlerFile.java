import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
public class CrawlerFile {

	private static int lineNum=0;
	
	private static int fileNum=0;
	
	private static LinkedList<File> files=new LinkedList<File>();
	
	private static String path="C:\\Users\\Administrator\\Desktop\\coding";
	
	public static void main(String []args){
		files.addLast(new File(path));
		execute();
		System.out.println("file number:"+fileNum+" ");
		System.out.println("total:"+lineNum+" lines");
	}
	public static void execute(){
		while(files.size()>0){
			File file=files.removeFirst();
			if(file.isDirectory()){
				for(File f:file.listFiles()){
					files.addLast(f);
				}
			}
			else{
				if(file.getName().endsWith(".java")||file.getName().endsWith(".java")){
					fileNum++;
					System.out.println(file.getName());
					lineNum+=lineCountInFile(file);
				}
			}
		}
	}
	public static int lineCountInFile(File file){
		int count=0;
		BufferedReader bufferedReader=null;
		try{
			bufferedReader=new BufferedReader(new FileReader(file));
			while(bufferedReader.readLine()!=null){
				count++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(bufferedReader!=null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return count;
	}
}
