package search;


import model.FallibleWithInners;
import utils.MyOptional;

import java.util.List;

public class FailSearchEngine {

    public Result findFail(FallibleWithInners currentFallibleWithInners) {
        List<MyOptional<? extends FallibleWithInners>> allInnerFallible = currentFallibleWithInners.getAllPresentInnerFallible();
//        allInnerFallible.forEach(x->x.get().getInnerFallible(1));
//        for (MyOptional<? extends FallibleWithInners> fallibleWithInners:allInnerFallible){
            int start = 0;
            int end = allInnerFallible.size();

            while (end - start > 1) {
                int current = (start + end) / 2;
                if (allInnerFallible.get(current).get().isTransactionPassed()){
//                if (currentFallibleWithInners.getInnerFallible(current).isPresent() && !((FallibleWithInners) currentFallibleWithInners.getInnerFallible(current).get()).isTransactionPassed()) {
                    end = current;
                } else {
                    start = current;
                }
            }
//        }

//        int start = 0;
//        int end = currentFallibleWithInners.getSize();
//
//        while (end - start > 1) {
//            int current = (start + end) / 2;
//            if (currentFallibleWithInners.getInnerFallible(current).isPresent() && !((FallibleWithInners) currentFallibleWithInners.getInnerFallible(current).get()).isTransactionPassed()) {
//                end = current;
//            } else {
//                start = current;
//            }
//        }
        System.out.println("fu"+start + " " +end);
        return getResult(allInnerFallible, start, end);
    }

    private Result getResult(List<MyOptional<? extends FallibleWithInners>> allInnerFallible, int start, int end) {

        FallibleWithInners innerFailed = allInnerFallible.get(start).get();

        System.out.println(innerFailed.getNumber());


        if (innerFailed.isTransactionPassed()) {
            innerFailed = allInnerFallible.get(end).get();
        }
        System.out.println("saaaaaaaaaaaaaaa");
        System.out.println(innerFailed.getNumber());
        return getResult(innerFailed);
    }

//    private Result getResult(FallibleWithInners currentFallibleWithInners, int start, int end) {
//        System.out.println(start);
//        System.out.println(end);
//        if (currentFallibleWithInners.getInnerFallible(start).isPresent() && currentFallibleWithInners.getInnerFallible(end).isPresent()) {
//            FallibleWithInners innerFailed = (FallibleWithInners) currentFallibleWithInners.getInnerFallible(start).get();
//            if (currentFallibleWithInners.getInnerFallible(end).isPresent()) {
//                innerFailed = (FallibleWithInners) currentFallibleWithInners.getInnerFallible(end).get();
//            }
//            return getResult(innerFailed);
//        } else if (currentFallibleWithInners.getInnerFallible(start).isPresent() && !(currentFallibleWithInners.getInnerFallible(end).isPresent())) {
//            FallibleWithInners innerFailed = (FallibleWithInners) currentFallibleWithInners.getInnerFallible(start).get();
//            int previousNotEmpty = findPreviousNotEmpty(currentFallibleWithInners, start);
//            if (previousNotEmpty == -1) {
//                return getResult(innerFailed);
//
//            } else {
//                if (!((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get()).isTransactionPassed()) {
//                    return getResult((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get());
//                }
//                else {
//                    return getResult(innerFailed);
//                }
//            }
////            if (previousNotEmpty != -1 && !((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get()).isTransactionPassed()) {
////                return getResult((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get());
////            } else if (previousNotEmpty == -1) {
////                return getResult(innerFailed);
////            }
//        } else if (currentFallibleWithInners.getInnerFallible(end).isPresent() && !(currentFallibleWithInners.getInnerFallible(start).isPresent())) {
////            if (currentFallibleWithInners.getInnerFallible(end).isPresent()) {
//            FallibleWithInners innerFailed = (FallibleWithInners) currentFallibleWithInners.getInnerFallible(end).get();
//
//            int previousNotEmpty = findPreviousNotEmpty(currentFallibleWithInners, start);
//            if (previousNotEmpty == -1) {
//                return getResult(innerFailed);
//
//            } else {
//                if (!((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get()).isTransactionPassed()) {
//                    return getResult((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get());
//                }
//            }
////            if (previousNotEmpty != -1 && !((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get()).isTransactionPassed()) {
////                return getResult((FallibleWithInners) currentFallibleWithInners.getInnerFallible(previousNotEmpty).get());
////            } else if (previousNotEmpty == -1) {
////                return getResult(innerFailed);
////            }
////            }
//        }
//        return null;
////        return getResult(innerFailed);
//    }

    private int findPreviousNotEmpty(FallibleWithInners fallibleWithInners, int position) {
        do {
            position = position - 1;
        } while (fallibleWithInners.getInnerFallible(position).isPresent() && position == -1);
        return position;
    }

    private Result getResult(FallibleWithInners failed) {
        if (failed.getSize() > 0) {
            Result result = findFail(failed);
            result.setFailedServer(failed.getNumber());
            return result;
        } else {
            return new Result(failed.getNumber());
        }
    }

}
