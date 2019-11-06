package seedu.moolah.logic.parser.statistics;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collections;
import java.util.List;

import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Mode;
import seedu.moolah.model.statistics.Statistics;

/**
 * Parses input arguments and creates a new StatsTrendCommand object
 */
public class StatsTrendCommandParser implements Parser<StatsTrendCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(PREFIX_MODE));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_START_DATE, PREFIX_END_DATE));


    /**
     * Parses the given {@code String} of arguments in the context of the StatsTrendCommand
     * and returns an StatsTrendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsTrendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE);

        if (!argMultimap.arePrefixesPresent(PREFIX_MODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        }

        if (argMultimap.hasRepeatedPrefixes(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Timestamp startDate = null;
        Timestamp endDate = null;

        boolean isStartPresent = argMultimap.getValue(PREFIX_START_DATE).isPresent();
        boolean isEndPresent = argMultimap.getValue(PREFIX_END_DATE).isPresent();
        Mode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());

        if (isStartPresent && isEndPresent) {
            checkStartBeforeEnd(argMultimap);
            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
            return StatsTrendCommand.createWithBothDates(startDate, endDate, mode);
        } else if (isStartPresent) {

            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            return StatsTrendCommand.createOnlyWithStartDate(startDate, mode);
        } else if (isEndPresent) {
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
            return StatsTrendCommand.createOnlyWithEndDate(endDate, mode);
        } else {
            return StatsTrendCommand.createWithNoDate(mode);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StatsTrendCommand
     * Checks that start date is before the end date of the given {@code ArgumentMultimap}
     *
     * @throws ParseException if the detected start date is after the end date
     */
    private void checkStartBeforeEnd(ArgumentMultimap argMultimap) throws ParseException {
        Timestamp startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
        Timestamp endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
        if (endDate.isBefore(startDate)) {
            throw new ParseException(Statistics.MESSAGE_CONSTRAINTS_END_DATE);
        }
    }

}



