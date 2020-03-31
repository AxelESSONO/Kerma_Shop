package com.obiangetfils.kermashop.fragments.childFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.customcompo.CustomEditText;
import com.obiangetfils.kermashop.fragments.childFragments.Checkout;


public class Shipping_Address extends Fragment {

    View rootView;
    String customerID, GetArgument;

    String[] zoneNames;
    String[] countryNames;

    ArrayAdapter<String> zoneAdapter;
    ArrayAdapter<String> countryAdapter;

    Button proceed_checkout_btn;
    CustomEditText input_firstname, input_lastname, input_address_1, input_address_2, input_company;
    CustomEditText input_country, input_zone, input_city, input_postcode, input_email, input_contact;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.childfragment_shippingaddress, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.shipping_address));
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        input_firstname = rootView.findViewById(R.id.firstname);
        input_lastname = rootView.findViewById(R.id.lastname);
        input_address_1 = rootView.findViewById(R.id.address_1);
        input_address_2 = rootView.findViewById(R.id.address_2);
        input_company = rootView.findViewById(R.id.company);
        input_country = rootView.findViewById(R.id.country);
        input_zone = rootView.findViewById(R.id.zone);
        input_city = rootView.findViewById(R.id.city);
        input_postcode = rootView.findViewById(R.id.postcode);
        input_email = rootView.findViewById(R.id.email);
        input_contact = rootView.findViewById(R.id.contact);
        proceed_checkout_btn = rootView.findViewById(R.id.save_address_btn);

        input_country.setKeyListener(null);
        input_zone.setKeyListener(null);
        input_zone.setFocusableInTouchMode(false);

        input_email.setVisibility(View.GONE);
        input_contact.setVisibility(View.GONE);

        if (getArguments() != null) {
            GetArgument = getArguments().getString("shipping");
            if ("1".equalsIgnoreCase(GetArgument)) {
                proceed_checkout_btn.setText("Update");
            } else {
                proceed_checkout_btn.setText("Next");
            }
        }


        zoneNames = new String[]{"Punjab", "Sindh", "Gilgit Baltistan", "Other"};
        countryNames = new String[]{"Pakistan", "Afghanistan", "America", "Other"};

        input_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    countryAdapter.addAll(countryNames);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);

                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);

                    dialog_title.setText(getString(R.string.country));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(countryAdapter);

                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            countryAdapter.getFilter().filter(charSequence);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    final AlertDialog alertDialog = dialog.create();

                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();

                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            final String selectedItem = countryAdapter.getItem(position);
                            input_country.setText(selectedItem);

                            input_zone.setText("");
                            input_zone.setFocusableInTouchMode(true);
                            alertDialog.dismiss();
                        }
                    });
                }
                return false;
            }
        });

        input_zone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    zoneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    zoneAdapter.addAll(zoneNames);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);

                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);

                    dialog_title.setText(getString(R.string.zone));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(zoneAdapter);

                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            zoneAdapter.getFilter().filter(charSequence);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    final AlertDialog alertDialog = dialog.create();
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String selectedItem = zoneAdapter.getItem(position);
                            input_zone.setText(selectedItem);
                            alertDialog.dismiss();
                        }
                    });
                }
                return false;
            }
        });

        proceed_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Checkout();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return rootView;
    }
}

