
package com.lifemanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class FunctionTester {

    public static void main(String args[]) {

        ArrayList<Date> al = new ArrayList<Date>();
        al.add(new Date(System.currentTimeMillis() - 10000));
        al.add(new Date(System.currentTimeMillis() - 50000));
        al.add(new Date(System.currentTimeMillis() + 20000));
        al.add(new Date(System.currentTimeMillis() + 90000));
        al.add(new Date(System.currentTimeMillis() - 70000));
        al.add(new Date(System.currentTimeMillis() + 50000));
        Collections.sort(al, new TimeComparator());
        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i).toString());
        }
    }

    private static class TimeComparator implements Comparator<Date> {
        @Override
        public int compare(Date lhs, Date rhs) {
            return (int) (lhs.getTime() - rhs.getTime());
        }
    }

}
