package com.paramedic.mobshaman.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.fragments.AccionesDetalleServicioFragment;
import com.paramedic.mobshaman.fragments.DetalleServicioFragment;

/**
 * Created by soporte on 23/07/2014.
 */
public class DetalleServicioPagerAdapter extends FragmentPagerAdapter {

    String[] pager_titles;

    public DetalleServicioPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        Resources resources = context.getResources();
        pager_titles = resources.getStringArray(R.array.detalle_servicios_titulos);

    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                DetalleServicioFragment dsFragment = new DetalleServicioFragment();
                return dsFragment;
            case 1:
                AccionesDetalleServicioFragment acDsFragment = new AccionesDetalleServicioFragment();
                return acDsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return pager_titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pager_titles[position];
    }
}
