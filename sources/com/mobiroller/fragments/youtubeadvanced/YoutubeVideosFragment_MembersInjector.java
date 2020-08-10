package com.mobiroller.fragments.youtubeadvanced;

import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class YoutubeVideosFragment_MembersInjector implements MembersInjector<YoutubeVideosFragment> {
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public YoutubeVideosFragment_MembersInjector(Provider<SharedPrefHelper> provider) {
        this.sharedPrefHelperProvider = provider;
    }

    public static MembersInjector<YoutubeVideosFragment> create(Provider<SharedPrefHelper> provider) {
        return new YoutubeVideosFragment_MembersInjector(provider);
    }

    public void injectMembers(YoutubeVideosFragment youtubeVideosFragment) {
        injectSharedPrefHelper(youtubeVideosFragment, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectSharedPrefHelper(YoutubeVideosFragment youtubeVideosFragment, SharedPrefHelper sharedPrefHelper) {
        youtubeVideosFragment.sharedPrefHelper = sharedPrefHelper;
    }
}
