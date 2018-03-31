package com.adja.apps.mohamednagy.bakingapp;

import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.GradientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;

public class MainActivity extends AppCompatActivity {

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String GRADIENT_FRAGMENT_TAG = "gradient-fg";

    private NavigationBottomSystem mNavigationBottomSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mNavigationBottomSystem = new NavigationBottomSystem(getSupportFragmentManager(), R.id.fragment);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);
        addFragmentsToNavigationSys();

//        final Media.Builder mediaBuilder = new Media.Builder(this)
//                .mediaView(activityMainBinding.simpleExoPlayer)
//                .videoLink("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4")
//                .audioFocusSystem(new AudioFocusSystem(this))
//                .mediaStateListener(new Media.OnMediaStateChanged() {
//                    @Override
//                    public void onStateChanged(Media.State state, boolean isPlaying) {
//                        if(!isPlaying && state == Media.State.READY)
//                            Toast.makeText(MainActivity.this, "pause", Toast.LENGTH_SHORT).show();
//
//                        switch (state){
//                            case READY:
//                                Toast.makeText(MainActivity.this, "ready", Toast.LENGTH_SHORT).show();
//                            break;
//                            case ENDED:
//                            Toast.makeText(MainActivity.this, "ended", Toast.LENGTH_SHORT).show();
//                            break;
//                        }
//                    }
//                });
//
//        media = mediaBuilder.build();
//        activityMainBinding.play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                media.pause();
//            }
//        });
//        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return true;
//            }
//        });
        Step step = new Step(
                        0,
                        "Melt butter and bittersweet chocolate.",
                        "2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4",
                        "");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.Step.STEP_DESCRIPTION_COLUMN, step.getDescription());
        contentValues.put(DbContent.Step.STEP_SHORT_DESCRIPTION_COLUMN, step.getShortDescription());
        contentValues.put(DbContent.Step.STEP_VIDEO_URL_COLUMN, step.getVideoLink());
        contentValues.put(DbContent.Step.STEP_THUMBNAIL_COLUMN, step.getThumbnailURL());
        contentValues.put(DbContent.Step.STEP_RECIPE_ID_COLUMN, 1);

        getContentResolver().insert(UriController.getStepTableUri(), contentValues);

    }

    private void addFragmentsToNavigationSys(){
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        StepFragment stepFragment             = new StepFragment();
        GradientFragment gradientFragment     = new GradientFragment();

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.gradient_nav;

        final NavigationBottomSystem.FragmentNav RECIPE_FRAGMENT_NAV =
                new NavigationBottomSystem.FragmentNav(HOME_NAV, recipeListFragment, RECIPE_FRAGMENT_TAG);
        final NavigationBottomSystem.FragmentNav STEP_FRAGMENT_NAV =
                new NavigationBottomSystem.FragmentNav(STEP_NAV, stepFragment, STEP_FRAGMENT_TAG);
        final NavigationBottomSystem.FragmentNav GRADIENT_FRAGMENT_NAV = new
                NavigationBottomSystem.FragmentNav(GRADIENT_NV, gradientFragment, GRADIENT_FRAGMENT_TAG);

        mNavigationBottomSystem.put(RECIPE_FRAGMENT_NAV);
        mNavigationBottomSystem.put(STEP_FRAGMENT_NAV);
        mNavigationBottomSystem.put(GRADIENT_FRAGMENT_NAV);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case PermissionHandler.REQUEST_CODE:
//                if (grantResults.length > 0 &&
//                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                }else{
//                    // TODO: error msg
//                    Log.e("error","erro " + String.valueOf(grantResults[0]) + " " + String.valueOf(grantResults[1]));
//                }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        media.release();
//        super.onPause();
//    }
}
