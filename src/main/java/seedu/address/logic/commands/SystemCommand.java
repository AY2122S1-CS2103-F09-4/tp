package seedu.address.logic.commands;

public class SystemCommand {

    /**
     * Executes help message when user is typing
     * @param userInput
     * @return
     */
    public static CommandResult execute(String userInput) {
        String[] parsedInput = userInput.split(" ");
        String commandWord = parsedInput[0];
        switch (commandWord) {
        case (AddCommand.COMMAND_WORD):
            return new CommandResult(AddCommand.COMMAND_EXAMPLE, false, false);
        case (DeleteCommand.COMMAND_WORD):
            return new CommandResult(DeleteCommand.COMMAND_EXAMPLE, false, false);
        case (DeleteMultipleCommand.COMMAND_WORD):
            return new CommandResult(DeleteMultipleCommand.COMMAND_EXAMPLE, false, false);
        case (EditCommand.COMMAND_WORD):
            return new CommandResult(EditCommand.COMMAND_EXAMPLE, false, false);
        case (FindAnyCommand.COMMAND_WORD):
            return new CommandResult(FindAnyCommand.COMMAND_EXAMPLE, false, false);
        case (FindCommand.COMMAND_WORD):
            return new CommandResult(FindCommand.COMMAND_EXAMPLE, false, false);
        case (FindTagCaseInsensitiveCommand.COMMAND_WORD):
            return new CommandResult(FindTagCaseInsensitiveCommand.COMMAND_EXAMPLE, false, false);
        case (FindTagCaseSensitiveCommand.COMMAND_WORD):
            return new CommandResult(FindTagCaseSensitiveCommand.COMMAND_EXAMPLE, false, false);
        /*case (PinCommand.COMMAND_WORD):
            return new CommandResult(PinCommand.COMMAND_EXAMPLE, false, false);*/
        case (TagCommand.COMMAND_WORD):
            return new CommandResult(TagCommand.COMMAND_EXAMPLE, false, false);
        /*case (UnpinCommand.COMMAND_WORD):
            return new CommandResult(UnpinCommand.COMMAND_EXAMPLE, false, false);*/
        case (UntagCommand.COMMAND_WORD):
            return new CommandResult(UntagCommand.COMMAND_EXAMPLE, false, false);
        default:
            return new CommandResult("", false, false);
        }
    }
}
