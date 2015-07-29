import java.util.Calendar;
/**
 *结合XingZhengDaiMa.java使用，用于穷举身份证号码
 */
public class Crack {
 
    int[] weightNum = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    int[] index;
    int[] nums = new int[18];
    boolean isFull = false;
    int year;
    int month;
    int day;
 
    public Crack() {
        Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        ;
    }
 
    /**
     * 
     * @param s
     *            长度必须是18位,星号不能多于9位
     */
    public void setNums(String s) {
        char[] cs = s.toCharArray();
        int cout = 0;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == '*')
                cout++;
        }
        if (cout == 0) {
            isFull = true;
        } else {
            index = new int[cout];
        }
        for (int i = 0, j = 0; i < cs.length - 1; i++) {
            if (cs[i] != '*')
                nums[i] = cs[i] - '0';
            else {
                index[j++] = i;
                nums[i] = 0;
            }
        }
        switch (cs[17]) {
        case '*':
            index[index.length - 1] = 17;
            nums[17] = 0;
            break;
        case '1':
            nums[17] = 0;
            break;
        case '0':
            nums[17] = 1;
            break;
        case 'x':
        case 'X':
            nums[17] = 2;
            break;
        case '9':
            nums[17] = 3;
            break;
 
        case '8':
            nums[17] = 4;
            break;
        case '7':
            nums[17] = 5;
            break;
        case '6':
            nums[17] = 6;
            break;
        case '5':
            nums[17] = 7;
            break;
        case '4':
            nums[17] = 8;
            break;
        case '3':
            nums[17] = 9;
            break;
        case '2':
            nums[17] = 10;
            break;
        default:
            break;
        }
    }
    int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private boolean isUsual() {
        boolean b = true;
        int year = nums[6] * 1000 + nums[7] * 100 + nums[8] * 10 + nums[9];
        int month = nums[10] * 10 + nums[11];
        int day = nums[12] * 10 + nums[13];
        b = year >= 1900 && year <= this.year;
        b = b && month <= 12 && month >= 1;
        if (!b)
            return false;
        if (month == 2) {
            if (year % 400 == 0 || (year % 400 != 0 && year % 4 == 0)) {
                b = b && day >= 1 && day <= 29;
            } else {
                b = b && day >= 1 && day <= 28;
            }
        } else {
            b = b && day >= 1 && day <= monthDays[month - 1];
        }
        return b;
    }
 
    private boolean istrue() {
        int x = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            x += weightNum[i] * nums[i];
        }
        if (x % 11 == nums[17])
            return true;
        return false;
    }
 
    private boolean testArea() {
        int x = 0;
        for (int i = 0; i < 6; i++) {
            x = x * 10 + nums[i];
        }
        return XingZhengDaiMa.test(x);
    }
 
    private String getArea() {
        int x = 0;
        for (int i = 0; i < 6; i++) {
            x = x * 10 + nums[i];
        }
        return XingZhengDaiMa.getAreaByCode(x);
    }
 
    private int calEnd() {
        int x = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            x += weightNum[i] * nums[i];
        }
        return x % 11;
    }
 
    char[] ends = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
 
    public String idToString() {
        return String.format("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d%c", nums[0],
                nums[1], nums[2], nums[3], nums[4], nums[5], nums[6], nums[7],
                nums[8], nums[9], nums[10], nums[11], nums[12], nums[13],
                nums[14], nums[15], nums[16], ends[nums[17]]);
    }
 
    public void cal(CarckListener carckListener, boolean callbackArea) {
        int count = 0;
        if (isFull) {
            if (istrue()) {
                if (!callbackArea) {
                    carckListener.callBack(0, idToString(), null);
                } else {
                    carckListener.callBack(0, idToString(), getArea());
                }
            }
            return;
        }
        if (index[index.length - 1] != 17) {
            int k = 1;
            for (int i = 0; i < index.length; i++) {
                k *= 10;
            }
            for (int i = 0, j = 0; i <= k; i++) {
                j = i;
                for (int j2 = 0; j2 < index.length; j2++) {
                    nums[index[j2]] = j % 10;
                    j /= 10;
                }
                if (istrue() && isUsual() && testArea()) {
                    if (!callbackArea) {
                        carckListener.callBack(0, idToString(), null);
                    } else {
                        carckListener
                                .callBack(count++, idToString(), getArea());
                    }
                }
            }
        } else {
            int k = 1;
            for (int i = 0; i < index.length - 1; i++) {
                k *= 10;
            }
            for (int i = 0, j = 0; i <= k; i++) {
                j = i;
                for (int j2 = 0; j2 < index.length - 1; j2++) {
                    nums[index[j2]] = j % 10;
                    j /= 10;
                }
                if (isUsual() && testArea()) {
                    nums[17] = calEnd();
                    if (!callbackArea) {
                        carckListener.callBack(0, idToString(), null);
                    } else {
                        carckListener
                                .callBack(count++, idToString(), getArea());
                    }
                }
            }
        }
    }
 
    public boolean verifyNums(String s) {
        if (s.length() != 18) {
            return false;
        }
        char[] cs = s.toCharArray();
        boolean b = true;
        for (int i = 0; i < cs.length - 1 && b; i++) {
            b = (cs[i] >= '0' && cs[i] <= '9') || cs[i] == '*';
        }
        b = b
                && ((cs[17] >= '0' && cs[17] <= '9') || cs[17] == 'X' || cs[17] == 'x');
        return b;
    }
 
    /**
     * 提供一个接口,输出可能的身份证号 接口
     */
    public interface CarckListener {
        /**
         * 
         * @param i
         *            第几个,从0开始
         * @param s
         *            身份证号码
         * @return 保留,可忽略
         */
        int callBack(int i, String s, String area);
 
        /**
         * 异常处理
         * 
         * @param msg
         */
        void error(String msg);
    }
	static int count=0;
    public static void main(String[] args) throws Exception{
		java.util.List<String> results=XingZhengDaiMa.getCodeByAddr("**省","**市","**县");
		for(String s:results){
			System.out.println(s);
			Thread.sleep(3*1000);
			Crack crack = new Crack();
			crack.setNums(s+"19900000****");
			crack.cal(new CarckListener() {
				@Override
				public int callBack(int i, String s, String area) {
					if("女".equals(getSex(s))){
						System.out.print(String.format("第%d个 ", ++count));
						System.out.print(s);
						System.out.print(" "+getSex(s));
						System.out.println(" " + area);
					}
					return 0;
				}
				@Override
				public void error(String msg) {
					System.err.println(msg);
					System.exit(1);
				}
			}, true);
		}
		System.out.println("总数为:"+count+"个");
    }
	private static String getSex(String s){
		int i=Integer.valueOf(s.substring(16,17));
		if(i%2==0){
			return "女";
		}
		return "男";
	}
}
