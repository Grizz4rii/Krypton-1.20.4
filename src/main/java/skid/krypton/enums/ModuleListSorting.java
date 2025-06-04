package skid.krypton.enums;

public enum ModuleListSorting {
    a("LENGTH", 0, "Length"),
    b("ALPHABETICAL", 1, "Alphabetical"),
    c("CATEGORY", 2, "Category");

    private final String d;

    ModuleListSorting(final String name, final int ordinal, final String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return this.d;
    }
}
