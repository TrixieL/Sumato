package team10.sumato;


import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;


public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();


            preference.setSummary(stringValue);

            return true;
        }
    };

        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.
         *
         * @see #sBindPreferenceSummaryToValueListener
         */
        private static void bindPreferenceSummaryToValue(Preference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setupActionBar();

            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
                    .commit();
        }


        public static class SettingsFragment extends PreferenceFragment {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Load the preferences from an XML resource
                addPreferencesFromResource(R.xml.pref_general);

                bindPreferenceSummaryToValue(findPreference("default_IP_key"));
                bindPreferenceSummaryToValue(findPreference("default_port_key"));

            }
        }

        /**
         * Set up the {@link android.app.ActionBar}, if the API is available.
         */
        private void setupActionBar() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                // Show the Up button in the action bar.
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        @Override
        public boolean onMenuItemSelected(int featureId, MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                if (!super.onMenuItemSelected(featureId, item)) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            }
            return super.onMenuItemSelected(featureId, item);
        }

        /**
         * This method stops fragment injection in malicious applications.
         * Make sure to deny any unknown fragments here.
         */
        protected boolean isValidFragment(String fragmentName) {
            return PreferenceFragment.class.getName().equals(fragmentName);
        }


    }

