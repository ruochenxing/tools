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
		System.out.println("�� :"+year);
		
		int month=calendar.get(Calendar.MONTH)+1;
		System.out.println("�� :"+month);
		
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("�� :"+day);

		int hour=calendar.get(Calendar.HOUR);
		System.out.println("ʱ :"+hour);

		int minute=calendar.get(Calendar.MINUTE);
		System.out.println("�� :"+minute);

		int second=calendar.get(Calendar.SECOND);
		System.out.println("�� :"+second);
		//����ڼ���
		int day_of_year=calendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("�����"+day_of_year+" ��");
		//����ڼ���
		int week_of_year=calendar.get(Calendar.WEEK_OF_YEAR);
		System.out.println("�����"+week_of_year+" ��");

		//���µڼ���
		int day_of_month=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("���µ�"+day_of_month+" ��");
		int date=calendar.get(Calendar.DATE);
		System.out.println("���µ�"+date+" ��");
		//���µڼ���
		int day_of_week_in_month=calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		System.out.println("���µ�"+day_of_week_in_month+" ��");
		int week_of_month=calendar.get(Calendar.WEEK_OF_MONTH);
		System.out.println("���µ�"+week_of_month+" ��");

		//���ܵڼ��죬����������Ϊ���
		int day_of_week=calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("���ܵ�"+day_of_week+" ��");

		System.out.println("==================================================");
		calendar.add(Calendar.MONTH,2);//��������
		System.out.println(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR,-2);//������
		System.out.println(df.format(calendar.getTime()));

		//���ܵ�һ��
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		System.out.println("���ܵ�һ��:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		calendar.add(Calendar.WEEK_OF_YEAR,1);
		System.out.println("�������һ��:"+df.format(calendar.getTime()));
		
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.getActualMinimum(Calendar.DATE));
		System.out.println("���µ�һ��:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DATE));
		System.out.println("�������һ��:"+df.format(calendar.getTime()));
		
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		System.out.println("�����һ��:"+df.format(calendar.getTime()));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		System.out.println("�������һ��:"+df.format(calendar.getTime()));
	}
}