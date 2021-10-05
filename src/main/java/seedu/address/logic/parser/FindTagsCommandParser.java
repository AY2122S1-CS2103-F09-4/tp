package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonTagsContainsTagsPredicate;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindTagsCommandParser implements Parser<FindTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
        }

        List<Tag> tagKeywords = Arrays.stream(trimmedArgs.split("\\s+")).
                map(Tag::new).collect(Collectors.toList());

        return new FindTagsCommand(new PersonTagsContainsTagsPredicate(tagKeywords));
    }

}
