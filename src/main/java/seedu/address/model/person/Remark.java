package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remark can take any values, and it should not be blank";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param text A valid address.
     */
    public Remark(String text) {
        requireNonNull(text);
        checkArgument(true, MESSAGE_CONSTRAINTS);
        value = text;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
