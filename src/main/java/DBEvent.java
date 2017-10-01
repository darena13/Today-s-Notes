abstract public class DBEvent {
    private final Type type;

    public DBEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    enum Type {
        ADD,
        EXIT;
    }

}
