package tnf.back.logic;

import tnf.back.db.entityes.MapPoint;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    public static List<String> MapPointToYMAPString(List<MapPoint> points){
        ArrayList<String> list = new ArrayList<>();
        for (var p : points)
            list.add(MapPointToYMAPString(p));
        return list;
    }
    public static String MapPointToYMAPString(MapPoint point){
        ArrayList<String> strings = new ArrayList<>();

        if (!Checker.isEmptyString(point.getLatitude()))
            strings.add(point.getLatitude());
        if (!Checker.isEmptyString(point.getLongitude()))
            strings.add(point.getLongitude());
        if (!Checker.isEmptyString(point.getTextRepresent()))
            strings.add(point.getTextRepresent());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            result.append(strings.get(i));
            if (i != strings.size() - 1)
                result.append("|");
        }
        return result.toString();
    }
}
