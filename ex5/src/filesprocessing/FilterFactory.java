package filesprocessing;

/**
 * this class describes the Filter Factory, that makes one Filter each time.
 */
public class FilterFactory {

    /**
     * some Magic Numbers to use in my Implementation.
     */
    private final static String GREATER = "greater_than", BETWEEN = "between", SMALLER = "smaller_than", FILE = "file",
            CONTAINS = "contains", PREFIX = "prefix", SUFFIX = "suffix", WRITABLE = "writable",
            EXECUTABLE = "executable", HIDDEN = "hidden", ALL = "all", SEPARATOR = "#", YES = "YES", NO = "NO",
            FILTER = "FILTER", ORDER = "ORDER", NOT = "NOT";

    /**
     * this method make one filter by the line that under the FILTER word.
     *
     * @param filterLine the filter line that is under the FILTER word.
     * @param numLine    the number of the current line.
     * @param section    the current section.
     * @return one type of filter.
     */
    public static Filter makingOneFilter(String filterLine, int numLine, Section section) {
        if (!filterLine.equals(FILTER) && !filterLine.equals(ORDER)) {
            String[] filterDesc = filterLine.split(SEPARATOR);
            String filterType = filterDesc[0];
            if (filterType.equals(ALL) || filterDesc.length > 1) {
                switch (filterType) {
                    case GREATER: // if the filter is greater_than.
                    {
                        double value = Double.parseDouble(filterDesc[1]);
                        if (value < 0) {
                            break;
                        }
                        if (filterDesc.length == 3) { // if there is "NOT" word.
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new GreaterThanFilter(value, notCheck);
                            } else {
                                break;
                            }
                        } else if (filterDesc.length > 3)
                            break;
                        return new GreaterThanFilter(value);
                    }

                    case BETWEEN: // if the filter is between.
                        if (filterDesc.length == 2)
                            break;
                        double first = Double.parseDouble(filterDesc[1]);
                        double second = Double.parseDouble(filterDesc[2]);
                        if (first < 0 || second < 0 || first > second) {
                            break;
                        }
                        if (filterDesc.length == 4) { // if  there is "NOT" word.
                            String notCheck = filterDesc[3];
                            if (notCheck.equals(NOT)) {
                                return new BetweenFilter(first, second, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new BetweenFilter(first, second);

                    case SMALLER: // if the filter is smaller_than.
                    {
                        double value = Double.parseDouble(filterDesc[1]);
                        if (value < 0) {
                            break;
                        }
                        if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new SmallerThanFilter(value, notCheck);
                            } else {
                                break;
                            }
                        } else if (filterDesc.length > 3)
                            break;
                        return new SmallerThanFilter(value);
                    }

                    case FILE: // if the filter is file.
                        String filename = filterDesc[1];
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new FileFilter(filename, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new FileFilter(filename);

                    case CONTAINS: // if the filter is contains.
                        String text = filterDesc[1];
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new ContainsFilter(text, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new ContainsFilter(text);

                    case PREFIX: // if the filter is prefix.
                        String prefix = filterDesc[1];
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new PrefixFilter(prefix, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new PrefixFilter(prefix);

                    case SUFFIX: // if the filter is suffix.
                        String suffix = filterDesc[1];
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new SuffixFilter(suffix, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new SuffixFilter(suffix);

                    case WRITABLE: // if the filter is writable.
                    {
                        String permission = filterDesc[1];
                        if (!permission.equals(YES) && !permission.equals(NO)) {
                            break;
                        }
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new WritableFilter(permission, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new WritableFilter(permission);

                    }
                    case EXECUTABLE: // if the filter is executable.
                    {
                        String permission = filterDesc[1];
                        if (!permission.equals(YES) && !permission.equals(NO)) {
                            break;
                        }
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new ExecutableFilter(permission, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new ExecutableFilter(permission);

                    }
                    case HIDDEN: // if the filter is hidden.
                        String hidden = filterDesc[1];
                        if (!hidden.equals(YES) && !hidden.equals(NO)) {
                            break;
                        }
                        if (filterDesc.length > 3) {
                            break;
                        } else if (filterDesc.length == 3) {
                            String notCheck = filterDesc[2];
                            if (notCheck.equals(NOT)) {
                                return new HiddenFilter(hidden, notCheck);
                            } else {
                                break;
                            }
                        }
                        return new HiddenFilter(hidden);

                    case ALL: // if the filter is all.
                        if (filterDesc.length == 2) {
                            String notCheck = filterDesc[1];
                            if (notCheck.equals(NOT)) {
                                return new AllFilter(notCheck);
                            } else {
                                break;
                            }
                        } else if (filterDesc.length > 2)
                            break;
                        return new AllFilter();
                }
            }
        }
        section.warnings.add("Warning in line " + numLine);
        return new AllFilter();

    }
}
