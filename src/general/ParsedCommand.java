package general;

public class ParsedCommand {

    private CommandEnum commandEnum;
    private String key;
    private String value;

    public ParsedCommand() {
    }

    public CommandEnum getCommandEnum() {
        return commandEnum;
    }

    public void setCommandEnum(CommandEnum commandEnum) {
        this.commandEnum = commandEnum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (commandEnum == CommandEnum.EXIT) {
            return commandEnum.getValue();
        }
        if (commandEnum == CommandEnum.GET || commandEnum == CommandEnum.DEL) {
            return String.format("%s %s", commandEnum.getValue(), key);
        }
        return String.format("%s %s %s", commandEnum.getValue(), key, value);
    }
}
