package search_engine;//package search_engine;
//
//import exeptions.NotFoundFailedElements;
//import model.FallibleWithInners;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 21.07.2019 10:27
// *
// * @author Edward
// */
//public class FailSearchEngine {
//
//
//
//    private FallibleWithInners findNearestNotEmpty(FallibleWithInners fallibleWithInners, int position){
//        while (fallibleWithInners.getInnerFallible(position).isPresent()){
//
//        }
//    }
//    private int findPreviousNotEmpty(FallibleWithInners fallibleWithInners, int position){
//        do {
//            position = position-1;
//        } while (fallibleWithInners.getInnerFallible(position).isPresent() || position==-1);
//        return position;
//    }
//    /**
//     * check current and previous element
//     *
//     * @param fallibleWithInners see fallibleWithInners {@link #getFailedElements(FallibleWithInners)}
//     * @return fallibleWithInners at position if found and null if not
//     */
//    private FallibleWithInners findFail(FallibleWithInners fallibleWithInners) {
//
//        FallibleWithInners firstFallibleWithInners = (FallibleWithInners) fallibleWithInners.getInnerFallible(0).get();
//        if (fallibleWithInners.getSize() == 1 || (!firstFallibleWithInners.isTransactionPassed() && fallibleWithInners.getNumber() != 0)) {
//            return firstFallibleWithInners;
//        }
//        FallibleWithInners current;
//        int left = 0;
//        int right = fallibleWithInners.getSize();
//        int position = (left + right) / 2;
//        while (position != 0 && position < fallibleWithInners.getSize()) {
////            if (fallibleWithInners.getInnerFallible(position).isPresent()){
////             current = (FallibleWithInners) fallibleWithInners.getInnerFallible(position).get();
////
////
////            } else {
////                int previousNotEmpty = findPreviousNotEmpty(fallibleWithInners, position);
////                if (previousNotEmpty==-1){
////
////                }
////            }
//            FallibleWithInners previous = (FallibleWithInners) fallibleWithInners.getInnerFallible(position - 1).get();
//            if (!current.isTransactionPassed()) {
//                if (previous.isTransactionPassed()) {
//                    return current;
//                } else {
//                    right = position;
//                }
//            } else {
//                left = position + 1;
//            }
//            position = (left + right) / 2;
//            if (position == 0 && !current.isTransactionPassed()) {
//                return current;
//            }
//        }
//        return null;
//    }
//
//
////    private FallibleWithInners findFail(FallibleWithInners fallibleWithInners) {
////        FallibleWithInners firstFallibleWithInners = (FallibleWithInners) fallibleWithInners.getInnerFallible(0).get();
////        if (fallibleWithInners.getSize() == 1 || (!((FallibleWithInners) fallibleWithInners.getInnerFallible(0).get().isTransactionPassed()) && fallibleWithInners.getNumber() != 0)) {
////            return fallibleWithInners.getInnerFallible(0).get();
////        }
////        int left = 0;
////        int right = fallibleWithInners.getSize();
////        int position = (left + right) / 2;
////        while (position != 0 && position < fallibleWithInners.getSize()) {
////            if (!fallibleWithInners.getInnerFallible(position).isTransactionPassed()) {
////                if (fallibleWithInners.getInnerFallible(position - 1).isTransactionPassed()) {
////                    return fallibleWithInners.getInnerFallible(position);
////                } else {
////                    right = position ;
////                }
////            } else {
////                left = position + 1;
////            }
////            position = (left + right) / 2;
////            if (position == 0 && !fallibleWithInners.getInnerFallible(position).isTransactionPassed()) {
////                return fallibleWithInners.getInnerFallible(position);
////            }
////        }
////        return null;
////    }
//
//    /**
//     * find all nested failed element until element not have children
//     *
//     * @param fallibleWithInners see {@link #findFailedElements(FallibleWithInners)}
//     * @return list of all found failed elements
//     */
//    private List<FallibleWithInners> getFailedElements(FallibleWithInners fallibleWithInners) {
//        List<FallibleWithInners> fallibleWithInnersList = new ArrayList<>();
//        while ((fallibleWithInners != null ? fallibleWithInners.getSize() : 0) > 0) {
//            fallibleWithInners = findFail(fallibleWithInners);
//            if (fallibleWithInners != null) {
//                fallibleWithInnersList.add(fallibleWithInners);
//            }
//        }
//
//        return fallibleWithInnersList;
//    }
//
//
//    /**
//     * make result according to search data
//     *
//     * @param fallibleWithInners element that implement {@link FallibleWithInners}
//     * @return result of search in string
//     * @throws NotFoundFailedElements if result list is empty
//     */
//    public String findFailedElements(FallibleWithInners fallibleWithInners) throws NotFoundFailedElements {
//        List<FallibleWithInners> failedElements = getFailedElements(fallibleWithInners);
//        StringBuilder result = new StringBuilder();
//        if (failedElements.size() > 0) {
//            failedElements.forEach(failElement -> result.append("Failed ").append(failElement.getClass().getSimpleName()).append(" with number ").append(failElement.getNumber()).append("\n"));
//        } else {
//            throw new NotFoundFailedElements("No failed elements found");
//        }
//        return result.toString();
//    }
//
//}
//
//
