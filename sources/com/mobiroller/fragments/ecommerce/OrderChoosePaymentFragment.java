package com.mobiroller.fragments.ecommerce;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.adapters.ecommerce.BankListAdapter;
import com.mobiroller.adapters.ecommerce.PaymentTypeAdapter;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.PaymentSettings;
import com.mobiroller.models.ecommerce.SupportedPaymentType;
import com.mobiroller.models.events.BankSelectedEvent;
import com.mobiroller.models.events.PaymentContinueEvent;
import com.mobiroller.models.events.ValidateStepEvent;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.CreditCardNumberTextWatcher;
import com.mobiroller.views.custom.MobirollerDialog.Builder;
import com.mobiroller.views.custom.MobirollerDialog.DialogListCallback;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerTextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class OrderChoosePaymentFragment extends OrderFlowBaseFragment implements OnFocusChangeListener, DialogListCallback {
    private BankListAdapter adapter;
    @BindView(2131361969)
    RecyclerView bankAccountList;
    @BindView(2131362016)
    ConstraintLayout bottomLayout;
    @BindView(2131362194)
    ConstraintLayout contentLayout;
    @BindView(2131362202)
    FloatingActionButton continueButton;
    @BindView(2131362215)
    ConstraintLayout creditCardLayout;
    /* access modifiers changed from: private */
    public CreditCardLayout creditCardLayoutView;
    @BindView(2131362311)
    ViewStub creditCardViewStub;
    private int currentStep = 2;
    private ECommerceRequestHelper eCommerceRequestHelper;
    @BindView(2131362333)
    MobirollerEmptyView emptyView;
    private boolean isInit = false;
    private boolean isOnline = false;
    private boolean isOnline3D = false;
    private boolean isValid = false;
    private LocalizationHelper localizationHelper;
    private String mCardExpireString;
    private String mCardNumber;
    @BindView(2131362893)
    TextView payAtDoorDesc;
    @BindView(2131362894)
    ConstraintLayout payAtDoorLayout;
    @BindView(2131362912)
    TextView payPalDescriptionTextView;
    @BindView(2131362913)
    ConstraintLayout payPalLayout;
    @BindView(2131362905)
    MobirollerTextView paymentMethodTextView;
    /* access modifiers changed from: private */
    public PaymentSettings paymentSettings;
    @BindView(2131362907)
    MobirollerTextView paymentTextView;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    private SupportedPaymentType selectedPaymentType;
    private int selection;
    @BindView(2131363290)
    ConstraintLayout transferLayout;
    Unbinder unbinder;

    private class CardNumberTextWatcher implements TextWatcher {
        private static final char space = ' ';
        private EditText mEditText;

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        CardNumberTextWatcher(EditText editText) {
            this.mEditText = editText;
        }

        public void afterTextChanged(Editable editable) {
            if (editable.length() >= 1) {
                if (editable.length() > 0 && editable.length() % 5 == 0 && ' ' == editable.charAt(editable.length() - 1)) {
                    editable.delete(editable.length() - 1, editable.length());
                }
                if (editable.length() > 0 && editable.length() % 5 == 0 && Character.isDigit(editable.charAt(editable.length() - 1)) && TextUtils.split(editable.toString(), String.valueOf(' ')).length <= 3) {
                    editable.insert(editable.length() - 1, String.valueOf(' '));
                }
                OrderChoosePaymentFragment.this.validateAllCreditCardFields();
            }
        }
    }

    public class CreditCardLayout {
        @BindView(2131362109)
        TextInputEditText cardNameTextInputEditText;
        @BindView(2131362110)
        TextInputLayout cardNameTextInputLayout;
        @BindView(2131362111)
        TextInputEditText cardNumberTextInputEditText;
        @BindView(2131362112)
        TextInputLayout cardNumberTextInputLayout;
        @BindView(2131362230)
        TextInputEditText cvvTextInputEditText;
        @BindView(2131362231)
        TextInputLayout cvvTextInputLayout;
        @BindView(2131362368)
        TextInputEditText expirationDateTextInputEditText;
        @BindView(2131362369)
        TextInputLayout expirationDateTextInputLayout;

        public CreditCardLayout(View view) {
            ButterKnife.bind((Object) this, view);
            OrderChoosePaymentFragment.this.creditCardLayout.setVisibility(0);
            TextInputEditText textInputEditText = this.cardNumberTextInputEditText;
            textInputEditText.addTextChangedListener(new CardNumberTextWatcher(textInputEditText));
            TextInputEditText textInputEditText2 = this.cardNumberTextInputEditText;
            textInputEditText2.addTextChangedListener(new CreditCardNumberTextWatcher(textInputEditText2, this.cardNumberTextInputLayout));
            TextInputEditText textInputEditText3 = this.expirationDateTextInputEditText;
            textInputEditText3.addTextChangedListener(new TwoDigitsCardTextWatcher(textInputEditText3));
            TextInputEditText textInputEditText4 = this.cvvTextInputEditText;
            textInputEditText4.addTextChangedListener(new ValidateTextWatcher(textInputEditText4));
            TextInputEditText textInputEditText5 = this.cardNameTextInputEditText;
            textInputEditText5.addTextChangedListener(new ValidateTextWatcher(textInputEditText5));
            this.cardNumberTextInputEditText.setOnFocusChangeListener(OrderChoosePaymentFragment.this);
            this.cardNameTextInputEditText.setOnFocusChangeListener(OrderChoosePaymentFragment.this);
            this.expirationDateTextInputEditText.setOnFocusChangeListener(OrderChoosePaymentFragment.this);
            this.cvvTextInputEditText.setOnFocusChangeListener(OrderChoosePaymentFragment.this);
        }
    }

    public class CreditCardLayout_ViewBinding implements Unbinder {
        private CreditCardLayout target;

        public CreditCardLayout_ViewBinding(CreditCardLayout creditCardLayout, View view) {
            this.target = creditCardLayout;
            creditCardLayout.cardNumberTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.cardNumberTextInputEditText, "field 'cardNumberTextInputEditText'", TextInputEditText.class);
            creditCardLayout.cardNumberTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.cardNumberTextInputLayout, "field 'cardNumberTextInputLayout'", TextInputLayout.class);
            creditCardLayout.cardNameTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.cardNameTextInputEditText, "field 'cardNameTextInputEditText'", TextInputEditText.class);
            creditCardLayout.cardNameTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.cardNameTextInputLayout, "field 'cardNameTextInputLayout'", TextInputLayout.class);
            creditCardLayout.expirationDateTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.expirationDateTextInputEditText, "field 'expirationDateTextInputEditText'", TextInputEditText.class);
            creditCardLayout.expirationDateTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.expirationDateTextInputLayout, "field 'expirationDateTextInputLayout'", TextInputLayout.class);
            creditCardLayout.cvvTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.cvvTextInputEditText, "field 'cvvTextInputEditText'", TextInputEditText.class);
            creditCardLayout.cvvTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.cvvTextInputLayout, "field 'cvvTextInputLayout'", TextInputLayout.class);
        }

        public void unbind() {
            CreditCardLayout creditCardLayout = this.target;
            if (creditCardLayout != null) {
                this.target = null;
                creditCardLayout.cardNumberTextInputEditText = null;
                creditCardLayout.cardNumberTextInputLayout = null;
                creditCardLayout.cardNameTextInputEditText = null;
                creditCardLayout.cardNameTextInputLayout = null;
                creditCardLayout.expirationDateTextInputEditText = null;
                creditCardLayout.expirationDateTextInputLayout = null;
                creditCardLayout.cvvTextInputEditText = null;
                creditCardLayout.cvvTextInputLayout = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }

    private class TwoDigitsCardTextWatcher implements TextWatcher {
        private static final String DEFAULT_MONTH = "01";
        private static final String INITIAL_MONTH_ADD_ON = "0";
        private static final String SPACE = "/";
        private EditText mEditText;
        private int mLength;

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public TwoDigitsCardTextWatcher(EditText editText) {
            this.mEditText = editText;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.mLength = this.mEditText.getText().length();
        }

        public void afterTextChanged(Editable editable) {
            boolean z = this.mLength > this.mEditText.getText().length();
            String str = SPACE;
            if (!z || !editable.toString().startsWith(str)) {
                Calendar instance = Calendar.getInstance();
                int i = instance.get(1) % 100;
                int i2 = instance.get(2) + 1;
                if (editable.length() != 1 || isNumberChar(String.valueOf(editable.charAt(0)))) {
                    int length = editable.length();
                    String str2 = INITIAL_MONTH_ADD_ON;
                    if (length == 1 && !isCharValidMonth(editable.charAt(0))) {
                        EditText editText = this.mEditText;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(String.valueOf(editable.charAt(0)));
                        sb.append(str);
                        editText.setText(sb.toString());
                    } else if (editable.length() == 2 && String.valueOf(editable.charAt(editable.length() - 1)).equals(str)) {
                        EditText editText2 = this.mEditText;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(String.valueOf(editable));
                        editText2.setText(sb2.toString());
                    } else if (!z && editable.length() == 2 && !String.valueOf(editable.charAt(editable.length() - 1)).equals(str)) {
                        EditText editText3 = this.mEditText;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(this.mEditText.getText().toString());
                        sb3.append(str);
                        editText3.setText(sb3.toString());
                    } else if (editable.length() == 3 && !String.valueOf(editable.charAt(editable.length() - 1)).equals(str) && !z) {
                        editable.insert(2, str);
                        this.mEditText.setText(String.valueOf(editable));
                    } else if (editable.length() > 3 && !isCharValidMonth(editable.charAt(0))) {
                        EditText editText4 = this.mEditText;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str2);
                        sb4.append(editable);
                        editText4.setText(sb4.toString());
                    }
                } else {
                    this.mEditText.setText("01/");
                }
                if (editable.length() == 3 && Integer.parseInt(this.mEditText.getText().toString().substring(0, 2)) > 12) {
                    this.mEditText.setText("12/");
                }
                if (editable.length() == 4 && !isNumberChar(String.valueOf(editable.charAt(3)))) {
                    EditText editText5 = this.mEditText;
                    editText5.setText(editText5.getText().toString().substring(0, 2));
                } else if (editable.length() == 5 && !isNumberChar(String.valueOf(editable.charAt(4)))) {
                    EditText editText6 = this.mEditText;
                    editText6.setText(editText6.getText().toString().substring(0, 3));
                }
                if (editable.length() > 3 && editable.charAt(3) == '0') {
                    EditText editText7 = this.mEditText;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(this.mEditText.getText().toString().substring(0, 3));
                    sb5.append(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                    editText7.setText(sb5.toString());
                }
                if (editable.length() == 5 && Integer.valueOf(String.valueOf(editable.subSequence(3, 5))).intValue() < i) {
                    if (Integer.valueOf(String.valueOf(editable.subSequence(0, 2))).intValue() < i2) {
                        EditText editText8 = this.mEditText;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(this.mEditText.getText().toString().substring(0, 3));
                        sb6.append(i + 1);
                        editText8.setText(sb6.toString());
                    } else {
                        EditText editText9 = this.mEditText;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(this.mEditText.getText().toString().substring(0, 3));
                        sb7.append(i);
                        editText9.setText(sb7.toString());
                    }
                }
                if (!z) {
                    EditText editText10 = this.mEditText;
                    editText10.setSelection(editText10.getText().toString().length());
                }
                OrderChoosePaymentFragment.this.validateAllCreditCardFields();
            }
        }

        private boolean isCharValidMonth(char c) {
            return (Character.isDigit(c) ? Integer.parseInt(String.valueOf(c)) : 0) <= 1;
        }

        private boolean isNumberChar(String str) {
            return str.matches(".*\\d.*");
        }
    }

    private class ValidateTextWatcher implements TextWatcher {
        TextInputEditText editText;

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public ValidateTextWatcher(TextInputEditText textInputEditText) {
            this.editText = textInputEditText;
        }

        public void afterTextChanged(Editable editable) {
            if (editable.length() >= 2) {
                if (this.editText.getId() == R.id.cardNameTextInputEditText && this.editText.toString().length() >= 1) {
                    return;
                }
                if (this.editText.getId() != R.id.cvvTextInputEditText || this.editText.toString().length() >= 3) {
                    OrderChoosePaymentFragment.this.validateAllCreditCardFields();
                }
            }
        }
    }

    public OrderChoosePaymentFragment() {
        String str = "";
        this.mCardNumber = str;
        this.mCardExpireString = str;
        this.selection = 0;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
        this.localizationHelper = UtilManager.localizationHelper();
        this.paymentSettings = (PaymentSettings) getArguments().getSerializable(ECommerceConstant.PAYMENT_SETTINGS_MODEL);
        if (this.paymentSettings == null) {
            getPaymentOptions();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_choose_payment, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.paymentSettings = (PaymentSettings) getArguments().getSerializable(ECommerceConstant.PAYMENT_SETTINGS_MODEL);
        setContinueButton(false);
        if (this.paymentSettings != null) {
            this.bottomLayout.setVisibility(0);
            setupView();
        }
        return inflate;
    }

    @Subscribe
    public void onPostBankSelectedEvent(BankSelectedEvent bankSelectedEvent) {
        isValid();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public boolean isValid() {
        return validate();
    }

    private void getPaymentOptions() {
        if (UtilManager.networkHelper().isConnected()) {
            if (getActivity() != null && !getActivity().isFinishing() && !this.progressViewHelper.isShowing()) {
                this.progressViewHelper.show();
            }
            this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getPaymentSettings(), new ECommerceCallBack<PaymentSettings>() {
                public void done() {
                    if (!OrderChoosePaymentFragment.this.getActivity().isFinishing() && OrderChoosePaymentFragment.this.progressViewHelper.isShowing()) {
                        OrderChoosePaymentFragment.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(PaymentSettings paymentSettings) {
                    OrderChoosePaymentFragment.this.paymentSettings = paymentSettings;
                    OrderChoosePaymentFragment.this.bottomLayout.setVisibility(0);
                    OrderChoosePaymentFragment.this.setupView();
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(OrderChoosePaymentFragment.this.getContext());
                }

                public void onNetworkError(String str) {
                    ErrorUtils.showErrorToast(OrderChoosePaymentFragment.this.getContext());
                }
            });
            return;
        }
        DialogUtil.showNoConnectionError(getActivity());
    }

    /* access modifiers changed from: private */
    public void setupView() {
        if (!this.isInit) {
            if (this.paymentSettings.supportedPaymentTypes == null || this.paymentSettings.supportedPaymentTypes.size() == 0) {
                this.contentLayout.setVisibility(8);
                this.emptyView.setVisibility(0);
                this.bottomLayout.setVisibility(8);
                return;
            }
            this.contentLayout.setVisibility(0);
            this.emptyView.setVisibility(8);
            this.bottomLayout.setVisibility(0);
            for (int i = 0; i < this.paymentSettings.supportedPaymentTypes.size(); i++) {
                String str = ((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType;
                String str2 = ECommerceConstant.ONLINE;
                if (str.equalsIgnoreCase(str2)) {
                    this.isOnline = true;
                }
                String str3 = ((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType;
                String str4 = ECommerceConstant.ONLINE3DS;
                if (str3.equalsIgnoreCase(str4)) {
                    this.isOnline3D = true;
                }
                if ((((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(str2) || ((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(str4)) && this.creditCardLayout.getVisibility() == 8) {
                    this.creditCardLayoutView = new CreditCardLayout(this.creditCardViewStub.inflate());
                } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
                    this.transferLayout.setVisibility(0);
                    if (this.adapter != null || this.paymentSettings.paymentAccounts == null) {
                        BankListAdapter bankListAdapter = this.adapter;
                        if (bankListAdapter != null) {
                            this.bankAccountList.setAdapter(bankListAdapter);
                            this.bankAccountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    } else {
                        this.adapter = new BankListAdapter(getActivity(), this.paymentSettings.paymentAccounts);
                        this.bankAccountList.setAdapter(this.adapter);
                        this.bankAccountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.PAY_AT_DOOR)) {
                    this.payAtDoorLayout.setVisibility(0);
                    this.payAtDoorDesc.setText(this.localizationHelper.getLocalizedTitle(((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).description));
                } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
                    this.payPalLayout.setVisibility(0);
                    this.payPalDescriptionTextView.setText(this.localizationHelper.getLocalizedTitle(((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).description));
                }
                this.transferLayout.setVisibility(8);
                this.creditCardLayout.setVisibility(8);
                this.payAtDoorLayout.setVisibility(8);
                this.payPalLayout.setVisibility(8);
                onSelect(this.selection);
            }
            this.isInit = true;
        }
    }

    @OnClick({2131362904})
    public void onClickPaymentTypeSelection() {
        List arrayList = new ArrayList();
        if (this.paymentSettings.supportedPaymentTypes != null) {
            arrayList = this.paymentSettings.supportedPaymentTypes;
        }
        new Builder().setContext(getActivity()).setType(MobirollerDialogType.LIST).setTitle(getString(R.string.e_commerce_payment_method_selection_payment_type_popup_title)).setHasDivider(true).setListSelectionListener(this).setDescription(getString(R.string.e_commerce_payment_method_selection_payment_type_popup_description)).setAdapter(new PaymentTypeAdapter(arrayList)).show();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0069, code lost:
        if (r0.getSelectedBank() != null) goto L_0x006d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkIsValid() {
        /*
            r8 = this;
            java.lang.String r0 = "CreditCard"
            java.lang.String r1 = "checkIsValid"
            android.util.Log.e(r0, r1)
            boolean r0 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            r1 = 1
            if (r0 == 0) goto L_0x001e
            r8.setContinueButton(r1)
            org.greenrobot.eventbus.EventBus r0 = org.greenrobot.eventbus.EventBus.getDefault()
            com.mobiroller.models.events.ValidateStepEvent r2 = new com.mobiroller.models.events.ValidateStepEvent
            int r3 = r8.currentStep
            r2.<init>(r3, r1)
            r0.post(r2)
            return
        L_0x001e:
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            java.lang.String r2 = "PayAtDoor"
            boolean r0 = r0.equalsIgnoreCase(r2)
            r3 = 0
            java.lang.String r4 = "Transfer"
            java.lang.String r5 = "Online3DS"
            java.lang.String r6 = "Online"
            java.lang.String r7 = "PayPal"
            if (r0 != 0) goto L_0x006d
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r7)
            if (r0 != 0) goto L_0x006d
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r6)
            if (r0 != 0) goto L_0x0051
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x0057
        L_0x0051:
            boolean r0 = r8.validateCreditCardFields()
            if (r0 != 0) goto L_0x006d
        L_0x0057:
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L_0x006c
            com.mobiroller.adapters.ecommerce.BankListAdapter r0 = r8.adapter
            if (r0 == 0) goto L_0x006c
            com.mobiroller.models.ecommerce.BankAccount r0 = r0.getSelectedBank()
            if (r0 == 0) goto L_0x006c
            goto L_0x006d
        L_0x006c:
            r1 = 0
        L_0x006d:
            r8.isValid = r1
            boolean r0 = r8.isValid
            r8.setContinueButton(r0)
            boolean r0 = r8.isValid
            if (r0 == 0) goto L_0x0139
            com.mobiroller.models.events.OrderPaymentEvent r0 = new com.mobiroller.models.events.OrderPaymentEvent
            r0.<init>()
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x008b
            r0.paymentType = r2
            goto L_0x0132
        L_0x008b:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r6)
            if (r1 != 0) goto L_0x009f
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r5)
            if (r1 == 0) goto L_0x0107
        L_0x009f:
            boolean r1 = r8.validateCreditCardFields()
            if (r1 == 0) goto L_0x0107
            boolean r1 = r8.isOnline3D
            if (r1 == 0) goto L_0x00ac
            r0.paymentType = r5
            goto L_0x00b2
        L_0x00ac:
            boolean r1 = r8.isOnline
            if (r1 == 0) goto L_0x00b2
            r0.paymentType = r6
        L_0x00b2:
            com.mobiroller.models.ecommerce.OrderCard r1 = new com.mobiroller.models.ecommerce.OrderCard
            r1.<init>()
            r0.orderCard = r1
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment$CreditCardLayout r2 = r8.creditCardLayoutView
            com.google.android.material.textfield.TextInputEditText r2 = r2.cardNameTextInputEditText
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.cardHolderName = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.String r2 = r8.mCardNumber
            r1.cardNumber = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.String r2 = r8.mCardExpireString
            r4 = 2
            java.lang.String r2 = r2.substring(r3, r4)
            r1.expireMonth = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "20"
            r2.append(r3)
            java.lang.String r3 = r8.mCardExpireString
            r5 = 4
            java.lang.String r3 = r3.substring(r4, r5)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.expireYear = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment$CreditCardLayout r2 = r8.creditCardLayoutView
            com.google.android.material.textfield.TextInputEditText r2 = r2.cvvTextInputEditText
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.cvc = r2
            goto L_0x0132
        L_0x0107:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r4)
            if (r1 == 0) goto L_0x0126
            com.mobiroller.adapters.ecommerce.BankListAdapter r1 = r8.adapter
            if (r1 == 0) goto L_0x0126
            com.mobiroller.models.ecommerce.BankAccount r1 = r1.getSelectedBank()
            if (r1 == 0) goto L_0x0126
            r0.paymentType = r4
            com.mobiroller.adapters.ecommerce.BankListAdapter r1 = r8.adapter
            com.mobiroller.models.ecommerce.BankAccount r1 = r1.getSelectedBank()
            r0.bankAccount = r1
            goto L_0x0132
        L_0x0126:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r7)
            if (r1 == 0) goto L_0x0132
            r0.paymentType = r7
        L_0x0132:
            org.greenrobot.eventbus.EventBus r1 = org.greenrobot.eventbus.EventBus.getDefault()
            r1.post(r0)
        L_0x0139:
            org.greenrobot.eventbus.EventBus r0 = org.greenrobot.eventbus.EventBus.getDefault()
            com.mobiroller.models.events.ValidateStepEvent r1 = new com.mobiroller.models.events.ValidateStepEvent
            int r2 = r8.currentStep
            boolean r3 = r8.isValid
            r1.<init>(r2, r3)
            r0.post(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment.checkIsValid():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0054, code lost:
        if (r0.getSelectedBank() != null) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean validate() {
        /*
            r8 = this;
            boolean r0 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            r1 = 1
            if (r0 == 0) goto L_0x0009
            r8.setContinueButton(r1)
            return r1
        L_0x0009:
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            java.lang.String r2 = "PayAtDoor"
            boolean r0 = r0.equalsIgnoreCase(r2)
            r3 = 0
            java.lang.String r4 = "Transfer"
            java.lang.String r5 = "Online3DS"
            java.lang.String r6 = "Online"
            java.lang.String r7 = "PayPal"
            if (r0 != 0) goto L_0x0058
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r7)
            if (r0 != 0) goto L_0x0058
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r6)
            if (r0 != 0) goto L_0x003c
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x0042
        L_0x003c:
            boolean r0 = r8.validateCreditCardFields()
            if (r0 != 0) goto L_0x0058
        L_0x0042:
            com.mobiroller.models.ecommerce.SupportedPaymentType r0 = r8.selectedPaymentType
            java.lang.String r0 = r0.paymentType
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L_0x0057
            com.mobiroller.adapters.ecommerce.BankListAdapter r0 = r8.adapter
            if (r0 == 0) goto L_0x0057
            com.mobiroller.models.ecommerce.BankAccount r0 = r0.getSelectedBank()
            if (r0 == 0) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r1 = 0
        L_0x0058:
            r8.isValid = r1
            boolean r0 = r8.isValid
            r8.setContinueButton(r0)
            boolean r0 = r8.isValid
            if (r0 == 0) goto L_0x0124
            com.mobiroller.models.events.OrderPaymentEvent r0 = new com.mobiroller.models.events.OrderPaymentEvent
            r0.<init>()
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0076
            r0.paymentType = r2
            goto L_0x011d
        L_0x0076:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r6)
            if (r1 != 0) goto L_0x008a
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r5)
            if (r1 == 0) goto L_0x00f2
        L_0x008a:
            boolean r1 = r8.validateCreditCardFields()
            if (r1 == 0) goto L_0x00f2
            boolean r1 = r8.isOnline3D
            if (r1 == 0) goto L_0x0097
            r0.paymentType = r5
            goto L_0x009d
        L_0x0097:
            boolean r1 = r8.isOnline
            if (r1 == 0) goto L_0x009d
            r0.paymentType = r6
        L_0x009d:
            com.mobiroller.models.ecommerce.OrderCard r1 = new com.mobiroller.models.ecommerce.OrderCard
            r1.<init>()
            r0.orderCard = r1
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment$CreditCardLayout r2 = r8.creditCardLayoutView
            com.google.android.material.textfield.TextInputEditText r2 = r2.cardNameTextInputEditText
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.cardHolderName = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.String r2 = r8.mCardNumber
            r1.cardNumber = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.String r2 = r8.mCardExpireString
            r4 = 2
            java.lang.String r2 = r2.substring(r3, r4)
            r1.expireMonth = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "20"
            r2.append(r3)
            java.lang.String r3 = r8.mCardExpireString
            r5 = 4
            java.lang.String r3 = r3.substring(r4, r5)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.expireYear = r2
            com.mobiroller.models.ecommerce.OrderCard r1 = r0.orderCard
            com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment$CreditCardLayout r2 = r8.creditCardLayoutView
            com.google.android.material.textfield.TextInputEditText r2 = r2.cvvTextInputEditText
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            r1.cvc = r2
            goto L_0x011d
        L_0x00f2:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r4)
            if (r1 == 0) goto L_0x0111
            com.mobiroller.adapters.ecommerce.BankListAdapter r1 = r8.adapter
            if (r1 == 0) goto L_0x0111
            com.mobiroller.models.ecommerce.BankAccount r1 = r1.getSelectedBank()
            if (r1 == 0) goto L_0x0111
            r0.paymentType = r4
            com.mobiroller.adapters.ecommerce.BankListAdapter r1 = r8.adapter
            com.mobiroller.models.ecommerce.BankAccount r1 = r1.getSelectedBank()
            r0.bankAccount = r1
            goto L_0x011d
        L_0x0111:
            com.mobiroller.models.ecommerce.SupportedPaymentType r1 = r8.selectedPaymentType
            java.lang.String r1 = r1.paymentType
            boolean r1 = r1.equalsIgnoreCase(r7)
            if (r1 == 0) goto L_0x011d
            r0.paymentType = r7
        L_0x011d:
            org.greenrobot.eventbus.EventBus r1 = org.greenrobot.eventbus.EventBus.getDefault()
            r1.post(r0)
        L_0x0124:
            boolean r0 = r8.isValid
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment.validate():boolean");
    }

    private boolean validateCreditCardFields() {
        String str = "";
        if (this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().equalsIgnoreCase(str) || this.creditCardLayoutView.cvvTextInputEditText.getText().toString().equalsIgnoreCase(str) || this.creditCardLayoutView.cardNumberTextInputEditText.getText().toString().equalsIgnoreCase(str) || this.creditCardLayoutView.cardNameTextInputEditText.getText().toString().equalsIgnoreCase(str)) {
            return false;
        }
        this.mCardNumber = this.creditCardLayoutView.cardNumberTextInputEditText.getText().toString().replace(" ", str);
        this.mCardExpireString = this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().replace("/", str);
        boolean z = validateCreditCardExpireDate() && (validateCreditCardName() && validateCreditCardNumber());
        if (!validateCreditCardCVV() || !z) {
            return false;
        }
        return true;
    }

    private boolean validateCreditCardCVV() {
        validateAllCreditCardFields();
        if (this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().equals("")) {
            this.creditCardLayoutView.cvvTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_empty, getString(R.string.e_commerce_payment_credit_card_security_code)));
            return false;
        } else if (this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().length() == 3 || this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().length() == 4) {
            if (this.creditCardLayoutView.cvvTextInputLayout.getError() != null) {
                this.creditCardLayoutView.cvvTextInputLayout.setError(null);
            }
            return true;
        } else {
            this.creditCardLayoutView.cvvTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_invalid, getString(R.string.e_commerce_payment_credit_card_security_code)));
            return false;
        }
    }

    private boolean validateCreditCardExpireDate() {
        validateAllCreditCardFields();
        if (this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().length() == 0) {
            this.creditCardLayoutView.expirationDateTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_empty, getString(R.string.e_commerce_payment_credit_card_expire_date)));
            return false;
        } else if (this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().length() != 5) {
            this.creditCardLayoutView.expirationDateTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_invalid, getString(R.string.e_commerce_payment_credit_card_expire_date)));
            return false;
        } else {
            if (this.creditCardLayoutView.expirationDateTextInputLayout.getError() != null) {
                this.creditCardLayoutView.expirationDateTextInputLayout.setError(null);
            }
            return true;
        }
    }

    private boolean validateCreditCardNumber() {
        validateAllCreditCardFields();
        String str = "";
        this.mCardNumber = this.creditCardLayoutView.cardNumberTextInputEditText.getText().toString().replace(" ", str);
        if (this.mCardNumber.equals(str)) {
            this.creditCardLayoutView.cardNumberTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_empty, getString(R.string.e_commerce_payment_credit_card_number)));
            return false;
        } else if (this.mCardNumber.length() != 16) {
            this.creditCardLayoutView.cardNumberTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_invalid, getString(R.string.e_commerce_payment_credit_card_number)));
            return false;
        } else if (!ECommerceUtil.validateCreditCardNumber(this.mCardNumber)) {
            this.creditCardLayoutView.cardNumberTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_invalid, getString(R.string.e_commerce_payment_credit_card_number)));
            return false;
        } else {
            if (this.creditCardLayoutView.cardNumberTextInputLayout.getError() != null) {
                this.creditCardLayoutView.cardNumberTextInputLayout.setError(null);
            }
            return true;
        }
    }

    private void clearCreditCardErrors() {
        CreditCardLayout creditCardLayout2 = this.creditCardLayoutView;
        if (creditCardLayout2 != null && creditCardLayout2.cardNameTextInputEditText != null && this.creditCardLayoutView.cardNameTextInputEditText.getVisibility() == 0) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (OrderChoosePaymentFragment.this.creditCardLayoutView.cardNumberTextInputLayout.getError() != null) {
                        OrderChoosePaymentFragment.this.creditCardLayoutView.cardNumberTextInputLayout.setError(null);
                    }
                    if (OrderChoosePaymentFragment.this.creditCardLayoutView.expirationDateTextInputLayout.getError() != null) {
                        OrderChoosePaymentFragment.this.creditCardLayoutView.expirationDateTextInputLayout.setError(null);
                    }
                    if (OrderChoosePaymentFragment.this.creditCardLayoutView.cvvTextInputLayout.getError() != null) {
                        OrderChoosePaymentFragment.this.creditCardLayoutView.cvvTextInputLayout.setError(null);
                    }
                    if (OrderChoosePaymentFragment.this.creditCardLayoutView.cardNameTextInputLayout.getError() != null) {
                        OrderChoosePaymentFragment.this.creditCardLayoutView.cardNameTextInputLayout.setError(null);
                    }
                    String str = "";
                    OrderChoosePaymentFragment.this.creditCardLayoutView.cardNameTextInputEditText.setText(str);
                    OrderChoosePaymentFragment.this.creditCardLayoutView.expirationDateTextInputEditText.setText(str);
                    OrderChoosePaymentFragment.this.creditCardLayoutView.cardNumberTextInputEditText.setText(str);
                    OrderChoosePaymentFragment.this.creditCardLayoutView.cvvTextInputEditText.setText(str);
                }
            });
        }
    }

    private boolean validateCreditCardName() {
        validateAllCreditCardFields();
        if (this.creditCardLayoutView.cardNameTextInputEditText.getText().toString().equals("")) {
            this.creditCardLayoutView.cardNameTextInputLayout.setError(getString(R.string.e_commerce_payment_credit_card_validation_empty, getString(R.string.e_commerce_payment_credit_card_name)));
            return false;
        }
        if (this.creditCardLayoutView.cardNameTextInputLayout.getError() != null) {
            this.creditCardLayoutView.cardNameTextInputLayout.setError(null);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void validateAllCreditCardFields() {
        if (this.creditCardLayoutView.cardNameTextInputEditText != null && this.creditCardLayout.getVisibility() != 8) {
            String str = "";
            boolean z = !this.creditCardLayoutView.cardNameTextInputEditText.getText().toString().equals(str);
            boolean z2 = false;
            if (z) {
                this.mCardNumber = this.creditCardLayoutView.cardNumberTextInputEditText.getText().toString().replace(" ", str);
                if (this.mCardNumber.equals(str) || this.mCardNumber.length() != 16 || !ECommerceUtil.validateCreditCardNumber(this.mCardNumber)) {
                    z = false;
                }
            }
            if (z && (this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().length() == 0 || this.creditCardLayoutView.expirationDateTextInputEditText.getText().toString().length() != 5)) {
                z = false;
            }
            if (!z || (!this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().equals(str) && (this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().length() == 3 || this.creditCardLayoutView.cvvTextInputEditText.getText().toString().trim().length() == 4))) {
                z2 = z;
            }
            if (this.isValid != z2) {
                this.isValid = z2;
                setContinueButton(this.isValid);
                EventBus.getDefault().post(new ValidateStepEvent(this.currentStep, z2));
            }
        }
    }

    public void onFocusChange(View view, boolean z) {
        if (!isRemoving() && !isHidden() && !isDetached() && !z) {
            if (view.getId() == R.id.cvvTextInputEditText) {
                validateCreditCardCVV();
            } else if (view.getId() == R.id.cardNumberTextInputEditText) {
                validateCreditCardNumber();
            } else if (view.getId() == R.id.cardNameTextInputEditText) {
                validateCreditCardName();
            } else if (view.getId() == R.id.expirationDateTextInputEditText) {
                validateCreditCardExpireDate();
            }
        }
    }

    public void onSelect(int i) {
        String str;
        if (this.paymentSettings.supportedPaymentTypes != null) {
            this.selection = i;
            this.selectedPaymentType = (SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i);
            if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
                this.transferLayout.setVisibility(0);
                this.creditCardLayout.setVisibility(8);
                this.payAtDoorLayout.setVisibility(8);
                this.payPalLayout.setVisibility(8);
                str = getString(R.string.e_commerce_payment_method_selection_transfer);
            } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE) || ((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
                this.transferLayout.setVisibility(8);
                this.creditCardLayout.setVisibility(0);
                if (this.creditCardLayoutView == null) {
                    this.creditCardLayoutView = new CreditCardLayout(this.creditCardViewStub.inflate());
                } else {
                    clearCreditCardErrors();
                }
                this.payAtDoorLayout.setVisibility(8);
                this.payPalLayout.setVisibility(8);
                str = getString(R.string.e_commerce_payment_method_selection_credit_card);
            } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.PAY_AT_DOOR)) {
                this.transferLayout.setVisibility(8);
                this.creditCardLayout.setVisibility(8);
                this.payPalLayout.setVisibility(8);
                this.payAtDoorLayout.setVisibility(0);
                str = getString(R.string.e_commerce_payment_method_selection_pay_at_door);
            } else if (((SupportedPaymentType) this.paymentSettings.supportedPaymentTypes.get(i)).paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
                this.transferLayout.setVisibility(8);
                this.creditCardLayout.setVisibility(8);
                this.payAtDoorLayout.setVisibility(8);
                this.payPalLayout.setVisibility(0);
                str = getString(R.string.e_commerce_payment_method_selection_paypal);
            } else {
                str = "";
            }
            this.paymentTextView.setText(str);
            this.paymentMethodTextView.setText(str);
        }
        checkIsValid();
    }

    @OnClick({2131362202})
    public void onViewClicked() {
        EventBus.getDefault().post(new PaymentContinueEvent());
    }

    private void setContinueButton(boolean z) {
        FloatingActionButton floatingActionButton = this.continueButton;
        if (floatingActionButton != null) {
            floatingActionButton.setEnabled(z);
        }
    }

    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
