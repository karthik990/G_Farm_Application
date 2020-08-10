package com.mancj.materialsearchbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mancj.materialsearchbar.adapter.DefaultSuggestionsAdapter;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter.OnItemViewClickListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MaterialSearchBar extends FrameLayout implements OnClickListener, AnimationListener, OnItemViewClickListener, OnFocusChangeListener, OnEditorActionListener {
    public static final int BUTTON_BACK = 3;
    public static final int BUTTON_NAVIGATION = 2;
    public static final int BUTTON_SPEECH = 1;
    public static final int VIEW_INVISIBLE = 0;
    public static final int VIEW_VISIBLE = 1;
    private SuggestionsAdapter adapter;
    private ImageView arrowIcon;
    private int arrowIconRes;
    private int arrowIconTint;
    private boolean arrowIconTintEnabled;
    private boolean borderlessRippleEnabled = false;
    private ImageView clearIcon;
    private int clearIconRes;
    private int clearIconTint;
    private boolean clearIconTintEnabled;
    private float destiny;
    private int dividerColor;
    private int highlightedTextColor;
    private int hintColor;
    private CharSequence hintText;
    private LinearLayout inputContainer;
    private boolean isSuggestionsEnabled = true;
    private int maxSuggestionCount;
    private ImageView menuIcon;
    private int menuIconRes;
    private int menuIconTint;
    private boolean menuIconTintEnabled;
    private boolean navButtonEnabled;
    private ImageView navIcon;
    private int navIconResId;
    private int navIconTint;
    private boolean navIconTintEnabled;
    private OnSearchActionListener onSearchActionListener;
    private TextView placeHolder;
    private int placeholderColor;
    private CharSequence placeholderText;
    private PopupMenu popupMenu;
    private boolean roundedSearchBarEnabled;
    private CardView searchBarCardView;
    private int searchBarColor;
    private EditText searchEdit;
    private ImageView searchIcon;
    private int searchIconRes;
    private int searchIconTint;
    private boolean searchIconTintEnabled;
    private boolean searchOpened;
    private int speechIconRes;
    private boolean speechMode;
    private View suggestionDivider;
    private boolean suggestionsVisible;
    private int textColor;
    private int textCursorColor;

    public interface OnSearchActionListener {
        void onButtonClicked(int i);

        void onSearchConfirmed(CharSequence charSequence);

        void onSearchStateChanged(boolean z);
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        /* access modifiers changed from: private */
        public String hint;
        /* access modifiers changed from: private */
        public int isSearchBarVisible;
        /* access modifiers changed from: private */
        public int maxSuggestions;
        /* access modifiers changed from: private */
        public int navIconResId;
        /* access modifiers changed from: private */
        public int searchIconRes;
        /* access modifiers changed from: private */
        public int speechMode;
        /* access modifiers changed from: private */
        public List suggestions;
        /* access modifiers changed from: private */
        public int suggestionsVisible;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isSearchBarVisible = parcel.readInt();
            this.suggestionsVisible = parcel.readInt();
            this.speechMode = parcel.readInt();
            this.navIconResId = parcel.readInt();
            this.searchIconRes = parcel.readInt();
            this.hint = parcel.readString();
            this.suggestions = parcel.readArrayList(null);
            this.maxSuggestions = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isSearchBarVisible);
            parcel.writeInt(this.suggestionsVisible);
            parcel.writeInt(this.speechMode);
            parcel.writeInt(this.searchIconRes);
            parcel.writeInt(this.navIconResId);
            parcel.writeString(this.hint);
            parcel.writeList(this.suggestions);
            parcel.writeInt(this.maxSuggestions);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public MaterialSearchBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public MaterialSearchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public MaterialSearchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        inflate(getContext(), C3850R.layout.searchbar, this);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C3850R.styleable.MaterialSearchBar);
        this.speechMode = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_speechMode, false);
        this.maxSuggestionCount = obtainStyledAttributes.getInt(C3850R.styleable.MaterialSearchBar_mt_maxSuggestionsCount, 3);
        this.navButtonEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_navIconEnabled, false);
        this.roundedSearchBarEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_roundedSearchBarEnabled, false);
        this.dividerColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_dividerColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarDividerColor));
        this.searchBarColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_searchBarColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarPrimaryColor));
        this.menuIconRes = obtainStyledAttributes.getResourceId(C3850R.styleable.MaterialSearchBar_mt_menuIconDrawable, C3850R.C3852drawable.ic_dots_vertical_black_48dp);
        this.searchIconRes = obtainStyledAttributes.getResourceId(C3850R.styleable.MaterialSearchBar_mt_searchIconDrawable, C3850R.C3852drawable.ic_magnify_black_48dp);
        this.speechIconRes = obtainStyledAttributes.getResourceId(C3850R.styleable.MaterialSearchBar_mt_speechIconDrawable, C3850R.C3852drawable.ic_microphone_black_48dp);
        this.arrowIconRes = obtainStyledAttributes.getResourceId(C3850R.styleable.MaterialSearchBar_mt_backIconDrawable, C3850R.C3852drawable.ic_arrow_left_black_48dp);
        this.clearIconRes = obtainStyledAttributes.getResourceId(C3850R.styleable.MaterialSearchBar_mt_clearIconDrawable, C3850R.C3852drawable.ic_close_black_48dp);
        this.navIconTint = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_navIconTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarNavIconTintColor));
        this.menuIconTint = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_menuIconTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarMenuIconTintColor));
        this.searchIconTint = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_searchIconTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarSearchIconTintColor));
        this.arrowIconTint = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_backIconTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarBackIconTintColor));
        this.clearIconTint = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_clearIconTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarClearIconTintColor));
        this.navIconTintEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_navIconUseTint, true);
        this.menuIconTintEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_menuIconUseTint, true);
        this.searchIconTintEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_searchIconUseTint, true);
        this.arrowIconTintEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_backIconUseTint, true);
        this.clearIconTintEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_clearIconUseTint, true);
        this.borderlessRippleEnabled = obtainStyledAttributes.getBoolean(C3850R.styleable.MaterialSearchBar_mt_borderlessRippleEnabled, false);
        this.hintText = obtainStyledAttributes.getString(C3850R.styleable.MaterialSearchBar_mt_hint);
        this.placeholderText = obtainStyledAttributes.getString(C3850R.styleable.MaterialSearchBar_mt_placeholder);
        this.textColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_textColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarTextColor));
        this.hintColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_hintColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarHintColor));
        this.placeholderColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_placeholderColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarPlaceholderColor));
        this.textCursorColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_textCursorTint, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarCursorColor));
        this.highlightedTextColor = obtainStyledAttributes.getColor(C3850R.styleable.MaterialSearchBar_mt_highlightedTextColor, ContextCompat.getColor(getContext(), C3850R.C3851color.searchBarTextHighlightColor));
        this.destiny = getResources().getDisplayMetrics().density;
        if (this.adapter == null) {
            this.adapter = new DefaultSuggestionsAdapter(LayoutInflater.from(getContext()));
        }
        SuggestionsAdapter suggestionsAdapter = this.adapter;
        if (suggestionsAdapter instanceof DefaultSuggestionsAdapter) {
            ((DefaultSuggestionsAdapter) suggestionsAdapter).setListener(this);
        }
        this.adapter.setMaxSuggestionsCount(this.maxSuggestionCount);
        RecyclerView recyclerView = (RecyclerView) findViewById(C3850R.C3853id.mt_recycler);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        obtainStyledAttributes.recycle();
        this.searchBarCardView = (CardView) findViewById(C3850R.C3853id.mt_container);
        this.suggestionDivider = findViewById(C3850R.C3853id.mt_divider);
        this.menuIcon = (ImageView) findViewById(C3850R.C3853id.mt_menu);
        this.clearIcon = (ImageView) findViewById(C3850R.C3853id.mt_clear);
        this.searchIcon = (ImageView) findViewById(C3850R.C3853id.mt_search);
        this.arrowIcon = (ImageView) findViewById(C3850R.C3853id.mt_arrow);
        this.searchEdit = (EditText) findViewById(C3850R.C3853id.mt_editText);
        this.placeHolder = (TextView) findViewById(C3850R.C3853id.mt_placeholder);
        this.inputContainer = (LinearLayout) findViewById(C3850R.C3853id.inputContainer);
        this.navIcon = (ImageView) findViewById(C3850R.C3853id.mt_nav);
        findViewById(C3850R.C3853id.mt_clear).setOnClickListener(this);
        setOnClickListener(this);
        this.arrowIcon.setOnClickListener(this);
        this.searchIcon.setOnClickListener(this);
        this.searchEdit.setOnFocusChangeListener(this);
        this.searchEdit.setOnEditorActionListener(this);
        this.navIcon.setOnClickListener(this);
        postSetup();
    }

    public void inflateMenu(int i) {
        inflateMenuRequest(i, -1);
    }

    public void inflateMenu(int i, int i2) {
        inflateMenuRequest(i, i2);
    }

    private void inflateMenuRequest(int i, int i2) {
        if (i > 0) {
            ImageView imageView = (ImageView) findViewById(C3850R.C3853id.mt_menu);
            if (i2 != -1) {
                this.menuIconRes = i2;
                imageView.setImageResource(this.menuIconRes);
            }
            imageView.setVisibility(0);
            imageView.setOnClickListener(this);
            this.popupMenu = new PopupMenu(getContext(), imageView);
            this.popupMenu.inflate(i);
            this.popupMenu.setGravity(5);
        }
    }

    public PopupMenu getMenu() {
        return this.popupMenu;
    }

    private void postSetup() {
        setupTextColors();
        setupRoundedSearchBarEnabled();
        setupSearchBarColor();
        setupIcons();
        setupSearchEditText();
    }

    private void setupRoundedSearchBarEnabled() {
        if (!this.roundedSearchBarEnabled || VERSION.SDK_INT < 21) {
            this.searchBarCardView.setRadius(getResources().getDimension(C3850R.dimen.corner_radius_default));
        } else {
            this.searchBarCardView.setRadius(getResources().getDimension(C3850R.dimen.corner_radius_rounded));
        }
    }

    private void setupSearchBarColor() {
        this.searchBarCardView.setCardBackgroundColor(this.searchBarColor);
        setupDividerColor();
    }

    private void setupDividerColor() {
        this.suggestionDivider.setBackgroundColor(this.dividerColor);
    }

    private void setupTextColors() {
        this.searchEdit.setHintTextColor(this.hintColor);
        this.searchEdit.setTextColor(this.textColor);
        this.placeHolder.setTextColor(this.placeholderColor);
    }

    private void setupSearchEditText() {
        setupCursorColor();
        this.searchEdit.setHighlightColor(this.highlightedTextColor);
        CharSequence charSequence = this.hintText;
        if (charSequence != null) {
            this.searchEdit.setHint(charSequence);
        }
        if (this.placeholderText != null) {
            this.arrowIcon.setBackground(null);
            this.placeHolder.setText(this.placeholderText);
        }
    }

    private void setupCursorColor() {
        try {
            Field declaredField = TextView.class.getDeclaredField("mEditor");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this.searchEdit);
            Field declaredField2 = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField2.setAccessible(true);
            Drawable mutate = ContextCompat.getDrawable(getContext(), declaredField2.getInt(this.searchEdit)).mutate();
            mutate.setColorFilter(this.textCursorColor, Mode.SRC_IN);
            Drawable[] drawableArr = {mutate, mutate};
            Field declaredField3 = obj.getClass().getDeclaredField("mCursorDrawable");
            declaredField3.setAccessible(true);
            declaredField3.set(obj, drawableArr);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    private void setupIcons() {
        this.navIconResId = C3850R.C3852drawable.ic_menu_animated;
        this.navIcon.setImageResource(this.navIconResId);
        setNavButtonEnabled(this.navButtonEnabled);
        if (this.popupMenu == null) {
            findViewById(C3850R.C3853id.mt_menu).setVisibility(8);
        }
        setSpeechMode(this.speechMode);
        this.arrowIcon.setImageResource(this.arrowIconRes);
        this.clearIcon.setImageResource(this.clearIconRes);
        setupNavIconTint();
        setupMenuIconTint();
        setupSearchIconTint();
        setupArrowIconTint();
        setupClearIconTint();
        setupIconRippleStyle();
    }

    private void setupNavIconTint() {
        if (this.navIconTintEnabled) {
            this.navIcon.setColorFilter(this.navIconTint, Mode.SRC_IN);
        } else {
            this.navIcon.clearColorFilter();
        }
    }

    private void setupMenuIconTint() {
        if (this.menuIconTintEnabled) {
            this.menuIcon.setColorFilter(this.menuIconTint, Mode.SRC_IN);
        } else {
            this.menuIcon.clearColorFilter();
        }
    }

    private void setupSearchIconTint() {
        if (this.searchIconTintEnabled) {
            this.searchIcon.setColorFilter(this.searchIconTint, Mode.SRC_IN);
        } else {
            this.searchIcon.clearColorFilter();
        }
    }

    private void setupArrowIconTint() {
        if (this.arrowIconTintEnabled) {
            this.arrowIcon.setColorFilter(this.arrowIconTint, Mode.SRC_IN);
        } else {
            this.arrowIcon.clearColorFilter();
        }
    }

    private void setupClearIconTint() {
        if (this.clearIconTintEnabled) {
            this.clearIcon.setColorFilter(this.clearIconTint, Mode.SRC_IN);
        } else {
            this.clearIcon.clearColorFilter();
        }
    }

    private void setupIconRippleStyle() {
        if (VERSION.SDK_INT > 16) {
            TypedValue typedValue = new TypedValue();
            if (!this.borderlessRippleEnabled || VERSION.SDK_INT < 21) {
                getContext().getTheme().resolveAttribute(16843534, typedValue, true);
            } else {
                getContext().getTheme().resolveAttribute(16843868, typedValue, true);
            }
            this.navIcon.setBackgroundResource(typedValue.resourceId);
            this.searchIcon.setBackgroundResource(typedValue.resourceId);
            this.menuIcon.setBackgroundResource(typedValue.resourceId);
            this.arrowIcon.setBackgroundResource(typedValue.resourceId);
            this.clearIcon.setBackgroundResource(typedValue.resourceId);
            return;
        }
        Log.w("ContentValues", "setupIconRippleStyle() Only Available On SDK Versions Higher Than 16!");
    }

    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener2) {
        this.onSearchActionListener = onSearchActionListener2;
    }

    public void closeSearch() {
        animateNavIcon(false);
        this.searchOpened = false;
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), C3850R.anim.fade_out);
        Animation loadAnimation2 = AnimationUtils.loadAnimation(getContext(), C3850R.anim.fade_in_right);
        loadAnimation.setAnimationListener(this);
        this.searchIcon.setVisibility(0);
        this.inputContainer.startAnimation(loadAnimation);
        this.searchIcon.startAnimation(loadAnimation2);
        if (this.placeholderText != null) {
            this.placeHolder.setVisibility(0);
            this.placeHolder.startAnimation(loadAnimation2);
        }
        if (listenerExists()) {
            this.onSearchActionListener.onSearchStateChanged(false);
        }
        if (this.suggestionsVisible) {
            animateSuggestions(getListHeight(false), 0);
        }
    }

    public void openSearch() {
        if (isSearchOpened()) {
            this.onSearchActionListener.onSearchStateChanged(true);
            this.searchEdit.requestFocus();
            return;
        }
        animateNavIcon(true);
        this.adapter.notifyDataSetChanged();
        this.searchOpened = true;
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), C3850R.anim.fade_in_left);
        Animation loadAnimation2 = AnimationUtils.loadAnimation(getContext(), C3850R.anim.fade_out_left);
        loadAnimation.setAnimationListener(this);
        this.placeHolder.setVisibility(8);
        this.inputContainer.setVisibility(0);
        this.inputContainer.startAnimation(loadAnimation);
        if (listenerExists()) {
            this.onSearchActionListener.onSearchStateChanged(true);
        }
        this.searchIcon.startAnimation(loadAnimation2);
    }

    private void animateNavIcon(boolean z) {
        if (z) {
            this.navIcon.setImageResource(C3850R.C3852drawable.ic_menu_animated);
        } else {
            this.navIcon.setImageResource(C3850R.C3852drawable.ic_back_animated);
        }
        Drawable drawable = this.navIcon.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private void animateSuggestions(int i, int i2) {
        this.suggestionsVisible = i2 > 0;
        final RecyclerView recyclerView = (RecyclerView) findViewById(C3850R.C3853id.mt_recycler);
        final LayoutParams layoutParams = recyclerView.getLayoutParams();
        if (i2 != 0 || layoutParams.height != 0) {
            findViewById(C3850R.C3853id.mt_divider).setVisibility(i2 > 0 ? 0 : 8);
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, i2});
            ofInt.setDuration(1200);
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    recyclerView.setLayoutParams(layoutParams);
                }
            });
            if (this.adapter.getItemCount() > 0) {
                ofInt.start();
            }
        }
    }

    public void showSuggestionsList() {
        animateSuggestions(0, getListHeight(false));
    }

    public void hideSuggestionsList() {
        animateSuggestions(getListHeight(false), 0);
    }

    public void clearSuggestions() {
        if (this.suggestionsVisible) {
            animateSuggestions(getListHeight(false), 0);
        }
        this.adapter.clearSuggestions();
    }

    public boolean isSuggestionsVisible() {
        return this.suggestionsVisible;
    }

    public boolean isSuggestionsEnabled() {
        return this.isSuggestionsEnabled;
    }

    public void setSuggestionsEnabled(boolean z) {
        this.isSuggestionsEnabled = z;
    }

    public void setMenuIcon(int i) {
        this.menuIconRes = i;
        this.menuIcon.setImageResource(this.menuIconRes);
    }

    public void setSearchIcon(int i) {
        this.searchIconRes = i;
        this.searchIcon.setImageResource(i);
    }

    public void setArrowIcon(int i) {
        this.arrowIconRes = i;
        this.arrowIcon.setImageResource(this.arrowIconRes);
    }

    public void setClearIcon(int i) {
        this.clearIconRes = i;
        this.clearIcon.setImageResource(this.clearIconRes);
    }

    public void setNavIconTint(int i) {
        this.navIconTint = i;
        setupNavIconTint();
    }

    public void setMenuIconTint(int i) {
        this.menuIconTint = i;
        setupMenuIconTint();
    }

    public void setSearchIconTint(int i) {
        this.searchIconTint = i;
        setupSearchIconTint();
    }

    public void setArrowIconTint(int i) {
        this.arrowIconTint = i;
        setupArrowIconTint();
    }

    public void setClearIconTint(int i) {
        this.clearIconTint = i;
        setupClearIconTint();
    }

    public void setIconRippleStyle(boolean z) {
        this.borderlessRippleEnabled = z;
        setupIconRippleStyle();
    }

    public void setHint(CharSequence charSequence) {
        this.hintText = charSequence;
        this.searchEdit.setHint(charSequence);
    }

    public CharSequence getPlaceHolderText() {
        return this.placeHolder.getText();
    }

    public void setSpeechMode(boolean z) {
        this.speechMode = z;
        if (z) {
            this.searchIcon.setImageResource(this.speechIconRes);
            this.searchIcon.setClickable(true);
            return;
        }
        this.searchIcon.setImageResource(this.searchIconRes);
        this.searchIcon.setClickable(false);
    }

    public boolean isSpeechModeEnabled() {
        return this.speechMode;
    }

    public boolean isSearchOpened() {
        return this.searchOpened;
    }

    public void setMaxSuggestionCount(int i) {
        this.maxSuggestionCount = i;
        this.adapter.setMaxSuggestionsCount(i);
    }

    public void setCustomSuggestionAdapter(SuggestionsAdapter suggestionsAdapter) {
        this.adapter = suggestionsAdapter;
        ((RecyclerView) findViewById(C3850R.C3853id.mt_recycler)).setAdapter(this.adapter);
    }

    public List getLastSuggestions() {
        return this.adapter.getSuggestions();
    }

    public void setLastSuggestions(List list) {
        this.adapter.setSuggestions(list);
    }

    public void updateLastSuggestions(List list) {
        int listHeight = getListHeight(false);
        if (list.size() > 0) {
            this.adapter.setSuggestions(new ArrayList(list));
            animateSuggestions(listHeight, getListHeight(false));
            return;
        }
        animateSuggestions(listHeight, 0);
    }

    public void setSuggestionsClickListener(OnItemViewClickListener onItemViewClickListener) {
        SuggestionsAdapter suggestionsAdapter = this.adapter;
        if (suggestionsAdapter instanceof DefaultSuggestionsAdapter) {
            ((DefaultSuggestionsAdapter) suggestionsAdapter).setListener(onItemViewClickListener);
        }
    }

    public void setTextColor(int i) {
        this.textColor = i;
        setupTextColors();
    }

    public void setTextHintColor(int i) {
        this.hintColor = i;
        setupTextColors();
    }

    public void setPlaceHolderColor(int i) {
        this.placeholderColor = i;
        setupTextColors();
    }

    public void setTextHighlightColor(int i) {
        this.highlightedTextColor = i;
        this.searchEdit.setHighlightColor(i);
    }

    public void setDividerColor(int i) {
        this.dividerColor = i;
        setupDividerColor();
    }

    public void setNavButtonEnabled(boolean z) {
        this.navButtonEnabled = z;
        if (z) {
            this.navIcon.setVisibility(0);
            this.navIcon.setClickable(true);
            this.arrowIcon.setVisibility(8);
        } else {
            this.navIcon.setVisibility(8);
            this.navIcon.setClickable(false);
            this.arrowIcon.setVisibility(0);
        }
        this.navIcon.requestLayout();
        this.placeHolder.requestLayout();
        this.arrowIcon.requestLayout();
    }

    public void setRoundedSearchBarEnabled(boolean z) {
        this.roundedSearchBarEnabled = z;
        setupRoundedSearchBarEnabled();
    }

    public void setCardViewElevation(int i) {
        ((CardView) findViewById(C3850R.C3853id.mt_container)).setCardElevation((float) i);
    }

    public String getText() {
        return this.searchEdit.getText().toString();
    }

    public void setText(String str) {
        this.searchEdit.setText(str);
    }

    public void addTextChangeListener(TextWatcher textWatcher) {
        this.searchEdit.addTextChangedListener(textWatcher);
    }

    public EditText getSearchEditText() {
        return this.searchEdit;
    }

    public TextView getPlaceHolderView() {
        return this.placeHolder;
    }

    public void setPlaceHolder(CharSequence charSequence) {
        this.placeholderText = charSequence;
        this.placeHolder.setText(charSequence);
    }

    private boolean listenerExists() {
        return this.onSearchActionListener != null;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == getId()) {
            if (!this.searchOpened) {
                openSearch();
            }
        } else if (id == C3850R.C3853id.mt_arrow) {
            closeSearch();
        } else if (id == C3850R.C3853id.mt_search) {
            if (listenerExists()) {
                this.onSearchActionListener.onButtonClicked(1);
            }
        } else if (id == C3850R.C3853id.mt_clear) {
            this.searchEdit.setText("");
        } else if (id == C3850R.C3853id.mt_menu) {
            this.popupMenu.show();
        } else if (id == C3850R.C3853id.mt_nav) {
            int i = this.searchOpened ? 3 : 2;
            if (this.searchOpened) {
                closeSearch();
            }
            if (listenerExists()) {
                this.onSearchActionListener.onButtonClicked(i);
            }
        }
    }

    public void onAnimationEnd(Animation animation) {
        if (!this.searchOpened) {
            this.inputContainer.setVisibility(8);
            this.searchEdit.setText("");
            return;
        }
        this.searchIcon.setVisibility(8);
        this.searchEdit.requestFocus();
        if (!this.suggestionsVisible && this.isSuggestionsEnabled) {
            showSuggestionsList();
        }
    }

    public void onFocusChange(View view, boolean z) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (z) {
            inputMethodManager.showSoftInput(view, 1);
        } else {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (listenerExists()) {
            this.onSearchActionListener.onSearchConfirmed(this.searchEdit.getText());
        }
        if (this.suggestionsVisible) {
            hideSuggestionsList();
        }
        SuggestionsAdapter suggestionsAdapter = this.adapter;
        if (suggestionsAdapter instanceof DefaultSuggestionsAdapter) {
            suggestionsAdapter.addSuggestion(this.searchEdit.getText().toString());
        }
        return true;
    }

    private int getListHeight(boolean z) {
        float itemCount;
        float f;
        if (!z) {
            itemCount = (float) this.adapter.getListHeight();
            f = this.destiny;
        } else {
            itemCount = (float) ((this.adapter.getItemCount() - 1) * this.adapter.getSingleViewHeight());
            f = this.destiny;
        }
        return (int) (itemCount * f);
    }

    public void OnItemClickListener(int i, View view) {
        if (view.getTag() instanceof String) {
            this.searchEdit.setText((String) view.getTag());
        }
    }

    public void OnItemDeleteListener(int i, View view) {
        if (view.getTag() instanceof String) {
            animateSuggestions(getListHeight(false), getListHeight(true));
            this.adapter.deleteSuggestion(i, view.getTag());
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isSearchBarVisible = this.searchOpened ? 1 : 0;
        savedState.suggestionsVisible = this.suggestionsVisible ? 1 : 0;
        savedState.speechMode = this.speechMode ? 1 : 0;
        savedState.navIconResId = this.navIconResId;
        savedState.searchIconRes = this.searchIconRes;
        savedState.suggestions = getLastSuggestions();
        savedState.maxSuggestions = this.maxSuggestionCount;
        CharSequence charSequence = this.hintText;
        if (charSequence != null) {
            savedState.hint = charSequence.toString();
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        boolean z = true;
        this.searchOpened = savedState.isSearchBarVisible == 1;
        if (savedState.suggestionsVisible != 1) {
            z = false;
        }
        this.suggestionsVisible = z;
        setLastSuggestions(savedState.suggestions);
        if (this.suggestionsVisible) {
            animateSuggestions(0, getListHeight(false));
        }
        if (this.searchOpened) {
            this.inputContainer.setVisibility(0);
            this.placeHolder.setVisibility(8);
            this.searchIcon.setVisibility(8);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || !this.searchOpened) {
            return super.dispatchKeyEvent(keyEvent);
        }
        animateSuggestions(getListHeight(false), 0);
        closeSearch();
        return true;
    }
}
