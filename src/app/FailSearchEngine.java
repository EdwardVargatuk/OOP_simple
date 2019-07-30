package app;

import exeptions.NotFoundFailedElements;
import model.FallibleWithInners;

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
        if (fallibleWithInners.getSize() == 1 || (fallibleWithInners.getInnerFallible(0).isFailed() && fallibleWithInners.getNumber() != 0)) {
            return fallibleWithInners.getInnerFallible(0);
        }
        int left = 0;
        int right = fallibleWithInners.getSize();
        int position = (left + right) / 2;
        while (position != 0 && position < fallibleWithInners.getSize()) {
            if (fallibleWithInners.getInnerFallible(position).isFailed()) {
                if (!fallibleWithInners.getInnerFallible(position - 1).isFailed()) {
                    return fallibleWithInners.getInnerFallible(position);
                } else {
                    right = position - 1;
                }
            } else {
                left = position + 1;
            }
            position = (left + right) / 2;
            if (position == 0 && fallibleWithInners.getInnerFallible(position).isFailed()) {
                return fallibleWithInners.getInnerFallible(position);
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


