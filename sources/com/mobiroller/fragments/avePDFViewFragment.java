package com.mobiroller.fragments;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.pdf.PdfRenderer;
import android.graphics.pdf.PdfRenderer.Page;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VerticalViewPager;
import java.io.File;
import java.io.IOException;

public class avePDFViewFragment extends BaseModuleFragment {
    @BindView(2131362016)
    public ConstraintLayout bottomLayout;
    @BindView(2131362572)
    public ConstraintLayout innerLayout;
    /* access modifiers changed from: private */
    public Page mCurrentPage;
    /* access modifiers changed from: private */
    public int mCurrentPageNumber;
    private PDFLoader mPdfLoader;
    /* access modifiers changed from: private */
    public PdfRenderer mPdfRenderer;
    /* access modifiers changed from: private */
    public int mTotalPageCountNumber;
    /* access modifiers changed from: private */
    public String mURL;
    @BindView(2131362649)
    public RelativeLayout mainLayout;
    @BindView(2131362796)
    public ImageView next;
    @BindView(2131362879)
    public TextView pageCount;
    @BindView(2131362982)
    public ImageView previus;
    @BindView(2131362566)
    public ProgressBar progressBar;
    @BindView(2131363359)
    public VerticalViewPager verticalViewPager;

    private class ImagePagerAdapter extends PagerAdapter {
        LayoutInflater inflater;
        private int size;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        ImagePagerAdapter(int i) {
            this.inflater = (LayoutInflater) avePDFViewFragment.this.getActivity().getSystemService("layout_inflater");
            this.size = i;
        }

