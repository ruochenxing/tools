import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
import java.net.URL;
 
public class DownloadImage {
 
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
           String imageUrl = "�ڴ˴���д��ƬURL";
           URL url = new URL(imageUrl);
           //������������
           DataInputStream dis = new DataInputStream(url.openStream());
           String newImageName="C://image/1.jpg";
            //����һ���µ��ļ�
           FileOutputStream fos = new FileOutputStream(new File(newImageName));
           byte[] buffer = new byte[1024];
           int length;
           //��ʼ�������
           while((length = dis.read(buffer))>0){
           fos.write(buffer,0,length);
           }
           dis.close();
           fos.close();     
    }
 
}