import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.macoscope.ketchuplunch.view.login.LoginActivity;

import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SmokeTest {

    ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class, true, false);


    //@Test
    public void lunchLogin() {
        loginActivityRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        assertTrue(loginActivityRule.getActivity() instanceof LoginActivity);
    }
}
