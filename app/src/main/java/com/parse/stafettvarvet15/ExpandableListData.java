package com.parse.stafettvarvet15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> arr = new ArrayList<String>();
        arr.add("Göteborgsvarvet, Trailvarvet, Stafettvarvet, Specialvarvet, Cityvarvet, Minivarvet och Lilla Varvet arrangeras av Göteborgs Friidrottsförbund. " +
                "Alla förbundets föreningar hjälper till och allt överskott från tävlingarna används till föreningarnas ungdomsverksamhet samt driften av Friidrottens Hus.");

        List<String> grill = new ArrayList<String>();
        grill.add("Av brandsäkerhetsskäl är det endast tillåtet att använda kolgrill. Ej gasol! Eventuella grillar skall stå på öppen yta, ej i direkt anslutning till tälten.");

        List<String> expo = new ArrayList<String>();
        expo.add("Under Göteborgsvarvetveckan (torsdag 19 maj – lördag 20 maj) anordnas mässan Göteborgsvarvet Expo. Mässan är belägen på Svenska Mässan vid Korsvägen, i centrala Göteborg. " +
                "Som löpare i Stafettvarvet går du och dina lagkamrater in gratis på mässan (mer information på ert lags startbevis).\n" +
                "\n" +
                "På mässan finns drygt 80 utställare från olika branscher med produkter relaterade till sportswear, träningsprodukter, outdoor, kost/energi, resor, teknik, fitness, massage/rehab. Mässan har över 80 000 besökare.");

        List<String> park = new ArrayList<String>();
        park.add("Bästa sättet att ta sig till loppet är att resa kollektivt. Måste du ta bilen så erbjuds parkering mot en miljöavgift, 20 kr, på Dalens grusplaner vid Slottsskogsvallen (Swish eller kontant betalning). " +
                "Parkera enligt funktionärernas anvisningar så att så många som möjligt får plats.");

        List<String> start = new ArrayList<String>();
        start.add("Vi skickar ut startbevis via e-post till lagets kontaktperson under april.\n" +
                "\n" +
                "Mot uppvisande av startbevis erhålls nummerlappar (varav sista sträckans nummerlapp har tidtagningschip), säkerhetsnålar, stafettpinne samt ett matpaket från mat.se till laget.\n" +
                "\n" +
                "Som löpare i Stafettvarvet går du och dina lagkamrater in gratis på mässan Göteborgvarvet Expo (mer information på ert lags startbevis).");

        List<String> tid = new ArrayList<String>();
        tid.add("Onsdagen den 17 maj 2017. Första startgrupp startar kl 18.00, startnummer 1–100. Kl 18.01 startnummer 101–200 osv. Det är viktigt att starta i rätt grupp. Löpare som startar i fel startgrupp diskvalificeras. Det är inte tillåtet att byta startgrupp.");

        List<String> tent = new ArrayList<String>();
        tent.add("Vid anmälan finns det möjlighet att köpa till plats i tält eller tältplats i området. Det finns ett begränsat antal tält så först till kvarn gäller.");

        expandableListDetail.put("Grillning", grill);
        expandableListDetail.put("Staffettvarvet Expo", expo);
        expandableListDetail.put("Arrangör", arr);
        expandableListDetail.put("Parkering", park);
        expandableListDetail.put("Startbevis", start);
        expandableListDetail.put("Tidtagning", tid);
        expandableListDetail.put("Tältplats", tent);

        return expandableListDetail;
    }
}