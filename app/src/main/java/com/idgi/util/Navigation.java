package com.idgi.util;

import android.view.MenuItem;

import com.idgi.R;

/**
 * Created by tuyenngo on 16-04-29.
 */
public class Navigation {

    //Handle all navigation clicks here

    public static void onMenuItemSelected(MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }
}
