package pl.gregorymartin.akademiaspringaw3.model;

public enum Colors {
    NONE("N/S"),
    BLACK("Black"),
    RED("Red"),
    GRAY("Gray"),
    BLUE("Blue"),
    GREEN("Green"),
    WHITE("White"),
    PINK("Pink"),
    ORANGE("Orange");

    private final String displayValue;

    private Colors(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
