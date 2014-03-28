package com.eccyan.bootcamp;

import java.util.Locale;

import com.nineoldandroids.view.ViewHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CardsFragment extends Fragment {

	private static final String POSITION = "position";

	private int position;

	public static CardsFragment newInstance(int position) {
		CardsFragment cardsFragment = new CardsFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(POSITION, position);
		cardsFragment.setArguments(bundle);
		return cardsFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_cards, container, false);

        TextView textView = (TextView)frameLayout.findViewById(R.id.card_text_view);
        textView.setText(String.format(Locale.JAPAN, "Cards %s", position));
        
        return frameLayout;
		
	}

}