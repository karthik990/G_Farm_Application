package com.mobiroller.util;

import android.content.Context;
import android.content.Intent;
import com.mobiroller.activities.menu.ListMenu;
import com.mobiroller.activities.menu.MainActivity;
import com.mobiroller.activities.menu.SlidingMenu;
import com.mobiroller.activities.menu.SlidingPanelActivity;
import com.mobiroller.activities.menu.aveNavigationActivity;
import com.mobiroller.models.NavigationModel;

public class MenuUtil {
    public static Intent getMenuIntentFromMenuType(NavigationModel navigationModel, Context context) {
        if (navigationModel.menuType == 1) {
            return new Intent(context, MainActivity.class);
        }
        if (navigationModel.menuType == 2) {
            return new Intent(context, aveNavigationActivity.class);
        }
        if (navigationModel.menuType == 3) {
            if (navigationModel.subMenuType == 1) {
                return new Intent(context, SlidingMenu.class);
            }
            if (navigationModel.subMenuType != 2) {
                return new Intent(context, SlidingMenu.class);
            }
            Intent intent = new Intent(context, SlidingMenu.class);
            intent.putExtra(SlidingMenu.SCALED_MENU, true);
            return intent;
        } else if (navigationModel.menuType == 4) {
            return new Intent(context, ListMenu.class);
        } else {
            if (navigationModel.menuType == 5) {
                return new Intent(context, SlidingPanelActivity.class);
            }
            return new Intent(context, SlidingMenu.class);
        }
    }

    public static NavigationModel setMenuTypeFromType(NavigationModel navigationModel) {
        if (navigationModel.menuType == 0 && navigationModel.subMenuType == 0) {
            if (navigationModel.getType() == 0) {
                navigationModel.menuType = 1;
            } else if (navigationModel.getType() == 1) {
                navigationModel.menuType = 2;
            } else if (navigationModel.getType() == 2 && navigationModel.getNumberOfRows() == 6 && navigationModel.getNumberOfColumns() == 0) {
                navigationModel.menuType = 3;
                navigationModel.subMenuType = 2;
            } else if (navigationModel.getType() == 2) {
                navigationModel.menuType = 3;
                navigationModel.subMenuType = 1;
            } else if (navigationModel.getType() == 3) {
                navigationModel.menuType = 4;
            } else {
                navigationModel.menuType = 3;
                navigationModel.subMenuType = 2;
            }
        }
        return navigationModel;
    }
}
