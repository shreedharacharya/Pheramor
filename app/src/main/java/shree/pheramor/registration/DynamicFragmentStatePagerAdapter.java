package shree.pheramor.registration;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class DynamicFragmentStatePagerAdapter  extends FragmentPagerAdapter {

    private Context context;
    private List<AtomicBoolean> flags = new ArrayList<AtomicBoolean>();
    private final ArrayList<Fragment> screens = new ArrayList<Fragment>();

    public DynamicFragmentStatePagerAdapter(FragmentManager fm, Context context, List<Fragment> screens) {
        super(fm);
        this.context = context;

        for(Fragment screen : screens)
            addScreen(screen, null);

        notifyDataSetChanged();
    }

    private void addScreen(Fragment screen, Bundle args) {
        screens.add(Fragment.instantiate(context, screen.getClass().getName(), args));

        flags.add(new AtomicBoolean(true));
    }

    public void setEnabled(int position, boolean enabled) {
        AtomicBoolean flag = flags.get(position);
        if(flag.get() != enabled) {
            flag.set(enabled);
            notifyDataSetChanged();
        }
    }


    @Override
    public Fragment getItem(int position) {
        return screens.get(position);
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
}
