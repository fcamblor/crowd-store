package com.crowdstore.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author fcamblor
 */
public class Lists {
    public static class Diff<T> {
        public List<T> elementsToAdd;
        public List<T> elementsToRemove;
        public List<T> intersectionElements;
    }

    public static <T> Diff<T> createDiff(Collection<T> existingElements, Collection<T> newElements) {
        return createDiff(new ArrayList(existingElements), new ArrayList(newElements));
    }

    public static <T> Diff<T> createDiff(List<T> existingElements, Collection<T> newElements) {
        return createDiff(existingElements, new ArrayList(newElements));
    }

    public static <T> Diff<T> createDiff(Collection<T> existingElements, List<T> newElements) {
        return createDiff(new ArrayList(existingElements), newElements);
    }

    public static <T> Diff<T> createDiff(List<T> existingElements, List<T> newElements) {
        if (existingElements == null) {
            existingElements = Collections.EMPTY_LIST;
        }
        if (newElements == null) {
            newElements = Collections.EMPTY_LIST;
        }

        Diff<T> diff = new Diff<T>();

        diff.elementsToRemove = new ArrayList<T>(existingElements);
        diff.elementsToRemove.removeAll(newElements);
        diff.elementsToAdd = new ArrayList<T>(newElements);
        diff.elementsToAdd.removeAll(existingElements);
        diff.intersectionElements = new ArrayList<T>(existingElements);
        diff.intersectionElements.removeAll(diff.elementsToRemove);

        return diff;
    }
}
