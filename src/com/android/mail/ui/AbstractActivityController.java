/*******************************************************************************
 *      Copyright (C) 2012 Google Inc.
 *      Licensed to The Android Open Source Project.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *******************************************************************************/

package com.android.mail.ui;


import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;

import com.android.mail.R;
import com.android.mail.ConversationListContext;
import com.android.mail.providers.Account;
import com.android.mail.providers.AccountCacheProvider;
import com.android.mail.providers.Folder;
import com.android.mail.providers.UIProvider;
import com.android.mail.utils.Utils;

/**
 * This is an abstract implementation of the Activity Controller. This class knows how to
 * respond to menu items, state changes, layout changes, etc.  It weaves together the views and
 * listeners, dispatching actions to the respective underlying classes.
 *
 * <p>Even though this class is abstract, it should provide default implementations for most, if
 * not all the methods in the ActivityController interface. This makes the task of the subclasses
 * easier: OnePaneActivityController and TwoPaneActivityController can be concise when the common
 * functionality is in AbstractActivityController.
 *</p>
 * <p>
 * In the Gmail codebase, this was called BaseActivityController</p>
 */
public abstract class AbstractActivityController implements ActivityController {
    private static final String SAVED_CONVERSATION = "saved-conversation";
    private static final String SAVED_CONVERSATION_POSITION = "saved-conv-pos";
    // Keys for serialization of various information in Bundles.
    private static final String SAVED_LIST_CONTEXT = "saved-list-context";
    /**
     * Are we on a tablet device or not.
     */
    public final boolean IS_TABLET_DEVICE;

    protected Account mAccount;
    protected ActionBarView mActionBarView;
    protected final RestrictedActivity mActivity;
    protected final Context mContext;
    protected ConversationListContext mConvListContext;

    protected ConversationListFragment mConversationListFragment;
    /**
     * The current mode of the application. All changes in mode are initiated by the activity
     * controller. View mode changes are propagated to classes that attach themselves as listeners
     * of view mode changes.
     */
    protected final ViewMode mViewMode;
    protected ContentResolver mResolver;

    public AbstractActivityController(MailActivity activity, ViewMode viewMode) {
        mActivity = activity;
        mViewMode = viewMode;
        mContext = activity.getApplicationContext();
        IS_TABLET_DEVICE = Utils.useTabletUI(mContext);
    }

    @Override
    public void attachConversationList(ConversationListFragment conversationList) {
        mConversationListFragment = conversationList;
    }

    @Override
    public void clearSubject() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void dispatchTouchEvent(MotionEvent ev) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void doneChangingFolders(FolderOperations folderOperations) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void enterSearchMode() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void exitSearchMode() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public String getCurrentAccount() {
        // TODO(viki): Auto-generated method stub
        return null;
    }

    @Override
    public ConversationListContext getCurrentListContext() {
        // TODO(viki): Auto-generated method stub
        return null;
    }

    @Override
    public String getHelpContext() {
        return "Mail";
    }

    @Override
    public int getMode() {
        return mViewMode.getMode();
    }

    @Override
    public String getUnshownSubject(String subject) {
        // Calculate how much of the subject is shown, and return the remaining.
        return null;
    }

    @Override
    public void handleConversationLoadError() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void handleSearchRequested() {
        // TODO(viki): Auto-generated method stub

    }

    /**
     * Initialize the action bar. This is not visible to OnePaneController and TwoPaneController so
     * they cannot override this behavior.
     */
    private void initCustomActionBarView() {
        ActionBar actionBar = mActivity.getActionBar();
        mActionBarView = (MailActionBar) LayoutInflater.from(mContext).inflate(
                R.layout.actionbar_view, null);

        if (actionBar != null && mActionBarView != null) {
            // Why have a different variable for the same thing? We should apply the same actions
            // on mActionBarView instead.
            // mSubjectDisplayer = (ConversationSubjectDisplayer) mActionBarView;
            mActionBarView.initialize(mActivity, this, mViewMode, actionBar);
            actionBar.setCustomView((LinearLayout) mActionBarView, new ActionBar.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM
                            | ActionBar.DISPLAY_SHOW_TITLE);
        }
    }

    /**
     * Returns whether the conversation list fragment is visible or not. Different layouts will have
     * their own notion on the visibility of fragments, so this method needs to be overriden.
     * @return
     */
    protected abstract boolean isConversationListVisible();

