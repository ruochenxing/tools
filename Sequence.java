import java.util.UUID;

import org.safehaus.uuid.UUIDGenerator;

/**
 * jug-2.0.0-asl.jar
 */

public class Sequence {
	private static final int base = 100000;

	private static long millis = 0;

	private static long counter = 0;

	private static long old = 0;

	public final static int MAX_TOPIC_NUMBER = 1000000;

	public final static int MAX_TOPICCHILD_NUMBER = 1000;

	public final static int MAX_TITLE_COUNT = 10000;

	public final static int MAX_SHORTMESSAGE_NUMBER = 9999;

	private static Object obj = new Object();

	/**
	 * Get the base Sequence.
	 * 
	 * @return the Sequence
	 * @throws SequenceException
	 */
	public static synchronized String getSequence() throws SequenceException {
		UUIDGenerator gen = UUIDGenerator.getInstance();
		org.safehaus.uuid.UUID uuid = gen.generateTimeBasedUUID();

		String uuidStr = uuid.toString();
		String[] uuidParts = uuidStr.split("-");
		StringBuffer builder = new StringBuffer();
		builder.append(uuidParts[2]);
		builder.append("-");
		builder.append(uuidParts[1]);
		builder.append("-");
		builder.append(uuidParts[0]);
		builder.append("-");
		builder.append(uuidParts[3]);
		builder.append("-");
		builder.append(uuidParts[4]);

		return builder.toString();
	}

	public static synchronized String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static synchronized String getTimeSequence() throws SequenceException {
		long result = System.currentTimeMillis();
		if (result == millis) {
			old++;
			if (old >= base)
				throw new SequenceException("It had exceed the maxium sequence in this moment.");
			result = millis * base + old;
		} else {
			millis = result;
			result *= base;
			old = 0;
		}
		return result + "";
	}


	public static synchronized long getSequenceTimes() {
		try {
			long rtn = System.currentTimeMillis();
			while (rtn == counter) {
				obj.wait(2);
				rtn = System.currentTimeMillis();
			}
			counter = rtn;
			return rtn;
		} catch (Exception ie) {
			return System.currentTimeMillis();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Sequence.getSequence());
		System.out.println(Sequence.getSequenceTimes());
		System.out.println(Sequence.getTimeSequence());
		System.out.println(Sequence.getUUID());
	}
}
class SequenceException extends Exception {
	
	private static final long serialVersionUID = 5962606139762737987L;

  /**
    * @param message The error nessage.
    */
    public SequenceException(String message) {
        super(message);
    }
}
