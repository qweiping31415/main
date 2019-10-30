package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.ui.expense.ExpenseListPanel;

/**
 * Deletes a expense identified using it's displayed index from the MooLah.
 */
public class DeleteExpenseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete" + CommandGroup.EXPENSE;
    public static final String COMMAND_DESCRIPTION = "Delete expense with index %1$d";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the expense identified by the index number used in the displayed expense list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted Expense: %1$s";

    private final Index targetIndex;

    public DeleteExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetIndex.getOneBased());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        Expense expenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpense(expenseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete),
                ExpenseListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExpenseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteExpenseCommand) other).targetIndex)); // state check
    }
}