package com.lifemanager.logging;

import java.util.HashMap;
import java.util.Map;

final class MessageFormatter {

	static final char DELIM_START = 123;
	static final char DELIM_STOP = 125;
	static final String DELIM_STR = "{}";
	private static final char ESCAPE_CHAR = 92;

	MessageFormatter() {
	}

	public static final FormattingTuple format(String messagePattern, Object arg) {
		return arrayFormat(messagePattern, new Object[] { arg });
	}

	public static final FormattingTuple format(String messagePattern,
			Object arg1, Object arg2) {
		return arrayFormat(messagePattern, new Object[] { arg1, arg2 });
	}

	static final Throwable getThrowableCandidate(Object argArray[]) {
		if (argArray == null || argArray.length == 0)
			return null;
		Object lastEntry = argArray[argArray.length - 1];
		if (lastEntry instanceof Throwable)
			return (Throwable) lastEntry;
		else
			return null;
	}

	public static final FormattingTuple arrayFormat(String messagePattern,
			Object argArray[]) {
		Throwable throwableCandidate = getThrowableCandidate(argArray);
		if (messagePattern == null)
			return new FormattingTuple(null, argArray, throwableCandidate);
		if (argArray == null)
			return new FormattingTuple(messagePattern);
		int i = 0;
		StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);
		int L;
		for (L = 0; L < argArray.length; L++) {
			int j = messagePattern.indexOf("{}", i);
			if (j == -1)
				if (i == 0) {
					return new FormattingTuple(messagePattern, argArray,
							throwableCandidate);
				} else {
					sbuf.append(messagePattern.substring(i,
							messagePattern.length()));
					return new FormattingTuple(sbuf.toString(), argArray,
							throwableCandidate);
				}
			if (isEscapedDelimeter(messagePattern, j)) {
				if (!isDoubleEscaped(messagePattern, j)) {
					L--;
					sbuf.append(messagePattern.substring(i, j - 1));
					sbuf.append('{');
					i = j + 1;
				} else {
					sbuf.append(messagePattern.substring(i, j - 1));
					deeplyAppendParameter(sbuf, argArray[L], new HashMap());
					i = j + 2;
				}
			} else {
				sbuf.append(messagePattern.substring(i, j));
				deeplyAppendParameter(sbuf, argArray[L], new HashMap());
				i = j + 2;
			}
		}

		sbuf.append(messagePattern.substring(i, messagePattern.length()));
		if (L < argArray.length - 1)
			return new FormattingTuple(sbuf.toString(), argArray,
					throwableCandidate);
		else
			return new FormattingTuple(sbuf.toString(), argArray, null);
	}

	static final boolean isEscapedDelimeter(String messagePattern,
			int delimeterStartIndex) {
		if (delimeterStartIndex == 0)
			return false;
		char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
		return potentialEscape == '\\';
	}

	static final boolean isDoubleEscaped(String messagePattern,
			int delimeterStartIndex) {
		return delimeterStartIndex >= 2
				&& messagePattern.charAt(delimeterStartIndex - 2) == '\\';
	}

	private static void deeplyAppendParameter(StringBuffer sbuf, Object o,
			Map seenMap) {
		if (o == null) {
			sbuf.append("null");
			return;
		}
		if (!o.getClass().isArray())
			safeObjectAppend(sbuf, o);
		else if (o instanceof boolean[])
			booleanArrayAppend(sbuf, (boolean[]) o);
		else if (o instanceof byte[])
			byteArrayAppend(sbuf, (byte[]) o);
		else if (o instanceof char[])
			charArrayAppend(sbuf, (char[]) o);
		else if (o instanceof short[])
			shortArrayAppend(sbuf, (short[]) o);
		else if (o instanceof int[])
			intArrayAppend(sbuf, (int[]) o);
		else if (o instanceof long[])
			longArrayAppend(sbuf, (long[]) o);
		else if (o instanceof float[])
			floatArrayAppend(sbuf, (float[]) o);
		else if (o instanceof double[])
			doubleArrayAppend(sbuf, (double[]) o);
		else
			objectArrayAppend(sbuf, (Object[]) o, seenMap);
	}

	private static void safeObjectAppend(StringBuffer sbuf, Object o) {
		try {
			String oAsString = o.toString();
			sbuf.append(oAsString);
		} catch (Throwable t) {
			System.err
					.println((new StringBuilder(
							"SLF4J: Failed toString() invocation on an object of type ["))
							.append(o.getClass().getName()).append("]")
							.toString());
			t.printStackTrace();
			sbuf.append("[FAILED toString()]");
		}
	}

	private static void objectArrayAppend(StringBuffer sbuf, Object a[],
			Map seenMap) {
		sbuf.append('[');
		if (!seenMap.containsKey(((Object) (a)))) {
			seenMap.put(((Object) (a)), null);
			int len = a.length;
			for (int i = 0; i < len; i++) {
				deeplyAppendParameter(sbuf, a[i], seenMap);
				if (i != len - 1)
					sbuf.append(", ");
			}

			seenMap.remove(((Object) (a)));
		} else {
			sbuf.append("...");
		}
		sbuf.append(']');
	}

	private static void booleanArrayAppend(StringBuffer sbuf, boolean a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void byteArrayAppend(StringBuffer sbuf, byte a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void charArrayAppend(StringBuffer sbuf, char a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void shortArrayAppend(StringBuffer sbuf, short a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void intArrayAppend(StringBuffer sbuf, int a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void longArrayAppend(StringBuffer sbuf, long a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void floatArrayAppend(StringBuffer sbuf, float a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	private static void doubleArrayAppend(StringBuffer sbuf, double a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}
}