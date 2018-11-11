package io.left.meshim.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import io.left.meshim.R;
import io.left.meshim.models.User;

/**
 * User avatar selection interface.
 */
public class ChooseAvatarActivity extends AppCompatActivity {
    public static final String ONBOARDING_ACTION = "from onboarding";

    //used for the table layout
    private static final int ROWS = 9;
    private static final int COLUMNS = 3;

    private Button mSaveButton;
    private ImageButton mSelectedAvatar;
    private int mUserAvatarId = R.mipmap.account_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);

        // Populate list of avatars.
        setupAvatars();

        // Save reference to buttons for use when avatars are selected
        mSaveButton = findViewById(R.id.saveUserAvatarButton);
        mSelectedAvatar = findViewById(R.id.choose_avatar_selected_avatar);
    }

    /**
     * Update user and launch next activity when save button is tapped.
     * @param view save button
     */
    public void saveAvatar(View view) {
        User user = User.fromDisk(this);
        if (user == null) {
            // Initialize if not found, so we can still save the user's selection.
            user = new User();
        }
        user.setAvatar(mUserAvatarId);
        user.save(this);

        // Launch app if called from onboarding activity.
        String action = getIntent().getAction();
        if (action != null && action.equals(ONBOARDING_ACTION)) {
            Intent intent = new Intent(ChooseAvatarActivity.this, MainActivity.class);
            startActivity(intent);
        }

        finish();
    }

    /**
     * The function finds the scrollview in the layout and creates a dynamic
     * table layout of avatars user can choose from.
     */
    private void setupAvatars() {
        //keep track of the avatars
        int avatarNum = 1;

        ScrollView scrollView = findViewById(R.id.avatarScrollView);
        TableLayout tableLayout = new TableLayout(this);

        // Populate table.
        for (int r = 0; r < ROWS; r++) {
            // Define a new row.
            TableRow tableRow = (TableRow) getLayoutInflater()
                    .inflate(R.layout.table_row_choose_avatar, tableLayout, false);

            // Populate row with images.
            for (int c = 0; c < COLUMNS; c++) {
                final ImageButton imageButton = (ImageButton) tableRow.getChildAt(c);
                final int id = getResources().getIdentifier("avatar" + avatarNum,
                        "mipmap", getPackageName());
                imageButton.setImageResource(id);

                imageButton.setOnClickListener(v -> {
                    mUserAvatarId = id;
                    mSelectedAvatar.setImageResource(mUserAvatarId);
                    mSaveButton.setClickable(true);
                });
                avatarNum++;
            }
            tableLayout.addView(tableRow);
        }
        scrollView.addView(tableLayout);
    }
}