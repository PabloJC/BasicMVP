package com.pabji.basicmvp.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pabji.basicmvp.R;
import com.pabji.basicmvp.domain.components.DaggerMainActivityComponent;
import com.pabji.basicmvp.domain.components.MainActivityComponent;
import com.pabji.basicmvp.domain.scopes.HasComponent;
import com.pabji.basicmvp.domain.modules.MainActivityModule;
import com.pabji.basicmvp.presentation.activities.base.BaseMVPActivity;
import com.pabji.basicmvp.presentation.mvp.models.Recipe;
import com.pabji.basicmvp.presentation.mvp.presenters.MainActivityPresenter;
import com.pabji.basicmvp.presentation.mvp.views.MainActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseMVPActivity<MainActivityPresenter,MainActivityView> implements MainActivityView, HasComponent<MainActivityComponent> {

    private MainActivityComponent component;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    private Unbinder unbind;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeInjector();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
        presenter.init();
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return mainActivityPresenter;
    }

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    private void initializeInjector() {
        this.component = DaggerMainActivityComponent.builder()
                .myApplicationComponent(getInjector())
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    @Override
    public void showRecipeList(List<Recipe> recipes) {
        for(Recipe recipe: recipes){
            Log.d("Recipe",recipe.name);
        }
    }
}