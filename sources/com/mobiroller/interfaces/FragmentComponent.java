package com.mobiroller.interfaces;

import androidx.fragment.app.Fragment;
import com.mobiroller.AppComponent;
import com.mobiroller.fragments.BaseModuleFragment;
import com.mobiroller.fragments.UserLoginFragment;
import com.mobiroller.fragments.chat.ChatMessageListFragment;
import com.mobiroller.fragments.chat.ChatUserListFragment;
import com.mobiroller.fragments.preview.NotSupportedFragment;
import com.mobiroller.fragments.user.UserAddressFragment;
import com.mobiroller.fragments.youtubeadvanced.YoutubePlaylistFragment;
import com.mobiroller.fragments.youtubeadvanced.YoutubeVideosFragment;
import com.mobiroller.module.FragmentModule;
import com.mobiroller.scopes.PerFragment;
import dagger.Component;

@PerFragment
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    Fragment inject(Fragment fragment);

    void inject(BaseModuleFragment baseModuleFragment);

    void inject(UserLoginFragment userLoginFragment);

    void inject(ChatMessageListFragment chatMessageListFragment);

    void inject(ChatUserListFragment chatUserListFragment);

    void inject(NotSupportedFragment notSupportedFragment);

    void inject(UserAddressFragment userAddressFragment);

    void inject(YoutubePlaylistFragment youtubePlaylistFragment);

    void inject(YoutubeVideosFragment youtubeVideosFragment);
}
