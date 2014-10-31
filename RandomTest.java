public class RandomTest{

	public static void main(String []args){
		for(int i=0;i<10;i++){
			System.out.println(Math.random());//0.0-10.0 double
		}
		System.out.println("*********************************");
		for(int i=0;i<10;i++){
			System.out.print(Math.round(Math.random()*10)+",");
			//0-10 int	分布不均匀，0.0 - 0.499999将四舍五入为0，而0.5至1.499999将四舍五入为1
		}
		System.out.println("\n*********************************");
		for(int i=0;i<10;i++){
			System.out.print(Math.floor(Math.random() * 11)+",");//分布均匀
		}

		System.out.println("\n*********************************");
		//以上都使用了Random来实现
		for(int i=0;i<10;i++){
			System.out.print(new java.util.Random().nextInt(10)+",");
		}
		System.out.println("\n*********************************");
		for(int i=0;i<10;i++){
			System.out.print(java.util.concurrent.ThreadLocalRandom.current().nextInt(10)+",");//jdk7
		}
		//Math.abs(rnd.nextInt())%n不要使用这个
	}
}
