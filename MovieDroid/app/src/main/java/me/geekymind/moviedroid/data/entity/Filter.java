package me.geekymind.moviedroid.data.entity;

import android.support.annotation.StringDef;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
@StringDef({ Filter.POPULAR, Filter.TOP_RATED })
public @interface Filter {
  String POPULAR = "popular";
  String TOP_RATED = "top_rated";
}


