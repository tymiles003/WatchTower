package com.hitherejoe.proximityapidemo;


import android.content.Intent;

import com.hitherejoe.proximityapidemo.android.R;
import com.hitherejoe.proximityapidemo.android.data.model.Attachment;
import com.hitherejoe.proximityapidemo.android.data.model.Beacon;
import com.hitherejoe.proximityapidemo.android.data.remote.ProximityApiService;
import com.hitherejoe.proximityapidemo.android.ui.activity.AddAttachmentActivity;
import com.hitherejoe.proximityapidemo.android.util.MockModelsUtil;
import com.hitherejoe.proximityapidemo.util.BaseTestCase;

import rx.Observable;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;


public class AddAttachmentActivityTest extends BaseTestCase<AddAttachmentActivity> {

    public AddAttachmentActivityTest() {
        super(AddAttachmentActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testActivityDisplayed() throws Exception {
        Beacon beacon = MockModelsUtil.createMockRegisteredBeacon();
        ProximityApiService.NamespacesResponse namespacesResponse = new ProximityApiService.NamespacesResponse();
        namespacesResponse.namespaces = MockModelsUtil.createMockListOfNamespaces(1);
        when(mProximityApiService.getNamespaces())
                .thenReturn(Observable.just(namespacesResponse));

        Intent i = new Intent(AddAttachmentActivity.getStartIntent(getInstrumentation().getContext(), beacon));
        setActivityIntent(i);
        getActivity();

        onView(withId(R.id.text_namespace))
                .check(matches(isDisplayed()));
        onView(withId(R.id.spinner_namespace))
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_data))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_text_data))
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_data_error_message))
                .check(matches(not(isDisplayed())));

        onData(allOf(is(instanceOf(String.class)), is(namespacesResponse.namespaces.get(0).namespaceName)))
                .check(matches(isDisplayed()));
    }

    public void testAddAttachmentInvalidInput() throws Exception {
        Beacon beacon = MockModelsUtil.createMockRegisteredBeacon();
        ProximityApiService.NamespacesResponse namespacesResponse = new ProximityApiService.NamespacesResponse();
        namespacesResponse.namespaces= MockModelsUtil.createMockListOfNamespaces(1);
        when(mProximityApiService.getNamespaces())
                .thenReturn(Observable.just(namespacesResponse));

        Intent i = new Intent(AddAttachmentActivity.getStartIntent(getInstrumentation().getContext(), beacon));
        setActivityIntent(i);
        getActivity();

        onView(withId(R.id.text_data_error_message))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.action_done))
                .perform(click());
        onView(withId(R.id.text_data_error_message))
                .check(matches(isDisplayed()));
    }

    // Test for adding an attachment are found in AttachmentsActivityTest as the activity is finished, and we
    // need to test this

}