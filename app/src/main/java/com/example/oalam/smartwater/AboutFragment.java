package com.example.oalam.smartwater;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        FancyAboutPage fancyAboutPage = view.findViewById(R.id.fancyaboutpage);
        fancyAboutPage.setCoverTintColor(Color.BLUE);  //Optional

        fancyAboutPage.setCover(R.drawable.home); //Pass your cover image

        fancyAboutPage.setName("Smart Water Quality Solution");
        fancyAboutPage.setDescription("Google Certified Associate Android Developer | Android App, Game, Web and Software Developer.Cake Pop Icon Pack is an icon pack which follows Google's Material Design language.This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details! A fresh new take on Material Design iconography. Cake Pop offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        // fancyAboutPage.setAppIcon(R.drawable.cakepop); //Pass your app icon image
        //fancyAboutPage.setAppName("Cake Pop Icon Pack");
        //fancyAboutPage.setVersionNameAsAppSubTitle("1.2.3");

//        fancyAboutPage.setAppDescription("Cake Pop Icon Pack is an icon pack which follows Google's Material Design language.\n\n" +
//                "This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details!\n\n"+
//                "A fresh new take on Material Design iconography. Cake Pop offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        fancyAboutPage.addEmailLink("owaisalamkhi@gmail.com");     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com/iowaisalam");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com/");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in//");
        fancyAboutPage.addGitHubLink("https://github.com/owaisalam12");
        return view;
    }

}
