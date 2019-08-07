package app;

import exeptions.NotFoundFailedElements;
import model.FallibleWithInners;
import utils.MyOptional;

import java.util.ArrayList;
import java.util.List;

/**
 * 21.07.2019 10:27
 *
 * @author Edward
 */
public class FailSearchEngine {


    /**
     * check current and previous element
     *
     * @param fallibleWithInners see fallibleWithInners {@link #getFailedElements(FallibleWithInners)}
     * @return fallibleWithInners at position if found and null if not
     */
    private FallibleWithInners findFail(FallibleWithInners fallibleWithInners) {
        List<MyOptional<? extends FallibleWithInners>> allInnerFallible = fallibleWithInners.getAllPresentInnerFallible();
        if (allInnerFallible.size() == 1 || (!allInnerFallible.get(0).get().isTransactionPassed() && fallibleWithInners.getNumber() != 0)) {
            return allInnerFallible.get(0).get();
        }
        int left = 0;
        int right = allInnerFallible.size();
        int position = (left + right) / 2;
        while (position != 0 && position < allInnerFallible.size()) {
            if (!allInnerFallible.get(position).get().isTransactionPassed()) {
                if (allInnerFallible.get(position - 1).get().isTransactionPassed()) {
                    return allInnerFallible.get(position).get();
                } else {
                    right = position ;
                }
            } else {
                left = position + 1;
            }
            position = (left + right) / 2;
            if (position == 0 && !allInnerFallible.get(position).get().isTransactionPassed()) {
                return allInnerFallible.get(position).get();
            }
        }
        return null;
    }

    /**
     * find all nested failed element until element not have children
     *
     * @param fallibleWithInners see {@link #findFailedElements(FallibleWithInners)}
     * @return list of all found failed elements
     */
    private List<FallibleWithInners> getFailedElements(FallibleWithInners fallibleWithInners) {
        List<FallibleWithInners> fallibleWithInnersList = new ArrayList<>();
        while ((fallibleWithInners != null ? fallibleWithInners.getSize() : 0) > 0) {
            fallibleWithInners = findFail(fallibleWithInners);
            if (fallibleWithInners != null) {
                fallibleWithInnersList.add(fallibleWithInners);
            }
        }

        return fallibleWithInnersList;
    }


    /**
     * make result according to search data
     *
     * @param fallibleWithInners element that implement {@link FallibleWithInners}
     * @return result of search in string
     * @throws NotFoundFailedElements if result list is empty
     */
    public String findFailedElements(FallibleWithInners fallibleWithInners) throws NotFoundFailedElements {
        List<FallibleWithInners> failedElements = getFailedElements(fallibleWithInners);
        StringBuilder result = new StringBuilder();
        if (failedElements.size() > 0) {
            failedElements.forEach(failElement -> result.append("Failed ").append(failElement.getClass().getSimpleName()).append(" with number ").append(failElement.getNumber()).append("\n"));
        } else {
            throw new NotFoundFailedElements("No failed elements found");
        }
        return result.toString();
    }

}


