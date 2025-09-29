package ac.za.cput.domain.Enum;

public enum Province {
    WESTERN_CAPE("Western Cape"),
    EASTERN_CAPE("Eastern Cape"),
    NORTHERN_CAPE("Northern Cape"),
    FREE_STATE("Free State"),
    KWAZULU_NATAL("KwaZulu-Natal"),
    NORTH_WEST("North West"),
    GAUTENG("Gauteng"),
    MPUMALANGA("Mpumalanga"),
    LIMPOPO("Limpopo");

    private final String displayName;

    Province(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
