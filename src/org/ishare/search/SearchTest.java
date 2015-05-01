package org.ishare.search;

import java.util.ArrayList;
import java.util.List;

import org.ishare.config.Config;

public class SearchTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> photoPrints = new ArrayList<String>();

        String hashCode = null;

        for (int i = 1; i <= 5; i++) {
            hashCode = ImageSearch.produceFingerPrint(Config.TrainImageDirPath + i + ".jpg");
            photoPrints.add(hashCode);
        }
        System.out.println("Train: ");
        System.out.println(photoPrints);
        System.out.println();

        String testPrint = ImageSearch.produceFingerPrint(Config.TestImageDirPath + "1.jpg");
        System.out.println("Test: ");
        System.out.println(testPrint);
        System.out.println();

        List<Integer> differences = new ArrayList<Integer>();
        for (int i = 0; i < photoPrints.size(); i++) {
            int difference = ImageSearch.hammingDistance(testPrint, photoPrints.get(i));
            differences.add(difference);
            
            System.out.print("pic: " + i + ".jpg :");
            if (difference <= 5) {
                System.out.println("Match");
            } else if (difference <= 10) {
                System.out.println("Like");
            } else {
                System.out.println("Not Match");
            }
        }

        System.out.println("difference: " + differences);

    }
}
