package com.shanghai.templateapp.base;

import android.support.annotation.NonNull;


import com.kennyc.view.MultiStateView;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import nucleus5.presenter.Presenter;



/**
 * http://msdx.github.io/AndroidSnippet/com/githang/android/snippet/app/LazyFragment.html
 */
public abstract class LazyFragment<PresenterType extends Presenter> extends BaseStatefulFragment<PresenterType> {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected boolean canLoadData(@NonNull MultiStateView multiStateView, @NonNull RecyclerMultiAdapter adapter) {
        @MultiStateView.ViewState int viewState = multiStateView.getViewState();
        if (viewState == MultiStateView.VIEW_STATE_LOADING ||
            (viewState == MultiStateView.VIEW_STATE_CONTENT && adapter.getItemCount() > 0) ||
            viewState == MultiStateView.VIEW_STATE_EMPTY ||
            viewState == MultiStateView.VIEW_STATE_ERROR) {
            return false;
        }
        return true;
    }

    protected boolean canLoadData(@NonNull MultiStateView multiStateView, @NonNull MultiAdapter adapter) {
        @MultiStateView.ViewState int viewState = multiStateView.getViewState();
        if (viewState == MultiStateView.VIEW_STATE_LOADING ||
            (viewState == MultiStateView.VIEW_STATE_CONTENT && adapter.getCount() > 0) ||
            viewState == MultiStateView.VIEW_STATE_EMPTY ||
            viewState == MultiStateView.VIEW_STATE_ERROR) {
            return false;
        }
        return true;
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}
}
