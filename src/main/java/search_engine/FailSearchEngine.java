package search_engine;

import com.google.gson.JsonObject;
import exeptions.NotFoundFailedElements;
import model.FallibleWithInners;
import utils.MyOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * @return origin Optional of fallibleWithInners at position
     */
    private Optional<FallibleWithInners> findFail(FallibleWithInners fallibleWithInners) {
        List<MyOptional<? extends FallibleWithInners>> allInnerFallible = fallibleWithInners.getAllPresentInnerFallible();
        //if size equals 1 can return not failed element and it checks in getFailedElements
        if (allInnerFallible.size() > 0) {
            if (allInnerFallible.size() == 1 || (!allInnerFallible.get(0).get().isTransactionPassed() && fallibleWithInners.getNumber() != 0)) {
                return Optional.of(allInnerFallible.get(0).get());
            }
        }
        int left = 0;
        int right = allInnerFallible.size();
        int position = (left + right) / 2;
        while (position != 0 && position < allInnerFallible.size()) {
            if (!allInnerFallible.get(position).get().isTransactionPassed()) {
                if (allInnerFallible.get(position - 1).get().isTransactionPassed()) {
                    return Optional.of(allInnerFallible.get(position).get());
                } else {
                    right = position;
                }
            } else {
                left = position + 1;
            }
            position = (left + right) / 2;
            if (position == 0 && !allInnerFallible.get(position).get().isTransactionPassed()) {
                return Optional.of(allInnerFallible.get(position).get());
            }
        }
        return Optional.empty();
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
            Optional<FallibleWithInners> fail = findFail(fallibleWithInners);
            if (fail.isPresent()) {
                fallibleWithInners = fail.get();
                if (!fallibleWithInners.isTransactionPassed()) {
                    fallibleWithInnersList.add(fallibleWithInners);
                }
            } else fallibleWithInners = null;
        }
        return fallibleWithInnersList;
    }


    /**
     * make result according to search data
     *
     * @param fallibleWithInners element that implement {@link FallibleWithInners}
     * @return result of search in json
     * @throws NotFoundFailedElements if result list is empty
     */
    public JsonObject findFailedElements(FallibleWithInners fallibleWithInners) throws NotFoundFailedElements {
        List<FallibleWithInners> failedElements = getFailedElements(fallibleWithInners);
        JsonObject jsonObject = new JsonObject();
        if (failedElements.size() > 0) {
            failedElements.forEach(failElement -> jsonObject.addProperty("Failed " + failElement.getClass().getSimpleName(), failElement.getNumber()));
        } else {
            throw new NotFoundFailedElements("No failed elements found");
        }
        return jsonObject;
    }

}


