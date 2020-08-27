package com.example.mynangosia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link s1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class s1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView picpostRecycler;
    Processed_OrderAd discpostAd;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public s1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment s1.
     */
    // TODO: Rename and change types and number of parameters
    public static s1 newInstance(String param1, String param2) {
        s1 fragment = new s1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f4, container, false);


        picpostRecycler = view.findViewById(R.id.Posts);
        my_Delivered_OrdersAd adapter2 = new my_Delivered_OrdersAd();
        picpostRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        picpostRecycler.setLayoutManager(linearLayoutManager2);
        picpostRecycler.setAdapter(adapter2);


        return view;
    }

    public interface OnFragmentInteractionListener {
    }
}