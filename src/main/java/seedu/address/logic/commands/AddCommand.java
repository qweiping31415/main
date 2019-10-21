package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Adds a expense to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the address book. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Chicken Rice "
            + PREFIX_PRICE + "3.50 "
            + PREFIX_CATEGORY + "Food";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the address book";

    private final Expense toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
