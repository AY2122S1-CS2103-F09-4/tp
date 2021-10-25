package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports the current view as a mailing list in the csv file format.
 */
public class MailingListCommand extends Command {

    public static final String COMMAND_WORD = "mailingList";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current view as a mailing list. "
            + "Parameters: "
            + "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_TAG + "]"
            + "[" + PREFIX_BIRTHDAY + "] \n"
            + "Default export is Phone numbers and Email addresses.\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_PHONE + " "
            + PREFIX_EMAIL;

    public static final String MESSAGE_EMPTY_PERSON_LIST = "Trying to export an empty mailing list does not make sense";

    public static final String MESSAGE_SUCCESS = "Exporting current view as a CSV file";

    private final Set<Prefix> prefixToWrite;

    /**
     * @param prefixToWrite Set of Prefixes to write into the csv file
     */
    public MailingListCommand(Set<Prefix> prefixToWrite) {
        this.prefixToWrite = Collections.unmodifiableSet(prefixToWrite);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = List.copyOf(model.getFilteredPersonList());
        if (personList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_PERSON_LIST);
        }

        return new CommandResultExport(MESSAGE_SUCCESS, personList, prefixToWrite);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MailingListCommand)) {
            return false;
        }

        // state check
        MailingListCommand e = (MailingListCommand) other;
        return prefixToWrite.equals(e.prefixToWrite);
    }
}
