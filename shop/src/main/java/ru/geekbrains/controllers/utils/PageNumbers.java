package ru.geekbrains.controllers.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageNumbers {
    public static List<Integer> get(int lastPageNumber, int currentPageNumber, int maxNeighbors) {
        List<Integer> numbers = new ArrayList<>();
        int leftEdgePage = currentPageNumber - maxNeighbors / 2;
        int rightEdgePage = currentPageNumber + maxNeighbors / 2;
        if (leftEdgePage < 0) {
            leftEdgePage = 0;
            rightEdgePage = leftEdgePage + maxNeighbors;
            if (rightEdgePage > lastPageNumber) rightEdgePage = lastPageNumber;
        } else if (rightEdgePage > lastPageNumber) {
            rightEdgePage = lastPageNumber;
            leftEdgePage = rightEdgePage - maxNeighbors;
            if (leftEdgePage < 0) leftEdgePage = 0;
        }
        if (leftEdgePage > 0) numbers.add(1);
        if (leftEdgePage > 1) numbers.add(-1);
        for (int i = leftEdgePage; i <= rightEdgePage; i++) {
            numbers.add(i + 1);
        }
        if (rightEdgePage < lastPageNumber - 1) numbers.add(-1);
        if (rightEdgePage < lastPageNumber) numbers.add(lastPageNumber + 1);
        return numbers;
    }
}
