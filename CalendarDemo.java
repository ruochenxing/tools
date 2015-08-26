import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
public class CalendarDemo{
	public static void main(String []args) throws Exception{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());

		int year=calendar.get(Calendar.YEAR);
		System.out.println("年 :"+year);
		
		int month=calendar.get(Calendar.MONTH)+1;
		System.out.println("月 :"+month);
		
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("日 :"+day);

		int hour=calendar.get(Calendar.HOUR);
		System.out.println("时 :"+hour);

		int minute=calendar.get(Calendar.MINUTE);
		System.out.println("分 :"+minute);

		int second=calendar.get(Calendar.SECOND);
		System.out.println("秒 :"+second);
		//本年第几天
		int day_of_year=calendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("本年第"+day_of_year+" 天");
		//本年第几周
		int week_of_year=calendar.get(Calendar.WEEK_OF_YEAR);
		System.out.println("本年第"+week_of_year+" 周");

		//本月第几天
		int day_of_month=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("本月第"+day_of_month+" 天");
		int date=calendar.get(Calendar.DATE);
		System.out.println("本月第"+date+" 天");
		//本月第几周
		int day_of_week_in_month=calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		System.out.println("本月第"+day_of_week_in_month+" 周");
		int week_of_month=calendar.get(Calendar.WEEK_OF_MONTH);
		System.out.println("本月第"+week_of_month+" 周");

		//本周第几天，以星期日作为起点
		int day_of_week=calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("本周第"+day_of_week+" 天");

		System.out.println("==================================================");
		calendar.add(Calendar.MONTH,2);//加两个月
		System.out.println(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR,-2);//减两天
		System.out.println(df.format(calendar.getTime()));

		//本周第一天
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		System.out.println("本周第一天:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		calendar.add(Calendar.WEEK_OF_YEAR,1);
		System.out.println("本周最后一天:"+df.format(calendar.getTime()));
		
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.getActualMinimum(Calendar.DATE));
		System.out.println("本月第一天:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DATE));
		System.out.println("本月最后一天:"+df.format(calendar.getTime()));
		
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		System.out.println("本年第一天:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		System.out.println("本年最后一天:"+df.format(calendar.getTime()));
	}
}