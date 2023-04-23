package com.example.hellojapan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initially, navigate to hiragana
        navigate("Hiragana", hiraganaMaps());

        Button hiraganaBtn = findViewById(R.id.hiragana_btn);
        Button katakanaBtn = findViewById(R.id.katakana_btn);

        // switch alphabet
        hiraganaBtn.setOnClickListener(v -> {
            navigate("Hiragana", hiraganaMaps());
            changeActiveButton(katakanaBtn, hiraganaBtn);
        });
        katakanaBtn.setOnClickListener(v -> {
            navigate("Katakana", katakanaMaps());
            changeActiveButton(hiraganaBtn, katakanaBtn);
        });
    }

    private void changeActiveButton(Button nonActiveBtn, Button activeBtn) {
        nonActiveBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nonActiveBtn.setTextColor(Color.parseColor("#101010"));
        activeBtn.setBackgroundColor(Color.parseColor("#3E54AC"));
        activeBtn.setTextColor(Color.parseColor("#FFFFFF"));
    }

    private HashMap<Integer, Integer> hiraganaMaps() {
        HashMap<Integer, Integer> maps = new LinkedHashMap<>();
        maps.put(R.id.a, R.raw.a);
        maps.put(R.id.i, R.raw.i);
        maps.put(R.id.u, R.raw.u);
        maps.put(R.id.e, R.raw.e);
        maps.put(R.id.o, R.raw.o);
        maps.put(R.id.ka, R.raw.ka);
        maps.put(R.id.ki, R.raw.ki);
        maps.put(R.id.ku, R.raw.ku);
        maps.put(R.id.ke, R.raw.ke);
        maps.put(R.id.ko, R.raw.ko);
        maps.put(R.id.sa, R.raw.sa);
        maps.put(R.id.shi, R.raw.shi);
        maps.put(R.id.su, R.raw.su);
        maps.put(R.id.se, R.raw.se);
        maps.put(R.id.so, R.raw.so);
        maps.put(R.id.ta, R.raw.ta);
        maps.put(R.id.chi, R.raw.chi);
        maps.put(R.id.tsu, R.raw.tsu);
        maps.put(R.id.te, R.raw.te);
        maps.put(R.id.to, R.raw.to);
        maps.put(R.id.na, R.raw.na);
        maps.put(R.id.ni, R.raw.ni);
        maps.put(R.id.nu, R.raw.nu);
        maps.put(R.id.ne, R.raw.ne);
        maps.put(R.id.no, R.raw.no);
        maps.put(R.id.ha, R.raw.ha);
        maps.put(R.id.hi, R.raw.hi);
        maps.put(R.id.fu, R.raw.fu);
        maps.put(R.id.he, R.raw.he);
        maps.put(R.id.ho, R.raw.ho);
        maps.put(R.id.ma, R.raw.ma);
        maps.put(R.id.mi, R.raw.mi);
        maps.put(R.id.mu, R.raw.mu);
        maps.put(R.id.me, R.raw.me);
        maps.put(R.id.mo, R.raw.mo);
        maps.put(R.id.ya, R.raw.ya);
        maps.put(R.id.yu, R.raw.yu);
        maps.put(R.id.yo, R.raw.yo);
        maps.put(R.id.ra, R.raw.ra);
        maps.put(R.id.ri, R.raw.ri);
        maps.put(R.id.ru, R.raw.ru);
        maps.put(R.id.re, R.raw.re);
        maps.put(R.id.ro, R.raw.ro);
        maps.put(R.id.wa, R.raw.wa);
        maps.put(R.id.wo, R.raw.wo);
        maps.put(R.id.n, R.raw.n);

        return maps;
    }

    private HashMap<Integer, Integer> katakanaMaps() {
        HashMap<Integer, Integer> maps = new LinkedHashMap<>();
        maps.put(R.id.a1, R.raw.a);
        maps.put(R.id.i1, R.raw.i);
        maps.put(R.id.u1, R.raw.u);
        maps.put(R.id.e1, R.raw.e);
        maps.put(R.id.o1, R.raw.o);
        maps.put(R.id.ka1, R.raw.ka);
        maps.put(R.id.ki1, R.raw.ki);
        maps.put(R.id.ku1, R.raw.ku);
        maps.put(R.id.ke1, R.raw.ke);
        maps.put(R.id.ko1, R.raw.ko);
        maps.put(R.id.sa1, R.raw.sa);
        maps.put(R.id.shi1, R.raw.shi);
        maps.put(R.id.su1, R.raw.su);
        maps.put(R.id.se1, R.raw.se);
        maps.put(R.id.so1, R.raw.so);
        maps.put(R.id.ta1, R.raw.ta);
        maps.put(R.id.chi1, R.raw.chi);
        maps.put(R.id.tsu1, R.raw.tsu);
        maps.put(R.id.te1, R.raw.te);
        maps.put(R.id.to1, R.raw.to);
        maps.put(R.id.na1, R.raw.na);
        maps.put(R.id.ni1, R.raw.ni);
        maps.put(R.id.nu1, R.raw.nu);
        maps.put(R.id.ne1, R.raw.ne);
        maps.put(R.id.no1, R.raw.no);
        maps.put(R.id.ha1, R.raw.ha);
        maps.put(R.id.hi1, R.raw.hi);
        maps.put(R.id.fu1, R.raw.fu);
        maps.put(R.id.he1, R.raw.he);
        maps.put(R.id.ho1, R.raw.ho);
        maps.put(R.id.ma1, R.raw.ma);
        maps.put(R.id.mi1, R.raw.mi);
        maps.put(R.id.mu1, R.raw.mu);
        maps.put(R.id.me1, R.raw.me);
        maps.put(R.id.mo1, R.raw.mo);
        maps.put(R.id.ya1, R.raw.ya);
        maps.put(R.id.yu1, R.raw.yu);
        maps.put(R.id.yo1, R.raw.yo);
        maps.put(R.id.ra1, R.raw.ra);
        maps.put(R.id.ri1, R.raw.ri);
        maps.put(R.id.ru1, R.raw.ru);
        maps.put(R.id.re1, R.raw.re);
        maps.put(R.id.ro1, R.raw.ro);
        maps.put(R.id.wa1, R.raw.wa);
        maps.put(R.id.wo1, R.raw.wo);
        maps.put(R.id.n1, R.raw.n);

        return maps;
    }

    private void renderAndPlaySound(HashMap<Integer, Integer> maps) {
        maps.forEach((id, sound) -> {
            ImageButton imageButton = findViewById(id);
            imageButton.setOnClickListener(v -> {
                MediaPlayer mediaPlayer = MediaPlayer.create(this, sound);
                mediaPlayer.start();
            });
        });
    }

    private void navigate(String nameAlphabet, HashMap<Integer, Integer> maps)  {
        ScrollView katakanaScrollView = findViewById(R.id.katakana_scroll_view);
        ScrollView hiraganaScrollView = findViewById(R.id.hiragana_scroll_view);

        // animation
        if (nameAlphabet.equals("Katakana")) {
            hiraganaScrollView.setVisibility(View.GONE);
            hiraganaScrollView.setAlpha(0.0f);
            katakanaScrollView.setVisibility(View.VISIBLE);
            katakanaScrollView
                    .animate()
                    .alpha(1.0f)
                    .setDuration(1000);
        } else if (nameAlphabet.equals("Hiragana")) {
            katakanaScrollView.setVisibility(View.GONE);
            katakanaScrollView.setAlpha(0.0f);
            hiraganaScrollView.setVisibility(View.VISIBLE);
            hiraganaScrollView
                    .animate()
                    .alpha(1.0f)
                    .setDuration(1000);
        }

        renderAndPlaySound(maps);

        TextView title = findViewById(R.id.title);
        title.setAlpha(0.0f);
        title.setText(nameAlphabet);
        title
            .animate()
            .alpha(1.0f)
            .setDuration(1000);
    }
}