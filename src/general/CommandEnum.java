package general;

public enum CommandEnum {
    SET("set"),
    GET("get"),
    DEL("del"),
    EXIT("exit");

    private final String value;

    CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CommandEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "set":
                return SET;
            case "get":
                return GET;
            case "del":
                return DEL;
            case "exit":
                return EXIT;
            default:
                return null;
        }
    }
}
