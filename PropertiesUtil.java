import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static final String url=PropertiesUtil.class.getResource("").getPath().replaceAll("%20", " ");;
	private static final String path = url.substring(0, url.indexOf("WEB-INF"))+ "WEB-INF/config/base.properties";
	
	public static void main(String[] args) {
		System.out.println(getProperties("DEVELOPER_ID"));
		System.out.println(getProperties("SENIORPERMS"));
		System.out.println(getProperties("SALERPERMS"));
		System.out.println(getProperties("DEVELOPERPERMS"));
		System.out.println(getProperties("ORIGINS"));
		System.out.println(getProperties("PRODUCTTYPES"));
	}
	
	public static String getProperties(String key) {
		try {
			Properties config = new Properties();
			config.load(new FileInputStream(path));
			return config.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
