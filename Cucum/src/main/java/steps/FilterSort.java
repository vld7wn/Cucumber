package steps;

public enum FilterSort {
    ПО_УМОЛЧАНИЮ("По умолчанию"),
    ДЕШЕВЛЕ("Дешевле"),
    ДОРОЖЕ("Дороже"),
    ПО_ДАТЕ("По дате");

    private final String value;


    public String getValue() {
        return value;
    }

    FilterSort(String value) {
        this.value = value;
    }
}
