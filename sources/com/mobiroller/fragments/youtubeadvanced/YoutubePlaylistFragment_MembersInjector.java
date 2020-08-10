package com.mobiroller.fragments.youtubeadvanced;

import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class YoutubePlaylistFragment_MembersInjector implements MembersInjector<YoutubePlaylistFragment> {
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public YoutubePlaylistFragment_MembersInjector(Provider<SharedPrefHelper> provider) {
        this.sharedPrefHelperProvider = provider;
    }

    public static MembersInjector<YoutubePlaylistFragment> create(Provider<SharedPrefHelper> provider) {
        return new YoutubePlaylistFragment_MembersInjector(provider);
    }

    public void injectMembers(YoutubePlaylistFragment youtubePlaylistFragment) {
        injectSharedPrefHelper(youtubePlaylistFragment, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectSharedPrefHelper(YoutubePlaylistFragment youtubePlaylistFragment, SharedPrefHelper sharedPrefHelper) {
        youtubePlaylistFragment.sharedPrefHelper = sharedPrefHelper;
    }
}
