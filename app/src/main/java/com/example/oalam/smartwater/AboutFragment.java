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
        fancyAboutPage.setDescription("Water pollution is one of the biggest fears for the green globalization. To ensure the safe supply of the drinking water the quality of drinkable water needs to be monitor in real time. \nTo tackle the issue, we present a design and development of a low-cost system for real time monitoring of the water quality in IoT (internet of things) and automate the entire system with push notification on smart phone. Our smart water Quality Solution consist of several sensors which is use in measuring physical and chemical parameters of the water in the flow. The parameters such as pH, turbidity, flow, level of the water can be measured, and response will be pushed and notify to the user in real time.\n The model developed is used for reading water quality data from the physical world through the sensors and sent to Raspberry Pie. The data is then analyzed by the Raspberry Pie and the result will be pushed on server and sent to user Android App via notification. The system also provides an alert in the form of Android App notification to a remote user, when there is a deviation of water quality parameters from the pre-defined set of standard values. \n  This system can keep a strict check on the pollution of the water resources and be able to provide an environment for safe drinking water and check the availability of water in the line as well as the quantity of water in tank. The hardware which we use in this system includes.");
        // fancyAboutPage.setAppIcon(R.drawable.cakepop); //Pass your app icon image
        //fancyAboutPage.setAppName("Cake Pop Icon Pack");
        //fancyAboutPage.setVersionNameAsAppSubTitle("1.2.3");

//        fancyAboutPage.setAppDescription("Cake Pop Icon Pack is an icon pack which follows Google's Material Design language.\n\n" +
//                "This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details!\n\n"+
//                "A fresh new take on Material Design iconography. Cake Pop offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        fancyAboutPage.addEmailLink("owaisalamkhi@gmail.com");     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com/");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com/");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in//");
        fancyAboutPage.addGitHubLink("https://github.com/owaisalam12");
        return view;
    }

}