        public int getCount() {
            return this.size;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = this.inflater.inflate(R.layout.layout_pdf_view_item, viewGroup, false);
            PhotoView photoView = (PhotoView) inflate.findViewById(R.id.image_view);
            ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.progress_bar);
            if (VERSION.SDK_INT >= 21) {
                avePDFViewFragment.this.bottomLayout.setVisibility(0);
                avePDFViewFragment.this.mainLayout.setBackgroundColor(-1);
                new PDFPageLoader(progressBar, photoView, i).execute(new Void[0]);
            }
            viewGroup.addView(inflate, -1, -1);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            avePDFViewFragment.this.setHasOptionsMenu(true);
            avePDFViewFragment.this.pageCount.setVisibility(0);
            avePDFViewFragment.this.mCurrentPageNumber = i;
            TextView textView = avePDFViewFragment.this.pageCount;
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1);
            sb.append(" / ");
            sb.append(avePDFViewFragment.this.mTotalPageCountNumber);
            textView.setText(sb.toString());
        }
    }

    private class PDFLoader extends AsyncTask<String, Integer, File> {
        File file;

        private PDFLoader() {
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x006a, code lost:
            if (r14 == null) goto L_0x00ac;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0092, code lost:
            if (r14 == null) goto L_0x00ac;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0094, code lost:
            r14.disconnect();
         */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x0087 A[SYNTHETIC, Splitter:B:40:0x0087] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x008f A[Catch:{ IOException -> 0x008b }] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x009b A[SYNTHETIC, Splitter:B:50:0x009b] */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x00a3 A[Catch:{ IOException -> 0x009f }] */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x00a8  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.io.File doInBackground(java.lang.String... r14) {
            /*
                r13 = this;
                r0 = 0
                r1 = r14[r0]
                java.io.File r1 = com.mobiroller.util.ImageManager.getFileFromUrl(r1)
                r13.file = r1
                java.io.File r1 = r13.file
                boolean r1 = r1.isFile()
                if (r1 != 0) goto L_0x00ac
                r1 = r14[r0]
                java.io.File r2 = r13.file
                com.mobiroller.fragments.avePDFViewFragment.downloadFile(r1, r2)
                r1 = 0
                java.net.URL r2 = new java.net.URL     // Catch:{ Exception -> 0x007f, all -> 0x007b }
                r14 = r14[r0]     // Catch:{ Exception -> 0x007f, all -> 0x007b }
                r2.<init>(r14)     // Catch:{ Exception -> 0x007f, all -> 0x007b }
                java.net.URLConnection r14 = r2.openConnection()     // Catch:{ Exception -> 0x007f, all -> 0x007b }
                java.net.HttpURLConnection r14 = (java.net.HttpURLConnection) r14     // Catch:{ Exception -> 0x007f, all -> 0x007b }
                r14.connect()     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                int r2 = r14.getContentLength()     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                java.io.InputStream r3 = r14.getInputStream()     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0073 }
                java.io.File r5 = r13.file     // Catch:{ Exception -> 0x0073 }
                r4.<init>(r5)     // Catch:{ Exception -> 0x0073 }
                r1 = 4096(0x1000, float:5.74E-42)
                byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                r5 = 0
            L_0x003e:
                int r7 = r3.read(r1)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                r8 = -1
                if (r7 == r8) goto L_0x0060
                long r8 = (long) r7     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                long r5 = r5 + r8
                if (r2 <= 0) goto L_0x005c
                r8 = 1
                java.lang.Integer[] r8 = new java.lang.Integer[r8]     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                r9 = 100
                long r9 = r9 * r5
                long r11 = (long) r2     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                long r9 = r9 / r11
                int r10 = (int) r9     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                java.lang.Integer r9 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                r8[r0] = r9     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                r13.publishProgress(r8)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
            L_0x005c:
                r4.write(r1, r0, r7)     // Catch:{ Exception -> 0x0070, all -> 0x006d }
                goto L_0x003e
            L_0x0060:
                r4.close()     // Catch:{ IOException -> 0x0069 }
                if (r3 == 0) goto L_0x006a
                r3.close()     // Catch:{ IOException -> 0x0069 }
                goto L_0x006a
            L_0x0069:
            L_0x006a:
                if (r14 == 0) goto L_0x00ac
                goto L_0x0094
            L_0x006d:
                r0 = move-exception
                r1 = r4
                goto L_0x0099
            L_0x0070:
                r0 = move-exception
                r1 = r4
                goto L_0x0082
            L_0x0073:
                r0 = move-exception
                goto L_0x0082
            L_0x0075:
                r0 = move-exception
                r3 = r1
                goto L_0x0099
            L_0x0078:
                r0 = move-exception
                r3 = r1
                goto L_0x0082
            L_0x007b:
                r0 = move-exception
                r14 = r1
                r3 = r14
                goto L_0x0099
            L_0x007f:
                r0 = move-exception
                r14 = r1
                r3 = r14
            L_0x0082:
                r0.printStackTrace()     // Catch:{ all -> 0x0098 }
                if (r1 == 0) goto L_0x008d
                r1.close()     // Catch:{ IOException -> 0x008b }
                goto L_0x008d
            L_0x008b:
                goto L_0x0092
            L_0x008d:
                if (r3 == 0) goto L_0x0092
                r3.close()     // Catch:{ IOException -> 0x008b }
            L_0x0092:
                if (r14 == 0) goto L_0x00ac
            L_0x0094:
                r14.disconnect()
                goto L_0x00ac
            L_0x0098:
                r0 = move-exception
            L_0x0099:
                if (r1 == 0) goto L_0x00a1
                r1.close()     // Catch:{ IOException -> 0x009f }
                goto L_0x00a1
            L_0x009f:
                goto L_0x00a6
            L_0x00a1:
                if (r3 == 0) goto L_0x00a6
                r3.close()     // Catch:{ IOException -> 0x009f }
            L_0x00a6:
                if (r14 == 0) goto L_0x00ab
                r14.disconnect()
            L_0x00ab:
                throw r0
            L_0x00ac:
                java.io.File r14 = r13.file
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.avePDFViewFragment.PDFLoader.doInBackground(java.lang.String[]):java.io.File");
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
            avePDFViewFragment.this.progressBar.setMax(100);
            avePDFViewFragment.this.progressBar.setProgress(numArr[0].intValue());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(File file2) {
            avePDFViewFragment.this.progressBar.setVisibility(8);
            if (file2 != null) {
                try {
                    avePDFViewFragment.this.openRenderer(file2);
                    avePDFViewFragment.this.verticalViewPager.setAdapter(new ImagePagerAdapter(avePDFViewFragment.this.mTotalPageCountNumber));
                } catch (IOException e) {
                    Toast.makeText(avePDFViewFragment.this.getActivity(), R.string.common_error, 0).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(avePDFViewFragment.this.getActivity(), R.string.common_error, 0).show();
            }
        }
    }

    private class PDFPageLoader extends AsyncTask<Void, Integer, Bitmap> {
        ImageView imageView;
        int position;
        ProgressBar progressBar;

        PDFPageLoader(ProgressBar progressBar2, ImageView imageView2, int i) {
            this.progressBar = progressBar2;
            this.imageView = imageView2;
            this.position = i;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            if (VERSION.SDK_INT >= 21) {
                try {
                    if (avePDFViewFragment.this.mCurrentPage != null) {
                        avePDFViewFragment.this.mCurrentPage.close();
                    }
                    avePDFViewFragment.this.mCurrentPage = avePDFViewFragment.this.mPdfRenderer.openPage(this.position);
                    Bitmap createBitmap = Bitmap.createBitmap((avePDFViewFragment.this.getResources().getDisplayMetrics().densityDpi * avePDFViewFragment.this.mCurrentPage.getWidth()) / 216, (avePDFViewFragment.this.getResources().getDisplayMetrics().densityDpi * avePDFViewFragment.this.mCurrentPage.getHeight()) / 216, Config.ARGB_8888);
                    avePDFViewFragment.this.mCurrentPage.render(createBitmap, null, null, 1);
                    return createBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                this.imageView.setVisibility(0);
                Glide.with(MobiRollerApplication.context).load(bitmap).into(this.imageView);
                this.progressBar.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void downloadFile(String str, File file) {
    }

    static /* synthetic */ int access$504(avePDFViewFragment avepdfviewfragment) {
        int i = avepdfviewfragment.mCurrentPageNumber + 1;
        avepdfviewfragment.mCurrentPageNumber = i;
        return i;
    }

    static /* synthetic */ int access$506(avePDFViewFragment avepdfviewfragment) {
        int i = avepdfviewfragment.mCurrentPageNumber - 1;
        avepdfviewfragment.mCurrentPageNumber = i;
        return i;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.pdf_layout, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        setHasOptionsMenu(false);
        loadUI();
        return inflate;
    }

    private void loadUI() {
        String str;
        String str2 = "pdf_url";
        if (getArguments().containsKey(str2)) {
            this.mURL = getArguments().getString(str2);
            loadPDF();
            return;
        }
        if (this.screenModel.getLocalizedURL() == null) {
            str = this.screenModel.getURL();
        } else {
            str = this.localizationHelper.getLocalizedTitle(this.screenModel.getLocalizedURL());
            if (str == null || str.isEmpty()) {
                str = this.screenModel.getURL();
            }
        }
        if (str != null) {
            this.mURL = str;
            loadPDF();
            return;
        }
        Toast.makeText(getActivity(), R.string.common_error, 0).show();
    }

    private void loadPDF() {
        if (VERSION.SDK_INT >= 21) {
            this.mPdfLoader = new PDFLoader();
            this.mPdfLoader.execute(new String[]{this.mURL});
            return;
        }
        this.progressBar.setVisibility(8);
        this.mainLayout.setBackgroundColor(-1);
        new Builder(getActivity()).content((int) R.string.pdf_version_message).positiveText((int) R.string.OK).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                avePDFViewFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(avePDFViewFragment.this.mURL)));
            }
        }).show();
    }

    /* access modifiers changed from: private */
    public void openRenderer(File file) throws IOException {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
        if (open != null) {
            this.mPdfRenderer = new PdfRenderer(open);
            this.mTotalPageCountNumber = this.mPdfRenderer.getPageCount();
        }
    }

    public void onStop() {
        super.onStop();
        PDFLoader pDFLoader = this.mPdfLoader;
        if (pDFLoader != null) {
            pDFLoader.cancel(true);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.pdf_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_download) {
            checkFilePermission();
        } else {
            super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    private void downloadFile() {
        Request request = new Request(Uri.parse(this.mURL));
        if (!(this.screenModel == null || this.screenModel.getTitle() == null)) {
            request.setTitle(UtilManager.localizationHelper().getLocalizedTitle(this.screenModel.getTitle()));
        }
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        ((DownloadManager) getActivity().getSystemService("download")).enqueue(request);
    }

    private boolean checkFilePermission() {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.WRITE_EXTERNAL_STORAGE";
            if (ContextCompat.checkSelfPermission(getActivity(), str) != 0) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), str)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{str}, 1);
                } else {
                    Toast.makeText(getActivity(), R.string.permission_storage_denied_download_never, 0).show();
                }
                return false;
            }
        }
        downloadFile();
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            downloadFile();
        } else {
            Toast.makeText(getActivity(), R.string.permission_storage_denied_download, 0).show();
        }
    }

    @OnClick({2131362796})
    public void onClickNext() {
        if (this.mTotalPageCountNumber > this.mCurrentPageNumber + 1) {
            this.verticalViewPager.post(new Runnable() {
                public void run() {
                    avePDFViewFragment.this.verticalViewPager.setCurrentItem(avePDFViewFragment.access$504(avePDFViewFragment.this));
                }
            });
        }
    }

    @OnClick({2131362982})
    public void onClickPrevius() {
        if (this.mCurrentPageNumber > 0) {
            this.verticalViewPager.post(new Runnable() {
                public void run() {
                    avePDFViewFragment.this.verticalViewPager.setCurrentItem(avePDFViewFragment.access$506(avePDFViewFragment.this));
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.innerLayout);
        }
    }
}
