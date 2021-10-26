package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.util.EditUtil.EditPersonDescriptor;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.DeleteMultipleCommand.INDEX_SPLITTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindOrCommand;
import seedu.address.logic.commands.FindTagCaseInsensitiveCommand;
import seedu.address.logic.commands.FindTagCaseSensitiveCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MailingListCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindOrPredicate;
import seedu.address.model.person.FindPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonTagsContainsCaseInsensitiveTagsPredicate;
import seedu.address.model.person.PersonTagsContainsCaseSensitiveTagsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_mailingListNoArg() throws Exception {
        MailingListCommand command = (MailingListCommand) parser.parseCommand(
                MailingListCommand.COMMAND_WORD + " ");

        assertEquals(new MailingListCommand(MailingListCommandParser.DEFAULT_PREFIXES), command);
    }

    @Test
    public void parseCommand_mailingListArg() throws Exception {
        MailingListCommand command = (MailingListCommand) parser.parseCommand(
                MailingListCommand.COMMAND_WORD + " " + PREFIX_PHONE.getPrefix());

        assertEquals(new MailingListCommand(Set.of(PREFIX_PHONE)), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteM() throws Exception {
        DeleteMultipleCommand command = (DeleteMultipleCommand) parser.parseCommand(
                DeleteMultipleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INDEX_SPLITTER + INDEX_EIGHTH_PERSON.getOneBased());
        assertEquals(new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_EIGHTH_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_unTag() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_HUSBAND).build();
        UntagCommand command = (UntagCommand) parser.parseCommand(UntagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new UntagCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addTag() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new TagCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findOnlyNames() throws Exception {
        List<String> nameStringList = List.of("Alan", "Bob", "Chris");
        List<String> tagStringList = List.of();
        List<Name> nameList = List.of(new Name("Alan"), new Name("Bob"), new Name("Chris"));
        List<Tag> tagList = List.of();
        FindPredicate findPredicate = new FindPredicate(nameList, tagList);
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(findPredicate), command);
    }

    @Test
    public void parseCommand_findOnlyTags() throws Exception {
        List<String> nameStringList = List.of();
        List<String> tagStringList = List.of("football", "friends");
        List<Name> nameList = List.of();
        List<Tag> tagList = List.of(new Tag("football"), new Tag("friends"));
        FindPredicate findPredicate = new FindPredicate(nameList, tagList);
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(findPredicate), command);
    }

    @Test
    public void parseCommand_findNamesAndTags() throws Exception {
        List<String> nameStringList = List.of("Alan", "Bob", "Chris");
        List<String> tagStringList = List.of("football", "friends");
        List<Name> nameList = List.of(new Name("Alan"), new Name("Bob"), new Name("Chris"));
        List<Tag> tagList = List.of(new Tag("football"), new Tag("friends"));
        FindPredicate findPredicate = new FindPredicate(nameList, tagList);
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(findPredicate), command);
    }

    @Test
    public void parseCommand_findOrOnlyNames() throws Exception {
        List<String> nameStringList = List.of("Alan", "Bob", "Chris");
        List<String> tagStringList = List.of();
        List<Name> nameList = List.of(new Name("Alan"), new Name("Bob"), new Name("Chris"));
        List<Tag> tagList = List.of();
        FindOrPredicate findOrPredicate = new FindOrPredicate(nameList, tagList);
        FindOrCommand command = (FindOrCommand) parser.parseCommand(
                FindOrCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindOrCommand(findOrPredicate), command);
    }

    @Test
    public void parseCommand_findOrOnlyTags() throws Exception {
        List<String> nameStringList = List.of();
        List<String> tagStringList = List.of("football", "friends");
        List<Name> nameList = List.of();
        List<Tag> tagList = List.of(new Tag("football"), new Tag("friends"));
        FindOrPredicate findOrPredicate = new FindOrPredicate(nameList, tagList);
        FindOrCommand command = (FindOrCommand) parser.parseCommand(
                FindOrCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindOrCommand(findOrPredicate), command);
    }

    @Test
    public void parseCommand_findOrNamesAndTags() throws Exception {
        List<String> nameStringList = List.of("Alan", "Bob", "Chris");
        List<String> tagStringList = List.of("football", "friends");
        List<Name> nameList = List.of(new Name("Alan"), new Name("Bob"), new Name("Chris"));
        List<Tag> tagList = List.of(new Tag("football"), new Tag("friends"));
        FindOrPredicate findOrPredicate = new FindOrPredicate(nameList, tagList);
        FindOrCommand command = (FindOrCommand) parser.parseCommand(
                FindOrCommand.COMMAND_WORD + " " + nameStringList.stream().map(x -> PREFIX_NAME + x)
                        .collect(Collectors.joining(" "))
                        + " " + tagStringList.stream().map(x -> PREFIX_TAG + x).collect(Collectors.joining(" ")));
        assertEquals(new FindOrCommand(findOrPredicate), command);
    }

    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        List<Tag> tagList = keywords.stream().map(Tag::new).collect(Collectors.toList());
        FindTagCaseInsensitiveCommand command = (FindTagCaseInsensitiveCommand) parser.parseCommand(
                FindTagCaseInsensitiveCommand.COMMAND_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagCaseInsensitiveCommand(new PersonTagsContainsCaseInsensitiveTagsPredicate(tagList)),
                command);
    }

    @Test
    public void parseCommand_findTagC() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        List<Tag> tagList = keywords.stream().map(Tag::new).collect(Collectors.toList());
        FindTagCaseSensitiveCommand command = (FindTagCaseSensitiveCommand) parser.parseCommand(
                FindTagCaseSensitiveCommand.COMMAND_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagCaseSensitiveCommand(new PersonTagsContainsCaseSensitiveTagsPredicate(tagList)),
                command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
