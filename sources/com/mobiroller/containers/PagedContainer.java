package com.mobiroller.containers;

public interface PagedContainer {
    boolean canScrollToNextPage();

    boolean canScrollToPreviousPage();

    int currentPage();

    void disableScroll();

    void enableScroll();

    void scrollLeft();

    void scrollRight();

    void scrollToPage(int i);
}
