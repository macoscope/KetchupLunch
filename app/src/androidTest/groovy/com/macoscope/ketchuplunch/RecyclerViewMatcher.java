package com.macoscope.ketchuplunch;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Taken from https://github.com/dannyroa/espresso-samples and refactored
 *
 * @author Danny Roa
 */
public class RecyclerViewMatcher {

    private final int recyclerViewId;

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, ItemViewMatcher.ITEM_VIEW_ID);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
        return new ItemViewMatcher(recyclerViewId, position, targetViewId);
    }

    static class ItemViewMatcher extends TypeSafeMatcher<View> {

        public static final int ITEM_VIEW_ID = -1;
        private Resources resources;
        private View itemView;
        private final int position;
        private final int targetViewId;
        private final int recyclerViewId;


        public ItemViewMatcher(int recyclerViewId, int position, int targetViewId) {
            this.recyclerViewId = recyclerViewId;
            this.position = position;
            this.targetViewId = targetViewId;
        }

        public void describeTo(Description description) {
            String idDescription = Integer.toString(recyclerViewId);

            if (resources != null) {
                idDescription = prepareIdDescription(recyclerViewId);
            }

            description.appendText("with id: " + idDescription);
        }

        public boolean matchesSafely(View view) {
            resources = view.getResources();

            if (cacheItemView(view)) {
                //item
                return false;
            }

            if (targetViewId == ITEM_VIEW_ID) {
                return view == itemView;
            } else {
                return view == itemView.findViewById(targetViewId);
            }
        }

        private String prepareIdDescription(int id) {
            String idDescription;
            try {
                idDescription = resources.getResourceName(id);
            } catch (Resources.NotFoundException e) {
                idDescription = String.format("%s (resource name not found)", id);
            }
            return idDescription;
        }

        private boolean cacheItemView(View view) {
            if (itemView == null) {
                RecyclerView recyclerView = findRecyclerViewById(view);

                if (recyclerView == null) {
                    return true;
                }

                itemView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
            }
            return false;
        }

        private RecyclerView findRecyclerViewById(View view) {
            return (RecyclerView) view.getRootView().findViewById(recyclerViewId);
        }
    }
}