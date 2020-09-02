package com.obiangetfils.kermashop.Sellers.ui.notifications;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.obiangetfils.kermashop.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment {



    private Button.OnClickListener mOnDisplayNotificationButtonClick = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setContentTitle("BottomNavigationView")
                            .setContentText("Example Notification");
            NotificationManager mNotifyMgr =
                    (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(1, builder.build());

        }
    };

    public NotificationsFragment() {
        // Required empty public constructor
    }





    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        Button displayNotificationButton = (Button) root.findViewById(R.id.display_notification);
        displayNotificationButton.setOnClickListener(mOnDisplayNotificationButtonClick);
        return root;


    }
}