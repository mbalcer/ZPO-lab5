package table;

public class TableFields {
    private int no;
    private String field;
    private Object value;

    public TableFields() {
    }

    public TableFields(int no, String field, Object value) {
        this.no = no;
        this.field = field;
        this.value = value;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
