import java.text.NumberFormat;
import java.text.ParsePosition;


public class StringUtil {

	/**
	 * �ж��ַ���str�Ƿ�Ϊnull��trim()Ϊ""��ֻ�пո����ݣ�
	 */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * ���ַ�ȥ��ִ��trim()������ַ�Ϊ�գ����ؿ��ַ���
     * @param str
     * @return
     */
    public static String trim(String str) {
    	if(isNullOrEmpty(str))
    		return "";
        return str.trim();
    }

    public static boolean isNumber(char chr) {
        return chr > '0' && chr < '9';
    }

    public static boolean isLetter(char chr) {
        return chr > 'A' && chr < 'Z' || chr > 'a' && chr < 'z';
    }

    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

}
