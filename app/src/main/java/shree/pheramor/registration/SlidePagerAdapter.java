package shree.pheramor.registration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlidePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<AtomicBoolean> flags = new ArrayList<AtomicBoolean>();
    private final ArrayList<Fragment> screens = new ArrayList<Fragment>();
    
    public SlidePagerAdapter(FragmentManager fm, Context context,  List<Fragment> screens) {
        super(fm);
        this.context = context;

        for(Fragment screen : screens)
            addScreen(screen, null);

        notifyDataSetChanged();

    }

    private void addScreen(Fragment fragment, Bundle args) {
        screens.add(Fragment.instantiate(context, fragment.getClass().getName(), args));
        flags.add(new AtomicBoolean(true));
    }


    public void disableAllPageExceptFirst() {
        setEnabled(0,true);
        for(int i=1; i<flags.size();i++){
            setEnabled(i,false);
        }
    }

    public void disableAllPageAfterCurrentPage( int position) {
        for(int i= position+1;i<getCount();i++){
            setEnabled(i, false);

        }
    }
    public void setEnabled(int position, boolean enabled) {
        AtomicBoolean flag = flags.get(position);
        if(flag.get() != enabled) {
            flag.set(enabled);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        int n = 0;
        for(AtomicBoolean flag : flags) {
            if(flag.get())
                n++;
        }
        return n;
    }

    @Override
    public Fragment getItem(int position) {
        return screens.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE; // To make notifyDataSetChanged() do something
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            super.notifyDataSetChanged();
        } catch (Exception e) {
            //catch
        }
    }

    private List<Fragment> getEnabledScreens() {
        List<Fragment> res = new ArrayList<Fragment>();
        for(int n = 0; n < screens.size(); n++) {
            if(flags.get(n).get())
                res.add(screens.get(n));
        }
        return res;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // We map the requested position to the position as per our screens array list
        Fragment fragment = getEnabledScreens().get(position);
        int internalPosition = screens.indexOf(fragment);
        return super.instantiateItem(container, internalPosition);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = getEnabledScreens().get(position);
        if(fragment instanceof TitledFragment)
            return ((TitledFragment)fragment).getTitle(context);
        return super.getPageTitle(position);
    }

    public  interface TitledFragment {
         String getTitle(Context context);
    }


}
