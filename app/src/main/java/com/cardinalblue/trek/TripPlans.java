package com.cardinalblue.trek;

import android.content.Context;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class TripPlans {
    public List<Folder> mData = new ArrayList<>();

    protected static WeakHashMap<Context, TripPlans> sPool = new WeakHashMap<>();

    public TripPlans(Context ctx) {
        if (!sPool.containsKey(ctx)) {
            sPool.put(ctx, this);
        }

        // FIXME: testing data.
        Spot spot1 = new Spot(48.8529834, 2.3458981, "聖母院", "http://rainieis.tw/blog/post/30684958-%5B%E6%B3%95%E5%9C%8B%5D-%E5%B7%B4%E9%BB%8E-paris-@-%E8%81%96%E6%AF%8D%E9%99%A2-cath%C3%A9drale-notre-dame-de-");
        Spot spot2 = new Spot(48.8048649, 2.1181667, "凡爾賽宮", "http://jay7134.pixnet.net/blog/post/160355318-%EF%BC%88%E5%B7%B4%E9%BB%8E%E9%81%8A%E8%A8%98%EF%BC%89%E7%B5%A2%E7%88%9B%E6%B3%95%E5%9C%8B%E7%9A%87%E5%AE%A4-%E5%87%A1%E7%88%BE%E8%B3%BD%E5%AE%AE%EF%BC%8D%E5%90%AB%E4%BA%A4");
        Spot spot3 = new Spot(48.8583701, 2.2922926, "艾菲爾鐵塔", "http://yoyovilla.pixnet.net/blog/post/23598486--%E6%B3%95%E5%9C%8B%E5%B7%B4%E9%BB%8E-%E8%89%BE%E8%8F%B2%E7%88%BE%E9%90%B5%E5%A1%94-tour-eiffel,-paris,-france");
        Spot spot4 = new Spot(43.9477452, 4.7965594, "亞維儂", "http://shishashabi.pixnet.net/blog/post/72468347-%E6%88%91%E7%9A%84%E4%B8%80%E5%80%8B%E4%BA%BA%E5%8D%97%E6%B3%95%E8%87%AA%E5%8A%A9%E6%97%85%E8%A1%8C(%E6%99%AE%E7%BE%85%E6%97%BA%E6%96%AF)");
        Spot spot5 = new Spot(43.7314751, 7.417674, "摩納哥王宮", "http://blog.angelatheangel.com.tw/2014-04-10-fragonard/");
        Spot spot6 = new Spot(43.6006786, 1.3628007, "土魯斯", "http://bluerin0726.pixnet.net/blog/post/112364749-%E6%8B%8B%E5%AE%B6%E6%A3%84%E5%AD%90%E5%88%B0%E6%B3%95%E5%9C%8B%E5%8E%BB!(2)-%E5%8D%97%E6%B3%95%E5%9C%9F%E9%AD%AF%E6%96%AF-%E9%80%99%E8%A3%A1%E4%B9%9F%E6%9C%89lou");
        Spot spot7 = new Spot(43.2618617, 6.628764, "St Tropez", "http://blog.sttropezhouse.com/");
        Spot spot8 = new Spot(43.7859452, 6.2950245, "Gorges du Verdon", "http://www.lesgorgesduverdon.fr/en/index.html");
        Spot spot9 = new Spot(44.0288821, 4.3459933, "Uzes", "http://uk.uzes-tourisme.com/");
        Spot spot10 = new Spot(43.4960915, 4.2972831, "Saintes-Maries-de-la-Mer", "http://www.saintesmaries.com/en/");

        Folder folder = new Folder("2015 France Vocation");

//        folder.add(spot1);
//        folder.add(spot2);
//        folder.add(spot3);
        folder.add(spot4);
        folder.add(spot5);
        folder.add(spot6);
        folder.add(spot7);
        folder.add(spot8);
        folder.add(spot9);
        folder.add(spot10);

        mData.add(folder);
    }

    public static TripPlans getModel(Context ctx) {
        if (sPool.containsKey(ctx)) {
            return sPool.get(ctx);
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Folder //////////////////////////////////////////////////////////////////////////////////////

    public static class Folder {
        public final String mName;

        public List<Spot> mFolder = new ArrayList<>();

        public Folder(String name) {
            mName = name;
        }

        public void add(Spot spot) {
            mFolder.add(spot);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Folder //////////////////////////////////////////////////////////////////////////////////////

    public static class Spot {
        public String mLocation;
        public String mSourceLink;
        public LatLng mLatLng;

        public Spot(double lat, double lng, String location, String sourceLink) {
            mLatLng = new LatLng(lat, lng);
            mLocation = location;
            mSourceLink = sourceLink;
        }
    }
}