    @Override
    public boolean navigateToAccount(String account) {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public void navigateToFolder(String folderCanonicalName) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public boolean onBackPressed() {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public void onConversationListVisibilityChanged(boolean visible) {
        // TODO(viki): Auto-generated method stub

    }

    /**
     * By default, doing nothing is right. A two-pane controller will need to
     * override this.
     */
    @Override
    public void onConversationVisibilityChanged(boolean visible) {
        // Do nothing.
        return;
    }

    @Override
    public boolean onCreate(Bundle savedState) {
        // Initialize the action bar view.
        initCustomActionBarView();

        final Intent intent = mActivity.getIntent();
        // TODO(viki) Choose an account here.
        // If we cannot choose an account, we return false

        // Allow shortcut keys to function for the ActionBar and menus.
        mActivity.setDefaultKeyMode(Activity.DEFAULT_KEYS_SHORTCUT);
        mResolver = mActivity.getContentResolver();
        mViewMode.addListener(this);
        restoreState(savedState);
        return true;
    }

    @Override
    public Dialog onCreateDialog(int id, Bundle bundle) {
        // TODO(viki): Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public void onEndBulkOperation() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onFolderChanged(Folder folder, long conversationId, boolean added) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public void onPause() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO(viki): Auto-generated method stub
        return false;
    }

    @Override
    public void onResume() {
        // TODO(viki): Auto-generated method stub
        //        mBatchConversations.addObserver(this);
        if (mActionBarView != null) {
            mActionBarView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mConvListContext != null) {
            outState.putBundle(SAVED_LIST_CONTEXT, mConvListContext.toBundle());
        }
    }

    @Override
    public void onSearchRequested() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onStartBulkOperation() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onStartDragMode() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onStop() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onStopDragMode() {
        // TODO(viki): Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     *
     * Subclasses must override this to listen to mode changes from the ViewMode. Subclasses
     * <b>must</b> call the parent's onViewModeChanged since the parent will handle common state
     * changes.
     */
    @Override
    public void onViewModeChanged(int newMode) {
        // Perform any mode specific work here.
        // reset the action bar icon based on the mode. Why don't the individual controllers do
        // this themselves?

        // On conversation list mode, clean up the conversation.
        if (newMode == ViewMode.CONVERSATION_LIST) {
            // Clean up the conversation here.
        }

        // We don't want to invalidate the options menu when switching to conversation
        // mode, as it will happen when the conversation finishes loading.
        if (newMode != ViewMode.CONVERSATION) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void reloadSearch(String string) {
        // TODO(viki): Auto-generated method stub

    }

    /**
     * @param savedState
     */
    protected void restoreListContext(Bundle savedState) {
        // TODO(viki): Auto-generated method stub
        Bundle listContextBundle = savedState.getBundle(SAVED_LIST_CONTEXT);
        if (listContextBundle != null) {
            mConvListContext = ConversationListContext.forBundle(listContextBundle);
        }
    }

    /**
     * Restore the state from the previous bundle. Subclasses should call this method from the
     * parent class, since it performs important UI initialization.
     * @param savedState
     */
    protected void restoreState(Bundle savedState) {
        if (savedState != null) {
            restoreListContext(savedState);
            // Restore the list context
            restoreListContext(savedState);

            // Attach the menu handler here.

            // Restore the view mode
            mViewMode.handleRestore(savedState);
        } else {
            // Null saved state. We have to initialize the activity to a sane first state

            // Set the account. Use the first account for want of anything better.
            // TODO(viki): Use a cursor loader here to notice changes to the underlying data.
            final Cursor accountCursor = mResolver.query(AccountCacheProvider.getAccountsUri(),
                    UIProvider.ACCOUNTS_PROJECTION, null, null, null);
            if (accountCursor != null && accountCursor.moveToFirst()) {
                final int uriCol = accountCursor.getColumnIndex(
                        UIProvider.AccountColumns.FOLDER_LIST_URI);
                mAccount = new Account(accountCursor);
            }

            final Intent intent = mActivity.getIntent();
            //  TODO(viki): Show the list context from Intent
            mConvListContext = ConversationListContext.forIntent(mContext, mAccount, intent);
            // Instead of this, switch to the conversation list mode and have that do the right
            // things automatically.
            showConversationList(mConvListContext);
            mViewMode.enterConversationListMode();
        }

        // Set the correct mode based on the current context
    }

    @Override
    public void setSubject(String subject) {
        // Do something useful with the subject. This requires changing the
        // conversation view's subject text.
    }

    @Override
    public void showFolderList() {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void startActionBarStatusCursorLoader(String account) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void stopActionBarStatusCursorLoader(String account) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void toggleStar(boolean toggleOn, long conversationId, long maxMessageId) {
        // TODO(viki): Auto-generated method stub

    }

    @Override
    public void onConversationSelected(int position) {
        // TODO(viki): Auto-generated method stub

    }
}
