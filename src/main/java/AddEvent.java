public class AddEvent extends DBEvent {
    private final Note note;

    public AddEvent(Note note) {
        super(Type.ADD);
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
