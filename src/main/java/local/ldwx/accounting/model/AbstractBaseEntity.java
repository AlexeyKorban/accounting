package local.ldwx.accounting.model;

public abstract class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    protected Integer id;

    public AbstractBaseEntity() {}

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "AbstractBaseEntity{" +
                "id=" + id +
                ", class=" +getClass().getName() +
                '}';
    }
}
