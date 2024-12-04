package com.imdemo.protocol;

public class ResponseStatus {
    public static final byte SUCCESS = (byte) 0;
    public static final byte ERROR = (byte) 1;
    public static final byte UNAUTHORIZED = (byte) 2;
    public static final byte TIMEOUT = (byte) 3;

    public static final byte STRING_FORMAT_ERROR = (byte) 4;
    public static final byte ALREADY_REGISTERED = (byte) 5;
    public static final byte ALREADY_ONLINE = (byte) 8;
    public static final byte NONEXISTENT = (byte) 6;
    public static final byte AUTHENTICATION_FAILED = (byte) 7;

    public static final byte ALREADY_EXIST = (byte) 9;
    public static final byte ILLEGAL_VALUE = (byte) 10;
    public static final byte NO_SUCH_MEMBER = (byte) 11;
    public static final long OFFLINE_MESSAGE = (byte) 12;
    public static final byte MESSAGE = (byte) 13;
}
