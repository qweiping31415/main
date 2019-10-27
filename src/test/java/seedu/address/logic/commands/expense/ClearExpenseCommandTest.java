package seedu.address.logic.commands.expense;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.MooLah;
import seedu.address.model.UserPrefs;

public class ClearExpenseCommandTest {

    @Test
    public void run_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.addToHistory();

        assertCommandSuccess(new ClearExpenseCommand(), model, ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        Model expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.addToHistory();
        expectedModel.setMooLah(new MooLah());

        assertCommandSuccess(new ClearExpenseCommand(), model, ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
