package com.lifemanager.logging;

class FormattingTuple {

	public static FormattingTuple NULL = new FormattingTuple(null);
	private String message;
	private Throwable throwable;
	private Object argArray[];

	public FormattingTuple(String message) {
		this(message, null, null);
	}

	public FormattingTuple(String message, Object argArray[],
			Throwable throwable) {
		this.message = message;
		this.throwable = throwable;
		if (throwable == null)
			this.argArray = argArray;
		else
			this.argArray = trimmedCopy(argArray);
	}

	static Object[] trimmedCopy(Object argArray[]) {
		if (argArray == null || argArray.length == 0) {
			throw new IllegalStateException(
					"non-sensical empty or null argument array");
		} else {
			int trimemdLen = argArray.length - 1;
			Object trimmed[] = new Object[trimemdLen];
			System.arraycopy(((Object) (argArray)), 0, ((Object) (trimmed)), 0,
					trimemdLen);
			return trimmed;
		}
	}

	public String getMessage() {
		return message;
	}

	public Object[] getArgArray() {
		return argArray;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}