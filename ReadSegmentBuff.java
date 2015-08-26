import java.io.*;
import java.util.Random;
public class ReadSegmentBuff{
	private static final int BUFF_SIZE = 15;
	private Random random=new Random(47);
	private int buffOffset;//��ǰ��segmentBuff�����reader��ʼλ�õ�λ��
    private int cursor;//��ǰ��segmentBuff����λ�õ�λ��
	private int available;//��ǰ��segmentBuff��Ч�ַ�����
    private char[] segmentBuff = new char[BUFF_SIZE];
	public static void main(String []args) throws Exception{
		ReadSegmentBuff test=new ReadSegmentBuff();
		String content="18�����꣬�и��Գ��������������ҵ������˵�������㽫ע�������˾�������";
		StringReader sr = new StringReader(content);
		while(test.begin(sr)>0){
			System.out.print(test.getAvailable()+","+test.getBuffOffset()+"\t");
			System.out.println(test.getSegmentBuff());
			test.process();
		}
	}
	public char[] getSegmentBuff(){
		return segmentBuff;
	}
	public int getAvailable(){
		return available;
	}
	public int getBuffOffset(){
		return buffOffset;
	}
	public void process(){
		this.cursor=available;
	}
	public int begin(Reader reader) throws Exception{
		int readCount = 0;
		if(this.buffOffset == 0){
    		readCount = reader.read(segmentBuff);
    	}else{
			int offset = this.available - this.cursor;//�ѣ�δ��������ַ���segmentBuff�е�ƫ����
			if(offset > 0){
    			System.arraycopy(this.segmentBuff , this.cursor , this.segmentBuff , 0 , offset);
    			readCount = offset;
    		}
    		readCount += reader.read(this.segmentBuff , offset , BUFF_SIZE - offset);
		}
    	this.available = readCount;
		this.buffOffset=this.buffOffset+readCount;
    	return readCount;
	}
}